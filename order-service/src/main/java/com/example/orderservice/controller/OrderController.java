package com.example.orderservice.controller;


import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.ReqeustOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order-service")
public class OrderController {

    private final Environment env;
    private final OrderService orderService;



    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{user_id}/orders")
    public ResponseEntity<?> createOrder(@PathVariable("user_id") String user_id,@RequestBody ReqeustOrder reqeustOrder){

        OrderDto orderDto=new ModelMapper().map(reqeustOrder, OrderDto.class);
        orderDto.setUserId(user_id);
        OrderDto createdOrder = orderService.createOrder(orderDto);

        ResponseOrder responseOrder=new ModelMapper().map(createdOrder, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }
    @GetMapping("/{user_id}/orders")
    public ResponseEntity<?> getOrder(@PathVariable("user_id") String user_id) {

        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(user_id);

        List<ResponseOrder> result=new ArrayList<>();

        orderList.forEach(v ->{
            result.add(new ModelMapper().map(v,ResponseOrder.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }



}
