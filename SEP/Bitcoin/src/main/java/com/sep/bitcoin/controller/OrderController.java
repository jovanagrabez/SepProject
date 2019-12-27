package com.sep.bitcoin.controller;

import com.sep.bitcoin.CreateOrderDTO;
import com.sep.bitcoin.CreateOrderResponse;
import com.sep.bitcoin.Order;
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

@RestController
@RequestMapping(value = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String SUCCESS_URL = "https://localhost:8762/koncentrator_placanja/api/transaction/success/";
    private static final String CANCEL_URL = "https://localhost:8762/koncentrator_placanja/api/transaction/cancel/";


    @PostMapping
    public String createOrder(@RequestBody CreateOrderDTO orderDTO) {

        Order order = new Order(orderDTO.getOrderId(), orderDTO.getHashedOrderId(), orderDTO.getPriceAmount(), orderDTO.getPriceCurrency(), orderDTO.getReceiveCurrency(),
                orderDTO.getTitle(), orderDTO.getDescription(), orderDTO.getCallbackUrl(), CANCEL_URL+orderDTO.getHashedOrderId(),
                SUCCESS_URL+orderDTO.getHashedOrderId(), orderDTO.getToken());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json" );
        requestHeaders.setBearerAuth(orderDTO.getBitcoinToken());

        HttpEntity<Order> requestEntity = new HttpEntity<>(order, requestHeaders);

        ResponseEntity<CreateOrderResponse> resp = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders",requestEntity, CreateOrderResponse.class);
        String url = resp.getBody().getPayment_url();
        return url;

    }

}
