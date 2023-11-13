package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Setter
public class OrderDetailDTO {
    @Min(value = 1,message = "order_id must be > 0")
    @JsonProperty("order_id")
    private Long orderId;

    @Min(value = 1,message = "product_id must be > 0")
    @JsonProperty("product_id")
    private Long productId;

    @NotNull(message = "Price is require")
    @Min(value = 1,message = "number_of_products must be >0")
    private Float price;

    @Min(value = 1, message = "number_of_products must be >1")
    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @Min(value = 0, message = "total_money must be >")
    @JsonProperty("total_money")
    private Float totalMoney;

    private String color;
}
