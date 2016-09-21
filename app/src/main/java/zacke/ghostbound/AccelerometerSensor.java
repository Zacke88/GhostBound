package zacke.ghostbound;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Class which represents the accelerometer for the device. It has a listener
 * which keeps listening for new X and Y values based on the accelerometer
 * and has methods to return those values.
 *
 * Created by Zacke on 2016-09-21.
 */
public class AccelerometerSensor {

    private int dX = 0;
    private int dY = 0;

    public AccelerometerSensor(Context context) {
        SensorManager manager = (SensorManager)
                context.getSystemService(context.SENSOR_SERVICE);
        manager.registerListener(mSensorListener, manager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager
                .SENSOR_DELAY_NORMAL);
    }

    /**
     * Listener for the accelerometer
     */
    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event) {
            float axisX = event.values[0];
            float axisY = event.values[1];
            dX = (0 - (int) (axisX*10));
            dY = (int) (axisY*10);
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    /**
     *
     * @return Value given by accelerometers X value
     */
    public int getdX() {
        return dX;
    }

    /**
     *
     * @return Value given by accelerometers Y value
     */
    public int getdY() {
        return dY;
    }
}
