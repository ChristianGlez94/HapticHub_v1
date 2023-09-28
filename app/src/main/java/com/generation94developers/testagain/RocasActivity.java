package com.generation94developers.testagain;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
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

public class RocasActivity extends AppCompatActivity implements ServiceConnection {
    private BtleService.LocalBinder serviceBinder;
    private MetaWearBoard[] boards; // Array para los dispositivos
    private Button vibrar,rampaasc;
    private volatile boolean isVibrating = false;

    private ImageView imageView;
    private MediaPlayer mediaPlayer;
    int inten,time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocas);

        getApplicationContext().bindService(new Intent(this, BtleService.class),
                this, Context.BIND_AUTO_CREATE);


        // Obtener la vista principal
        View mainView = findViewById(R.id.main_layout);


        SharedPreferences sharedPreferences = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);
        String intensidad = sharedPreferences.getString("intensity", "90");
        String tiempo=sharedPreferences.getString("time", "30");

        inten = Integer.parseInt(intensidad);
        time = Integer.parseInt(tiempo);


        mediaPlayer = MediaPlayer.create(this, R.raw.sonido);
        mediaPlayer.setLooping(true); // Configurar el MediaPlayer para reproducir en bucle.

        // Agregar un detector de eventos táctiles a la vista principal
        mainView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case  MotionEvent.ACTION_DOWN:

                        break;

                    case MotionEvent.ACTION_MOVE:
                        // Ejecutar la secuencia de vibración cuando hay movimiento
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

        // Liberar recursos del MediaPlayer al destruir la actividad.
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        serviceBinder = (BtleService.LocalBinder) service;
        Log.i("Test", "Correct Connection");

        // Reemplazar las direcciones MAC con las de tus dispositivos
        boards = new MetaWearBoard[2]; // Cambia el tamaño según la cantidad de dispositivos
       // retrieveBoard("C9:64:8B:2B:EB:13", 0); // Primer dispositivo
       retrieveBoard("EF:4C:14:4E:18:15", 1); // Segundo dispositivo
       retrieveBoard("CB:03:47:C3:03:D9", 0); // Tercer dispositivo
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
                                hapticModule.startMotor(inten, (short) 60);
                                Thread.sleep(time);
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



