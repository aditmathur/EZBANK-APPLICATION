package com.ezbank.accounts.mapper;

import com.ezbank.accounts.dto.CustomerDTO;
import com.ezbank.accounts.dto.CustomerDetailsDTO;
import com.ezbank.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDTO mapToCustomerDto(Customer customer, CustomerDTO customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDTO customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    public static CustomerDetailsDTO mapToCustomerDetailsDto(Customer customer, CustomerDetailsDTO customerDetailsDTODTO) {
        customerDetailsDTODTO.setName(customer.getName());
        customerDetailsDTODTO.setEmail(customer.getEmail());
        customerDetailsDTODTO.setMobileNumber(customer.getMobileNumber());
        return customerDetailsDTODTO;
    }

}
