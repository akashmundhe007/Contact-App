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

import com.test.test.Adapters.IncomingAdapter;
import com.test.test.Models.IncomingCalls;
import com.test.test.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentIncomingCalls extends Fragment {

    private View view;
    private RecyclerView rvIncoming;
    private ProgressBar pbCalls;
    private TextView tvNotAvailable;
    private ArrayList<IncomingCalls> arrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_incoming_calls, container, false);

        init();

        Bundle bundle = getArguments();
        if (bundle != null) {
            arrayList = bundle.getParcelableArrayList("incoming");
        }

        setRVAdapter();
        return view;
    }

    private void setRVAdapter() {
        if (arrayList.size() != 0) {
            rvIncoming.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            IncomingAdapter incomingAdapter = new IncomingAdapter(getContext(), arrayList);
            rvIncoming.setAdapter(incomingAdapter);

            rvIncoming.setVisibility(View.VISIBLE);
            tvNotAvailable.setVisibility(View.GONE);
        } else {
            rvIncoming.setVisibility(View.GONE);
            tvNotAvailable.setVisibility(View.VISIBLE);
        }

        pbCalls.setVisibility(View.GONE);
    }

    private void init() {
        rvIncoming = view.findViewById(R.id.rvIncoming);
        pbCalls = view.findViewById(R.id.pbCalls);
        tvNotAvailable = view.findViewById(R.id.tvNotAvailable);
    }
}
