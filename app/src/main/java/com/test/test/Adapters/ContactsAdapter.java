package com.test.test.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;
import com.test.test.Models.Contact;
import com.test.test.R;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ItemViewHolder> {
    private Context mContext;
    private ArrayList<Contact> contactArrayList;


    public ContactsAdapter(Context context, ArrayList<Contact> listContacts) {
        this.mContext = context;
        this.contactArrayList = listContacts;
    }

    @NonNull
    @Override
    public ContactsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_item, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ItemViewHolder viewHolder, int position) {
        viewHolder.tvName.setText(contactArrayList.get(position).getName());
        viewHolder.tvNumber.setText(contactArrayList.get(position).getNumber());
        String image = contactArrayList.get(position).getPhoto();

        if (image != null) {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            viewHolder.circleImageView.setImageBitmap(decodedByte);
        } else {
            viewHolder.circleImageView.setImageResource(R.drawable.ic_user);
        }
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /*private void WriteByteArrayToFile(byte[] decodedString) {
        try {
            FileOutputStream fos = new FileOutputStream(strFilePath);

            fos.write(decodedString);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvNumber;
        private CircleImageView circleImageView;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            circleImageView = itemView.findViewById(R.id.image);
            tvNumber = itemView.findViewById(R.id.tv_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(contactArrayList.get(getAdapterPosition()).getName());
                }
            });

        }
    }

    private void showDialog(final String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setTitle("Action");
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "CALL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + message));
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });

        builder1.setNegativeButton(
                "SMS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
