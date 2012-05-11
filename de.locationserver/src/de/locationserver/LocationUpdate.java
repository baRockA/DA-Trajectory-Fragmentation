package de.locationserver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

// take the class as a xml representation
@XmlRootElement
// annotate the class with the jpa annotations
@Entity
public class LocationUpdate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double latitude;
	private double longitude;
	private double altitude;
	private double speed;
	private long time;
	private String moid;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getMoid() {
		return moid;
	}

	public void setMoid(String moid) {
		this.moid = moid;
	}

	@Override
	public String toString() {
		return "(long=" + longitude + ", lat=" + latitude
				+ "alt=" + altitude + ", speed=" + speed
				+ "t=" + time + ", moid=" + moid
				+ ")";
	}
}
