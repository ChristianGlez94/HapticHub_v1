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
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mbientlab.metawear.MetaWearBoard;
import com.mbientlab.metawear.android.BtleService;
import com.mbientlab.metawear.module.Haptic;

import bolts.Continuation;
import bolts.Task;

public class OnlyVibrationActivity extends AppCompatActivity implements ServiceConnection {
    private BtleService.LocalBinder serviceBinder;
    private MetaWearBoard[] boards; // Array para los dispositivos
    private Button vibrar,rampaasc;
    private volatile boolean isVibrating = false;
    private boolean isFingerDown = false; // Variable para rastrear el estado del dedo

    private ImageView imageView;
    private MediaPlayer mediaPlayer;
    int inten,time,actua,retar;

    String macs1,macs2,macs3,macs4,macs5,macs6;
    private PowerManager.WakeLock wakeLock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlyvibration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        getApplicationContext().bindService(new Intent(this, BtleService.class),
                this, Context.BIND_AUTO_CREATE);


        // Obtener la vista principal
        View mainView = findViewById(R.id.main_layout);


        SharedPreferences sharedPreferences = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
        String intensidad = sharedPreferences.getString("intensity", "80");
        String tiempo=sharedPreferences.getString("time", "60");
        String actuation=sharedPreferences.getString("actuation","2");
        String retardate=sharedPreferences.getString("retardate","60");

        inten = Integer.parseInt(intensidad);
        time = Integer.parseInt(tiempo);
        actua=Integer.parseInt(actuation);
        retar=Integer.parseInt(retardate);


        SharedPreferences shared2Preferences = getSharedPreferences("MisMacs", Context.MODE_PRIVATE);
        macs1 = shared2Preferences.getString("mac1", "EF:4C:14:4E:18:15");
        macs2 = shared2Preferences.getString("mac2", "CB:03:47:C3:03:D9");
        macs3 = shared2Preferences.getString("mac3", "C9:64:8B:2B:EB:13");
        macs4 = shared2Preferences.getString("mac4", "D7:B6:29:F1:EE:B3");
        macs5 = shared2Preferences.getString("mac5", "C8:41:5F:6F:E7:0C");
        macs6 = shared2Preferences.getString("mac6", "FE:25:7D:8E:53:E2");

        mainView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        startPulsePattern();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopVibrationSequence();
                        break;
                }
                return true;
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        getApplicationContext().unbindService(this);


    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        serviceBinder = (BtleService.LocalBinder) service;
        Log.i("Test", "Correct Connection");

        if(actua==1){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            boards = new MetaWearBoard[1]; // Cambia el tamaño según la cantidad de dispositivos
            // retrieveBoard("C9:64:8B:2B:EB:13", 0); // Primer dispositivo
            retrieveBoard(macs1, 0); // Segundo dispositivo
            //retrieveBoard("CB:03:47:C3:03:D9", 0); // Tercer dispositivo
            Toast.makeText(getApplicationContext(), "Conectando los actuadores", Toast.LENGTH_LONG).show();
        }

        if(actua==2){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            boards = new MetaWearBoard[2]; // Cambia el tamaño según la cantidad de dispositivos
            // retrieveBoard("C9:64:8B:2B:EB:13", 0); // Primer dispositivo
            retrieveBoard(macs1, 0); // Segundo dispositivo
            retrieveBoard(macs2,1 ); // Tercer dispositivo
            Toast.makeText(getApplicationContext(), "Conectando los actuadores", Toast.LENGTH_LONG).show();
        }

        if(actua==3){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            boards = new MetaWearBoard[3]; // Cambia el tamaño según la cantidad de dispositivos
            retrieveBoard(macs1, 0); // Primer dispositivo
            retrieveBoard(macs2, 1); // Segundo dispositivo
            retrieveBoard(macs3, 2); // Tercer dispositivo
            Toast.makeText(getApplicationContext(), "Conectando los actuadores", Toast.LENGTH_LONG).show();
        }

        if(actua==4){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            boards = new MetaWearBoard[4]; // Cambia el tamaño según la cantidad de dispositivos
            retrieveBoard(macs1, 0); // Primer dispositivo
            retrieveBoard(macs2, 1); // Segundo dispositivo
            retrieveBoard(macs3, 2); // Tercer dispositivo
            retrieveBoard(macs4, 3); // Cuarto dispositivo
            Toast.makeText(getApplicationContext(), "Conectando los actuadores", Toast.LENGTH_LONG).show();
        }

        if(actua==5){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            boards = new MetaWearBoard[5]; // Cambia el tamaño según la cantidad de dispositivos
            retrieveBoard(macs1, 0); // Primer dispositivo
            retrieveBoard(macs2, 1); // Segundo dispositivo
            retrieveBoard(macs3, 2); // Tercer dispositivo
            retrieveBoard(macs4, 3); // Cuarto dispositivo
            retrieveBoard(macs5, 4); // Quinto dispositivo
            Toast.makeText(getApplicationContext(), "Conectando los actuadores", Toast.LENGTH_LONG).show();
        }

        if(actua==6){
            // Reemplazar las direcciones MAC con las de tus dispositivos
            boards = new MetaWearBoard[6]; // Cambia el tamaño según la cantidad de dispositivos
            retrieveBoard(macs1, 0); // Primer dispositivo
            retrieveBoard(macs2, 1); // Segundo dispositivo
            retrieveBoard(macs3, 2); // Tercer dispositivo
            retrieveBoard(macs4, 3); // Cuarto dispositivo
            retrieveBoard(macs5, 4); // Quinto dispositivo
            retrieveBoard(macs6, 5); // Sexto dispositivo
            Toast.makeText(getApplicationContext(), "Conectando los actuadores", Toast.LENGTH_LONG).show();
        }

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
        if (!isVibrating) {
            isVibrating = true; // Marcar que la vibración está en progreso

            final int totalDevices = boards.length;
            final Object lock = new Object();

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
                                    while (isVibrating) {
                                        hapticModule.startMotor(inten, (short) time);
                                        Thread.sleep(retar);
                                    }
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
        }

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "TuApp:WakeLockTag"
        );

    }

    private void stopVibrationSequence() {
        isVibrating = false; // Marcar que la vibración ha terminado
    }

    protected void onResume() {
        super.onResume();
        // Adquirir el WakeLock al volver a la actividad
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Liberar el WakeLock al pausar la actividad
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

}




