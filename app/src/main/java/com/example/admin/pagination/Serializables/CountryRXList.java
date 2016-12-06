package com.example.admin.pagination.Serializables;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ulan on 12/5/16.
 */
public class CountryRXList {
    @SerializedName("objects")
    List<CountryRX> rulesOfIncomingList;

    public List<CountryRX> getRulesOfIncomingList() {
        return rulesOfIncomingList;
    }

    public void setRulesOfIncomingList(List<CountryRX> rulesOfIncomingList) {
        this.rulesOfIncomingList = rulesOfIncomingList;
    }
}
