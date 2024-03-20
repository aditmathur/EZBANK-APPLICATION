package com.ezbank.accounts.functions;

import com.ezbank.accounts.service.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountsFunctions {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(IAccountsService accountsService){

        return accountNumber -> {
          LOGGER.info("Updating Communication status for the account number: {}", accountNumber);
          boolean isUpdated = accountsService.updateCommunicationStatus(accountNumber);
          LOGGER.info("Is the communication status successfully updated?: {}", isUpdated);
        };
    }
}
