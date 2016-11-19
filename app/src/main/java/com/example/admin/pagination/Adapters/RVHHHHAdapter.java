package com.example.admin.pagination.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Diaspora;

import java.util.List;

/**
 * Created by ulan on 11/12/16.
 */
public class RVHHHHAdapter extends RecyclerView.Adapter<RVHHHHAdapter.ViewHolder> {

    private List<Diaspora> records;
    public Context context;
    public RVHHHHAdapter(List<Diaspora> records, Context context) {
        this.records = records;
        this.context=context;
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_diaspora, viewGroup, false);
        return new ViewHolder(v);
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(ViewHolder view, int i) {
        Diaspora singleStudent = records.get(i);
       view.tvManager.setText(singleStudent.getManager());
        view.tvNumber.setText(singleStudent.getNumber());
        view. tvAddress.setText(singleStudent.getAddress());
        view.tvNumber1.setText(singleStudent.getEmail());
        view.tvName.setText(singleStudent.getCity());

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public TextView tvAddress,tvManager,tvNumber,tvNumber1,tvNumber2;

        public Diaspora student;
        public ViewHolder(View v) {
            super(v);
            tvName=(TextView) v.findViewById(R.id.tv_name_employment) ;
            tvAddress=(TextView) v.findViewById(R.id.tv_address_second) ;
            tvManager=(TextView) v.findViewById(R.id.tv_manager_employment) ;
            tvNumber=(TextView) v.findViewById(R.id.tv_phone_number_second) ;
            tvNumber1=(TextView) v.findViewById(R.id.tv_phone_number1_second) ;

            tvNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",records.get(getAdapterPosition()).getNumber() , null)));
                }
            });
        }
    }
}
