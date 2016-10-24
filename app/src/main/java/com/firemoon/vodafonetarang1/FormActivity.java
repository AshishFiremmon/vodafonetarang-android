package com.firemoon.vodafonetarang1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Db.Contact;
import Db.DatabaseHandler;
import Util.AppUrl;
import Util.DialogInterfacecustom;
import web.CheckInternetConnectio;
import web.IResponse;
import web.Web;

/**
 * A login screen that offers login via email/password.
 */
public class FormActivity extends AppCompatActivity implements IResponse {

EditText msisdn,name,address,landmark,city,zone,issuetype,subissue,issue,altnumber,latitude,longitude,issuetime;

    ProgressDialog mProgressDialog;
    private Handler handler;
    RadioButton g2,g3,g4;

    ArrayList<String> list=new ArrayList<>();
    String date;
    GPSTracker tracker;
Contact contact;
    String[] issueType = new String[]{
            "Network",
            "Data",
            "Both"
    };
    String[] subIssue = new String[]{
            "4G accessibility",
            "3G accessibility",
            "2G accessibility",
            "4G speed related",
            "3G speed related",
            "2G speed related",
            "Coverage issue",
            "Voice cracking",
            "Call drop issue"
    };
    String[] timeIssue = new String[]{
            "Everytime",
            "Morning",
            "Evening",
            "Afternoon",

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_form);
        list.clear();
        list.add("Faizabad");
        list.add("Varanasi");
        list.add("Gorakhpur");
        list.add("Lucknow");
        list.add("Kanpur");
        list.add("Allahabad");
        list.add("Azamgarh");
      /*  Spinner  mySpinner = (Spinner) findViewById(R.id.subissue);
        MyArrayAdapter ma = new MyArrayAdapter(this);
        mySpinner.setAdapter(ma);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Change the selected item's text color
                if( ((TextView) view).getText().toString().equals(number[0]))
                {

                }else {
                    ((TextView) view).setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/


        handler=new Handler();
        subissue=(EditText)findViewById(R.id.subissue);
        longitude=(EditText)findViewById(R.id.longitude);
        latitude=(EditText)findViewById(R.id.lat);
        msisdn=(EditText)findViewById(R.id.msisdntxt);
        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.address);
        landmark=(EditText)findViewById(R.id.landmark);
        city=(EditText)findViewById(R.id.city);
        zone=(EditText)findViewById(R.id.zone);
        issuetype=(EditText)findViewById(R.id.issueType);
        altnumber=(EditText)findViewById(R.id.altnumber);
        issuetime=(EditText)findViewById(R.id.issuetime);

        issue=(EditText)findViewById(R.id.issue);
        g2=(RadioButton)findViewById(R.id.radio_2g);
        g3=(RadioButton)findViewById(R.id.radio_3g);
        g4=(RadioButton)findViewById(R.id.radio_4g);


        tracker = new GPSTracker(FormActivity.this);


      /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to use your current location");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {

                GPSTracker tracker = new GPSTracker(FormActivity.this);
                if (!tracker.canGetLocation()) {
//                    tracker.showSettingsAlert();
                    showSettingsAlert();
                } else {
                    address.setText(tracker.getAddressLine(FormActivity.this));
                    city.setText(tracker.getLocality(FormActivity.this));

                    landmark.setText(tracker.getFeatureName(FormActivity.this));
                    zone.setText(tracker.getLocality(FormActivity.this));
                    longitude.setText(tracker.getLatitude()+"");
                    latitude.setText(tracker.getLongitude()+"");
                    if(!city.getText().toString().equals("")) {
                        city.setFocusable(false);

                    }else
                    {

                    }
                    if(!zone.getText().toString().equals("")) {
                        zone.setFocusable(false);
                        zone.setPressed(false);
                        zone.setOnClickListener(null);
                    }
                    longitude.setFocusable(false);
                    latitude.setFocusable(false);


                }
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                longitude.setVisibility(View.GONE);
                latitude.setVisibility(View.GONE);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
*/
      /*  FormActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });*/


