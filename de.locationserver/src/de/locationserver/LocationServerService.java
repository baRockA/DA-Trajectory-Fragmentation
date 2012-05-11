package de.locationserver;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.locationserver.LocationUpdate;

@Produces("application/xml")
@Path("locationserver")
public class LocationServerService {
	private static final String PERSISTENCE_UNIT_NAME = "locationupdates0";
	private static EntityManagerFactory factory;
	
	/**
	 * This methode takes a GET request and returns a list of all location updates
	 * that are stored in the db.
	 * @return List<LocationUpdate> of all available location updates
	 */
	@GET
	@Path("getupdates")
	@Produces("application/xml")
	public List<LocationUpdate> getUpdates() {
		List<LocationUpdate> lus = new ArrayList<LocationUpdate>();
		
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		// Read the existing entries for the given moID
		Query q = em.createQuery("select l from LocationUpdate l");
		lus = q.getResultList();
		
		em.close();
		
		return lus;
	}
	
	/**
	 * This methode takes the id of a mobile object as an input and returns all
	 * location updates of this mobile object, that are available in the db
	 * @param moid
	 * @return List<LocationUpdate> of all location updates with moid
	 */
	@GET
	@Path("getupdatesbymoid")
	@Produces("application/xml")
	public Response getUpdatesByMoid(@QueryParam("moid") String moid) {
		List<LocationUpdate> lus = new ArrayList<LocationUpdate>();
		
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		// Read the existing entries for the given moID
		Query q = em.createQuery("select l from LocationUpdate l");
		List<LocationUpdate> luList = q.getResultList();

		for (LocationUpdate lu : luList) {
			if(lu.getMoid() != null){
				if(lu.getMoid().equals(moid)){
					lus.add(lu);
				}
			} else if(moid == "") {
				lus.add(lu);
			}
		}
			
		em.close();
		
		final GenericEntity<List<LocationUpdate>> entity = new GenericEntity<List<LocationUpdate>>(lus) { };
		return Response.ok().entity(entity).build();
	}
	
	/**
	 * Adds an update to the db
	 * 
	 * @param longi
	 * @param lati
	 * @param alti
	 * @param sp
	 * @param t
	 * @param moid
	 */
	@POST
	@Path("addupdate")
	public void addUpdate(@QueryParam("longitude") Double longi,
			@QueryParam("latitude") Double lati,
			@QueryParam("altitude") Double alti,
			@QueryParam("speed") Double sp,
			@QueryParam("time") Long t,
			@QueryParam("moid") String moid) {
		
		System.out.println(longi+","+lati+","+alti+","+sp+","+t+","+moid);
		//check that parameters are set
		if(alti != null && lati != null && longi != null &&
				sp != null && t != null && moid != null){

			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();

			// Create new location update
			em.getTransaction().begin();

			LocationUpdate locup = new LocationUpdate();
			locup.setAltitude(alti);
			locup.setLatitude(lati);
			locup.setLongitude(longi);
			locup.setMoid(moid);
			locup.setSpeed(sp);
			locup.setTime(t);

			em.persist(locup);
			em.getTransaction().commit();
			em.close();
		}
	}
	
	
}
