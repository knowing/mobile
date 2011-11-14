package de.lmu.ifi.dbs.sendsor;

import java.util.List;



import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerManager implements Config {
	private static Sensor sensor;
    private static SensorManager sensorManager;
    private static AccelerometerListener listener;

    /** Accuracy configuration */
    private static float threshold     = 0.2f;
    private static int interval     = 1000;
    
	/**
	 *  Zeigt an, ob ein unterst&uuml;tzter Sensor vorhanden ist     
	 */
	private static Boolean supported;
 
	/**
	 * Zeigt, ob der Sensor l&auml;uft
	 */
    private static boolean running = false;
	
    
    
    /*
     * Gettermethoden
     */
    /**
     * Gibt an, ob der Accelerometer l&auml;ft
     * @return boolean Sensorzustand
     */
    public static boolean isListening() {
        return running;
    }
    
    
    /*
     * Sensorverwaltung
     */
    /**
     * Abmeldung des Zuh&ouml;rers
     * @return Success
     */
    public static boolean stopListening() {
        running = false;
        try {
            if (sensorManager != null && sensorEventListener != null) {
                sensorManager.unregisterListener(sensorEventListener);
                return true;
            }
            return false;
        } catch (Exception e) {
        	return false;
        }
    }
    

    /**
     * Gibt an, ob mindestens ein unterst&uuml;tzter Sensor verf&uuml;gbar ist
     * @return boolean Sensor vorhanden?
     */
    public static boolean isSupported() {
        if (supported == null) {
        	//Accelerometer vorhanden
            if (SendsorActivity.getContext() != null) {
                sensorManager = (SensorManager) SendsorActivity.getContext().getSystemService(Context.SENSOR_SERVICE);
                List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
                //Gibt es Sensoren?
                if (sensors.size() > 0){
                	supported=true;
                }
                else{
                	supported=false;
                }
            } else {
                supported = false;
            }
        }
        return supported;
    }


    /**
     * Konfigurieren, ab wann der Sensor Sch&uuml;tteln erkennt und welches Intervall zwischen den Ersch&uuml;tterungen liegt.
     * Configure the listener for shaking
     * @param threshold Minimum f&uuml;r Accelerometer
     * @param interval Intervall
     */
    public static void setConfig(int threshold, int interval) {
        AccelerometerManager.threshold = threshold;
        AccelerometerManager.interval = interval;
    }


    /**
     * Starten der Datenerfassung
     * @param accelerometerListener
     */
    public static void startListening(AccelerometerListener accelerometerListener) {
        sensorManager = (SensorManager) SendsorActivity.getContext().getSystemService(Context.SENSOR_SERVICE);//Holen der Sensoren
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);//Vorhandene Sensoren in einer Liste speichern
        if (sensors.size() > 0) {
            sensor = sensors.get(0);
            running = sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);//TODO Auf echtzeit setzen
            listener = accelerometerListener;
        }
    }

    /**
     * 
     * @param accelerometerListener Listener f&uuml;r die Accelerometer
     * @param threshold Minimum f&uuml;r Accelerometer
     * @param interval Intervall
     */
    public static void startListening(AccelerometerListener accelerometerListener, int threshold, int interval) {
        setConfig(threshold, interval);
        startListening(accelerometerListener);
    }


    /**
     * Eventlistener der die Events der Sensoren &uuml;verwacht
     */
    private static SensorEventListener sensorEventListener =  new SensorEventListener() {
 
 
        private float x = 0;
        private float y = 0;
        private float z = 0;

 
        /**
         * Bei Genauigkeits&auml;nderung erfolgt keine Behandlung
         */
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
 
        /**
         * Bei &Auml;nderung der Sensorwerte werden diese weiterverarbeitet 
         */
        public void onSensorChanged(SensorEvent event) {
 
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            listener.onAccelerationChanged(x, y, z); //uebergabe der Werte
        }
 
    };
}


