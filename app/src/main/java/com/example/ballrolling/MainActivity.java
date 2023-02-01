package com.example.ballrolling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView ball;
    private View mur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ball = findViewById(R.id.ball);
        mur = findViewById(R.id.mur);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        // Crée le listener
        SensorEventListener EventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                float x = ball.getX() + event.values[0] * -1 * 2;
                float y = ball.getY() + event.values[1] * 2;

                // Vérifie si la balle est dans l'écran
                if (x < 0)
                    x = 0;
                if (y < 0)
                    y = 0;
                if (x > getWindowManager().getDefaultDisplay().getWidth() - ball.getWidth())
                    x = ball.getX();//getWindowManager().getDefaultDisplay().getWidth() - ball.getWidth();
                if (y > getWindowManager().getDefaultDisplay().getHeight() - ball.getHeight())
                    y = ball.getY();;

                // Vérifi que la balle ne traverse pas le mur

                if (y + ball.getHeight() > mur.getY() && y < mur.getY() + mur.getHeight() &&
                        x + ball.getWidth() > mur.getX() && x < mur.getX() + mur.getWidth())
                        y = ball.getY();
                if (x + ball.getWidth() > mur.getX() && x < mur.getX() + mur.getWidth() &&
                        y + ball.getHeight() > mur.getY() && y < mur.getY() + mur.getHeight())
                        x = ball.getX();


                // Mets à jour la position de la balle
                ball.setX(x);
                ball.setY(y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        // Active le listener
        sensorManager.registerListener(EventListener, sensor, 1);
    }
}