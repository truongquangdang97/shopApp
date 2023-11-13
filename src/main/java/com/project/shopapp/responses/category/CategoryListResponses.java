package com.project.shopapp.responses.category;

import com.project.shopapp.responses.product.ProductResponses;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class CategoryListResponses {
    private int pageNumber;
    private List<CategoryResponses> categories;
    private int totalPage;
    private  int totalContent;
}
