package com.example.pantry.model;

public enum ProductStatus {
    OKAY("Okay"),
    SPECIAL_OFFER("If special offer"),
    NEED_TO_BUY("Need to buy");

    private final String displayStatus;

    ProductStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }


}
