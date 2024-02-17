package com.ezbank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to hold Customer and Account Information")
public class CustomerDTO {


    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 30, message = "The length of customer name should be between 5 to 30 characters")
    @Schema(description = "Name of the Customer", example = "Din Djarin")
    private String name;


    @NotEmpty(message = "Email can not be null or empty")
    @Email(message = "Please provide a valid email address")
    @Schema(description = "Email address of the Customer", example = "dindjarin@mandalore.com")
    private String email;


    @NotEmpty
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 digits")
    @Schema(description = "Mobile number of the Customer", example = "9234256767")
    private String mobileNumber;

    @Schema(description = "Account Details of the Customer")
    private AccountsDTO accountsDTO;
}
