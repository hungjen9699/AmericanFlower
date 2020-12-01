/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.dao;

import hunglnv.entity.Bulb;
import hunglnv.service.AbstractFacade;
import hunglnv.utils.DBUtilities;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hungj
 */
public class BulbDAO extends AbstractFacade<Bulb>{
    
    private final EntityManager em = DBUtilities.getEntityManager();
    public boolean getFirstBulbByName(String bulbName) {
        try {
            List<Bulb> result = em.createNamedQuery("Bulb.findByBulbName", Bulb.class)
                    .setParameter("bulbName", bulbName)
                    .getResultList();
            if (result != null && !result.isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public BulbDAO() {
        super(Bulb.class);
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
        public boolean createBulb(String bulbName, String imgLink, String link, int price, int wattage, int luminous) {
        Bulb bulbDTO = null;
        try {
            if (bulbName != null && link != null) {
                boolean check=getFirstBulbByName(bulbName);
                if (!check) {
                    bulbDTO = new Bulb();
                    bulbDTO.setBulbName(bulbName);
                    bulbDTO.setImgLink(imgLink);
                    bulbDTO.setLink(link);
                    bulbDTO.setPrice(price);
                    bulbDTO.setWattage(wattage);
                    bulbDTO.setLuminous(luminous);
                    create(bulbDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
}
