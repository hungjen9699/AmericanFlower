/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.dao;

import hunglnv.entity.Bulb;
import hunglnv.entity.Flower;
import hunglnv.entity.TempaState;
import hunglnv.service.AbstractFacade;
import hunglnv.utils.DBUtilities;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author hungj
 */
public class TempaStateDAO extends AbstractFacade<TempaState> {

    private final EntityManager em = DBUtilities.getEntityManager();

    public TempaStateDAO() {
        super(TempaState.class);
    }

    public boolean getFirstTempaByNameAndSeason(String stateName, String season) {
        try {
            List<TempaState> result = em.createNamedQuery("TempaState.findByStateNameAndSeason", TempaState.class)
                    .setParameter("stateName", stateName)
                    .setParameter("season", season)
                    .getResultList();
            if (result != null && !result.isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public TempaState getTempaStateByName(String stateName) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        List<TempaState> result = em.createNamedQuery("TempaState.findBySeason", TempaState.class)
                .setParameter("stateName", stateName)
                .getResultList();
        transaction.commit();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void createTempaState(String name, float temparature, String season) {
        TempaState tempaDTO = null;
        try {
            if (name != null) {
                boolean check = getFirstTempaByNameAndSeason(name,season);
                if (!check) {
                    tempaDTO = new TempaState();
                    tempaDTO.setStateName(name);
                    tempaDTO.setTemparature(temparature);
                    tempaDTO.setSeason(season);
                    create(tempaDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
