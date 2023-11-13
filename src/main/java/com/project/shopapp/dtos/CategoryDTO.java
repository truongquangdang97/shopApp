package com.project.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class CategoryDTO {
    @NotEmpty(message = "Category is name cannot be empty")
    private String name;

}
