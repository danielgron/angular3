/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Car;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author danie
 */
@Path("car")
public class CarResource {
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("PU");

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CarResource
     */
    public CarResource() {
    }

    /**
     * Retrieves representation of an instance of rest.CarResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        EntityManager em = emf.createEntityManager();
        
        //EntityManager em = this.getEntityManager();
        List<Car> cars = new ArrayList();
        try {
            Query q = em.createQuery("Select c From Car c");
            cars = q.getResultList();
        } finally {
            em.close();
        }
        return gson.toJson(cars);
    }

    /**
     * PUT method for updating or creating an instance of CarResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putCar(String content) {
        EntityManager em = emf.createEntityManager();
        Car car = gson.fromJson(content, Car.class);
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void deleteCar(@PathParam("id") int id) {
        EntityManager em = emf.createEntityManager();
        Car car = null;
        try {
            car = em.find(Car.class, id);
            em.getTransaction().begin();
            em.remove(car);
            em.getTransaction().commit();

        } finally {
            em.close();

        }
    }
}
