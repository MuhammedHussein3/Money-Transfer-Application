package com.transfer.account.clients.restTemplate.accountNumber;


import com.transfer.account.dto.request.AccountNumberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AccountNumberClient {

    private final RestTemplate restTemplate;

    @Value("${application.config.account-number-url-restTemplate}")
    private String ACCOUNT_NUMBER_URL;



    public String generateAccountNumberFromAccountNumberService(
            AccountNumberCreateRequest accountNumberCreateRequest
    ){

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);


        HttpEntity<AccountNumberCreateRequest> request
                = new HttpEntity<>(accountNumberCreateRequest, headers);


        ParameterizedTypeReference<String> response
                = new ParameterizedTypeReference<String>() {};


        ResponseEntity<String> responseEntity =
                restTemplate.exchange(
                        ACCOUNT_NUMBER_URL,
                        HttpMethod.POST,
                        request,
                        response
                );
        return responseEntity.getBody();

    }
}

