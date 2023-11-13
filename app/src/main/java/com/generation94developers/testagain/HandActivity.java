package com.generation94developers.testagain;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;
import com.mbientlab.metawear.module.Haptic;

import bolts.Continuation;
import bolts.Task;

public class HandActivity extends AppCompatActivity implements ServiceConnection {
    private BtleService.LocalBinder serviceBinder;
    private MetaWearBoard[] boards; // Array para los dispositivos
    private Button vibrar,rampaasc;
    private volatile boolean isVibrating = false;

    private ImageView imageView;
    private MediaPlayer mediaPlayer;
    int inten,time,actua,retar;

    String macs1,macs2,macs3,macs4,macs5,macs6;

    CheckBox pulgar, indice, medio,anular, mennique,palma;

    Button start, configurar;

    int selectedCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getApplicationContext().bindService(new Intent(this, BtleService.class),
                this, Context.BIND_AUTO_CREATE);


        // Obtener la vista principal
        View mainView = findViewById(R.id.main_layout);


        pulgar = findViewById(R.id.checkBox);
        indice = findViewById(R.id.checkBox2);
        medio = findViewById(R.id.checkBox3);
        anular = findViewById(R.id.checkBox4);
        mennique = findViewById(R.id.checkBox5);
        palma = findViewById(R.id.checkBox6);

        start = findViewById(R.id.button3);
        configurar=findViewById(R.id.button2);

        SharedPreferences sharedPreferences = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
        String intensidad = sharedPreferences.getString("intensity", "80");
        String tiempo = sharedPreferences.getString("time", "60");
        String actuation = sharedPreferences.getString("actuation", "2");
        String retardate = sharedPreferences.getString("retardate", "60");

        inten = Integer.parseInt(intensidad);
        time = Integer.parseInt(tiempo);
        actua = Integer.parseInt(actuation);
        retar = Integer.parseInt(retardate);


        SharedPreferences shared2Preferences = getSharedPreferences("MisMacs", Context.MODE_PRIVATE);
        macs1 = shared2Preferences.getString("mac1", "EF:4C:14:4E:18:15");
        macs2 = shared2Preferences.getString("mac2", "CB:03:47:C3:03:D9");
        macs3 = shared2Preferences.getString("mac3", "C9:64:8B:2B:EB:13");
        macs4 = shared2Preferences.getString("mac4", "D7:B6:29:F1:EE:B3");
        macs5 = shared2Preferences.getString("mac5", "C8:41:5F:6F:E7:0C");
        macs6 = shared2Preferences.getString("mac6", "FE:25:7D:8E:53:E2");





        pulgar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCheckBoxes(pulgar);
            }
        });


        indice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCheckBoxes(indice);
            }
        });

        medio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCheckBoxes(medio);
            }
        });

        anular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCheckBoxes(anular);
            }
        });

        mennique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCheckBoxes(mennique);
            }
        });


        palma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCheckBoxes(palma);
            }
        });





    }



    private void updateCheckBoxes(CheckBox clickedCheckBox) {
        selectedCount = 0;

        if (pulgar.isChecked())selectedCount++;
        if (indice.isChecked()) selectedCount++;
        if (medio.isChecked()) selectedCount++;
        if (anular.isChecked()) selectedCount++;
        if (mennique.isChecked()) selectedCount++;
        if (palma.isChecked()) selectedCount++;

        // Desactiva las casillas no seleccionadas si ya hay 3 seleccionadas
        if (selectedCount >= 3) {
            if (pulgar != clickedCheckBox) {
                pulgar.setEnabled(false);
            }
            if (indice != clickedCheckBox) {
                indice.setEnabled(false);
            }
            if (medio != clickedCheckBox) {
                medio.setEnabled(false);
            }
            if (anular != clickedCheckBox) {
                anular.setEnabled(false);
            }
            if (mennique != clickedCheckBox) {
                mennique.setEnabled(false);
            }
            if (palma != clickedCheckBox) {
                palma.setEnabled(false);
            }
        } else {
            // Activa todas las casillas si no se han seleccionado 3
            pulgar.setEnabled(true);
            indice.setEnabled(true);
            medio.setEnabled(true);
            anular.setEnabled(true);
            mennique.setEnabled(true);
            palma.setEnabled(true);
        }



    }



    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        serviceBinder = (BtleService.LocalBinder) service;
        Log.i("Test", "Correct Connection");

