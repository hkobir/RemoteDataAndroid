package com.atn.remoteapiandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.atn.remoteapiandroid.adapters.AlbumAdapter;
import com.atn.remoteapiandroid.models.Album;
import com.atn.remoteapiandroid.remote.AlbumInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private List<Album> albumList;
    private RecyclerView albumRv;
    private String BASE_URL = "https://jsonplaceholder.typicode.com";
    private AlbumAdapter albumAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        albumList = new ArrayList<>();
        albumRv = findViewById(R.id.albumRV);
        progressBar = findViewById(R.id.progressView);
        albumRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        albumAdapter = new AlbumAdapter(this);
        populateAlbumData();

    }

    private void populateAlbumData() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<List<Album>> albumCall = retrofit.create(AlbumInterface.class).getAlbumList();
        albumCall.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()) {
                    albumList = response.body();
                    albumAdapter.setAlbumList(albumList);
                    albumRv.setAdapter(albumAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.d("MainActivity", "onFailure: " + t.getMessage());
            }
        });

    }
}