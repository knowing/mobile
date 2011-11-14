package de.lmu.ifi.dbs.sendsor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.hardware.Camera.Size;
import android.os.Environment;

/**
 * Klasse zum Schreiben der Accelerometerdaten
 * @author walonka
 *
 */
public class Writer{
	private Context context;
	private BufferedWriter out;
	private File filepath = Environment.getExternalStorageDirectory();
	private String filename;
	final static String lineSeparator = System.getProperty("line.separator");
	
	
	public Writer (String filename){
		this.context=SendsorActivity.getContext();
		this.filename=filename;
		
		boolean initializised = false;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(filepath, filename)));
			if (reader.readLine()!=null){
				initializised=true;
			}
			else{
			}
			reader.close();
		}
		catch(Exception e){
		}
		try {
			out = new BufferedWriter(new FileWriter(new File(filepath, filename),true),1200);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void writeData(float x, float y, float z){
		try {
			x/=9.81;
			x*=64;
			y/=9.81;
			y*=64;
			z/=9.81;
			z*=64;
			Calendar c = Calendar.getInstance();
			/*
			long ms = System.currentTimeMillis();
			String milli = Long.toString(ms).substring(10);
			Date d = new Date(ms);
			//String.format
			//out.write(System.currentTimeMillis()+";"+x+";"+y+";"+z+lineSeparator);*/
			//out.write(d.getYear()+"-"+d.getMonth()+"-"+d.getDay()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"."+milli+","+(int)x+","+(int)y+","+(int)z+lineSeparator);
			out.write(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DATE)+" "+c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+"."+c.get(Calendar.MILLISECOND)+","+(int)x+","+(int)y+","+(int)z+lineSeparator);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void flushout(){
		try {
			out.flush();
		} catch (IOException e) {
		}
	}
}
