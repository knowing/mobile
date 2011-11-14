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

public class SendsorService extends Service implements AccelerometerListener{

	private static Context CONTEXT;
	private Writer writer;

	
	@Override
	public void onAccelerationChanged(float x, float y, float z) {
			System.out.println("x = "+x+" y = "+y+" z = "+z);
			writer.writeData(x, y, z);
			writer.flushout();
		
	}

	@Override
	public void onShake(float force) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	public void onCreate(){
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		System.out.println("Service gestartet");
		AccelerometerManager.startListening(this);
		CONTEXT = this.getApplicationContext();
        writer=new Writer("database"+".csv");
	}
	
	public void onDestroy() {
		System.out.println("stop Listening");
		AccelerometerManager.stopListening();
	}

	public Context getContext(){
		return this.CONTEXT;
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
	}

}
