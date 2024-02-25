package com.ezbank.accounts.controller;

import com.ezbank.accounts.dto.CustomerDetailsDTO;
import com.ezbank.accounts.dto.ErrorResponseDTO;
import com.ezbank.accounts.service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "REST APIs for Customers in EZBank", description = "REST APIs in EZBank to fetch Customer details")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ICustomersService iCustomersService;


    @Operation(summary = "Fetch Customer Details REST API", description = "REST API to fetch Customer Details based on mobile number")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDTO> fetchCustomerDetails(@RequestHeader("ezbank-correlation-id")String correlationId, @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 digits") String mobileNumber){

        logger.debug("ezbank correlation id found: {}", correlationId);
        CustomerDetailsDTO customerDetailsDTO = iCustomersService.fetchCustomerDetails(mobileNumber, correlationId);

        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDTO);
    }
}
