package com.generation94developers.testagain;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CapturaPantalla{

    // Método para tomar una captura de pantalla de una vista
    public static void takeScreenshot(View view) {
        try {
            // Crea un bitmap de la vista
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            // Guarda el bitmap como una imagen en la tarjeta SD
            File file = getOutputMediaFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obtener la ruta del archivo de salida
    private static File getOutputMediaFile() {
        // Verifica si hay una tarjeta SD disponible
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "MyAppScreenshots");

        // Crea el directorio si no existe
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Crea un nombre de archivo único basado en la fecha y hora
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".png");

        return mediaFile;
    }
}
