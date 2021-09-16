package ntswwm.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import jade.wrapper.gateway.JadeGateway;
import ntswwm.agents.BudgetAgent;
import ntswwm.bean.AgentToServletStack;
import ntswwm.bean.CBRManager;
import ntswwm.platform.AgentPlatform;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/QueryServlet")
public class QueryServlet extends HttpServlet {

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

        // Use value of pressed button for agent name
        var targetAgentName = request.getParameter("submit-button");

        JadeGateway.init(null, AgentPlatform.PROPERTIES);
        try {
            JadeGateway.execute(new OneShotBehaviour() {

                @Override
                public void action() {
                    System.out.println("Sending message ");
                    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    message.addReceiver(new AID(targetAgentName, AID.ISLOCALNAME));
                    message.setContent("Message sent from QueryServlet");
                    message.addUserDefinedParameter("agentType", targetAgentName);
                    Iterator<String> it = request.getParameterNames().asIterator();
                    System.out.println("### QUERY PARAMS ###");
                    while (it.hasNext()) {
                        var paramName = it.next();
                        message.addUserDefinedParameter(paramName, request.getParameter(paramName));
                    }
                    myAgent.send(message);
                }
            });
        } catch (StaleProxyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ControllerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        var timeOut = 0;
        var sleepTime = 10;
        var timeOuted = false;

        switch (targetAgentName) {
        case "BudgetAgent":
            var budgetMessage = "";
            while (AgentToServletStack.BUDGET_AGENT_INSTANCES.size() == 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                timeOut += sleepTime;

                // Need exit condition based on time, if there is no case in the stack
                if (timeOut == 5000) {
                    budgetMessage = "Sorry, we could not find any case to derive budgets from.";
                    timeOuted = true;
                    break;
                }
            }
            if (!timeOuted) {
                Pair<Instance, Similarity> retrievalResult = AgentToServletStack.BUDGET_AGENT_INSTANCES
                        .get(AgentToServletStack.BUDGET_AGENT_INSTANCES.size() - 1);
                budgetMessage = "Derived budgets from a party with similarity of: "
                        + retrievalResult.getSecond().toString();

                for (String attributeName : BudgetAgent.ANSWER_ATTRIBUTES) {
                    System.out.println("Setting attributes to request: " + attributeName + ", value: "
                            + retrievalResult.getFirst()
                                    .getAttForDesc(CBRManager.CONCEPT.getAttributeDesc(attributeName))
                                    .getValueAsString());
                    request.setAttribute(attributeName, retrievalResult.getFirst()
                            .getAttForDesc(CBRManager.CONCEPT.getAttributeDesc(attributeName)).getValueAsString());
                }

            }
            request.setAttribute("budgetMessage", budgetMessage);

            break;
        case "FoodAgent":
            break;
        case "DrinksAgent":
            break;
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
