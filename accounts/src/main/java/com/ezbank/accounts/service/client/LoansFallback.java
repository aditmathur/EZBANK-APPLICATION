package com.ezbank.accounts.service.client;

import com.ezbank.accounts.dto.LoansDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFiegnClient{

    @Override
    public ResponseEntity<LoansDTO> fetchLoanDetails(String mobileNumber) {
        return null;
    }
}
