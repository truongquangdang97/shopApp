package com.project.shopapp.service;

import java.util.List;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.OrderDetail;

public interface IOrderDetailService {
      OrderDetail createOrderDetail(OrderDetailDTO newOrderDetailDto) throws Exception;
      OrderDetail getOrderDetail(Long id)  ;
      OrderDetail updatOrderDetail(Long id, OrderDetailDTO newOrderDetailData);
      void deleteOrderDetail(Long id);
      List<OrderDetail> getOrderDetails(Long OrderId);
}
