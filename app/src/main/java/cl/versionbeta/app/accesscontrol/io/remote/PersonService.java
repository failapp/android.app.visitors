package cl.versionbeta.app.accesscontrol.io.remote;

import java.util.List;

import cl.versionbeta.app.accesscontrol.model.Person;
import cl.versionbeta.app.accesscontrol.model.PersonImage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface PersonService {

    @GET("persons")
    Call<List<Person>> loadPersons();

    @GET("persons/dni/{dni}")
    Call<Person> loadPerson(@Path("dni") String dni);


    @GET("persons/find")  //persons/find?dni=14.195.464-4
    Call<Person> findPersonById(@Query("dni") String dni);

    @POST("persons")
    Call<Person> savePerson(@Body Person person);

    /*
    @POST("persons/image")
    Call<Void> savePersonImage(@Body PersonImage personImage);
    @Streaming
    @GET("persons/image/{personId}")
    Call<PersonImage> loadPersonImage(@Path("personId") int personId);
    */

}
