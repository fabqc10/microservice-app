package com.fabdev.customer;

import com.fabdev.clients.fraud.FraudCheckResponse;
import com.fabdev.clients.fraud.FraudClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;

    public CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate, FraudClient fraudClient) {
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
        this.fraudClient = fraudClient;
    }

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo: check if email valid
        //todo: check if email not taken
        customerRepository.saveAndFlush(customer);
        //todo: check if fraudster
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("Fraudster");
        }
        //todo: send notification
    }
}
