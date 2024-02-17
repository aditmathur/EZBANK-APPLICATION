package com.ezbank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Response", description = "Schema to hold successful response information.")
public class ResponseDTO {

    @Schema(description = "Status Code in response")
    private String statusCode;

    @Schema(description = "Status Message in response")
    private String statusMessage;
}
