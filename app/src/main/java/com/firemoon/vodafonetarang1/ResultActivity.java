package com.firemoon.vodafonetarang1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * A login screen that offers login via email/password.
 */
public class ResultActivity extends AppCompatActivity {


    // UI references.
  TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,txt11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.detail_screen);
        txt1=(TextView)findViewById(R.id.txt1);
        txt2=(TextView)findViewById(R.id.txt2);
        txt3=(TextView)findViewById(R.id.txt3);
        txt4=(TextView)findViewById(R.id.txt4);
        txt5=(TextView)findViewById(R.id.txt5);
        txt6=(TextView)findViewById(R.id.txt6);
        txt7=(TextView)findViewById(R.id.txt7);
        txt8=(TextView)findViewById(R.id.txt8);
        txt9=(TextView)findViewById(R.id.txt9);
        txt10=(TextView)findViewById(R.id.txt10);
        txt11=(TextView)findViewById(R.id.txt11);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        txt1.setText(sharedpreferences.getString("msisdn",""));
        txt2.setText(sharedpreferences.getString("name",""));
        txt3.setText(sharedpreferences.getString("address",""));
        txt4.setText(sharedpreferences.getString("landmark",""));
        txt5.setText(sharedpreferences.getString("city",""));
        txt6.setText(sharedpreferences.getString("zone",""));
        txt7.setText(sharedpreferences.getString("issue_type",""));
        txt8.setText(sharedpreferences.getString("issue",""));
        txt9.setText(sharedpreferences.getString("alt_number",""));
        txt10.setText(sharedpreferences.getString("sim_type",""));
        txt11.setText(sharedpreferences.getString("exp_close_date",""));






    }


}

