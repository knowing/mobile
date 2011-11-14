package de.lmu.ifi.dbs.sendsor;

/**
 * @version 0.5
 * @author walonka
 *
 * Interface f&uuml;r die Verarbeitung der vom Sensor gelieferten Daten
 * 
 */
public interface AccelerometerListener {
	 
	public void onAccelerationChanged(float x, float y, float z);
 
	public void onShake(float force);
 
}