package com.test.test.Activities;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.test.test.Fragments.FragmentCallLogs;
import com.test.test.Fragments.FragmentContacts;
import com.test.test.Fragments.FragmentSmsInbox;
import com.test.test.R;
import com.test.test.Utils.Sessionmanager;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.widget.Toast;

import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_SMS;
import static com.test.test.Utils.ConstantVariables.REQUEST_PERMISSION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    private Fragment fragment = null;
    private String title;

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private Sessionmanager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setupToolbar();
        setPermissions();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_contacts:
                fragment = new FragmentContacts();
                title = getResources().getString(R.string.menu_contact);
                sessionmanager.setNavposition(0);
                break;

            case R.id.nav_calls:
                fragment = new FragmentCallLogs();
                title = getResources().getString(R.string.menu_call);
                sessionmanager.setNavposition(1);
                break;

            case R.id.nav_sms:
                fragment = new FragmentSmsInbox();
                title = getResources().getString(R.string.menu_sms);
                sessionmanager.setNavposition(2);
                break;
        }

        loadFragment(fragment, title);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setPermissions() {
        if (checkPermission()) {
            defaultLoadFragment();
        } else {
            requestPermission();
        }
    }

    private void defaultLoadFragment() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        switch (sessionmanager.getNavposition()) {
            case 0:
                fragment = new FragmentContacts();
                title = getResources().getString(R.string.menu_contact);
                break;

            case 1:
                fragment = new FragmentCallLogs();
                title = getResources().getString(R.string.menu_call);
                break;

            case 2:
                fragment = new FragmentSmsInbox();
                title = getResources().getString(R.string.menu_sms);
                break;
        }

        loadFragment(fragment, title);
    }

    private void loadFragment(Fragment fragment, String title) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.flContainer, fragment);
            ft.commit();
            getSupportActionBar().setTitle(title);
        }
    }

    //check runtime permission
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, READ_CONTACTS);
        int result1 = ContextCompat.checkSelfPermission(this, READ_CALL_LOG);
        int result2 = ContextCompat.checkSelfPermission(this, READ_SMS);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS, READ_CALL_LOG, READ_SMS}, REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0) {
                boolean contactPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean callPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean SmsPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                if (contactPermission && callPermission && SmsPermission) {
                    defaultLoadFragment();
                } else {
                    Toast.makeText(this, getString(R.string.permission), Toast.LENGTH_SHORT).show();
                    requestPermission();
                }
            }
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    private void init() {
        sessionmanager = new Sessionmanager(this);
        toolbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }
}
