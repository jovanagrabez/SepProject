package com.sep.bitcoin.controller;

import com.sep.bitcoin.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Timer;
import java.util.TimerTask;

@Slf4j
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
                SUCCESS_URL + orderDTO.getHashedOrderId(), orderDTO.getParams().get("bitcoinToken"), "new");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.setBearerAuth(orderDTO.getParams().get("bitcoinToken"));

        HttpEntity<Order> requestEntity = new HttpEntity<>(order, requestHeaders);

        ResponseEntity<CreateOrderResponse> resp = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders", requestEntity, CreateOrderResponse.class);
        String url = resp.getBody().getPayment_url();

        order.setOrder_id(Long.valueOf(resp.getBody().getId()));
        Order savedOrder = this.orderRepository.save(order);
        checkOrderStatus(savedOrder);
        return url;

    }

    @GetMapping(value = "/status/{hashedId}")
    public ResponseEntity<String> getUpdateAboutOrderStatus(@PathVariable String hashedId) {
        Order order = orderRepository.getOrderByHashedOrderId(hashedId);
        String status = checkStatusOnBitcoinAPI(order);

        if (status.equals("paid")) {
            return ResponseEntity.ok("Paid");
        } else if (status.equals("invalid") || status.equals("expired")
                || status.equals("canceled") || status.equals("refunded")) {
            return ResponseEntity.ok("Cancelled");
        } else {
            return ResponseEntity.ok("New");
        }
    }

    public void checkOrderStatus(Order order) {

        Timer timer = new Timer();
        TimerTask repeatedTask = new TimerTask() {

            public void run() {
                String status = checkStatusOnBitcoinAPI(order);
                if (status.equals("paid")) {

                    ResponseEntity<String> resp1 = restTemplate.getForEntity(SUCCESS_URL + order.getHashedOrderId(), String.class);
                    log.info("Successful bitcoin transaction id: "+order.getOrder_id());
                    timer.cancel();
                    timer.purge();

                } else if (status.equals("invalid") || status.equals("expired")
                        || status.equals("canceled") || status.equals("refunded")) {

                    ResponseEntity<String> resp2 = restTemplate.getForEntity(CANCEL_URL + order.getHashedOrderId(), String.class);
                    log.error("Error in bitcoin transaction id: "+order.getOrder_id());

                    timer.cancel();
                    timer.purge();
                }

            }
        };

        long delay = 60000L;
        long period = 60000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    private String checkStatusOnBitcoinAPI(Order order) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.setBearerAuth(order.getToken());

        HttpEntity<Long> requestEntity = new HttpEntity(null, requestHeaders);

        ResponseEntity<CheckOrderStatusDto> resp = restTemplate.exchange("https://api-sandbox.coingate.com/v2/orders/" + order.getOrder_id(), HttpMethod.GET, requestEntity, CheckOrderStatusDto.class);

        return resp.getBody().getStatus();
    }

}
