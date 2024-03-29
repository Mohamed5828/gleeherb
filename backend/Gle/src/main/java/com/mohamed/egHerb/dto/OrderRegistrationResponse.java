package com.mohamed.egHerb.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class OrderRegistrationResponse {
    private String orderString;
    private JsonNode total;

    public OrderRegistrationResponse(String orderString, JsonNode total) {
        this.orderString = orderString;
        this.total = total;
    }

    public String getOrderString() {
        return orderString;
    }

    public JsonNode getTotal() {
        return total;
    }
}
