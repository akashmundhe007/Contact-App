package com.test.test.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;

import android.provider.Telephony;
import android.util.Base64;

import androidx.loader.content.CursorLoader;

import com.test.test.Models.Contact;
import com.test.test.Models.IncomingCalls;
import com.test.test.Models.MissedCalls;
import com.test.test.Models.OutgoingCalls;
import com.test.test.Models.SmsData;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ContactFetcher {
    private final Context context;

    //Contacts
    private Bitmap bitmap;
    private String contactNumber;
    private String encodedImage = "null";
    private ArrayList<Contact> listContacts;

    //Call history
    private ArrayList<IncomingCalls> incomingArraylist;
    private ArrayList<OutgoingCalls> outgoingArraylist;
    private ArrayList<MissedCalls> missedArraylist;

    //SMS inbox
    private ArrayList<SmsData> smsArraylist;

    public ContactFetcher(Context c) {
        this.context = c;
    }

    public ArrayList<Contact> fetchContacts() {
        getContactDetails();
        return listContacts;
    }

    public Bundle fetchCalls() {
        getCallDetails();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("incoming", incomingArraylist);
        bundle.putParcelableArrayList("outgoing", outgoingArraylist);
        bundle.putParcelableArrayList("missed", missedArraylist);
        return bundle;
    }

    public ArrayList<SmsData> fetchSms() {
        getSMSDetails();
        return smsArraylist;
    }

    //To get contact details from content provider
    private void getContactDetails() {
        String[] projectionFields = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI,
                ContactsContract.Contacts.HAS_PHONE_NUMBER

        };
        listContacts = new ArrayList<>();
        CursorLoader cursorLoader = new CursorLoader(context,
                ContactsContract.Contacts.CONTENT_URI,
                projectionFields, // the columns to retrieve
                null, // the selection criteria (none)
                null, // the selection args (none)
                null // the sort order (default)
        );


        Cursor c = cursorLoader.loadInBackground();


        if (c.moveToFirst()) {
            int idIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
            int nameIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            do {
                String contactId = c.getString(idIndex);
                String contactDisplayName = c.getString(nameIndex);
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    contactNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                Uri contactPhotoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(Uri.encode(contactId)));

                InputStream photoDataStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), contactPhotoUri); // <-- always null
                bitmap = BitmapFactory.decodeStream(photoDataStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                } else {
                    encodedImage = null;
                }

                Contact contact = new Contact();
                contact.setId(contactId);
                contact.setName(contactDisplayName);
                contact.setNumber(contactNumber);
                contact.setPhoto(encodedImage);
                listContacts.add(contact);
            } while (c.moveToNext());
        }

        c.close();
    }

    //To get call details
    private void getCallDetails() {

        String[] projectionFields = new String[]{
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION
        };

        incomingArraylist = new ArrayList<>();
        outgoingArraylist = new ArrayList<>();
        missedArraylist = new ArrayList<>();

        CursorLoader cursorLoader = new CursorLoader(context,
                CallLog.Calls.CONTENT_URI,
                projectionFields, // the columns to retrieve
                null, // the selection criteria (none)
                null, // the selection args (none)
                null // the sort order (default)
        );

        Cursor c = cursorLoader.loadInBackground();

        if (c.moveToFirst()) {
            int name = c.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int number = c.getColumnIndex(CallLog.Calls.NUMBER);
            int type = c.getColumnIndex(CallLog.Calls.TYPE);
            int date = c.getColumnIndex(CallLog.Calls.DATE);
            int duration = c.getColumnIndex(CallLog.Calls.DURATION);

            do {
                String contactDisplayName = c.getString(name);
                String phNumber = c.getString(number);
                String callType = c.getString(type);
                String callDate = c.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = c.getString(duration);

                int dircode = Integer.parseInt(callType);

                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        OutgoingCalls outgoingCalls = new OutgoingCalls();
                        outgoingCalls.setName(contactDisplayName);
                        outgoingCalls.setNumber(phNumber);
                        outgoingCalls.setDate(callDayTime);
                        outgoingCalls.setDuration(callDuration);

                        outgoingArraylist.add(outgoingCalls);
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        IncomingCalls incomingCalls = new IncomingCalls();
                        incomingCalls.setName(contactDisplayName);
                        incomingCalls.setNumber(phNumber);
                        incomingCalls.setDate(callDayTime);
                        incomingCalls.setDuration(callDuration);

                        incomingArraylist.add(incomingCalls);
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        MissedCalls missedCalls = new MissedCalls();
                        missedCalls.setName(contactDisplayName);
                        missedCalls.setNumber(phNumber);
                        missedCalls.setDate(callDayTime);
                        missedCalls.setDuration(callDuration);

                        missedArraylist.add(missedCalls);
                        break;
                }
            } while (c.moveToNext());
        }

        c.close();
    }

    //To get sms details
    private void getSMSDetails() {
        String[] projectionFields = new String[]{
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.DATE,
        };

        smsArraylist = new ArrayList<>();

        CursorLoader cursorLoader = new CursorLoader(context,
                Telephony.Sms.CONTENT_URI,
                projectionFields, // the columns to retrieve
                null, // the selection criteria (none)
                null, // the selection args (none)
                null // the sort order (default)
        );

        Cursor c = cursorLoader.loadInBackground();

        if (c.moveToFirst()) {
            int address = c.getColumnIndex(Telephony.Sms.ADDRESS);
            int body = c.getColumnIndex(Telephony.Sms.BODY);
            int date = c.getColumnIndex(Telephony.Sms.DATE);

            do {
                String contactAddress = c.getString(address);
                String smsBody = c.getString(body);
                String smsDate = c.getString(date);
                Date dayTime = new Date(Long.valueOf(smsDate));

                SmsData smsData = new SmsData();
                smsData.setNumber(contactAddress);
                smsData.setBody(smsBody);
                smsData.setDate(dayTime);

                smsArraylist.add(smsData);
            } while (c.moveToNext());
        }

        c.close();
    }
}
