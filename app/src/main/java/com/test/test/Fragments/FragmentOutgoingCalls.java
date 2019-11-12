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

import com.test.test.Adapters.OutgoingAdapter;
import com.test.test.Models.OutgoingCalls;
import com.test.test.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOutgoingCalls extends Fragment {

    private View view;
    private RecyclerView rvOutgoing;
    private ProgressBar pbCalls;
    private TextView tvNotAvailable;
    private ArrayList<OutgoingCalls> arrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_outgoing_calls, container, false);

        init();

        Bundle bundle = getArguments();
        if (bundle != null) {
            arrayList = bundle.getParcelableArrayList("outgoing");
        }

        setRVAdapter();
        return view;
    }

    private void setRVAdapter() {
        if (arrayList != null && arrayList.size() != 0) {
            rvOutgoing.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            OutgoingAdapter outgoingAdapter = new OutgoingAdapter(getContext(), arrayList);
            rvOutgoing.setAdapter(outgoingAdapter);

            rvOutgoing.setVisibility(View.VISIBLE);
            tvNotAvailable.setVisibility(View.GONE);
        } else {
            rvOutgoing.setVisibility(View.GONE);
            tvNotAvailable.setVisibility(View.VISIBLE);
        }

        pbCalls.setVisibility(View.GONE);
    }

    private void init() {
        rvOutgoing = view.findViewById(R.id.rvOutgoing);
        pbCalls = view.findViewById(R.id.pbCalls);
        tvNotAvailable = view.findViewById(R.id.tvNotAvailable);
    }
}
