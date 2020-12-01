/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DemoServlet;

import hunglnv.object.Bulb;
import hunglnv.object.BulbEvaluateObject;
import hunglnv.object.EvaluateResult;
import hunglnv.object.Flower;
import hunglnv.object.TempaState;
import hunglnv.webservice.BulbService;
import hunglnv.webservice.FlowerService;
import hunglnv.webservice.TempaStateService;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import static java.lang.Math.abs;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 *
 * @author hungj
 */
public class EvaluateFlowerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String state = request.getParameter("state");
            String season = request.getParameter("season");
            String area = request.getParameter("area");
            String flower = request.getParameter("flower");

            FlowerService flowSer = new FlowerService();
            TempaStateService tempaSer = new TempaStateService();
            BulbService bulbSer = new BulbService();

            Flower flowerObj = flowSer.getFlowerByName_XML(Flower.class, flower);
            TempaState tempaObj = tempaSer.getBySeasonAndState_XML(TempaState.class, season, state);
            List<Bulb> listBulb = bulbSer.getAllBulb(Bulb.class);

            float range = abs(flowerObj.getTemparature() - tempaObj.getTemparature());
            setAttribute(request, range, area, state, season, flowerObj, tempaObj, listBulb);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("flower.jsp").forward(request, response);
        }
    }

    public BulbEvaluateObject selectBulbByFormula(List<Bulb> listBulb, String area, Flower flowerObj, TempaState tempaObj) {
        Bulb bulbObj = listBulb.get(0);
        int intArea = Integer.parseInt(area) * 20;
        int quantity = intArea * 1000 / (listBulb.get(0).getWattage() * listBulb.get(0).getLuminous());
        int minPrice = quantity * listBulb.get(0).getPrice();
        if (quantity < 1) {
            quantity = 1;
        }
        for (Bulb bulb : listBulb) {
            int bulbQuantity = intArea * 1000 / (bulb.getWattage() * bulb.getLuminous());
            if (bulbQuantity < 1) {
                bulbQuantity = 1;
            }
            int price = bulbQuantity * bulb.getPrice();
            if (price < minPrice) {
                minPrice = price;
                bulbObj = bulb;
                quantity = bulbQuantity;
            }
        }
        BulbEvaluateObject bulbObject = new BulbEvaluateObject(bulbObj, flowerObj, quantity, minPrice, tempaObj.getTemparature());
        return bulbObject;
    }

    public Bulb selectBulb(List<Bulb> listBulb, String area, Flower flowerObj, TempaState tempaObj) {
        Bulb bulbObj = listBulb.get(0);
        int intArea = Integer.parseInt(area) * 20;
        int quantity = intArea * 1000 / (listBulb.get(0).getWattage() * listBulb.get(0).getLuminous());
        int minPrice = quantity * listBulb.get(0).getPrice();
        if (quantity < 1) {
            quantity = 1;
        }
        for (Bulb bulb : listBulb) {
            int bulbQuantity = intArea * 1000 / (bulb.getWattage() * bulb.getLuminous());
            if (bulbQuantity < 1) {
                bulbQuantity = 1;
            }
            int price = bulbQuantity * bulb.getPrice();
            if (price < minPrice) {
                minPrice = price;
                bulbObj = bulb;
                quantity = bulbQuantity;
            }
        }
        bulbObj.setQuantity(quantity);
        return bulbObj;
    }

    public void setAttribute(HttpServletRequest request, float range, String area, String state, String season, Flower flowerObj, TempaState tempaObj, List<Bulb> listBulb) {

        String noti = "Because of your state temperature is too low for this flower. So we suggest you to buy this bulb for heating the flower";
        int check = 1;
        if (range > 5 && tempaObj.getTemparature() < flowerObj.getTemparature()) {
            request.setAttribute("bulbObject", selectBulbByFormula(listBulb, area, flowerObj, tempaObj));
        } else if (range > 5 && tempaObj.getTemparature() > flowerObj.getTemparature()) {
            noti = "Your state temperature is too high compare to the flower standard temperature (" + range + " celcius)."
                    + "\nSo you should water flower regularly.";
            request.setAttribute("NoNeed", noti);
            check = 2;
        } else {
            noti = "Your state temperature is not too different from the flower standard temperature (" + range + " celcius)."
                    + "\nSo you can plant this flower in your state.";
            check = 3;
            request.setAttribute("NoNeed", noti);
        }
        EvaluateResult evaResult = new EvaluateResult(flowerObj, tempaObj, noti);
        if (check == 1) {
            evaResult.setBulb(selectBulb(listBulb, area, flowerObj, tempaObj));
        }
        evaResult.setCheck(check);
        createXMLfile(evaResult);
        request.setAttribute("stateTemparature", String.valueOf(tempaObj.getTemparature()));
        request.setAttribute("currentState", state);
        request.setAttribute("currentSeason", season);
        request.setAttribute("currentArea", area);
        request.setAttribute("flower", flowerObj);
        TempaStateService tempaService = new TempaStateService();
        List<TempaState> listState = tempaService.getStateBySeason(TempaState.class, "Summer");
        request.setAttribute("listState", listState);
    }

    public void createXMLfile(EvaluateResult result) {
        try {
            JAXBContext context = JAXBContext.newInstance(EvaluateResult.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
            String webInfPath = getServletConfig().getServletContext().getRealPath("WEB-INF");
            StringWriter sw = new StringWriter();
            File file = new File(webInfPath + "/evaluateresult.xml");
            m.marshal(result, file);
            System.out.println(sw.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //parameter can get: state, season, flower, area
    //Atribute can set: stateTemparature (String)
    //================= currentState (String)
    //================= currentSeason (String)
    //================= currentArea (String)
    //================= flower (Flower)
    //================= listState (List TempaState)
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
