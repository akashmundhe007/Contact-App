package com.test.test.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.test.Models.IncomingCalls;
import com.test.test.Models.OutgoingCalls;
import com.test.test.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OutgoingAdapter extends RecyclerView.Adapter<OutgoingAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<OutgoingCalls> arrayList;

    public OutgoingAdapter(Context context, ArrayList<OutgoingCalls> outgoingCallsArrayList) {
        this.mContext = context;
        this.arrayList = outgoingCallsArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calls_list_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvNumber.setText(arrayList.get(position).getNumber());
        holder.tvTime.setText("Duration " + arrayList.get(position).getDuration() + "s");

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

        private TextView tvNumber, tvDate, tvTime;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + arrayList.get(getAdapterPosition()).getNumber()));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
