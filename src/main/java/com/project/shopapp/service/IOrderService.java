package com.project.shopapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.responses.order.OrderResponse;

public interface IOrderService {
      Order createOrder(OrderDTO orderDTO) throws Exception;

      Page<OrderResponse> getAllOrder(PageRequest pageRequest);

      List<Order> findByUserId(Long userId);

      Order getOrderById(Long id);

      Order updateOrder(Long id , OrderDTO orderDTO) throws DataNotFoundException;

      Order deleteOrder(Long id);

      

}
