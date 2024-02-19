package com.ezbank.loans;

import com.ezbank.loans.dto.LoansContactInfoDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInfoDTO.class})
@OpenAPIDefinition(info = @Info(title = "Loans microservice REST API Documentation", description = "EZBank Loans microservice REST API Documentation", version = "v1", contact = @Contact(name = "Adit Mathur", email = "aditmathur7@gmail.com"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
public class LoansServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoansServiceApplication.class, args);
    }

}
