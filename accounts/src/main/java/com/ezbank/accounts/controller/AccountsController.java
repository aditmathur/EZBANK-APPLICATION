package com.ezbank.accounts.controller;

import com.ezbank.accounts.constants.AccountsConstants;
import com.ezbank.accounts.dto.CustomerDTO;
import com.ezbank.accounts.dto.ErrorResponseDTO;
import com.ezbank.accounts.dto.ResponseDTO;
import com.ezbank.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(name = "CRUD REST APIs for Accounts in EZBank", description = "CRUD REST APIs for Accounts in EZBank to CREATE, UPDATE, FETCH and DELETE account details")
public class AccountsController {

    private IAccountsService accountsService;

    @Operation(summary = "Create Account REST API", description = "REST API to create new Customer & Account inside EZBank")
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {

        accountsService.createAccount(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch Account Details REST API", description = "REST API to fetch Customer & Account details based on mobile number")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 digits") String mobileNumber) {

        CustomerDTO customerDTO = accountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @Operation(summary = "Update Account & Customer Details REST API", description = "REST API to update Customer & Account details")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "417", description = "Expectation Failed"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDto) {

        boolean isUpdated = accountsService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(summary = "Delete Account & Customer Details REST API", description = "REST API to Delete Customer & Account details")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "417", description = "Expectation Failed"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")})
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 digits") String mobileNumber) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

}
