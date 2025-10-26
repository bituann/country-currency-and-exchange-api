package com.bituan.country_currency_and_exchange_api.model;

import java.util.List;

public class RcResponseModel {
    private List<CountryModel> response;

    public List<CountryModel> getResponse() {
        return response;
    }

    public void setResponse(List<CountryModel> response) {
        this.response = response;
    }
}
