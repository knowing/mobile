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
    
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try{
        BufferedWriter out = new BufferedWriter(new FileWriter(new File(Environment.getExternalStorageDirectory(), "activity"),true),1200);
        out.write("hallo");
        }
        catch(Exception e){
        	
        }
        CONTEXT = this;
        Intent intent = new Intent(this, SendsorService.class);
        CONTEXT.startService(intent);
        ((TextView) findViewById(R.id.t)).setText("Gestartet");

    }
    

    protected void onResume() {
        super.onResume();
    }
 
    protected void onPause(){
    	super.onPause();
    	
    }
    
    
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, SendsorService.class);
        stopService(intent);

 
    }
 
    public static Context getContext() {
        return CONTEXT;
    }
 

    
    public void writeError(String error){
        ((TextView) findViewById(R.id.t)).setText(error);
    }
}