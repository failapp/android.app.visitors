package cl.versionbeta.app.accesscontrol.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cl.versionbeta.app.accesscontrol.R;
import cl.versionbeta.app.accesscontrol.io.remote.AccessLogService;
import cl.versionbeta.app.accesscontrol.io.remote.ApiUtils;
import cl.versionbeta.app.accesscontrol.io.remote.PersonService;
import cl.versionbeta.app.accesscontrol.model.Person;
import cl.versionbeta.app.accesscontrol.model.Register;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InitFragment extends Fragment {

    PersonService personService;
    AccessLogService accessLogService;

    private Person personTest = null;
    private List<Register> registerListTest = null;


    public InitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_init, container, false);

        //this.personServiceTest();
        //this.accessLogServiceTest();
        return view;
    }


    public void accessLogServiceTest(Person person) {

        Register register = new Register(person, 1, "xD");
        accessLogService = ApiUtils.getAccessLogService();

        Log.e("WS TEST", register.toString());

        accessLogService.saveAccessLog(register).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.isSuccessful()) {
                    Log.e("TEST WS", "register access OK !!..");
                }
            }
            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Log.e("TEST WS", ":( .." + t.getMessage());
            }
        });

    }

    public void personServiceTest() {

        Log.i("TEST WS","webservice persons ..");

        try {

            personService = ApiUtils.getPersonService();
            personService.findPersonById("13469932-9").enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    if (response.isSuccessful()) {
                        Person p = response.body();
                        if (p != null) {
                            Log.i("person", p.toString());
                            accessLogServiceTest(p);
                        }
                    }
                }
                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Log.e("error webservice ..", t.getMessage());
                }
            });


            /*
            Person p = new Person("15.800.700-6", "ALAN",  "BRITO DELGADO");

            personService.savePerson(p).enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    if (response.isSuccessful()) {
                        Log.e("TEST WS", "save person OK !!");
                    }
                }
                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Log.e("TEST WS", t.getMessage());
                }
            });
            */

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