configurar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int count=0;

        boards = new MetaWearBoard[selectedCount]; // Cambia el tamaño según la cantidad de dispositivos
        if(pulgar.isChecked()){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            retrieveBoard(macs1, count); // Primer Dispositivo
            Toast.makeText(getApplicationContext(), "Conectados los actuadores", Toast.LENGTH_SHORT).show();

           count++;
        }
        if(indice.isChecked()){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            retrieveBoard(macs2, count); // Primer Dispositivo
            Toast.makeText(getApplicationContext(), "Conectados los actuadores", Toast.LENGTH_SHORT).show();
            count++;
        }

        if(medio.isChecked()){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            retrieveBoard(macs3, count); // Primer Dispositivo
            Toast.makeText(getApplicationContext(), "Conectados los actuadores", Toast.LENGTH_SHORT).show();
            count++;
        }
        if(anular.isChecked()){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            retrieveBoard(macs4, count); // Primer Dispositivo
            Toast.makeText(getApplicationContext(), "Conectados los actuadores", Toast.LENGTH_SHORT).show();
            count++;
        }

        if(mennique.isChecked()){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            retrieveBoard(macs5, count); // Primer Dispositivo
            Toast.makeText(getApplicationContext(), "Conectados los actuadores", Toast.LENGTH_SHORT).show();
            count++;
        }

        if(palma.isChecked()){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            retrieveBoard(macs6, count); // Primer Dispositivo
            Toast.makeText(getApplicationContext(), "Conectados los actuadores", Toast.LENGTH_SHORT).show();
            count++;
        }

    }
});


start.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        startPulsePattern();
    }
});


    }





    @Override
    public void onServiceDisconnected(ComponentName componentName) {
    }

    private void retrieveBoard(String macAddr, final int deviceIndex) {
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothDevice bluetoothDevice = bluetoothManager.getAdapter().getRemoteDevice(macAddr);

        // Conexión del dispositivo
        boards[deviceIndex] = serviceBinder.getMetaWearBoard(bluetoothDevice);
        boards[deviceIndex].connectAsync().continueWith(new Continuation<Void, Void>() {
            @Override
            public Void then(Task<Void> task) throws Exception {
                if (task.isFaulted()) {
                    Log.i("Board", "No se pudo conectar al Board " + deviceIndex);
                } else {
                    Log.i("Board", "Conectado al Board " + deviceIndex + ": " + macAddr);
                }
                return null;
            }
        });
    }

    private void startPulsePattern() {
        final int totalDevices = boards.length;
        final Object lock = new Object();
        isVibrating = true; // Iniciar la vibración

        for (int i = 0; i < totalDevices; i++) {
            final int currentIndex = i;
            final MetaWearBoard currentBoard = boards[currentIndex];
            final Haptic hapticModule = currentBoard.getModule(Haptic.class);

            if (hapticModule != null) {
                Thread vibrationThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (lock) {
                                hapticModule.startMotor(inten, (short) time);
                                Thread.sleep(retar);
                                Log.i("Info", "Vibración iniciada en el dispositivo " + currentIndex);
                                lock.wait(); // Esperar la señal de desbloqueo
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                vibrationThread.start();
            } else {
                Log.e("Error", "El módulo Haptic no se ha inicializado correctamente en el dispositivo " + currentIndex);
            }
        }

        // Desbloquear todos los hilos simultáneamente
        synchronized (lock) {
            lock.notifyAll();


        }
    }

    private void stopVibrationSequence() {
        final int totalDevices = boards.length;
        final Object lock = new Object();
        isVibrating = true; // Iniciar la vibración

        for (int i = 0; i < totalDevices; i++) {
            final int currentIndex = i;
            final MetaWearBoard currentBoard = boards[currentIndex];
            final Haptic hapticModule = currentBoard.getModule(Haptic.class);

            if (hapticModule != null) {
                Thread vibrationThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (lock) {
                                hapticModule.startMotor(0, (short) 0);
                                Log.i("Info", "Vibración iniciada en el dispositivo " + currentIndex);
                                lock.wait(); // Esperar la señal de desbloqueo
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                vibrationThread.start();
            } else {
                Log.e("Error", "El módulo Haptic no se ha inicializado correctamente en el dispositivo " + currentIndex);
            }
        }

        // Desbloquear todos los hilos simultáneamente
        synchronized (lock) {
            lock.notifyAll();


        }
    }

}



