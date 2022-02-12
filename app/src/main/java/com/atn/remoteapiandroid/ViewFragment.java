package com.atn.remoteapiandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atn.remoteapiandroid.models.Album;
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
    private EditText userIdEt, titleEt;
    Button submitBtn;
    private TextView userId, dbId, title, tvResult;
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
        submitBtn.setOnClickListener(v -> {
            if (isUpdate) {
                updateAlbumData();
            } else {
                insertAlbumData();
            }
        });
    }


    private void loadArgument() {
        if (getArguments() != null) {
            result = getArguments().getString("result");
        }
        switch (result) {
            case "insert":
                insertView.setVisibility(View.VISIBLE);
                idView.setVisibility(View.GONE);
                submitBtn.setText("Submit");
                break;
            case "update":
                isUpdate = true;
                submitBtn.setText("Update");
                getById(); //populate previous data
                break;
            case "delete":
                deleteAlbum();
                break;
            default:
                insertView.setVisibility(View.GONE);
                idView.setVisibility(View.VISIBLE);
                getById();
        }
    }

    private void getById() {
        showProgress(true);
        Call<Album> albumCall = retrofit.create(AlbumInterface.class).getAlbumById(1);
        albumCall.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.isSuccessful()) {
                    Album album = response.body();
                    if (isUpdate) {
                        titleEt.setText(album.getTitle());
                        userIdEt.setText(String.valueOf(album.getUserId()));
                    } else {
                        tvResult.setText("Code: " + response.code());
                        userId.setText("User id: " + album.getUserId());
                        dbId.setText("Id: " + album.getId());
                        title.setText("Title: " + album.getTitle());
                    }
                    showProgress(false);
                } else {
                    tvResult.setText("Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                tvResult.setText(t.getMessage());
                showProgress(false);
            }
        });
    }

    private void insertAlbumData() {
        showProgress(true);
        if (userIdEt.getText().toString().equals("")) {
            userIdEt.setError("user id required");
            return;
        }
        if (titleEt.getText().toString().equals("")) {
            titleEt.setError("title required");
            return;
        }
        Album album = new Album(
                Integer.parseInt(userIdEt.getText().toString()),
                titleEt.getText().toString()
        );
        Call<Album> albumCall = retrofit.create(AlbumInterface.class).insertAlbum(album);
        albumCall.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.isSuccessful()) {
                    tvResult.setText("Code: " + response.code() + "\n");
                    tvResult.append("title: " + response.body().getTitle());
                    tvResult.append("\nId: " + response.body().getId());
                    tvResult.append("\nUser Id: " + response.body().getUserId());
                    showProgress(false);
                } else {
                    tvResult.setText("Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                tvResult.setText(t.getMessage());
                showProgress(false);
            }
        });
    }

    private void updateAlbumData() {
        showProgress(true);
        if (userIdEt.getText().toString().equals("")) {
            userIdEt.setError("user id required");
            return;
        }
        if (titleEt.getText().toString().equals("")) {
            titleEt.setError("title required");
            return;
        }
        Album album = new Album(
                Integer.parseInt(userIdEt.getText().toString()),
                titleEt.getText().toString()
        );
        Call<Album> albumCall = retrofit.create(AlbumInterface.class).updateAlbum(5, album);
        albumCall.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.isSuccessful()) {
                    tvResult.setText("Code: " + response.code() + "\n");
                    tvResult.append("title: " + response.body().getTitle());
                    tvResult.append("\nId: " + response.body().getId());
                    tvResult.append("\nUser Id: " + response.body().getUserId());
                    showProgress(false);
                } else {
                    tvResult.setText("Code: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                tvResult.setText(t.getMessage());
                showProgress(false);
            }
        });
    }


    private void deleteAlbum() {
        showProgress(true);
        Call<Album> albumCall = retrofit.create(AlbumInterface.class).deleteAlbum(5);
        albumCall.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    tvResult.setText("Deleted success with id: 5");
                    showProgress(false);
                } else {
                    tvResult.setText("Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                tvResult.setText(t.getMessage());
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
        tvResult = view.findViewById(R.id.textViewResult);
        userIdEt = view.findViewById(R.id.titleEt);
        titleEt = view.findViewById(R.id.urlEt);
        submitBtn = view.findViewById(R.id.submitBtn);

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