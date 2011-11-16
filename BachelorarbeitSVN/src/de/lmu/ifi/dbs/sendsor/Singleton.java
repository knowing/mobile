package de.lmu.ifi.dbs.sendsor;

public class Singleton {
	private static Writer writer;
	public static boolean serviceRunning;
	
	public  static Writer getWriter(){
		if(writer==null){
			writer=new Writer("database.csv");
		}
		return writer;
	}
}