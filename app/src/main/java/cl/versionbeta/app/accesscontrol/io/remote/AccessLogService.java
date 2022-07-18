package cl.versionbeta.app.accesscontrol.io.remote;

import java.util.List;

import cl.versionbeta.app.accesscontrol.model.Register;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccessLogService {

    @GET("accesslogs/person/{id}")
    Call<List<Register>> findAccessLogByPerson(@Path("id") String id);

    @GET("accesslogs")
    Call<List<Register>> loadAccessLogs();

    @POST("accesslogs")
    Call<Register> saveAccessLog(@Body Register register);

    @POST("accesslogs/image")
    Call<String> saveImageAccessLog(@Body String image);

}
