package com.sep.bitcoin.controller;

import com.sep.bitcoin.CreateOrderDTO;
import com.sep.bitcoin.CreateOrderResponse;
import com.sep.bitcoin.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping
    public String createOrder(@RequestBody CreateOrderDTO orderDTO) {

        Order order = new Order(orderDTO.getOrderId(), orderDTO.getPriceAmount(), orderDTO.getPriceCurrency(), orderDTO.getReceiveCurrency(),
                orderDTO.getTitle(), orderDTO.getDescription(), orderDTO.getCallbackUrl(), orderDTO.getCancelUrl(), orderDTO.getSuccessUrl(), orderDTO.getToken());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json" );
        requestHeaders.setBearerAuth(orderDTO.getBitcoinToken());

        HttpEntity<Order> requestEntity = new HttpEntity<>(order, requestHeaders);

        ResponseEntity<CreateOrderResponse> resp = restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders",requestEntity, CreateOrderResponse.class);
        String url = resp.getBody().getPayment_url();
        return url;

    }
}
