package com.ezbank.message.functions;

import com.ezbank.message.dto.AccountMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<AccountMessageDTO, AccountMessageDTO> generateEmail(){

        return accountMessageDTO -> {
            LOGGER.info("Sending email with details: {}", accountMessageDTO);
            return accountMessageDTO;
        };
    }

    @Bean
    public Function<AccountMessageDTO, Long> generateSMS(){

        return accountMessageDTO -> {
            LOGGER.info("Sending sms with details: {}", accountMessageDTO);
            return accountMessageDTO.accountNumber();
        };
    }
}
