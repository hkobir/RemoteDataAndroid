package com.atn.remoteapiandroid.remote;

import com.atn.remoteapiandroid.models.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumInterface {
    @GET("/albums/1/photos")
    Call<List<Album>> getAlbumList();

}
