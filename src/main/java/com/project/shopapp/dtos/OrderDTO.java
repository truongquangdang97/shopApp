package com.project.shopapp.dtos;

import java.sql.Date;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1,message = "userID must be >0")
    private Long userId;
    @JsonProperty("full_name")
    private String fullName;
    private String email;
    @JsonProperty("phone_number")
    @NotBlank(message = "phoneNumber is require")
    @Size(min = 4,max = 10, message = "phoneNumber must 4->10")
    private String phoneNumber;

    private String address;

    private String note;
    @JsonProperty("total_money")
    @Min(value = 0,message = "total Money must be >0")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;

    @JsonProperty("payment_method")
    private String paymentMethod;
}
