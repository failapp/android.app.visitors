package cl.versionbeta.app.accesscontrol.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.versionbeta.app.accesscontrol.R;
import cl.versionbeta.app.accesscontrol.model.Register;
import cl.versionbeta.app.accesscontrol.util.Util;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.RegisterViewHolder> {


    public List<Register> registerList;

    public RegisterAdapter(List<Register> registers) {
        this.registerList = registers;
    }

    @Override
    public RegisterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_list, parent, false);
        RegisterViewHolder viewHolder=new RegisterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RegisterViewHolder holder, int position) {

        holder.tvDate.setText(registerList.get(position).getDate());

        String dni = (registerList.get(position).getPerson() != null) ? registerList.get(position).getPerson().getDni().trim() : "";
        String fullName = (registerList.get(position).getPerson() != null) ? registerList.get(position).getPerson().getFirstName().trim() + " " +
                            registerList.get(position).getPerson().getLastName().trim() : "";

        String[] arr = dni.split("-");
        String dv = " - " + arr[1];
        String number = Util.decimalFormat.format(Integer.parseInt(arr[0]));

        //Log.e("CEDULA ->", number + dv);

        dni = number + dv;

        holder.tvPersonDni.setText(dni);
        holder.tvPersonName.setText(fullName);

        int type = registerList.get(position).getType();
        if (type == 0) holder.ivType.setImageResource(R.drawable.ic_letter_e);
        if (type == 1) holder.ivType.setImageResource(R.drawable.ic_letter_s);

    }

    @Override
    public int getItemCount() {
        return this.registerList.size();
    }


    public static class RegisterViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate;
        private TextView tvPersonName;
        private TextView tvPersonDni;
        private ImageView ivPhoto;
        private ImageView ivType;

        public RegisterViewHolder(View view) {
            super(view);
            tvDate = view.findViewById(R.id.tvDate);
            tvPersonName = view.findViewById(R.id.tvPersonName);
            tvPersonDni = view.findViewById(R.id.tvPersonDni);
            ivType = view.findViewById(R.id.ivType);
        }



    }



}
