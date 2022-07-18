package cl.versionbeta.app.accesscontrol.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.versionbeta.app.accesscontrol.R;


public class AreaFragment extends Fragment {


    private TextInputEditText txArea;
    private TextInputEditText txAreaDescription;

    public AreaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_area, container, false);
        txArea = view.findViewById(R.id.txArea);
        txAreaDescription = view.findViewById(R.id.txAreaDescription);
        txArea.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        txAreaDescription.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        return view;

    }

}
