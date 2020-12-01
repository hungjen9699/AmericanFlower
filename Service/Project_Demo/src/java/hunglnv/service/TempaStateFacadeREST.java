/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.service;

import hunglnv.entity.TempaState;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author hungj
 */
@Path("hunglnv.entity.tempastate")
public class TempaStateFacadeREST extends AbstractFacade<TempaState> {

    private EntityManager em = DBUtilities.getEntityManager();

    public TempaStateFacadeREST() {
        super(TempaState.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TempaState entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, TempaState entity) {
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
    public TempaState find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TempaState> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TempaState> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("getStateBySeason/{season}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TempaState> getStateBySeason(@PathParam("season") String season) {
        List<TempaState> result = em.createNamedQuery("TempaState.findBySeason", TempaState.class).setParameter("season", season)
                .getResultList();
        return result;
    }

    @GET
    @Path("getStateBySeason/{season}/{stateName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TempaState getBySeasonAndState(@PathParam("season") String season, @PathParam("stateName") String state) {
        TempaState result = em.createNamedQuery("TempaState.findByStateNameAndSeason", TempaState.class).setParameter("season", season).setParameter("stateName", state)
                .getResultList().get(0);
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
