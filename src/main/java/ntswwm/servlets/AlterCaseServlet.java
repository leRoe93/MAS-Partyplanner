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
import ntswwm.agents.RetrievalAgent;
import ntswwm.bean.AgentToServletStack;

/**
 * Servlet implementation class AgentPingServlet
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

        var message = "Your feedback has been successfully saved, others now can benefit from your experience!";

        Instance caseToBeAltered = searchCaseById(request.getParameter("id"));

        Iterator<String> it = request.getParameterNames().asIterator();
        while (it.hasNext()) {
            String paramName = it.next();
            AttributeDesc aDesc = RetrievalAgent.concept.getAttributeDesc(paramName);

            // Special case, after providing feedback a case is always considered to be
            // verified
            try {
                if (paramName.equals("verified")) {
                    caseToBeAltered.addAttribute(aDesc, aDesc.getAttribute("yes"));
                } else {
                    caseToBeAltered.addAttribute(aDesc, aDesc.getAttribute(request.getParameter(paramName).toString()));
                }

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        for (String attributeName : RetrievalAgent.concept.getAttributeDescs().keySet()) {
            System.out.println(attributeName + ": " + AgentToServletStack.FEEDBACK_INSTANCE
                    .getAttForDesc(RetrievalAgent.concept.getAttributeDescs().get(attributeName)).getValueAsString());
        }

        RetrievalAgent.project.addInstance(caseToBeAltered);
        RetrievalAgent.caseBase.addCase(caseToBeAltered);

        request.setAttribute("message", message);
        request.getRequestDispatcher("/feedback.jsp").forward(request, response);

    }

    private Instance searchCaseById(String id) {
        for (Instance instance : RetrievalAgent.caseBase.getCases()) {
            if (id.equals(instance.getAttForDesc(RetrievalAgent.concept.getAttributeDesc("id")).getValueAsString())) {
                return instance;
            }
        }
        return null;
    }
}
