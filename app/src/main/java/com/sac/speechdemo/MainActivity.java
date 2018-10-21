package com.sac.speechdemo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.Theme;
import com.example.user.speechrecognizationasservice.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button btStartService;
    private TextView tvText;
    private TextView textViews[];
    private String phoneNumbers[];


    private static final int RESULT_PICK_CONTACT[] = {
            0,
            1,
            2};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViews= new TextView[]{
                (TextView) findViewById(R.id.tvContactName),
                (TextView) findViewById(R.id.tvContactNumber),

                (TextView) findViewById(R.id.tvContactName2),
                (TextView) findViewById(R.id.tvContactNumber2),

                (TextView) findViewById(R.id.tvContactName3),
                (TextView) findViewById(R.id.tvContactNumber3)
        };
        phoneNumbers = new String[3];
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> saveToFile());


        btStartService = (Button) findViewById(R.id.btStartService);
        tvText = (TextView) findViewById(R.id.tvText);
        //Some devices will not allow background service to work, So we have to enable autoStart for the app.
        //As per now we are not having any way to check autoStart is enable or not,so better to give this in LoginArea,
        //so user will not get this popup again and again until he logout
        enableAutoStart();

        if (checkServiceRunning()) {
            btStartService.setText(getString(R.string.stop_service));
            tvText.setVisibility(View.VISIBLE);
        }

        btStartService.setOnClickListener(v -> {
            if (btStartService.getText().toString().equalsIgnoreCase(getString(R.string.start_service))) {
                startService(new Intent(MainActivity.this, MyService.class));
                btStartService.setText(getString(R.string.stop_service));
                tvText.setVisibility(View.VISIBLE);
            } else {
                stopService(new Intent(MainActivity.this, MyService.class));
                btStartService.setText(getString(R.string.start_service));
                tvText.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void enableAutoStart() {
        for (Intent intent : Constants.AUTO_START_INTENTS) {
            if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                new Builder(this).title(R.string.enable_autostart)
                        .content(R.string.ask_permission)
                        .theme(Theme.LIGHT)
                        .positiveText(getString(R.string.allow))
                        .onPositive((dialog, which) -> {
                            try {
                                for (Intent intent1 : Constants.AUTO_START_INTENTS)
                                    if (getPackageManager().resolveActivity(intent1, PackageManager.MATCH_DEFAULT_ONLY)
                                            != null) {
                                        startActivity(intent1);
                                        break;
                                    }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        })
                        .show();
                break;
            }
        }
    }

    public boolean checkServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                    Integer.MAX_VALUE)) {
                if (getString(R.string.my_service_name).equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void pickContact(View v){
        int buttonIndex = Integer.parseInt(v.getTag().toString()) - 1;
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT[buttonIndex]);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int arrIndex = requestCode;
        if (resultCode == RESULT_OK) {
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
                textViews[arrIndex*2].setText("Contact Name : ".concat(contactName));
                textViews[arrIndex*2+1].setText("Contact Number : ".concat(contactNumber));
                phoneNumbers[arrIndex] = (contactNumber);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void saveToFile(){
        Toast.makeText(getApplicationContext(),"Contacts Saved! :)",Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        String data = gson.toJson(Arrays.asList(phoneNumbers));
        Context context = getApplicationContext();
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
