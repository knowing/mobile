package de.lmu.ifi.dbs.sendsor;



import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.widget.Toast;
/**
 * Service, der das Erfassen im Hintergrund steuert.
 * @author walonka
 * @version 1.0
 */
public class SendsorService extends Service implements AccelerometerListener{

	private static Context CONTEXT;
	private Writer writer;
	private static final String TAG = "SendsorActivity";

    private PowerManager mPowerManager;
    private WakeLock mWakeLock;
	
	
	@Override
	/**
	 * Methode, die die Bewegungsausschlaege entgegennimmt
	 * @param x X-Beschleunigung
	 * @param y Y-Beschleunigung
	 * @param z Z-Beschleunigung
	 */
	public void onAccelerationChanged(float x, float y, float z) {
			//System.out.println("x = "+x+" y = "+y+" z = "+z);
			writer.writeData(x, y, z);
			System.out.println(Float.toString(x) +" "+ Float.toString(y) +" "+ Float.toString(z));
		
	}

	@Override
	/**
	 * Nicht implementiert
	 */
	public void onShake(float force) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * Nicht implementiert
	 */
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	/**
	 * Methode, die beim Start des Service ausgef&uuml;rt wird
	 * 
	 * Dabei wird der AccelerometerListener beobachtet und die Aufzeichnung gestartet. Der Gebundene Powermanager sperrt die CPU-Abschaltung.
	 */
	public void onCreate(){
		
		Notification notification = new Notification(R.drawable.icon, "Datenaufzeichnung gestartet",System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, SendsorActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(this, "Datenaufzeichnung", "Datenaufzeichnung wurde gestartet", pendingIntent);
		startForeground(77, notification);
		Toast.makeText(this, "Service erstellt", Toast.LENGTH_LONG).show();
		System.out.println("Service gestartet");
		AccelerometerManager.startListening(this);
		CONTEXT = this.getApplicationContext();
        writer=Singleton.getWriter();
        Singleton.serviceRunning=true;
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
		Log.e(TAG, "Service gestartet");
	}
	
	/**
	 * Methode, die beim Beenden des Service aufgerufen wird.
	 * Beendet das H&ouml;ren auf Accelerometer
	 * Beendet den Schreiber
	 */
	public void onDestroy() {
		System.out.println("stop Listening");
		AccelerometerManager.stopListening();
		writer.flushout();
		writer.stopWriting();
		Singleton.serviceRunning=false;
		Toast.makeText(this, "Service gestoppt", Toast.LENGTH_LONG).show();

	}

	/**
	 * Methode zur r&uuml;ckgabe des Context
	 * @return gibt den Context zur&uuml;ck
	 */
	public Context getContext(){
		return this.CONTEXT;
	}
	


}
