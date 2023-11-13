package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class UserLoginDTO {
    @JsonProperty("phone_number")
    @NotBlank(message = "PhoneNumber is require")
    private String phoneNumber;

    @NotBlank(message = "password is require")
    private String password;
}
