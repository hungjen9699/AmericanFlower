/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.dao;

import hunglnv.entity.Flower;
import hunglnv.entity.FlowerCategory;
import hunglnv.service.AbstractFacade;
import hunglnv.utils.DBUtilities;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author hungj
 */
public class FlowerDAO extends AbstractFacade<Flower> {

    private EntityManager em = DBUtilities.getEntityManager();

    public FlowerDAO() {
        super(Flower.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
        public Flower getFlowerByName(String flowerName, int flowerCategoryId) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        List<Flower> result = em.createNamedQuery("Flower.findByFlowerName", Flower.class)
                .setParameter("flowerName", flowerName)
                .getResultList();
        transaction.commit();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }
    
    public void saveFlowerToDB(Flower flower, FlowerCategory flowerCategory) {
        Flower existedFlower = getFlowerByName(flower.getFlowerName(), flowerCategory.getFlowerCategoryID());
        if (existedFlower != null||flower.getTemparature()==0) {
           // edit(flower);
        } else {
            em = DBUtilities.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();
                flowerCategory.addFlower(flower);
                flower.setFlowerCategory(flowerCategory);
                em.persist(flower);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (em.isOpen()) {
                    em.close();
                }
            }
        }
    }

    public Flower createFlower(FlowerCategory category, String flowerName, String link) {
        Flower flower = null;
        try {
            if (category != null && link != null) {
                if (flower == null) {
                    flower = new Flower();
                    flower.setFlowerName(flowerName);
                    flower.setFlowerCategory(category);
                    flower.setLink(link);
                    create(flower);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flower;
    }
}
