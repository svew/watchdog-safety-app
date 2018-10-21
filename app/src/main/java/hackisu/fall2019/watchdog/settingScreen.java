package hackisu.fall2019.watchdog;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class settingScreen extends AppCompatActivity {

     List<String> phoneNumbers = new ArrayList<>();
    private static final int RESULT_PICK_CONTACT = 1001;
    private static final int RESULT_PICK_CONTACT2 = 1011;
    private static final int RESULT_PICK_CONTACT3 = 1111;
    TextView tvContactName, tvContactNumber;
    TextView tvContactName2, tvContactNumber2;
    TextView tvContactName3, tvContactNumber3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_screen);


        tvContactName = (TextView) findViewById(R.id.tvContactName);
        tvContactNumber = (TextView) findViewById(R.id.tvContactNumber);

        tvContactName2 = (TextView) findViewById(R.id.tvContactName2);
        tvContactNumber2 = (TextView) findViewById(R.id.tvContactNumber2);

        tvContactName3 = (TextView) findViewById(R.id.tvContactName3);
        tvContactNumber3 = (TextView) findViewById(R.id.tvContactNumber3);
    }

     public void pickContact(View v) {

        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    public void pickContact2(View v) {

        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT2);
    }

    public void pickContact3(View v) {

        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT3);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    try {
                        String contactNumber = null;
                        String contactName = null;
                        // getData() method will have the
                        // Content Uri of the selected contact
                        Uri uri = data.getData();
                        //Query the content uri
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        // column index of the phone number
                        int phoneIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);
                        // column index of the contact name
                        int nameIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        contactNumber = cursor.getString(phoneIndex);
                        contactName = cursor.getString(nameIndex);
                        // Set the value to the textviews
                        tvContactName.setText("Contact Name : ".concat(contactName));
                        tvContactNumber.setText("Contact Number : ".concat(contactNumber));
                        phoneNumbers.add(contactNumber);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


                case RESULT_PICK_CONTACT2:
                    Cursor cursor2 = null;
                    try {
                        String contactNumber = null;
                        String contactName = null;
                        // getData() method will have the
                        // Content Uri of the selected contact
                        Uri uri = data.getData();
                        //Query the content uri
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        // column index of the phone number
                        int phoneIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);
                        // column index of the contact name
                        int nameIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        contactNumber = cursor.getString(phoneIndex);
                        contactName = cursor.getString(nameIndex);
                        // Set the value to the textviews
                        tvContactName2.setText("Contact Name : ".concat(contactName));
                        tvContactNumber2.setText("Contact Number : ".concat(contactNumber));
                        phoneNumbers.add(contactNumber);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


                case RESULT_PICK_CONTACT3:
                    Cursor cursor3 = null;
                    try {
                        String contactNumber = null;
                        String contactName = null;
                        // getData() method will have the
                        // Content Uri of the selected contact
                        Uri uri = data.getData();
                        //Query the content uri
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        // column index of the phone number
                        int phoneIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);
                        // column index of the contact name
                        int nameIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        contactNumber = cursor.getString(phoneIndex);
                        contactName = cursor.getString(nameIndex);
                        // Set the value to the textviews
                        tvContactName3.setText("Contact Name : ".concat(contactName));
                        tvContactNumber3.setText("Contact Number : ".concat(contactNumber));
                        phoneNumbers.add(contactNumber);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;






            }

        }
    }






}
