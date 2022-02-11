package com.atn.remoteapiandroid.remote;

import com.atn.remoteapiandroid.models.Album;
import com.atn.remoteapiandroid.models.Photo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface AlbumInterface {
    @GET("/albums/1/photos")
    Call<List<Photo>> getAlbumList();
    @GET()
    Call<List<Photo>> getAlbumsByUrl(
            //Get url will pass from activity rather than init here: as string "/albums/1/photos"
            @Url String url
    );

    //https://jsonplaceholder.typicode.com/albums/1/photos?albumId=1&_sort=id&_order=desc
    @GET("/albums/1/photos")
    Call<List<Photo>> getAlbumsByQuery(
            @Query("albumId") int albumId,
            @Query("_sort") String sort,
            @Query("_order") int order
    );

    @GET("/albums/1/photos")
    Call<List<Photo>> getAlbumsByQueryMap(
            @QueryMap Map<String, String> parameters
    );

    @GET("/albums/{id}")
    Call<Album> getAlbumById(@Path("id") int id);

}
