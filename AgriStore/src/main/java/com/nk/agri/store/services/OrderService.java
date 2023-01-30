package com.nk.agri.store.services;

import com.nk.agri.store.dtos.CreateOrderRequest;
import com.nk.agri.store.dtos.OrderDto;
import com.nk.agri.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    //remove order
    void removeOrder(String orderId);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    //order methods(logic) related to order

}
