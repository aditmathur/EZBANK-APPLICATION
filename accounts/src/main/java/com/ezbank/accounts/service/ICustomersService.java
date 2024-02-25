package com.ezbank.accounts.service;

import com.ezbank.accounts.dto.CustomerDetailsDTO;

public interface ICustomersService {

    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId);
}
