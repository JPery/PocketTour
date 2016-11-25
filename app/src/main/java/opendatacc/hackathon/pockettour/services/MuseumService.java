package opendatacc.hackathon.pockettour.services;

import java.util.List;

import opendatacc.hackathon.pockettour.model.place_json_schema.MuseumJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by j.pery on 24/11/16.
 */

public interface MuseumService {
    @GET("museum/")
    Call<List<MuseumJSON>> getMuseums();

    @GET("museum/{museumID}")
    Call<MuseumJSON> getMuseumByID(@Path("museumID") int museumID);
}
