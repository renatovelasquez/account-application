package dev.renvl;

import dev.renvl.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    private final CustomerMapper customerMapper;

    public AccountApplication(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public void run(String... args) {
        System.out.println(this.customerMapper.getCustomer("cliente_1"));
        System.out.println(this.customerMapper.getCustomer("cliente_2"));
    }
}
