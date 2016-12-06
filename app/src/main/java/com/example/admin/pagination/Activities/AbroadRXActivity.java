package com.example.admin.pagination.Activities;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.pagination.Activities.ApiService.CounryAbroadService;
import com.example.admin.pagination.Activities.ApiService.RulesOfIncomingService;
import com.example.admin.pagination.Adapters.RVEAEUAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.DateDateDB;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Country;
import com.example.admin.pagination.Serializables.CountryRX;
import com.example.admin.pagination.Serializables.CountryRXList;
import com.example.admin.pagination.Serializables.EAEU;
import com.example.admin.pagination.Serializables.RulesOfIncoming;
import com.example.admin.pagination.Serializables.RulesOfIncomingList;
import com.example.admin.pagination.Serializables.RulesOfIncomingRX;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AbroadRXActivity extends AppCompatActivity {

    DataHelper dataHelper;
    RVEAEUAdapter mAdapter;
    ArrayList<EAEU> studentList;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    String date,dateDB;
    int lang;
    URL urlM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_line);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_abroad);
        mRecyclerView=(RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar =(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);
        dataHelper=new DataHelper(this);

        Cursor cursor=dataHelper.getDataLanguage();
        if (cursor.getCount()>0)
        {

            cursor.moveToFirst();
            lang=cursor.getInt(cursor.getColumnIndex(DataHelper.LANGUAGE_COLUMN));

        }
        else lang=0;
        if (lang==0) {
            try {
                urlM=new URL("http://176.126.167.249/api/v1/rules_of_incoming/?format=json&limit=0");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                urlM=new URL("http://176.126.167.249/api/v1/rules_of_incoming_kg/?format=json&limit=0");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        studentList=new ArrayList<>();
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        EAEU eaeu=new EAEU();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ifConnect();




    }
    public void ifConnect(){
        Calendar calendar=Calendar.getInstance();

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        date=day+"."+month+"."+year;
        Cursor cursor=dataHelper.getDataDate("3");

        if (cursor.getCount()==0){
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
               rx1();
                progressBar.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(this,R.string.toast_no_internet,Toast.LENGTH_SHORT).show();
            }

        }
        else {
            cursor.moveToFirst();

            dateDB=cursor.getString(cursor.getColumnIndex(DataHelper.DATE_LAST_DATE_COLUMN));
            DateDateDB dateDateDB=new DateDateDB();
            if (dateDateDB.calendar1(dateDB)){
                ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                  rx1();
                    progressBar.setVisibility(View.VISIBLE);

                }
                else {
                    Toast.makeText(this,R.string.toast_no_internet,Toast.LENGTH_SHORT).show();
                }
            }else db();
        }
    }

    public void rx1(){
        Toast.makeText(this,"RETROFIT",Toast.LENGTH_SHORT).show();
        Log.e("RXRXRX","sdsss");
        dataHelper.deleteAbroad();
        new Retrofit.Builder()
                .baseUrl("http://176.126.167.249/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(CounryAbroadService.class)
                .listRepos1().subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .map(new Func1<CountryRXList, List<CountryRX>>() {

                    @Override
                    public List<CountryRX> call(CountryRXList newsList) {

                        return newsList.getRulesOfIncomingList();
                    }
                }).flatMap(new Func1<List<CountryRX>, Observable<CountryRX>>() {
            @Override
            public Observable<CountryRX> call(List<CountryRX> newses) {
                return Observable.from(newses);
            }
        })

                .subscribe(new Subscriber<CountryRX>() {
                    @Override public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("HIYA", "An error!: " + e.getMessage());

                    }

                    @Override
                    public void onNext(CountryRX user) {

                        Log.e("HIYA", "So we've not got some text: " + user.getCountry());


                        dataHelper.insertAbroad(user);
                       rx();
                    }
                });

    }

    public void rx(){
        Toast.makeText(this,"RETROFIT",Toast.LENGTH_SHORT).show();
        Log.e("RXRXRX","sdsss");
        dataHelper.deleteROM();
        new Retrofit.Builder()
                .baseUrl("http://176.126.167.249/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(RulesOfIncomingService.class)
                .listRepos().subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .map(new Func1<RulesOfIncomingList, List<RulesOfIncomingRX>>() {

            @Override
            public List<RulesOfIncomingRX> call(RulesOfIncomingList newsList) {

                return newsList.getRulesOfIncomingList();
            }
        }).flatMap(new Func1<List<RulesOfIncomingRX>, Observable<RulesOfIncomingRX>>() {
            @Override
            public Observable<RulesOfIncomingRX> call(List<RulesOfIncomingRX> newses) {
                return Observable.from(newses);
            }
        })

                .subscribe(new Subscriber<RulesOfIncomingRX>() {
                    @Override public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("HIYA", "An error!: " + e.getMessage());

                    }

                    @Override
                    public void onNext(RulesOfIncomingRX user) {

                        Log.e("HIYA", "So we've not got some text: " + user.getTitle_ru());

                        Country country=user.getCountry();
                        dataHelper.insertROM(user,country.getId()+"");
                        progressBar.setVisibility(View.GONE);
                        mAdapter=new RVEAEUAdapter(studentList,mRecyclerView,AbroadRXActivity.this,2);
                        mRecyclerView.setAdapter(mAdapter);

                        dataHelper.updateDate(date,"3");
                    }
                });

    }
    public  void db(){
        Cursor cursor = dataHelper.getAbroad();
        studentList.clear();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                EAEU eaeu=new EAEU();
                eaeu.setPicture(cursor.getString(cursor.getColumnIndex(DataHelper.ABROAD_PICTURE_COLUMN)));
                eaeu.setName(cursor.getString(cursor.getColumnIndex(DataHelper.ABROAD_TITLE_COLUMN)));
                eaeu.setId(cursor.getString(cursor.getColumnIndex(DataHelper.ABROAD_ID_COLUMN)));

                studentList.add(eaeu);
                mAdapter=new RVEAEUAdapter(studentList,mRecyclerView,this,2);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();

                return true;
            case R.id.action_ubdate:
                dataHelper.updateDate("ss","3");
                ifConnect();

                return true;

            default:  return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);



        return true;
    }
    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("http://176.126.167.249/api/v1/country/?format=json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                jsonResult = builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            studentList.clear();

            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    EAEU eaeu=new EAEU();
                    eaeu.setName(menu.getString("country"));
                    eaeu.setPicture(menu.getString("image"));
                    eaeu.setId(menu.getString("id"));
                    studentList.add(eaeu);
                    if (i==0) dataHelper.deleteAbroad();
                    dataHelper.insertAbroad(eaeu);

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("TAG_1","NORM");
            Toast.makeText(AbroadRXActivity.this,"SUKA",Toast.LENGTH_SHORT).show();
         rx();

        }
    }

    public class ParseTask1 extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = urlM;

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                jsonResult = builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            JSONObject dataJsonObject;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    RulesOfIncoming student = new RulesOfIncoming();


                    student.setText(menu.getString("text_ru"));
                    student.setTitle(menu.getString("title_ru"));
                    if (i==0){dataHelper.deleteROM();
                        Log.e("TAG_NEWS","DELETE");
                    }
                    JSONObject country=menu.getJSONObject("country");
                    String id=country.getString("id");
                    dataHelper.insertROM(student,id);






                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "JSON_PIZDEC");
            }

            progressBar.setVisibility(View.GONE);
            mAdapter=new RVEAEUAdapter(studentList,mRecyclerView,AbroadRXActivity.this,2);
            mRecyclerView.setAdapter(mAdapter);

            dataHelper.updateDate(date,"3");
        }
    }
}
