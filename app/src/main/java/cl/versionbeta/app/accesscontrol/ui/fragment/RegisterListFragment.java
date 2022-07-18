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
import cl.versionbeta.app.accesscontrol.adapter.RegisterAdapter;
import cl.versionbeta.app.accesscontrol.io.remote.AccessLogService;
import cl.versionbeta.app.accesscontrol.io.remote.ApiUtils;
import cl.versionbeta.app.accesscontrol.model.Person;
import cl.versionbeta.app.accesscontrol.model.Register;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterListFragment extends Fragment {

    private RecyclerView rvRegisters;
    private RegisterAdapter registerAdapter;

    AccessLogService accessLogService;

    public RegisterListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_list, container, false);

        rvRegisters = view.findViewById(R.id.rvRegisters);
        rvRegisters.setLayoutManager(new LinearLayoutManager(getContext()));

        //int icon1 =  R.drawable.ic_letter_e; //view.findViewById(R.drawable.ic_letter_e);

        this.loadRegisters();

        //registerAdapter = new RegisterAdapter(fetchRegisters());
        //rvRegisters.setAdapter(registerAdapter);

        return view;

    }

    private void loadRegisters() {

        try {

            accessLogService = ApiUtils.getAccessLogService();
            // 121, 3 -< test ..
            accessLogService.loadAccessLogs().enqueue(new Callback<List<Register>>() {
                @Override
                public void onResponse(Call<List<Register>> call, Response<List<Register>> response) {

                    if (response.isSuccessful()) {

                        List<Register> dataList = response.body();
                        registerAdapter = new RegisterAdapter(dataList);
                        rvRegisters.setAdapter(registerAdapter);
                        Log.e("TEST WS", "registros -> " + dataList.size());

                    }

                }
                @Override
                public void onFailure(Call<List<Register>> call, Throwable t) {
                    Log.e("TEST WS", ":( .." + t.getMessage());
                }
            });


        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
