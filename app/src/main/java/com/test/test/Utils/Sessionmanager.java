package com.test.test.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SHREE on 24/10/2018.
 */

public class Sessionmanager {

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context mContext;
    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "TestPref";
    private static final String NAV_POSITION = "position";


    public Sessionmanager(Context mcontext) {

        this.mContext = mcontext;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //set nav position to open landing page
    public int setNavposition(int position) {
        editor.putInt(NAV_POSITION, position);
        editor.commit();
        return position;
    }

    //get nav position
    public int getNavposition() {
        return pref.getInt(NAV_POSITION, 0);

    }
}
