package de.lmu.ifi.dbs.sendsor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import android.*;

/**
 * Klasse zum Schreiben der Accelerometerdaten
 * @author walonka
 * @version 1.0
 *
 */
public class Writer{
	private static final String TAG = "SendsorActivity";
	private Context context;
	private BufferedWriter out;
	private File filepath = Environment.getExternalStorageDirectory();
	private String filename;
	final static String lineSeparator = System.getProperty("line.separator");

	/**
	 * Konstruktor der Writerklasse
	 * @param filename Dateiname (wo soll die Datenbank gespeichert werden?)
	 */
	@SuppressWarnings("static-access")
	public Writer (String filename){
		this.context=SendsorActivity.getContext();
		this.filename=filename;
		try {
			//out = new BufferedWriter(new FileWriter(new File(filepath, filename),true),768);
			out = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filename, context.MODE_PRIVATE)),256*1024);
			out.write("@relation time_series"+lineSeparator+lineSeparator);
			out.write("@attribute timestamp date 'yyyy-MM-dd HH:mm:ss.SSS'"+lineSeparator);
			out.write("@attribute y0 numeric"+lineSeparator);
			out.write("@attribute y1 numeric"+lineSeparator);
			out.write("@attribute y2 numeric"+lineSeparator+lineSeparator);
			out.write("@data"+lineSeparator);
			


			Log.v(TAG, "Datenschreiber gestartet");
			
		} catch (IOException e) {
			Log.v(TAG, e.getMessage());
		}
		Toast.makeText(SendsorActivity.getContext(), "Writer gestartet", 5).show();

	}
	
	/**
	 * Methode zum Schreiben von Accelerometerwerten
	 * @param x X-Beschleunigung
	 * @param y Y-Beschleunigung
	 * @param z Z-Beschleunigung
	 */
	public void writeData(float x, float y, float z){
		try {
			
			x/=9.81;
			x*=64;
			y/=9.81;
			y*=64;
			z/=9.81;
			z*=64;
			Calendar c = Calendar.getInstance();
			String jahr  = Integer.toString(c.get(Calendar.YEAR));
			String monat = Integer.toString((c.get(Calendar.MONTH)+1));
			if (monat.length()<2){
				monat = "0"+monat;
			}
			String tag   = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
			if(tag.length()<2){
				tag = "0"+tag;
			}
			String stunde   = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
			if(stunde.length()<2){
				stunde = "0"+stunde;
			}
			String minute   = Integer.toString(c.get(Calendar.MINUTE));
			if(minute.length()<2){
				minute = "0"+minute;
			}
			String milli = Integer.toString(c.get(Calendar.MILLISECOND));
			if(milli.length()<2){
				milli="0"+milli;
			}
			String sekunde = Integer.toString(c.get(Calendar.SECOND));
			if(sekunde.length()<2){
				sekunde="0"+sekunde;
			}
			if(milli.length()<3){
				milli="0"+milli;
			}
			out.write("\'"+jahr+"-"+monat+"-"+tag+" "+stunde+":"+minute+":"+sekunde+"."+milli+"\',"+(int)x+","+(int)y+","+(int)z+lineSeparator);
			//out.write(System.currentTimeMillis()+","+(int)x+","+(int)y+","+(int)z+","+activity+lineSeparator);
		} catch (IOException e) {
			Log.v(TAG, e.getMessage());
		}
	}
	
	/**
	 * Methode zum kompletten Schreiben aller noch im Puffer befindlichen Daten
	 */
	public void flushout(){
		try {
			out.flush();
			Log.v(TAG, "Puffer geschrieben");
		} catch (IOException e) {
			Log.v(TAG, e.getMessage());
		}
	}
	
	/**
	 * Beendet die Datenaufzeichnung und exportiert die Daten
	 */
	public void stopWriting(){
		try {
			out.flush();
			out.close();
			Singleton.killWriter();
			Toast.makeText(SendsorActivity.getContext(), "Writer gestoppt", 5).show();
			export();
		} catch (IOException e) {
			Log.v(TAG, e.getMessage());
		}
	}
	

	/**
	 * Methode zum Exportieren der geschriebenen Datei auf die SD-Karte.
	 */
	public void export(){
		
		//File quelle = context.openFile))		//new File("/home/user/inputFile.txt");

        try{
    		FileChannel quelle = context.openFileInput(filename).getChannel();
    		Calendar c = Calendar.getInstance();
    		
    		String name = "Export_"+c.get(Calendar.YEAR)+"_"+(c.get(Calendar.MONTH)+1)+"_"+c.get(Calendar.DATE)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+".arff";
            File ziel = new File(filepath, name); 
        	copyFile(quelle, ziel);
			Toast.makeText(SendsorActivity.getContext(), "Daten exportiert", 5).show();
        }
        catch (Exception e){
			Log.v(TAG, e.getMessage());
			Toast.makeText(SendsorActivity.getContext(), "Error", 5).show();
			Toast.makeText(SendsorActivity.getContext(), e.getMessage(), 5).show();
        }
	}
	
    
	/**
	 * Methode zum Kopieren einer Datei
	 * @param in Quelle im gesch&uuml;tzten Applikationsverzeichnis
	 * @param out Schreibziel (Speicherkarte)
	 * @throws IOException
	 */
    public static void copyFile(FileChannel in, File out) throws IOException {
        FileChannel inChannel = in;
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            throw e;
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    } 
}
