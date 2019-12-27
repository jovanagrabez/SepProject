package com.sep.bitcoin.controller;

import com.sep.bitcoin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Timer;
import java.util.TimerTask;

@RestController
@RequestMapping(value = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderRepository orderRepository;

    private static final String SUCCESS_URL = "https://localhost:8762/koncentrator_placanja/api/transaction/success/";
    private static final String CANCEL_URL = "https://localhost:8762/koncentrator_placanja/api/transaction/cancel/";


    @PostMapping
    public String createOrder(@RequestBody CreateOrderDTO orderDTO) {

        Order order = new Order(0L, orderDTO.getHashedOrderId(), orderDTO.getPriceAmount(), orderDTO.getPriceCurrency(), orderDTO.getReceiveCurrency(),
                orderDTO.getTitle(), orderDTO.getDescription(), orderDTO.getCallbackUrl(), CANCEL_URL + orderDTO.getHashedOrderId(),
                SUCCESS_URL + orderDTO.getHashedOrderId(), orderDTO.getBitcoinToken(), "new");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.setBearerAuth(orderDTO.getBitcoinToken());

        HttpEntity<Order> requestEntity = new HttpEntity<>(order, requestHeaders);

        ResponseEntity<CreateOrderResponse> resp = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders", requestEntity, CreateOrderResponse.class);
        String url = resp.getBody().getPayment_url();

        order.setOrder_id(Long.valueOf(resp.getBody().getId()));
        Order savedOrder = this.orderRepository.save(order);
        checkOrderStatus(savedOrder);
        return url;

    }


    public void checkOrderStatus(Order order) {

        Timer timer = new Timer();
        TimerTask repeatedTask = new TimerTask() {

            public void run() {
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.add("Content-Type", "application/json");
                requestHeaders.setBearerAuth(order.getToken());

                HttpEntity<Long> requestEntity = new HttpEntity(null, requestHeaders);

                ResponseEntity<CheckOrderStatusDto> resp = restTemplate.exchange("https://api-sandbox.coingate.com/v2/orders/" + order.getOrder_id(), HttpMethod.GET, requestEntity, CheckOrderStatusDto.class);

                if (resp.getBody().getStatus().equals("paid")) {

                    ResponseEntity<String> resp1 = restTemplate.getForEntity(SUCCESS_URL + order.getHashedOrderId(), String.class);

                    timer.cancel();
                    timer.purge();

                } else if (resp.getBody().getStatus().equals("invalid") || resp.getBody().getStatus().equals("expired")
                        || resp.getBody().getStatus().equals("canceled") || resp.getBody().getStatus().equals("refunded")) {

                    ResponseEntity<String> resp2 = restTemplate.getForEntity(CANCEL_URL + order.getHashedOrderId(), String.class);

                    timer.cancel();
                    timer.purge();
                }

            }
        };

        long delay = 60000L;
        long period = 60000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

}
