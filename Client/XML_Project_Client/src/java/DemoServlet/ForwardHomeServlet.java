/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DemoServlet;

import hunglnv.object.Flower;
import hunglnv.object.FlowerCategory;
import hunglnv.webservice.FlowerCategoryService;
import hunglnv.webservice.FlowerService;
import hunglnv.webservice.TempaStateService;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hungj
 */
public class ForwardHomeServlet extends HttpServlet {

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

            FlowerCategoryService cateSer = new FlowerCategoryService();
            FlowerService flowSer = new FlowerService();
            List<FlowerCategory> result = cateSer.findByLikeNameObject(FlowerCategory.class);
            String cate = request.getParameter("categoryValue");

            if (cate == null) {
                cate = result.get(0).getFlowerCategoryName();
            }
            request.setAttribute("currentCategoryName", cate);
            List<Flower> flower = flowSer.getCategoryByName(Flower.class, cate);
            request.setAttribute("listAtt", result);
            request.setAttribute("listFlower", flower);

        } catch (Exception e) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } finally {
            request.getRequestDispatcher("home.jsp").forward(request, response);
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
// Attribute can set: listAtt (list category de load len dropbox)
// -------------------listFLower (list FlowerCategory cua theo categoryValue tu request)
// -------------------currentCategoryName (Get ten cate hien tai)

    // Parameter can get: categoryValue (Ten category hien tai) 
