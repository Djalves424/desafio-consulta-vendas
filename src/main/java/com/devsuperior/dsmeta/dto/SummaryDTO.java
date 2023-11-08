package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

public class SummaryDTO {

    private String sellerName;
    private Double sellerCount;

    public SummaryDTO(String sellerName, Double sellerCount) {
        this.sellerName = sellerName;
        this.sellerCount = sellerCount;
    }

    public SummaryDTO(SummaryDTO sum) {
        sellerName = sum.sellerName;
        sellerCount = sum.sellerCount;
    }

    public SummaryDTO(Sale x) {

    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getSellerCount() {
        return sellerCount;
    }
}
