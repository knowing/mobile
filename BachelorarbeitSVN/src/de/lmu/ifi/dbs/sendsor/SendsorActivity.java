package de.lmu.ifi.dbs.sendsor;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Mainklasse die bei Programmstart ausge&uuml;hrt wird und die Interaktion mit dem User &uuml;bernimmt.
 * @author walonka
 * @version 1.0
 *
 */
public class SendsorActivity extends Activity{
    private static Context CONTEXT;
    private Button button;
    boolean startet = false;
    private static Writer writer;
    private PowerManager mPowerManager;
    private WakeLock mWakeLock;
    /*
     * Gettermethoden 
     */
  
	
    /**
     * Wird beim Methodenstart aufgerufen und initialisiert alles.
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
            ((TextView) findViewById(R.id.text)).setText("Die Datenaufzeichnung wurde gestartet und kann mit stop beendet werden! Die Daten befinden sich danach auf Ihrer Speicherkarte.");
            button.setText("stoppen");
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((TextView) findViewById(R.id.text)).setText("klick");
                if (Boolean.FALSE.equals(startet)){
                    ((TextView) findViewById(R.id.text)).setText("Die Datenaufzeichnung wurde gestartet und kann mit stop beendet werden! Die Daten befinden sich danach auf Ihrer Speicherkarte.");
                    startet=true;
                    button.setText("stoppen");
                    CONTEXT.startService(intent);

                }
                else{
                    ((TextView) findViewById(R.id.text)).setText("Bitte die Datenaufzeichnung starten!");
                    
                    startet = false;
                    button.setText("starten");
                    CONTEXT.stopService(intent);
                }
            }
        });
        //Ende des Klicklisteners
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());

    }
    
    /**
     * Wird beim aufruf minimierter Apps aufgerufen
     */
    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();

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
       // writer.stopWriting();
        /*
         * Der Service laeuft noch weiter!
         */

 
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