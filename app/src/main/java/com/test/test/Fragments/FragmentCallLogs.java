package com.test.test.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.test.test.Adapters.ViewPagerAdapter;
import com.test.test.Models.IncomingCalls;
import com.test.test.Models.MissedCalls;
import com.test.test.Models.OutgoingCalls;
import com.test.test.R;
import com.test.test.Utils.ContactFetcher;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCallLogs extends Fragment {

    private View view;
    private TabLayout tablayout;
    private ViewPager viewPager;

    private ArrayList<IncomingCalls> incomingCallsArrayList;
    private ArrayList<OutgoingCalls> outgoingCallsArrayList;
    private ArrayList<MissedCalls> missedCallsArrayList;
    private String[] tabTitles;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_call_logs, container, false);

        init();

        getCallLogs();
        setViewPagerAdapter();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calls, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refreshCalls) {
            getCallLogs();
            setViewPagerAdapter();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCallLogs() {
        Bundle bundle = new ContactFetcher(getContext()).fetchCalls();

        incomingCallsArrayList = bundle.getParcelableArrayList("incoming");
        outgoingCallsArrayList = bundle.getParcelableArrayList("outgoing");
        missedCallsArrayList = bundle.getParcelableArrayList("missed");
    }

    private void setViewPagerAdapter() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), tabTitles, incomingCallsArrayList, outgoingCallsArrayList, missedCallsArrayList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
    }

    private void init() {
        tabTitles = new String[]{"Incoming Calls", "Outgoing Calls", "Missed Calls"};
        incomingCallsArrayList = new ArrayList<>();
        outgoingCallsArrayList = new ArrayList<>();
        missedCallsArrayList = new ArrayList<>();

        tablayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewPager);
        tablayout.setupWithViewPager(viewPager);
    }
}
