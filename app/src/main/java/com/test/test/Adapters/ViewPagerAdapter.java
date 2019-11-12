package com.test.test.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.test.test.Fragments.FragmentIncomingCalls;
import com.test.test.Fragments.FragmentMissedCalls;
import com.test.test.Fragments.FragmentOutgoingCalls;
import com.test.test.Models.IncomingCalls;
import com.test.test.Models.MissedCalls;
import com.test.test.Models.OutgoingCalls;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mTabTitles;
    private ArrayList<IncomingCalls> mIncomingCallsArrayList;
    private ArrayList<OutgoingCalls> mOutgoingCallsArrayList;
    private ArrayList<MissedCalls> mMissedCallsArrayList;


    public ViewPagerAdapter(FragmentManager fm, String[] tabTitles, ArrayList<IncomingCalls> incomingCallsArrayList, ArrayList<OutgoingCalls> outgoingCallsArrayList, ArrayList<MissedCalls> missedCallsArrayList) {
        super(fm);
        this.mTabTitles = tabTitles;
        this.mIncomingCallsArrayList = incomingCallsArrayList;
        this.mOutgoingCallsArrayList = outgoingCallsArrayList;
        this.mMissedCallsArrayList = missedCallsArrayList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentIncomingCalls fragmentIncomingCalls = new FragmentIncomingCalls();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("incoming", mIncomingCallsArrayList);
                fragmentIncomingCalls.setArguments(bundle);
                return fragmentIncomingCalls;

            case 1:
                FragmentOutgoingCalls fragmentOutgoingCalls = new FragmentOutgoingCalls();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArrayList("outgoing", mOutgoingCallsArrayList);
                fragmentOutgoingCalls.setArguments(bundle2);
                return fragmentOutgoingCalls;

            case 2:
                FragmentMissedCalls fragmentMissedCalls = new FragmentMissedCalls();
                Bundle bundle3 = new Bundle();
                bundle3.putParcelableArrayList("missed", mMissedCallsArrayList);
                fragmentMissedCalls.setArguments(bundle3);
                return fragmentMissedCalls;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabTitles.length;
    }
}
