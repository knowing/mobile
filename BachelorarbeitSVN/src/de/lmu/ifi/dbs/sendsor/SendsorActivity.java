package de.lmu.ifi.dbs.sendsor;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SendsorActivity extends Activity{
    private static Context CONTEXT;
    private Button button;
    private Button activityButton;
    boolean startet = false;
    private static Writer writer;
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
        CONTEXT = this; //Setzen des Context
        writer = Singleton.getWriter(); //Holen der Schreiberadresse
        final Intent intent = new Intent(this, SendsorService.class); //Setzen der Umgebung
        ((TextView) findViewById(R.id.text)).setText("Bitte die Datenaufzeichnung starten!");
       
        /*
         * Erstellen des Start/Stopknopfs
         */
        button = (Button) findViewById(R.id.startstop);
        button.setText("start");
        startet=Singleton.serviceRunning; //ist der Service gestartet?
 
        if(Boolean.TRUE.equals(startet)){
            ((TextView) findViewById(R.id.text)).setText("Datenaufzeichnung laeuft!");
            button.setText("stoppen");
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((TextView) findViewById(R.id.text)).setText("klick");
                if (Boolean.FALSE.equals(startet)){
                    ((TextView) findViewById(R.id.text)).setText("Gestartet!");
                    startet=true;
                    button.setText("stoppen");
                    CONTEXT.startService(intent);

                }
                else{
                    ((TextView) findViewById(R.id.text)).setText("Gestoppt");
                    startet = false;
                    button.setText("starten");
                    CONTEXT.stopService(intent);
                }
            }
        });

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
        //Intent intent = new Intent(this, SendsorService.class);
        //stopService(intent);

 
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
        ((TextView) findViewById(R.id.text)).setText(error);
    }
    
}