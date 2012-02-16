package de.lmu.ifi.dbs.sendsor;

/**
 * Sorgt daf&uuml;r, dass nur eine Instanz des Writers verwendet wird.
 * @author walonka
 * @version 1.0
 */
public class Singleton {
	private static Writer writer;
	public static boolean serviceRunning;
	
	public  static Writer getWriter(){
		if(writer==null){
			writer=new Writer("datenbank.csv");
		}
		return writer;
	}
	
	public static void killWriter(){
		writer.stopWriting();
		writer=null;
	}
}
