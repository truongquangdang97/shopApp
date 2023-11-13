package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class ProductImageDTO {

    @JsonProperty("product_id")
    @Min(value = 1,message = "Product is Id must be >0")
    private Long productId;

    @JsonProperty( "image_url")
    @Size(min = 5, max = 200 ,message = "image is name")
    private String imageUrl;
}
