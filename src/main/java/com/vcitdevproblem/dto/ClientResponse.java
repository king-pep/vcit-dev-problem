package com.vcitdevproblem.dto;

import lombok.Data;

@Data
public class ClientResponse<T> {
    private int resultCode;
    private String resultMessageCode;
    private String resultMessage;
    private String friendlyCustomerMessage;
    private T payload;

    public ClientResponse(int resultCode, String resultMessageCode, String resultMessage, String friendlyCustomerMessage, T payload) {
        this.resultCode = resultCode;
        this.resultMessageCode = resultMessageCode;
        this.resultMessage = resultMessage;
        this.friendlyCustomerMessage = friendlyCustomerMessage;
        this.payload = payload;
    }

}
