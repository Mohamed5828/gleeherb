package com.mohamed.egHerb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FinalOrderDTO {
    private String authToken;
    private String amountCents;
    private String expiration;
    private String orderId;
    private List<FinalItemDTO> billingData;
    private String currency;
    private String integrationId;

    public FinalOrderDTO() {

    }

    public List<FinalItemDTO> getBilling_data() {
        return billingData;
    }
    public void setBilling_data(List<FinalItemDTO> billingData) {
        this.billingData = billingData;
    }
    @Override
    public String toString() {
        return "FinalOrderDTO{" +
                "authToken='" + authToken + '\'' +
                ", amountCents='" + amountCents + '\'' +
                ", expiration='" + expiration + '\'' +
                ", orderId='" + orderId + '\'' +
                ", billingData=" + billingData +
                ", currency='" + currency + '\'' +
                ", integrationId='" + integrationId + '\'' +
                '}';
    }
}
