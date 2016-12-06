package com.example.admin.pagination.Activities.ApiService;

import com.example.admin.pagination.Serializables.CountryRXList;
import com.example.admin.pagination.Serializables.RulesOfIncomingList;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ulan on 12/5/16.
 */
public interface CounryAbroadService {
    @GET("api/v1/country/?format=json")
    Observable<CountryRXList> listRepos1();
}
