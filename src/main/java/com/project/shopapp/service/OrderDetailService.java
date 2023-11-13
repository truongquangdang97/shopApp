package com.project.shopapp.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.OrderDetailRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderDetailService implements IOrderDetailService {

      private final OrderDetailRepository orderDetailRepository;

      private final OrderService orderService;
      private final ProductService productService;

      @Override
      public OrderDetail createOrderDetail(OrderDetailDTO newOrderDetailDto) throws Exception {
            Order existingOrder = orderService.getOrderById(newOrderDetailDto.getOrderId());
            // Product existingProduct = productService.getProductById(newOrderDetailDto.getProductId());
            Product existingProduct = productService.getProductById(newOrderDetailDto.getProductId());

            if(existingOrder == null){
                  throw new UnsupportedOperationException("Khong tim thay orderId tuong ung");
            }
            if(existingProduct == null){
                  throw new UnsupportedOperationException("Khong tim thay productId tuong ung");

            }
            OrderDetail newOrderDetail =  OrderDetail.builder()
            .product(existingProduct)
            .order(existingOrder)
            .price(newOrderDetailDto.getPrice())
            .numberOfProducts(newOrderDetailDto.getNumberOfProducts())
            .totalMoney(newOrderDetailDto.getPrice()*newOrderDetailDto.getNumberOfProducts())
            .color(newOrderDetailDto.getColor())
            .build();
            orderDetailRepository.save(newOrderDetail);
            return newOrderDetail;
      }

      @Override
      public OrderDetail getOrderDetail(Long id) {
            OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(); 
            if(orderDetail != null){
                  return orderDetail;
            }
            return null;
      }

      @Override
      public OrderDetail updatOrderDetail(Long id, OrderDetailDTO newOrderDetailData) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'updatOrderDetail'");
      }

      @Override
      public void deleteOrderDetail(Long id) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'deleteOrderDetail'");
      }

      @Override
      public List<OrderDetail> getOrderDetails(Long OrderId) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getOrderDetails'");
      }
      
}
