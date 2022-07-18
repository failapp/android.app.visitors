package cl.versionbeta.app.accesscontrol.ui.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import cl.versionbeta.app.accesscontrol.R;


public class ConfigAppFragment extends Fragment implements View.OnClickListener {


    private TextInputEditText txUrl;
    private TextInputEditText txTcpPORT;
    private CheckBox ckValidateDni;
    private CheckBox ckPhotoOption;
    private FloatingActionButton btSaveConfig;


    public ConfigAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_config_app, container, false);

        txUrl = view.findViewById(R.id.txUrl);
        txTcpPORT = view.findViewById(R.id.txPortTCP);
        ckValidateDni = view.findViewById(R.id.ckValidateDni);
        ckPhotoOption = view.findViewById(R.id.ckPhotoOption);
        btSaveConfig = view.findViewById(R.id.btSaveConfig);

        btSaveConfig.setOnClickListener(this);

        ckPhotoOption.setVisibility(View.INVISIBLE);
        ckValidateDni.setVisibility(View.INVISIBLE);

        this.loadPreferences();


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btSaveConfig:
                Log.e("CONF-APP", "btn Guardar configuración ..");

                this.saveAppPreferences();

                break;

        }

    }


    private void saveAppPreferences() {

        SharedPreferences preferences = getActivity().getSharedPreferences("configuracion", Context.MODE_PRIVATE);

        String url_webservice = txUrl.getText().toString().trim();
        String tcp_port = txTcpPORT.getText().toString().trim();
        Boolean validate_dni = ckValidateDni.isChecked();
        Boolean photo_option = ckPhotoOption.isChecked();

        //Log.e("CONF-APP", "bln -> " + validateDni + " bln -> " + photoOption);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("url_webservice", url_webservice);
        editor.putString("tcp_port", tcp_port);
        editor.putBoolean("validate_dni", validate_dni);
        editor.putBoolean("photo_option", photo_option);

        editor.commit();

        Toast.makeText(getActivity(),"Configuración registrada correctamente.", Toast.LENGTH_SHORT).show();

    }


    private void loadPreferences() {

        SharedPreferences preferences = getActivity().getSharedPreferences("configuracion", Context.MODE_PRIVATE);

        String url_webservice = preferences.getString("url_webservice", "http://192.168.1.11");  //192.168.1.11/api
        String tcp_port = preferences.getString("tcp_port", "8899");
        Boolean validate_dni = preferences.getBoolean("validate_dni", false);
        Boolean photo_option = preferences.getBoolean("photo_option", false);

        txUrl.setText(url_webservice);
        txTcpPORT.setText(tcp_port);
        ckValidateDni.setChecked(validate_dni);
        ckPhotoOption.setChecked(photo_option);

    }



    /// /////////////////////////////////////////////////////////////////////////////////
}
