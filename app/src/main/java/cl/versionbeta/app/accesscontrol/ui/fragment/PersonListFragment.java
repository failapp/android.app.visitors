package cl.versionbeta.app.accesscontrol.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cl.versionbeta.app.accesscontrol.R;
import cl.versionbeta.app.accesscontrol.adapter.PersonAdapter;
import cl.versionbeta.app.accesscontrol.io.remote.ApiUtils;
import cl.versionbeta.app.accesscontrol.io.remote.PersonService;
import cl.versionbeta.app.accesscontrol.model.Person;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonListFragment extends Fragment {

    private RecyclerView rvPersonList;
    private PersonAdapter personAdapter;

    private PersonService personService;

    public PersonListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_person_list, container, false);

        rvPersonList = view.findViewById(R.id.rvPersonList);
        rvPersonList.setLayoutManager(new LinearLayoutManager(getContext()));

        this.loadPersons();

        return view;
    }


    private void loadPersons() {

        this.personService = ApiUtils.getPersonService();

        this.personService.loadPersons().enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.isSuccessful()) {
                    List<Person> personList = response.body();
                    personAdapter = new PersonAdapter(personList);
                    rvPersonList.setAdapter(personAdapter);
                    Log.e("TEST WS", "visitas -> " + personList.size());
                }
            }
            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e("TEST WS", ":( .." + t.getMessage());
            }
        });


    }


}
