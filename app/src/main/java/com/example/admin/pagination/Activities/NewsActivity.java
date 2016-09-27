package com.example.admin.pagination.Activities;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.pagination.Adapters.RVNewsAdapter;
import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Istories;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    EditText editSearch;
    DataHelper dataHelper;
    String title="";
    String TAG="TAG";
    private Toolbar toolbar;

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVNewsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<Istories> studentList;
    ImageButton searchBt;
    ProgressBar progressBar;
    protected Handler handler;
    private MaterialSearchView searchView;
    TextView tvSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Новости");
        getSupportActionBar().setHomeButtonEnabled(true);

        dataHelper = new DataHelper(this);


        //  editSearch=(EditText) findViewById(R.id.tv_news_search);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        studentList = new ArrayList<>();
        handler = new Handler();
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }


        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setHasFixedSize(true);
//
//        mLayoutManager = new LinearLayoutManager(this);
//
//        // use a linear layout manager
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL,this.getResources().getDrawable(R.drawable.divider_news)));

        pizdec(1);




        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        //  searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG)
//                        .show();
                title = query;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    studentList.clear();
                    pizdec(3);
                }else
                {
                    Cursor cursor=dataHelper.getData();
                    studentList.clear();
                    String titleDataHelper;
                    if(cursor.getCount()>0){
                        while (cursor.moveToNext()){
                            titleDataHelper=cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_ZAGOLOVOK_COLUMN)).toLowerCase();
                            if(titleDataHelper.contains(title.toLowerCase())){
                                Istories istories=new Istories();
                                istories.setNickName(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_ZAGOLOVOK_COLUMN)));
                                istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_TEXT_COLUMN)));
                                studentList.add(istories);
                            }

                        }

                    }
                    mAdapter.notifyDataSetChanged();
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });



    }

    // load initial data
    private void loadData(int b) {
        new ParseTask(0,b).execute();


    }
    public void pizdec(int b){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            Toast.makeText(this,"RAbotaet",Toast.LENGTH_SHORT).show();
            studentList.add(null);
            mAdapter = new RVNewsAdapter(studentList, mRecyclerView, this);

            // set the adapter object to the Recyclerview
            mRecyclerView.setAdapter(mAdapter);
            //  mAdapter.notifyDataSetChanged();
            loadData(b);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView


            // create an Object for Adapter
            mAdapter = new RVNewsAdapter(studentList, mRecyclerView, this);

            // set the adapter object to the Recyclerview
            mRecyclerView.setAdapter(mAdapter);
            //  mAdapter.notifyDataSetChanged();


            if (studentList.isEmpty()) {
                mRecyclerView.setVisibility(View.GONE);
                tvEmptyView.setVisibility(View.VISIBLE);

            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                tvEmptyView.setVisibility(View.GONE);
            }

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    //add null , so the adapter will check view_type and show progress bar at bottom

                    Log.e("TAG_SUKA", "SUKA_RABOTAET");
                    int start = studentList.size();
                    if (start < total_count - 1)
                        progressBar.setVisibility(View.VISIBLE);
                    else progressBar.setVisibility(View.GONE);
                    new ParseTask(start, 0).execute();


                }
            });

        } else {
            Toast.makeText(this,"NeRAbotaet",Toast.LENGTH_SHORT).show();
            Cursor cursor = dataHelper.getData();
            Log.e("TAG_NEWS",cursor.getCount()+" kol");
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Istories istories=new Istories();
                    istories.setNickName(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_ZAGOLOVOK_COLUMN)));
                    istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_TEXT_COLUMN)));
                    studentList.add(istories);
                }
                mAdapter=new RVNewsAdapter(studentList,mRecyclerView,this);
                mRecyclerView.setAdapter(mAdapter);


            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

    public void onClickSearch(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
//        title = editSearch.getText().toString();
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//
//            studentList.clear();
//            pizdec(3);
//        }else
//        {
//            Cursor cursor=dataHelper.getData();
//            studentList.clear();
//            String titleDataHelper;
//            if(cursor.getCount()>0){
//                while (cursor.moveToNext()){
//                    titleDataHelper=cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_ZAGOLOVOK_COLUMN)).toLowerCase();
//                    if(titleDataHelper.contains(title.toLowerCase())){
//                        Istories istories=new Istories();
//                        istories.setNickName(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_ZAGOLOVOK_COLUMN)));
//                        istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.NEWS_TEXT_COLUMN)));
//                        studentList.add(istories);
//                    }
//
//                }
//
//            }
//            mAdapter.notifyDataSetChanged();
//        }
    }

    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";
        int a,b;
        public ParseTask(int a,int b){
            this.a=a;this.b=b;
        }
        @Override
        protected String doInBackground(Void... params) {
            Log.e("TAG_S",1+"");

            try {

                URL url = new URL("http://176.126.167.231:8000/api/v1/news/?title_ru__contains="+title+"&offset="+a+"&limit=15&format=json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                Log.e("TAG_S",""+a);
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                jsonResult = builder.toString();
                Log.e("TAG_S",3+"DELETE");

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG_S",1+"PIZDEC");
            }

            Log.e("TAG_S",4+"");
            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            if (b==1||b==3) {
                studentList.remove(studentList.size() - 1);
                mAdapter.notifyItemRemoved(studentList.size());
            }
            Log.e("TAG", json);

            JSONObject dataJsonObject;
            String secondName;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");
                total_count=meta.getInt("total_count");
                Log.e(TAG+"Total",total_count+""+a);


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    Log.d(TAG, "1: " );
                    Istories student = new Istories();
                    Log.d(TAG, "2: ");
                    student.setText(menu.getString("text_ru"));
                    student.setNickName(menu.getString("title_ru"));

                    if (i==0&&b==1){dataHelper.delete();
                        Log.e("TAG_NEWS","DELETE");
                    }
                    if (b!=3) {
                        dataHelper.insertNews(student);
                    }
                    Log.e("TAG_IS",student.getNickName()+"   "+student.getText());
                    studentList.add(student);


                }
                dataHelper.readData();
                mAdapter.notifyDataSetChanged();
                mAdapter.setLoaded();
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "NET NET dsfsadadsgf: ");
            } catch (JSONException e) {
                e.printStackTrace();



                Log.d(TAG, "JSON_PIZDEC");
            }
            Log.e("TAG_1","NORM");





        }
    }

}