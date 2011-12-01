package de.lmu.ifi.dbs.sendsor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class AccelerometerWriter extends PrintWriter {

	private String activity = "NONE";
	
	/**
	 * Das sind natuerlich noch mehr Konstruktoren,
	 * die du ableiten koenntest.
	 * @param fileName
	 * @throws IOException
	 */
	public AccelerometerWriter(String fileName) throws IOException {
		super(fileName);
	}

	/**
	* Methode zum Schreiben von Accelerometerwerten
	* @param x X-Beschleunigung
	* @param y Y-Beschleunigung
	* @param z Z-Beschleunigung
	*/
	public void writeData(float x, float y, float z) {
		x /= 9.81;
		x *= 64;
		y /= 9.81;
		y *= 64;
		z /= 9.81;
		z *= 64;
		Calendar c = Calendar.getInstance();
		print(c.get(Calendar.YEAR));
		print("-");
		print(c.get(Calendar.MONTH));
		print("-");
		print(c.get(Calendar.DATE));
		print(" ");
		print(c.get(Calendar.HOUR_OF_DAY));
		print(":");
		print(c.get(Calendar.MINUTE));
		print(":");
		print(c.get(Calendar.SECOND));
		print(".");
		print(c.get(Calendar.MILLISECOND));
		print(",");
		print(x);
		print(",");
		print(y);
		print(",");
		print(z);
		print(",");
		println(activity);
	}

}
