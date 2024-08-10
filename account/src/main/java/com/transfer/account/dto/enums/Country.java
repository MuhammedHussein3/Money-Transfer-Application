package com.transfer.account.dto.enums;

import lombok.Getter;

@Getter
public enum Country {
    EGYPT("EG", "Egypt"),
    PALESTINE("PS", "Palestine"),
    UNITED_STATES("US", "United States"),
    CANADA("CA", "Canada"),
    UNITED_KINGDOM("GB", "United Kingdom"),
    AUSTRALIA("AU", "Australia"),
    GERMANY("DE", "Germany"),
    FRANCE("FR", "France"),
    JAPAN("JP", "Japan"),
    CHINA("CN", "China"),
    INDIA("IN", "India"),
    BRAZIL("BR", "Brazil"),
    SOUTH_AFRICA("ZA", "South Africa"),
    RUSSIA("RU", "Russia"),
    MEXICO("MX", "Mexico"),
    SPAIN("ES", "Spain"),
    ITALY("IT", "Italy");


    private final String code;
    private final String name;

    Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
