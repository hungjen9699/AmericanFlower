/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.service;

import hunglnv.entity.Flower;
import hunglnv.utils.DBUtilities;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author hungj
 */
@Path("hunglnv.entity.flower")
public class FlowerFacadeREST extends AbstractFacade<Flower> {

    private EntityManager em = DBUtilities.getEntityManager();

    public FlowerFacadeREST() {
        super(Flower.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Flower entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Flower entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Flower find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Flower> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Flower> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("getFlowerByCateName/{flowerCategoryName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Flower> getCategoryByName(@PathParam("flowerCategoryName") String name) {
        List<Flower> result = em.createNamedQuery("Flower.findByCategoryName", Flower.class).setParameter("flowerCategoryName", name)
                .getResultList();
        return result;
    }

    @GET
    @Path("getFlowerByCateID/{flowerCategoryID}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Flower> getCategoryByID(@PathParam("flowerCategoryID") String name) {
        List<Flower> result = em.createNamedQuery("Flower.findByCategoryID", Flower.class).setParameter("flowerCategoryID", name)
                .getResultList();
        return result;
    }

    @GET
    @Path("getFlowerByCateAndName/{flowerCategoryName}/{flowerName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Flower> getFlowerByCateAndName(@PathParam("flowerCategoryName") String cateName, @PathParam("flowerName") String flowerName) {
        List<Flower> result = em.createNamedQuery("Flower.findByCategoryAndName", Flower.class).setParameter("flowerCategoryName", cateName).setParameter("flowerName", "%" + flowerName + "%")
                .getResultList();
        return result;
    }

    @GET
    @Path("getFlowerByState")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Flower> getFlowerByState(@QueryParam("day") int day, @QueryParam("temparature") float temparature, @QueryParam("flowerName") String flowerName) {
        List<Flower> result = em.createNamedQuery("Flower.findFlowerByState", Flower.class).setParameter("day", day).setParameter("temparature", temparature).setParameter("flowerName", "%" + flowerName + "%")
                .getResultList();
        return result;
    }
    
    @GET
    @Path("getFlowerByName/{flowerName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Flower getFlowerByName(@PathParam("flowerName") String flowerName) {
        Flower result = em.createNamedQuery("Flower.findByFlowerName", Flower.class).setParameter("flowerName",flowerName)
                .getResultList().get(0);
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
