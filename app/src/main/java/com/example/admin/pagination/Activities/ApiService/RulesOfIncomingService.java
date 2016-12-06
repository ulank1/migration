package com.example.admin.pagination.Activities.ApiService;

import com.example.admin.pagination.Serializables.RulesOfIncomingList;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ulan on 12/5/16.
 */
public interface RulesOfIncomingService {
    @GET("api/v1/rules_of_incoming/?format=json")
    Observable<RulesOfIncomingList> listRepos();
}
