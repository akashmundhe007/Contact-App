package com.test.test.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.test.Models.IncomingCalls;
import com.test.test.Models.SmsData;
import com.test.test.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<SmsData> arrayList;

    public SmsAdapter(Context context, ArrayList<SmsData> smsDataArrayList) {
        this.mContext = context;
        this.arrayList = smsDataArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_list_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvNumber.setText(arrayList.get(position).getNumber());
        holder.tvBody.setText(arrayList.get(position).getBody());

        SimpleDateFormat spf = new SimpleDateFormat("dd MMM");
        String date = spf.format(arrayList.get(position).getDate());
        holder.tvDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNumber, tvBody, tvDate;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
