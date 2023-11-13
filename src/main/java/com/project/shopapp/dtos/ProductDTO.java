package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class ProductDTO {

//    @NotBlank(message = "title a required")
    @NotBlank(message = "nameProduct is require")
    @Size(min = 3, max = 200,message = "Nhap lon hon 3 va nho hon 200")
    private String name;

    @Min(value = 0, message = "Gia tri nho nhat la 0")
    @Max(value = 100000000, message = "Gia tri lon nhat la 100000000")
    private Float price;

    private String thumbnail;

    private  String description;

//    @NotNull(message = "Category id is required")
    @JsonProperty("category_id")
    private Long categoryId;
//    private List<MultipartFile> files;

}
