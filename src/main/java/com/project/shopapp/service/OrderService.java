package com.project.shopapp.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.Option;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.jmx.export.UnableToRegisterMBeanException;
import org.springframework.stereotype.Service;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.order.OrderResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

      private final UserRepository userRepository;

      private final OrderRepository orderRepository;

      private final ModelMapper modelMapper;

      @Override
      public Order createOrder(OrderDTO orderDTO) throws Exception {
                  User user = userRepository.findById(orderDTO.getUserId())
                  .orElseThrow(()->new DataNotFoundException("Can not find id user :" + orderDTO.getUserId()));

                  modelMapper.typeMap(OrderDto.class, Order.class)
                        .addMappings(mapper->mapper.skip(Order::setId));

                        Order order = new Order();
                        modelMapper.map(orderDTO, order);
                        order.setUser(user);
                        order.setOrderDate(new Date());
                        order.setStatus(OrderStatus.PENDING);

                        LocalDate shippngDate = orderDTO.getShippingDate();
                        if(shippngDate == null){
                              throw new DataNotFoundException(" Date must be at least today !!!");
                        }
                        order.setActive(true);
                        
             orderRepository.save(order);
             return order;
      }

      @Override
      public Page<OrderResponse> getAllOrder(PageRequest pageRequest) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getAllOrder'");
      }

      @Override
      public List<Order> findByUserId(Long user_id){
            return orderRepository.findByUserId(user_id);
      }

      @Override
      public Order getOrderById(Long id) {
            Order order = orderRepository.findById(id).orElse(null);
            if(order==null){
                  throw new UnsupportedOperationException("OrderId not found"); 
            }
            return order;
      }

      @Override
      public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
            
            // Order existingOrder = orderRepository.findById(id).orElseThrow(()->
            // new DataNotFoundException("Không tìm thấy order có Id tương ứng"));
            Order existingOrder = getOrderById(id);
            if(existingOrder!=null){
                  User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(()->
                        new DataNotFoundException("Không tìm thấy user tương ứng"));
                        if(existingUser==null){
                              throw new UnableToRegisterMBeanException("Khong co user tuong ung"); 
                        }
                  existingOrder.setFullName(orderDTO.getFullName());
                  existingOrder.setPhoneNumber(orderDTO.getPhoneNumber());
                  existingOrder.setEmail(orderDTO.getEmail());
            orderRepository.save(existingOrder); 

            }
            

            // modelMapper.typeMap(OrderDto.class, Order.class)
            //             .addMappings(mapper->mapper.skip(Order::setId));

            // modelMapper.map(orderDTO, existingOrder);   
            // existingOrder.setUser(existingUser);     
            return null;
      }

      @Override
      public Order deleteOrder(Long id)  {
            Order order = orderRepository.findById(id).orElse(null);
            if(order == null){
                  return null;
            }
            order.setActive(false);
                  return orderRepository.save(order);
            
            
      }
      
}
