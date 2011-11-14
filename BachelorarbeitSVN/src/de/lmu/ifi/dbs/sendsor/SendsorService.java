package de.lmu.ifi.dbs.sendsor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;
/**
 * Service, der das Erfassen im Hintergrund steuert.
 * @author walonka
 * @version 0.5
 */
public class SendsorService extends Service implements AccelerometerListener{

	private static Context CONTEXT;
	private Writer writer;

	
	
	@Override
	/**
	 * Methode, die die Bewegungsausschlaege entgegennimmt
	 * @param x X-Beschleunigung
	 * @param y Y-Beschleunigung
	 * @param z Z-Beschleunigung
	 */
	public void onAccelerationChanged(float x, float y, float z) {
			System.out.println("x = "+x+" y = "+y+" z = "+z);
			writer.writeData(x, y, z);
			writer.flushout();
		
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
	 */
	public void onCreate(){
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		System.out.println("Service gestartet");
		AccelerometerManager.startListening(this);
		CONTEXT = this.getApplicationContext();
        writer=new Writer("database"+".csv");
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
	}

	/**
	 * Methode zur r&uuml;ckgabe des Context
	 * @return gibt den Context zur&uuml;ck
	 */
	public Context getContext(){
		return this.CONTEXT;
	}
	


}
