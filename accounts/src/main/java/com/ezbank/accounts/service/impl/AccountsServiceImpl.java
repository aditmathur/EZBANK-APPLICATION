package com.ezbank.accounts.service.impl;

import com.ezbank.accounts.constants.AccountsConstants;
import com.ezbank.accounts.dto.AccountMessageDTO;
import com.ezbank.accounts.dto.AccountsDTO;
import com.ezbank.accounts.dto.CustomerDTO;
import com.ezbank.accounts.entity.Accounts;
import com.ezbank.accounts.entity.Customer;
import com.ezbank.accounts.exceptions.CustomerAlreadyExistsException;
import com.ezbank.accounts.exceptions.ResourceNotFoundException;
import com.ezbank.accounts.mapper.AccountsMapper;
import com.ezbank.accounts.mapper.CustomerMapper;
import com.ezbank.accounts.repository.AccountsRepository;
import com.ezbank.accounts.repository.CustomerRepository;
import com.ezbank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private AccountsRepository accountsRepository;

    private CustomerRepository customerRepository;

    private final StreamBridge streamBridge;

    @Override
    public void createAccount(CustomerDTO customerDTO) {

        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Optional<Customer> optional = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (optional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with given mobile number.");
        }
        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Accounts account, Customer customer){

        AccountMessageDTO accountMessageDTO = new AccountMessageDTO(account.getAccountNumber(), customer.getName(), customer.getEmail(), customer.getMobileNumber());
        LOGGER.info("Sending Communication request for details: {}", accountMessageDTO);
        boolean result = streamBridge.send("sendCommunication-out-0", accountMessageDTO);
        LOGGER.info("Is the communication successfully processed?: {}", result);
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
        customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDto(accounts, new AccountsDTO()));
        return customerDTO;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {

        boolean isUpdated = false;
        AccountsDTO accountsDto = customerDTO.getAccountsDTO();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString()));
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));
            CustomerMapper.mapToCustomer(customerDTO, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;

    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {

        boolean isUpdated = false;
        if(accountNumber != null){
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString()));
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated = true;
        }
        return isUpdated;
    }
}
