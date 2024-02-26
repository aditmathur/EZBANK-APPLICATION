package com.ezbank.accounts.service.client;

import com.ezbank.accounts.dto.LoansDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "loans", fallback = LoansFallback.class)
public interface LoansFiegnClient {

    @GetMapping("/api/fetch")
    ResponseEntity<LoansDTO> fetchLoanDetails(@RequestHeader("ezbank-correlation-id")String correlationId, @RequestParam String mobileNumber);
}
