package com.nk.agri.store.controller;
import com.nk.agri.store.dtos.ApiResponseMsg;
import com.nk.agri.store.dtos.CreateOrderRequest;
import com.nk.agri.store.dtos.OrderDto;
import com.nk.agri.store.dtos.PageableResponse;
import com.nk.agri.store.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //create
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMsg> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        ApiResponseMsg responseMessage = ApiResponseMsg.builder()
                .status(HttpStatus.OK)
                .msg("order is removed !!")
                .success(true)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);

    }

    //get orders of the user

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}