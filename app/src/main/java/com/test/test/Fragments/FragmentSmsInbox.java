package com.test.test.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.test.Adapters.SmsAdapter;
import com.test.test.Models.SmsData;
import com.test.test.R;
import com.test.test.Utils.ContactFetcher;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSmsInbox extends Fragment {

    private View view;
    private RecyclerView rvSms;
    private ProgressBar pbCalls;
    private TextView tvNotAvailable;
    private ArrayList<SmsData> arrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sms_inbox, container, false);

        init();
        setSmsAdapter();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sms, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refreshSms){
            setSmsAdapter();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSmsAdapter() {
        arrayList = new ContactFetcher(getContext()).fetchSms();

        if (arrayList.size() != 0) {
            rvSms.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            SmsAdapter smsAdapter = new SmsAdapter(getContext(), arrayList);
            rvSms.setAdapter(smsAdapter);

            rvSms.setVisibility(View.VISIBLE);
            tvNotAvailable.setVisibility(View.GONE);
        } else {
            rvSms.setVisibility(View.GONE);
            tvNotAvailable.setVisibility(View.VISIBLE);
        }
        pbCalls.setVisibility(View.GONE);
    }

    private void init() {
        rvSms = view.findViewById(R.id.rvSms);
        pbCalls = view.findViewById(R.id.pbCalls);
        tvNotAvailable = view.findViewById(R.id.tvNotAvailable);
    }
}
