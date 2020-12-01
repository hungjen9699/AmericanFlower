/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.dao;

import hunglnv.entity.FlowerCategory;
import hunglnv.service.AbstractFacade;
import hunglnv.utils.DBUtilities;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hungj
 */
public class FlowerCategoriesDAO extends AbstractFacade<FlowerCategory> {

    private final EntityManager em = DBUtilities.getEntityManager();

    public FlowerCategoriesDAO() {
        super(FlowerCategory.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public synchronized FlowerCategory getFirstFlowerCategoryByName(String categoryName) {
        try {
            List<FlowerCategory> result = em.createNamedQuery("FlowerCategory.findByFlowerCategoryName", FlowerCategory.class)
                    .setParameter("flowerCategoryName", categoryName)
                    .getResultList();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FlowerCategory getFirstCategoryByName(String categoryName) {
        try {
            List<FlowerCategory> result = em.createNamedQuery("FlowerCategory.findByFlowerCategoryName", FlowerCategory.class)
                    .setParameter("flowerCategoryName", categoryName)
                    .getResultList();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }


    public FlowerCategory createCategory(String categoryName, String url) {
        FlowerCategory flowerCategory = null;
        try {
            if (categoryName != null && url != null) {
                flowerCategory = getFirstFlowerCategoryByName(categoryName);
                if (flowerCategory == null) {
                    flowerCategory = new FlowerCategory();
                    flowerCategory.setFlowerCategoryName(categoryName);
                    flowerCategory.setLink(url);
                    create(flowerCategory);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flowerCategory;
    }

}
