package edu.umd.peripatos.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.peripatos.GeoLocation;
import edu.umd.peripatos.dao.GeoLocationDao;

@Transactional
public class HibernateGeoLocationDao implements GeoLocationDao{
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void store(GeoLocation location) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(location);	
	}

	@Override
	public void delete(GeoLocation location) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(location);				
	}

	@Override
	public GeoLocation findGeoLocationById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		GeoLocation geoLocation = (GeoLocation)session.get(GeoLocation.class, id);
		return geoLocation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GeoLocation> getAllGeoLocations() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("From GeoLocation");
		return query.list();
	}
}
