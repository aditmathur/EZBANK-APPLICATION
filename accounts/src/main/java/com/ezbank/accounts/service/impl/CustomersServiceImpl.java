package com.ezbank.accounts.service.impl;

import com.ezbank.accounts.dto.AccountsDTO;
import com.ezbank.accounts.dto.CardsDTO;
import com.ezbank.accounts.dto.CustomerDetailsDTO;
import com.ezbank.accounts.dto.LoansDTO;
import com.ezbank.accounts.entity.Accounts;
import com.ezbank.accounts.entity.Customer;
import com.ezbank.accounts.exceptions.ResourceNotFoundException;
import com.ezbank.accounts.mapper.AccountsMapper;
import com.ezbank.accounts.mapper.CustomerMapper;
import com.ezbank.accounts.repository.AccountsRepository;
import com.ezbank.accounts.repository.CustomerRepository;
import com.ezbank.accounts.service.ICustomersService;
import com.ezbank.accounts.service.client.CardsFeignClient;
import com.ezbank.accounts.service.client.LoansFiegnClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;

    private CustomerRepository customerRepository;

    private CardsFeignClient cardsFeignClient;

    private LoansFiegnClient loansFiegnClient;


    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDTO());
        customerDetailsDTO.setAccountsDTO(AccountsMapper.mapToAccountsDto(accounts, new AccountsDTO()));

        ResponseEntity<LoansDTO> loansDTOResponseEntity = loansFiegnClient.fetchLoanDetails(mobileNumber);
        if (loansDTOResponseEntity != null)
            customerDetailsDTO.setLoansDTO(loansDTOResponseEntity.getBody());

        ResponseEntity<CardsDTO> cardsDTOResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        if (cardsDTOResponseEntity != null)
            customerDetailsDTO.setCardsDTO(cardsDTOResponseEntity.getBody());

        return customerDetailsDTO;
    }
}
