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

import com.test.test.Adapters.ContactsAdapter;
import com.test.test.Models.Contact;
import com.test.test.R;
import com.test.test.Utils.ContactFetcher;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentContacts extends Fragment {

    private View view;
    private RecyclerView rvContacts;
    private ProgressBar pbContacts;
    private TextView tvNotAvailable;

    private ArrayList<Contact> contactList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contacts, container, false);

        init();
        getContacts();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contact, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refreshContacts){
            getContacts();
        }
        return super.onOptionsItemSelected(item);
    }

    //get contact list and set to adapter
    private void getContacts() {
        contactList = new ArrayList<>();
        contactList = new ContactFetcher(getContext()).fetchContacts();

        if (contactList.size() != 0) {
            rvContacts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            ContactsAdapter contactsAdapter = new ContactsAdapter(getContext(), contactList);
            rvContacts.setAdapter(contactsAdapter);

            rvContacts.setVisibility(View.VISIBLE);
            tvNotAvailable.setVisibility(View.GONE);
        } else {
            rvContacts.setVisibility(View.GONE);
            tvNotAvailable.setVisibility(View.VISIBLE);
        }

        pbContacts.setVisibility(View.GONE);
    }

    //Initialize all views and variables
    private void init() {

        rvContacts = view.findViewById(R.id.rvContacts);
        pbContacts = view.findViewById(R.id.pbContacts);
        tvNotAvailable = view.findViewById(R.id.tvNotAvailable);
    }
}
