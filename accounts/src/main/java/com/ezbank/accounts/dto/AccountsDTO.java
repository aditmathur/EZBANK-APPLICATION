package com.ezbank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Accounts", description = "Schema to hold Account Information")
public class AccountsDTO {


    @NotEmpty
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be of 10 digits")
    @Schema(description = "Account Number of the EZBank Account")
    private Long accountNumber;


    @NotEmpty(message = "Account type can not be null or empty")
    @Schema(description = "Account type of the EZBank Account", example = "1234567890")
    private String accountType;

    @NotEmpty(message = "Branch address can not be null or empty")
    @Schema(description = "Branch Address of the EZBank Account", example = "Great Forge, Sundari")
    private String branchAddress;
}
