package ntswwm.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.AttributeDesc;
import ntswwm.bean.CBRManager;

/**
 * Servlet implementation class AlterCaseServlet
 */
@WebServlet("/AlterCaseServlet")
public class AlterCaseServlet extends HttpServlet {

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

        var message = "<span class='success-true'><span class='glyphicon glyphicon-ok'></span>Your feedback has been successfully saved, others now can benefit from your experience!</span>";

        try {

            Instance caseToBeAltered = searchCaseById(request.getParameter("id"));

            Iterator<String> it = request.getParameterNames().asIterator();
            while (it.hasNext()) {
                String paramName = it.next();
                AttributeDesc aDesc = CBRManager.CONCEPT.getAttributeDesc(paramName);

                // Special case, after providing feedback a case is always considered to be
                // verified
                try {
                    if (paramName.equals("verified")) {
                        caseToBeAltered.addAttribute(aDesc, aDesc.getAttribute("yes"));
                    } else {
                        caseToBeAltered.addAttribute(aDesc,
                                aDesc.getAttribute(request.getParameter(paramName).toString()));
                    }

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            CBRManager.PROJECT.addInstance(caseToBeAltered);
            CBRManager.CASE_BASE.addCase(caseToBeAltered);
            CBRManager.PROJECT.save();
        } catch (Exception e) {
            e.printStackTrace();
            message = "<span class='success-false'><span class='glyphicon glyphicon-remove'></span>Something went wrong, please try again later!</span>";
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("/feedback.jsp").forward(request, response);

    }

    private Instance searchCaseById(String id) {
        for (Instance instance : CBRManager.CASE_BASE.getCases()) {
            if (id.equals(instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("id")).getValueAsString())) {
                return instance;
            }
        }
        return null;
    }
}
