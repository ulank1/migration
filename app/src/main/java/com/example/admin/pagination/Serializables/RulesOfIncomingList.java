package com.example.admin.pagination.Serializables;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ulan on 12/5/16.
 */
public class RulesOfIncomingList {
    @SerializedName("objects")
    List<RulesOfIncomingRX> rulesOfIncomingList;

    public List<RulesOfIncomingRX> getRulesOfIncomingList() {
        return rulesOfIncomingList;
    }

    public void setRulesOfIncomingList(List<RulesOfIncomingRX> rulesOfIncomingList) {
        this.rulesOfIncomingList = rulesOfIncomingList;
    }
}
