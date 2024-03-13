package com.ezbank.accounts.service.client;

import com.ezbank.accounts.dto.CardsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping("/api/fetch")
    ResponseEntity<CardsDTO> fetchCardDetails(@RequestParam String mobileNumber);
}
