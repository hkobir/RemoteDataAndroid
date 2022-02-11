package com.atn.remoteapiandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atn.remoteapiandroid.models.Album;
import com.atn.remoteapiandroid.models.Photo;
import com.atn.remoteapiandroid.remote.AlbumInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewFragment extends Fragment {
    private String result = "";
    private boolean isUpdate = false;
    private ProgressBar progressBar;
    LinearLayout insertView, idView;
    Retrofit retrofit;
    private TextView userId, dbId, title;
    private String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        init(view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadArgument();
    }

    private void loadArgument() {
        showProgress(true);
        if (getArguments() != null) {
            result = getArguments().getString("result");
        }
        switch (result) {
            case "insert":
                insertView.setVisibility(View.VISIBLE);
                idView.setVisibility(View.GONE);
                break;
            case "update":
                isUpdate = true;
                break;
            case "delete":
                break;
            default:
                insertView.setVisibility(View.GONE);
                idView.setVisibility(View.VISIBLE);
                getById();
        }
    }

    private void getById() {
        Call<Album> albumCall = retrofit.create(AlbumInterface.class).getAlbumById(1);
        albumCall.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.isSuccessful()) {
                    Album album = response.body();
                    userId.setText(album.getUserId());
                    dbId.setText(album.getId());
                    title.setText(album.getTitle());
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                showProgress(false);
            }
        });
    }

    private void init(View view) {
        progressBar = view.findViewById(R.id.progressLoad);
        insertView = view.findViewById(R.id.insertView);
        idView = view.findViewById(R.id.getbyIdView);

        userId = view.findViewById(R.id.userId);
        dbId = view.findViewById(R.id.dbId);
        title = view.findViewById(R.id.titleTview);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void showProgress(boolean isShow) {
        if (isShow)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }
}