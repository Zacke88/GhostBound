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
 * @author Zacke
 * @version 2016-09-28
 */
public class AccelerometerSensor {

    private int dX = 0;
    private int dY = 0;
    private int multiplier = 10;

    public AccelerometerSensor(Context context) {
        SensorManager manager = (SensorManager)
                context.getSystemService(context.SENSOR_SERVICE);
        manager.registerListener(mSensorListener, manager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager
                .SENSOR_DELAY_NORMAL);
    }

    /**
     * Listener for the accelerometer which keeps updating the dX and dY value
     * as the phone is tilted.
     */
    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event) {
            float axisX = event.values[0];
            float axisY = event.values[1];
            dX = (0 - (int) (axisX * multiplier));
            dY = (int) (axisY * multiplier);
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    /**
     * Getter for dX value.
     *
     * @return Value given by accelerometers X value.
     */
    public int getdX() {
        return dX;
    }

    /**
     * Getter for dY value.
     *
     * @return Value given by accelerometers Y value.
     */
    public int getdY() {
        return dY;
    }
}
