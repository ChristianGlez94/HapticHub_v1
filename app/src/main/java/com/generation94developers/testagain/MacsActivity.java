package com.generation94developers.testagain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MacsActivity extends AppCompatActivity {

    EditText mac1,mac2,mac3,mac4,mac5,mac6;

    Button salvar;
    TextView meta;

    CheckBox macBox1,macBox2,macBox3,macBox4,macBox5,macBox6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macs);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mac1=findViewById(R.id.mac1);
        mac2=findViewById(R.id.mac2);
        mac3=findViewById(R.id.mac3);
        mac4=findViewById(R.id.mac4);
        mac5=findViewById(R.id.mac5);
        mac6=findViewById(R.id.mac6);
        

        salvar=findViewById(R.id.button);
        meta=findViewById(R.id.textView17);


        meta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = "https://mbientlab.com/metamotions/"; // Reemplaza con la URL que desees abrir.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);


            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MisMacs", Context.MODE_PRIVATE);
        String macs1 = sharedPreferences.getString("mac1", "EF:4C:14:4E:18:15");
        String macs2 = sharedPreferences.getString("mac2", "CB:03:47:C3:03:D9");
        String macs3 = sharedPreferences.getString("mac3", "C9:64:8B:2B:EB:13");
        String macs4 = sharedPreferences.getString("mac4", "D7:B6:29:F1:EE:B3");
        String macs5 = sharedPreferences.getString("mac5", "C8:41:5F:6F:E7:0C");
        String macs6 = sharedPreferences.getString("mac6", "FE:25:7D:8E:53:E2");



        mac1.setText(macs1);
        mac2.setText(macs2);
        mac3.setText(macs3);
        mac4.setText(macs4);
        mac5.setText(macs5);
        mac6.setText(macs6);




        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("MisMacs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String macs1=mac1.getText().toString();
                String macs2=mac2.getText().toString();
                String macs3=mac3.getText().toString();
                String macs4=mac4.getText().toString();
                String macs5=mac5.getText().toString();
                String macs6=mac6.getText().toString();

                editor.putString("mac1", macs1);
                editor.putString("mac2", macs2);
                editor.putString("mac3", macs3);
                editor.putString("mac4", macs4);
                editor.putString("mac5", macs5);
                editor.putString("mac6", macs6);
                editor.apply();


                Toast.makeText(getApplicationContext(), "MACs guardadas correctamente", Toast.LENGTH_SHORT).show();

                finish();
            }
        });




    }



}