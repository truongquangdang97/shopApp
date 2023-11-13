package com.project.shopapp.responses.category;

import com.project.shopapp.responses.BaseResponses;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class CategoryResponses {
    private long id;
    private String name;
}
