package cl.versionbeta.app.accesscontrol.io.remote;

import java.util.List;

import cl.versionbeta.app.accesscontrol.model.Area;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AreaService {

    @GET("areas")
    Call<List<Area>> loadPlaces();

    @GET("areas/{id}")
    Call<Area> findArea(@Path("id") Integer id);

}
