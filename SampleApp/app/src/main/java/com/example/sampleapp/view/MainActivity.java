package com.example.sampleapp.view;
/**
 * Created by sunil gowroji on 07/07/15.
 */

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sampleapp.R;
import com.example.sampleapp.helper.ConnectivityDetection;
import com.example.sampleapp.helper.DBManager;
import com.example.sampleapp.model.FactModel;
import com.example.sampleapp.model.RowsModel;
import com.example.sampleapp.retrofit.ApiInterface;
import com.example.sampleapp.retrofit.AppClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FactsAdapter factsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar mActionBarToolbar;

    private DBManager dbManager;
    private BroadcastReceiver connectivityDetection;

    public static final String MyPREFERENCES = "TitlePref";
    public static final String titleName = "title";


    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);
        dbManager.open();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String heading = sharedpreferences.getString(titleName, "");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        if (heading != "") {
            mActionBarToolbar.setTitle(heading);

        } else {
            mActionBarToolbar.setTitle("My Application");
        }
        setSupportActionBar(mActionBarToolbar);

        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // On swipping down we get the data from the server
                getServerData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        connectivityDetection = new ConnectivityDetection() {
            @Override
            protected void onNetworkChange(String status) {
                // Show network connections
                Snackbar snackbar = Snackbar.make(getView(), status, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };


    }

    public View getView() {
        return findViewById(android.R.id.content);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Getting data from server and local database
        if (dbManager.isDataAvailable() > 0) {
            getLocalData();
        } else {
            getServerData();
        }
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(connectivityDetection, filter);
    }

    @Override
    public void onPause() {
        this.unregisterReceiver(connectivityDetection);
        super.onPause();
    }

    public void getLocalData() {
        List<RowsModel> rowsModelList = dbManager.getData();
        factsAdapter = new FactsAdapter(getApplicationContext(), rowsModelList);
        recyclerView.setAdapter(factsAdapter);
    }

    public void getServerData() {
        ApiInterface api = AppClient.getRetrofit(getApplicationContext()).create(ApiInterface.class);
        Call<FactModel> call = api.getFacts("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json");

        call.enqueue(new Callback<FactModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<FactModel> call, Response<FactModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        FactModel factModel = response.body();
                        List<RowsModel> rowsModelList = factModel.getRow();
                        if (rowsModelList != null && rowsModelList.size() > 0) {
                            if (factModel.getTitle() != null) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.putString(titleName, factModel.getTitle());
                                editor.commit();
                                mActionBarToolbar.setTitle(factModel.getTitle());
                            }
                            int localrows = dbManager.isDataAvailable();
                            if (localrows != rowsModelList.size()) {
                                for (int i = 0; i < rowsModelList.size(); i++) {
                                    // Saving the data in sqlite
                                    RowsModel row = rowsModelList.get(i);
                                    dbManager.insert(row.getTitle(), row.getDescription(), row.getImageHref());
                                }
                            }
                            factsAdapter = new FactsAdapter(getApplicationContext(), rowsModelList);
                            recyclerView.setAdapter(factsAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FactModel> call, Throwable t) {
                System.out.println("failed url " + call.toString());
            }
        });
    }
}
