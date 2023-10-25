package com.generation94developers.testagain;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView superficie,title1,title2,title3,title4,title5;
    Button inicio,salvar;
    Switch configuracion;

    EditText intensidad, tiempo, actuadores, retardo;

    ImageView logo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       logo=findViewById(R.id.imageView);

       logo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String url = "https://sites.google.com/cicese.edu.mx/veritasresearchlab/vision?authuser=0"; // Reemplaza con la URL que desees abrir.
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
               startActivity(intent);

           }
       });

        superficie=findViewById(R.id.superficies);
        inicio=findViewById(R.id.iniciar);
        salvar=findViewById(R.id.salvar);
        configuracion=findViewById(R.id.switch1);
        intensidad=findViewById(R.id.intensidad);
        tiempo=findViewById(R.id.tiempo);
        actuadores=findViewById(R.id.actuadores);
        retardo=findViewById(R.id.retardo);

        title1=findViewById(R.id.textView5);
        title2=findViewById(R.id.textView6);
        title3=findViewById(R.id.textView2);
        title4=findViewById(R.id.textView4);
        title5=findViewById(R.id.configmacs);
        title1.setVisibility(View.INVISIBLE);
        title2.setVisibility(View.INVISIBLE);
        title3.setVisibility(View.INVISIBLE);
        title4.setVisibility(View.INVISIBLE);
        title5.setVisibility(View.INVISIBLE);
        intensidad.setVisibility(View.INVISIBLE);
        tiempo.setVisibility(View.INVISIBLE);
        actuadores.setVisibility(View.INVISIBLE);
        retardo.setVisibility(View.INVISIBLE);
        salvar.setVisibility(View.INVISIBLE);



        SharedPreferences sharedPreferences = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        configuracion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Aquí puedes ejecutar la acción que desees cuando el Switch cambie su estado.
                if (isChecked) {
                    // El Switch está activado.
                    // Ejecuta la acción correspondiente aquí.

                    title1.setVisibility(View.VISIBLE);
                    title2.setVisibility(View.VISIBLE);
                    title3.setVisibility(View.VISIBLE);
                    title4.setVisibility(View.VISIBLE);
                    title5.setVisibility(View.VISIBLE);

                    intensidad.setVisibility(View.VISIBLE);
                    tiempo.setVisibility(View.VISIBLE);
                    salvar.setVisibility(View.VISIBLE);
                    actuadores.setVisibility(View.VISIBLE);
                    retardo.setVisibility(View.VISIBLE);

                } else {
                    // El Switch está desactivado.
                    // Ejecuta la acción correspondiente aquí.


                    SharedPreferences sharedPreferences = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    title1.setVisibility(View.INVISIBLE);
                    title2.setVisibility(View.INVISIBLE);
                    title3.setVisibility(View.INVISIBLE);
                    title4.setVisibility(View.INVISIBLE);
                    title5.setVisibility(View.INVISIBLE);
                    intensidad.setVisibility(View.INVISIBLE);
                    tiempo.setVisibility(View.INVISIBLE);
                    salvar.setVisibility(View.INVISIBLE);
                    actuadores.setVisibility(View.INVISIBLE);
                    retardo.setVisibility(View.INVISIBLE);

                }
            }
        });

title5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MacsActivity.class);
        startActivity(intent);

    }
});

        superficie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.lista, popupMenu.getMenu());

                // Configura un escuchador de clics para los elementos del menú
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String opcionSeleccionada = item.getTitle().toString();
                        superficie.setText(opcionSeleccionada);
                        return true;
                    }
                });

                // Muestra el menú emergente
                popupMenu.show();

            }
        });



inicio.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(superficie.getText().equals("Solo Vibración")){
            Intent intent = new Intent(getApplicationContext(), OnlyVibrationActivity.class);
            startActivity(intent);
        }

        if(superficie.getText().equals("Arena")){
            Intent intent = new Intent(getApplicationContext(), ArenaActivity.class);
            startActivity(intent);
        }

        if(superficie.getText().equals("Peine")){
            Intent intent = new Intent(getApplicationContext(), PieneActivity.class);
            startActivity(intent);
        }

        if(superficie.getText().equals("Mano")){
            Intent intent = new Intent(getApplicationContext(), HandActivity.class);
            startActivity(intent);
        }

    }
});
salvar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        SharedPreferences sharedPreferences = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();


            String intensity=intensidad.getText().toString();
            String time=tiempo.getText().toString();
            String actuation=actuadores.getText().toString();
            String retardate=retardo.getText().toString();

            editor.putString("intensity", intensity);
            editor.putString("time",time);
            editor.putString("actuation",actuation);
            editor.putString("retardate",retardate);
            editor.apply();

            Toast.makeText(getApplicationContext(), "Configuración guardada correctamente", Toast.LENGTH_SHORT).show();
        }

});

    }
}