       if(AppUrl.currentLocation)
        {

            mProgressDialog = ProgressDialog.show(FormActivity.this, null,
                    "Please Wait....", true);
            mProgressDialog.setCancelable(true);

            FormActivity.this.runOnUiThread(new Runnable() {
                String addTxt=tracker.getAddressLine(FormActivity.this);
                String cityTxt=tracker.getLocality(FormActivity.this);
                String   landmarkTxt=tracker.getFeatureName(FormActivity.this);
                String zoneTxt=tracker.getLocality(FormActivity.this);
                String longitudeTxt=tracker.getLongitude()+"";
                String latitudeTxt=tracker.getLatitude()+"";

                @Override
                public void run() {
                    // your stuff to update the UI
                    address.setText(addTxt);
                    city.setText(cityTxt);
                    landmark.setText(landmarkTxt);
                    zone.setText(zoneTxt);
                    longitude.setText(longitudeTxt);
                    latitude.setText(latitudeTxt);
                    if(!city.getText().toString().equals("")) {
                        city.setFocusable(false);

                    }else
                    {

                    }
                    if(!zone.getText().toString().equals("")) {
                        zone.setFocusable(false);
                        zone.setPressed(false);
                        zone.setOnClickListener(null);
                    }
                    longitude.setFocusable(false);
                    latitude.setFocusable(false);

                    mProgressDialog.cancel();
                }
            });




        }else {
            longitude.setVisibility(View.GONE);
            latitude.setVisibility(View.GONE);
        }


       CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                FormActivity. this);

     /*   if(_checkInternetConnection.checkInterntConnection()) {
            zoneList();
        }else
        {
            Toast.makeText(FormActivity.this, "Check Internet Connection",
                    Toast.LENGTH_SHORT).show();


        }*/
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                        FormActivity. this);

                if(_checkInternetConnection.checkInterntConnection()) {

                    if (TextUtils.isEmpty(msisdn.getText().toString()) || TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(address.getText().toString()) || TextUtils.isEmpty(landmark.getText().toString()) || TextUtils.isEmpty(city.getText().toString()) || TextUtils.isEmpty(zone.getText().toString()) || TextUtils.isEmpty(issuetype.getText().toString()) || TextUtils.isEmpty(altnumber.getText().toString()) || TextUtils.isEmpty(issue.getText().toString())) {
                        Toast.makeText(FormActivity.this, "All Field are required", Toast.LENGTH_SHORT).show();


                    } else {
                        DatabaseHandler    db = new DatabaseHandler(FormActivity.this);
                        Date oldDate = new Date();
                        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date=    destFormat.format(oldDate);
                        contact=     new Contact( date, 1+"");
                        db.addContact(contact);

                              complain();
                        }

                    }else
                {
                    Toast.makeText(FormActivity.this, "Check Internet Connection",
                            Toast.LENGTH_SHORT).show();


                }

            }
        });


