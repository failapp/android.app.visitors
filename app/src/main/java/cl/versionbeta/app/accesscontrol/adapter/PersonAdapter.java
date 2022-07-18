package cl.versionbeta.app.accesscontrol.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.versionbeta.app.accesscontrol.R;
import cl.versionbeta.app.accesscontrol.model.Person;
import cl.versionbeta.app.accesscontrol.util.Util;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    public List<Person> personList;


    public PersonAdapter(List<Person> persons) {
        this.personList = persons;
    }


    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_list, parent, false);
        PersonViewHolder viewHolder = new PersonViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

        String firstName = personList.get(position).getFirstName().trim();
        String lastName = personList.get(position).getLastName().trim();
        String dni = personList.get(position).getDni().trim().trim();
        String company = "";
        //String photo = "";


        String[] arr = dni.split("-");
        String dv = " - " + arr[1];
        String number = Util.decimalFormat.format(Integer.parseInt(arr[0]));
        dni = number + dv;

        holder.tvPersonFirstName.setText(firstName);
        holder.tvPersonLastName.setText(lastName);
        holder.tvPersonDni.setText(dni);
        holder.tvPersonCompany.setText(company);

    }

    @Override
    public int getItemCount() {
        return this.personList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPersonFirstName;
        private TextView tvPersonLastName;
        private TextView tvPersonDni;
        private TextView tvPersonCompany;
        private ImageView ivPersonPhoto;

        public PersonViewHolder(View view) {
            super(view);
            tvPersonFirstName = view.findViewById(R.id.tvPersonFirstName);
            tvPersonLastName = view.findViewById(R.id.tvPersonLastName);
            tvPersonDni = view.findViewById(R.id.tvPersonDni);
            tvPersonCompany = view.findViewById(R.id.tvPersonCompany);
            ivPersonPhoto = view.findViewById(R.id.ivPersonPhoto);
        }
    }


}
