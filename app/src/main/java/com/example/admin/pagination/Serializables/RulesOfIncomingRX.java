package com.example.admin.pagination.Serializables;

/**
 * Created by ulan on 12/5/16.
 */
public class RulesOfIncomingRX {
    Country country;
    String text_ru,title_ru;

    public String getTitle_ru() {
        return title_ru;
    }

    public void setTitle_ru(String title_ru) {
        this.title_ru = title_ru;
    }

    public void setText_ru(String text_ru) {
        this.text_ru = text_ru;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getText_ru() {
        return text_ru;
    }

    public Country getCountry() {
        return country;
    }
}
