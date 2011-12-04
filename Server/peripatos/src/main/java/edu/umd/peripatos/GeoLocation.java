package edu.umd.peripatos;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "GeoLocation")
public class GeoLocation {
	
	private static final Random random = new Random();
	
	private Long id;
	private Double latitude;
	private Double longitude;
	
	public GeoLocation(){
		id = random.nextLong();
	}
	
	@Id
	@Column(name = "GEOLOCATION_ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@Column(name="LATITUDE")
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	@NotNull
	@Column(name = "LONGITUDE")
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
