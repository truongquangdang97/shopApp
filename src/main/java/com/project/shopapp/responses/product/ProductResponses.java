package com.project.shopapp.responses.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Product;
import com.project.shopapp.responses.BaseResponses;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponses extends BaseResponses {

    private String name;


    private Float price;

    private String thumbnail;

    private  String description;

    @JsonProperty("category_id")
    private Long categoryId;

    public static ProductResponses fromProduct(Product product){
         ProductResponses productResponses = ProductResponses.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId()) // chú ý sao lại .getId
                .build();
                productResponses.setCreatedAt(product.getCreatedAt());
                productResponses.setUpdatedAt(product.getUpdatedAt());
                return productResponses;
    }
}
