package com.github.prgrms.orders;

import javax.validation.constraints.NotBlank;

public class OrderRejectRequest {
    private String message;

    protected OrderRejectRequest() {
    }

    public OrderRejectRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
