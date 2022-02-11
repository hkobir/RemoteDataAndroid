package com.atn.remoteapiandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.atn.remoteapiandroid.adapters.AlbumAdapter;
import com.atn.remoteapiandroid.models.Photo;
import com.atn.remoteapiandroid.remote.AlbumInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {
    private List<Photo> albumList;
    private RecyclerView albumRv;
    private String BASE_URL = "https://jsonplaceholder.typicode.com";
    private AlbumAdapter albumAdapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //init
        albumList = new ArrayList<>();
        albumRv = view.findViewById(R.id.albumRV);
        progressBar = view.findViewById(R.id.progressView);
        albumRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        albumAdapter = new AlbumAdapter(requireContext());
        populateAlbumData();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void populateAlbumData() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<List<Photo>> albumCall = retrofit.create(AlbumInterface.class).getAlbumList();
        albumCall.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    albumList = response.body();
                    albumAdapter.setAlbumList(albumList);
                    albumRv.setAdapter(albumAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d("MainActivity", "onFailure: " + t.getMessage());
            }
        });

    }

}