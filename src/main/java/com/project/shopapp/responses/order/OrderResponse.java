package com.project.shopapp.responses.order;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.BaseEntity;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderResponse extends BaseEntity {
      private Long id;

      @JsonProperty("user_id")
      private Long userId;

      private String fullName;

      private String email;

      @JsonProperty("phone_number")
      private String phoneNumber;

      private String address;

      private String note;

      @JsonProperty("order_date")
      private LocalDateTime orderDate;

      private String status;
       
      @JsonProperty("total_money")
      private Float totalMoney;

      @JsonProperty("shipping_method")
      private String shippingMethod;

      @JsonProperty("shipping_address")
      private String shippingAddress;

      @JsonProperty("payment_method")
      private String paymentMethod;

      @JsonProperty("active")
      private Boolean active;
}
