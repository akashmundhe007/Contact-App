package com.test.test.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.test.Adapters.MissedAdapter;
import com.test.test.Models.MissedCalls;
import com.test.test.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMissedCalls extends Fragment {

    private View view;
    private RecyclerView rvMissed;
    private ProgressBar pbCalls;
    private TextView tvNotAvailable;
    private ArrayList<MissedCalls> arrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_missed_calls, container, false);

        init();
        Bundle bundle = getArguments();
        if (bundle != null) {
            arrayList = bundle.getParcelableArrayList("missed");
        }

        setRVAdapter();
        return view;
    }

    private void setRVAdapter() {
        if (arrayList.size() != 0) {
            rvMissed.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            MissedAdapter missedAdapter = new MissedAdapter(getContext(), arrayList);
            rvMissed.setAdapter(missedAdapter);

            rvMissed.setVisibility(View.VISIBLE);
            tvNotAvailable.setVisibility(View.GONE);
        } else {
            rvMissed.setVisibility(View.GONE);
            tvNotAvailable.setVisibility(View.VISIBLE);
        }

        pbCalls.setVisibility(View.GONE);
    }

    private void init() {
        rvMissed = view.findViewById(R.id.rvMissed);
        pbCalls = view.findViewById(R.id.pbCalls);
        tvNotAvailable = view.findViewById(R.id.tvNotAvailable);
    }
}
