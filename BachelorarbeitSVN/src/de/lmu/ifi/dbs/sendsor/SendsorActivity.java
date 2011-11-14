package de.lmu.ifi.dbs.sendsor;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

public class SendsorActivity extends Activity{
    private static Context CONTEXT;

    /*
     * Gettermethoden 
     */
    
	
    /**
     * Wird beim Methodenstart aufgerufen und initialisiert halles.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CONTEXT = this;
        Intent intent = new Intent(this, SendsorService.class);
        CONTEXT.startService(intent);
        ((TextView) findViewById(R.id.t)).setText("Gestartet");

    }
    
    /**
     * Wird beim aufruf minimierter Apps aufgerufen
     */
    protected void onResume() {
        super.onResume();
    }
 
    /**
     * Wird beim Legen in den Hintergrund aufgerufen
     */
    protected void onPause(){
    	super.onPause();
    	
    }
    
    /**
     * Wird beim Beenden der App aufgerufen
     */
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, SendsorService.class);
        stopService(intent);

 
    }
 
    /**
     * Gibt den Context zur&uuml;ck
     * @return Context
     */
    public static Context getContext() {
        return CONTEXT;
    }
 

    /**
     * K&uuml;mmert sich um die Fehlerbehanldung
     * @param error
     */
    public void writeError(String error){
        ((TextView) findViewById(R.id.t)).setText(error);
    }
}