zone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this,R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Select your Zone");
        builder.setItems(list.toArray(new String[list.size()]), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                zone.setText(list.get(item));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
});

        issuetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

     /*   final CharSequence[] items = {
                "Rajesh", "Mahesh", "Vijayakumar"
        };
*/
                AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this,R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Select Issue");
                builder.setItems(issueType, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        issuetype.setText(issueType[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        subissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this,R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Select Sub Issue");
                builder.setItems(subIssue, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        subissue.setText(subIssue[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        issuetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this,R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Select Issue Time");
                builder.setItems(timeIssue, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        issuetime.setText(timeIssue[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
          }
    @Override
    public void onComplete(final String result,final int i) {


        mProgressDialog.cancel();

        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (i == 100) {
                    try {
                        if (obj.getString("error").equals("false")) {
                            startActivity(new Intent(FormActivity.this, ResultActivity.class));
                            Toast.makeText(FormActivity.this, "Successfully Submited....", Toast.LENGTH_LONG).show();
                            JSONObject jObj2 = obj.getJSONObject("data");
                            SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("msisdn", jObj2.getString("msisdn"));
                            editor.putString("sim_type", jObj2.getString("sim_type"));
                            editor.putString("name", jObj2.getString("name"));
                            editor.putString("address", jObj2.getString("address"));
                            editor.putString("landmark", jObj2.getString("landmark"));
                            editor.putString("city", jObj2.getString("city"));
                            editor.putString("zone", jObj2.getString("zone"));
                            editor.putString("issue_type", jObj2.getString("issue_type"));
                            editor.putString("issue", jObj2.getString("issue"));
                            editor.putString("alt_number", jObj2.getString("alt_number"));
                            editor.putString("exp_close_date", jObj2.getString("expected_closer_date"));
                            editor.commit();
                            DatabaseHandler db = new DatabaseHandler(FormActivity.this);
                            db.deleteContact(contact);
                        } else {
                            DialogInterfacecustom.loginResponceDialog(FormActivity.this, obj.getString("message").toString(), "");
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if (i == 200) {
                    try {

                        if (obj.getString("error").equals("false")) {

                            JSONArray array = obj.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jObj1 = array.getJSONObject(i);
                                list.add(jObj1.getString("zone_name"));

                            }

                        }
                    } catch (JSONException e) {

                    }


                }
            }
        });

    }
    public void complain() {
        mProgressDialog = ProgressDialog.show(FormActivity.this, null,
                "Please Wait....", true);
        mProgressDialog.setCancelable(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();

                SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


                data.add(new BasicNameValuePair("user_id",sharedpreferences.getString("USER_ID", "")));

                data.add(new BasicNameValuePair("msisdn", msisdn.getText().toString()));
                data.add(new BasicNameValuePair("name",  name.getText().toString()));
                data.add(new BasicNameValuePair("address", address.getText().toString()));
                data.add(new BasicNameValuePair("landmark",  landmark.getText().toString()));

                data.add(new BasicNameValuePair("city", city.getText().toString()));

                data.add(new BasicNameValuePair("zone", zone.getText().toString()));

                data.add(new BasicNameValuePair("issue_type", issuetype.getText().toString()));
                data.add(new BasicNameValuePair("issue",  issue.getText().toString()));

                data.add(new BasicNameValuePair("alt_number", altnumber.getText().toString()));
                data.add(new BasicNameValuePair("sub_issue_type", subissue.getText().toString()));
                data.add(new BasicNameValuePair("issue_time", issuetime.getText().toString()));


                if(g2.isChecked()) {
                    data.add(new BasicNameValuePair("sim_type", "1"));
                }else   if(g3.isChecked()) {
                    data.add(new BasicNameValuePair("sim_type", "2"));
                }else   if(g4.isChecked()) {
                    data.add(new BasicNameValuePair("sim_type", "3"));
                }
                data.add(new BasicNameValuePair("lat", latitude.getText().toString()));

                data.add(new BasicNameValuePair("long",  longitude.getText().toString()));

                new Web().requestPostStringData(AppUrl.complainUrl, data, FormActivity.this, 100);
            }
        }).start();
    }
    public void zoneList() {
        mProgressDialog = ProgressDialog.show(FormActivity.this, null,
                "Please Wait....", true);
        mProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                new Web().requestGet(AppUrl.zoneUrl, FormActivity.this, 200);
            }
        }).start();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            switch (requestCode) {
                case 1:
                    address.setText(tracker.getAddressLine(FormActivity.this));
                    city.setText(tracker.getLocality(FormActivity.this));
                    landmark.setText(tracker.getFeatureName(FormActivity.this));
                    zone.setText(tracker.getLocality(FormActivity.this));
                    longitude.setText(tracker.getLatitude()+"");
                    latitude.setText(tracker.getLongitude()+"");
                    break;
            }
        }
    }
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
   /* public JSONObject getLocationInfo() {

        HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=true");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
*/

   /* JSONObject ret = getLocationInfo();
    JSONObject location;
    String location_string;
    try {
        location = ret.getJSONArray("results").getJSONObject(0);
        location_string = location.getString("formatted_address");
        Log.d("test", "formattted address:" + location_string);
    } catch (JSONException e1) {
        e1.printStackTrace();

    }*/
}

