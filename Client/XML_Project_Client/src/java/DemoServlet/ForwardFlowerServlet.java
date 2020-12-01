/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DemoServlet;

import hunglnv.object.EvaluateResult;
import hunglnv.object.Flower;
import hunglnv.object.TempaState;
import hunglnv.webservice.FlowerService;
import hunglnv.webservice.TempaStateService;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
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
public class ForwardFlowerServlet extends HttpServlet {

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
            String flowerName = request.getParameter("flowerName");

            FlowerService flowerSer = new FlowerService();
            Flower flower = flowerSer.getFlowerByName_XML(Flower.class, flowerName);
            TempaStateService tempaService = new TempaStateService();

            List<TempaState> listState = tempaService.getStateBySeason(TempaState.class, "Summer");
            createXMLfile(flower);
            request.setAttribute("listState", listState);
            request.setAttribute("flower", flower);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("flower.jsp").forward(request, response);
        }
    }
    //Parameter can get: flowerName

    //Attribute can set: listState(Cho dropdownbottom), flower 
    public void createXMLfile(Flower flower) {
        try {
            JAXBContext context = JAXBContext.newInstance(Flower.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
            String webInfPath = getServletConfig().getServletContext().getRealPath("WEB-INF");
            File file = new File(webInfPath + "/flowerdetail.xml");
            m.marshal(flower, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



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
