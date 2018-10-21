package hackisu.fall2019.watchdog;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class MotionSensorService extends Service implements SensorEventListener {
    /**
     * The maximum scalar acceleration needed to trigger a fall event
     */
    public static final float FALL_ACCELERATION_THRESHOLD = 15f;
    /**
     * Fall events wont occur faster than FALL_EVENT_DEBOUNCE_TIME seconds apart.
     */
    public static final float FALL_EVENT_DEBOUNCE_TIME = 3;

    private SensorManager mSensorManager;
    private Sensor mLinearAccelerometer;

    /**
     * The timestamp when the last fall event occured
     */
    private long timeCounter;

    public MotionSensorService() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mLinearAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        timeCounter = 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
        mSensorManager.registerListener(this, mLinearAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        return null;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        if(timeCounter != 0)
            if(System.nanoTime() < timeCounter + FALL_EVENT_DEBOUNCE_TIME * 1e9)
                return;

        timeCounter = 0;

        float x = Math.abs(event.values[0]);
        float y = Math.abs(event.values[1]);
        float z = Math.abs(event.values[2]);

        float scalarAcceleration = (float) Math.sqrt(x*x + y*y + z*z);

        //A fall event occurs!
        if(scalarAcceleration > FALL_ACCELERATION_THRESHOLD) {

            //THE CODE TO CALL A METHOD GOES HERE

            timeCounter = System.nanoTime();
        }
    }
}
