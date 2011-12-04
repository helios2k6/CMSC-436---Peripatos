package edu.umd.peripatos.dao;

import java.util.List;

import edu.umd.peripatos.GeoLocation;

public interface GeoLocationDao {
	public void store(GeoLocation location);
	public void delete(GeoLocation location);
	
	public GeoLocation findGeoLocationById(Long id);
	
	public List<GeoLocation> getAllGeoLocations();
}
