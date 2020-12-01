/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.webservice;

import hunglnv.object.Flower;
import hunglnv.object.FlowerCategory;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:FlowerFacadeREST
 * [hunglnv.entity.flower]<br>
 * USAGE:
 * <pre>
        FlowerService client = new FlowerService();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author hungj
 */
public class FlowerService {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/SeedWebService/webresources";

    public FlowerService() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("hunglnv.entity.flower");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public <T> T getCategoryByID_XML(Class<T> responseType, String flowerCategoryID) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getFlowerByCateID/{0}", new Object[]{flowerCategoryID}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T getCategoryByID_JSON(Class<T> responseType, String flowerCategoryID) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getFlowerByCateID/{0}", new Object[]{flowerCategoryID}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void edit_XML(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void create_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T getCategoryByName_XML(Class<T> responseType, String flowerCategoryName) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getFlowerByCateName/{0}", new Object[]{flowerCategoryName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
  public <T> List<Flower> getCategoryByName(Class<T> responseType,String flowerCategoryName) throws ClientErrorException {
        WebTarget resource = webTarget;
        GenericType<List<Flower>> list = new GenericType<List<Flower>>() {};
                resource = resource.path(java.text.MessageFormat.format("getFlowerByCateName/{0}", new Object[]{flowerCategoryName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE).get(list);
    }

    public <T> T getCategoryByName_JSON(Class<T> responseType, String flowerCategoryName) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getFlowerByCateName/{0}", new Object[]{flowerCategoryName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findAll_XML(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findAll_JSON(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }
    public <T> T getFlowerByState_XML(Class<T> responseType, String temparature, String flowerName, String day) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (temparature != null) {
            resource = resource.queryParam("temparature", temparature);
        }
        if (flowerName != null) {
            resource = resource.queryParam("flowerName", flowerName);
        }
        if (day != null) {
            resource = resource.queryParam("day", day);
        }
        resource = resource.path("getFlowerByState");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T getFlowerByState_JSON(Class<T> responseType, String temparature, String flowerName, String day) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (temparature != null) {
            resource = resource.queryParam("temparature", temparature);
        }
        if (flowerName != null) {
            resource = resource.queryParam("flowerName", flowerName);
        }
        if (day != null) {
            resource = resource.queryParam("day", day);
        }
        resource = resource.path("getFlowerByState");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }
        public <T> T getFlowerByName_XML(Class<T> responseType, String flowerName) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getFlowerByName/{0}", new Object[]{flowerName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T getFlowerByName_JSON(Class<T> responseType, String flowerName) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getFlowerByName/{0}", new Object[]{flowerName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }
    public void close() {
        client.close();
    }
    
}
