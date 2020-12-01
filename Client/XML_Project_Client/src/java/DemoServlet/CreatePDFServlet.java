/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DemoServlet;

import hunglnv.object.Flower;
import hunglnv.webservice.FlowerService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;

/**
 *
 * @author hungj
 */
public class CreatePDFServlet extends HttpServlet {

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
            String bedId = request.getParameter("bedId");
            FlowerService flower = new FlowerService();
            String bedXML = flower.find_XML(String.class, "");
            String path = getServletContext().getRealPath("/");
            String xslPath = path + "/WEB-INF/demo.xsl";
            String foPath = path + "/WEB-INF/demo.fo";
            methodTrAX(xslPath, bedXML, foPath, path);
            ByteArrayOutputStream outPDF = new ByteArrayOutputStream();
            response.setContentType("application/pdf");
            FopFactory ff = FopFactory.newInstance();
            FOUserAgent foua = ff.newFOUserAgent();
            Fop fop = ff.newFop(org.apache.fop.apps.MimeConstants.MIME_PDF, foua, outPDF);
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer transformer = tff.newTransformer();
            File fo = new File(foPath);
            Source src = new StreamSource(fo);
            Result result = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, result);
            byte[] content = outPDF.toByteArray();
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
            response.getOutputStream().flush();
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

    public void methodTrAX(String xslPath, String xmlString, String output, String path) {
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            StreamSource xslFile = new StreamSource(xslPath);
            Transformer transformer = tff.newTransformer(xslFile);
            transformer.setParameter("pathFile", path);
            String webInfPath = getServletConfig().getServletContext().getRealPath("WEB-INF");
            File initialFile = new File(webInfPath + "/evaluateresult.xml");
            InputStream is = FileUtils.openInputStream(initialFile);
            StreamSource xmlFile = new StreamSource(is);
            StreamResult htmlFile = new StreamResult(new FileOutputStream(output));
            transformer.transform(xmlFile, htmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
