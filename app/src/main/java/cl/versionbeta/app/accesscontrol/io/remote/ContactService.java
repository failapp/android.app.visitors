package cl.versionbeta.app.accesscontrol.io.remote;

import java.util.List;

import cl.versionbeta.app.accesscontrol.model.Contact;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ContactService {

    @GET("contacts")
    Call<List<Contact>> loadContacts();

    @GET("contacts/{id}")
    Call<Contact> findContact(@Path("id") Integer id);

    @GET("contacts/area/{id}")
    Call<Contact> findContactsByArea(@Path("id") Integer areaId);

}
