/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author hungj
 */
public class DBUtilities {
    private static EntityManagerFactory emf;
    
    public static EntityManager getEntityManager() {
        if(emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("Project_DemoPU");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emf.createEntityManager();
    }
  
}
