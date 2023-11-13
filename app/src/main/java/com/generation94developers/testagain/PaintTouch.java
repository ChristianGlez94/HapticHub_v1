package com.generation94developers.testagain;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaintTouch extends View {

    private Paint paint;
    private Path path;

    public PaintTouch(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Configuración del pincel
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        // Inicializa la ruta de dibujo
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibuja la ruta de la pintura
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Obtiene las coordenadas al tocar la pantalla
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Mueve la ruta de dibujo al punto de inicio
                path.moveTo(x, y);
                // Dibuja un punto en el punto de inicio
                drawPoint(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                // Dibuja una línea hasta el punto actual
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                // Nada que hacer aquí en este ejemplo
                break;
        }

        // Invalida la vista para forzar el redibujado
        invalidate();
        return true;
    }

    private void drawPoint(float x, float y) {
        // Dibuja un punto en el punto de inicio
        Canvas canvas = new Canvas();
        canvas.drawPoint(x, y, paint);
    }
}
