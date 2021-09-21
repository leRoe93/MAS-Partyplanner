package ntswwm.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.dfki.mycbr.core.casebase.Instance;
import ntswwm.bean.CBRManager;

/**
 * Servlet implementation class CreateCaseServlet
 */
@WebServlet("/CreateCaseServlet")
public class CreateCaseServlet extends HttpServlet {

    private static final long serialVersionUID = 8490923752286470389L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @throws IOException
     * @throws ServletException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

        var caseCreationMessage = "Your party has been successfully saved with ID: ";

        // Always takes a consistent enumeration of case ids
        Instance newCase = new Instance(CBRManager.CONCEPT, "party #" + CBRManager.CASE_BASE.getCases().size());

        try {
            for (String key : CBRManager.CONCEPT.getAllAttributeDescs().keySet()) {
                System.out.println("At attribute: " + key);
                if (key.equals("verified")) {
                    newCase.addAttribute(CBRManager.CONCEPT.getAttributeDesc("verified"), "no");
                } else if (key.equals("id")) {
                    var id = UUID.randomUUID().toString();
                    newCase.addAttribute(CBRManager.CONCEPT.getAttributeDesc("id"), id);
                    caseCreationMessage += id;
                } else {
                    System.out.println("Value in case creation form: " + request.getParameter(key + "Planner"));
                    newCase.addAttribute(CBRManager.CONCEPT.getAttributeDesc(key),
                            request.getParameter(key + "Planner").toString());
                }
            }
            CBRManager.PROJECT.addInstance(newCase);
            CBRManager.CASE_BASE.addCase(newCase);
            CBRManager.PROJECT.save();
        } catch (ParseException e) {
            caseCreationMessage = "Something went wrong while creating your party in our system, please try again!";
            e.printStackTrace();
        }

        request.setAttribute("caseCreationMessage", caseCreationMessage);
        request.getRequestDispatcher("/index.jsp").forward(request, response);

    }
}
