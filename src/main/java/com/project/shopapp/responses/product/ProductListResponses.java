package com.project.shopapp.responses.product;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class ProductListResponses {
    private int pageNumber;
    private List<ProductResponses> products;
    private int totalPage;
    private  int totalContent;
}
