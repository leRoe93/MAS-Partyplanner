package ntswwm.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import ntswwm.bean.AgentToServletStack;
import ntswwm.bean.CBRManager;
import ntswwm.platform.AgentPlatform;
import ntswwm.util.AttributeMappings;

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
        System.out.println("Started query for target agent: " + targetAgentName);

        if (!AgentToServletStack.QUERY_INSTANCES.containsKey(targetAgentName)) {
            AgentToServletStack.QUERY_INSTANCES.put(targetAgentName, new ArrayList<Pair<Instance, Similarity>>());
        }

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
                        System.out.println(paramName + ":" + request.getParameter(paramName));
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

        modifyRequest(targetAgentName, request);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void modifyRequest(String agentType, HttpServletRequest request) {
        var timeOut = 0;
        var sleepTime = 10;
        var timeOuted = false;
        var message = "";
        while (AgentToServletStack.QUERY_INSTANCES.get(agentType).size() == 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            timeOut += sleepTime;

            // Need exit condition based on time, if there is no case in the stack
            if (timeOut == 5000) {
                message = "Sorry, the " + agentType + " could not find any case to derive data from.";
                timeOuted = true;
                break;
            }
        }

        if (!timeOuted) {
            Pair<Instance, Similarity> retrievalResult = AgentToServletStack.QUERY_INSTANCES.get(agentType)
                    .get(AgentToServletStack.QUERY_INSTANCES.get(agentType).size() - 1);
            message = "Derived data from a party with similarity of: " + retrievalResult.getSecond().toString();

            var guestCountQuery = Integer.parseInt(request.getParameter("guestCount"));
            var guestCountCase = Integer.parseInt(retrievalResult.getFirst()
                    .getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("guestCount")).getValueAsString());

            for (String attributeName : AttributeMappings.getAnswerAttributesForAgent(agentType)) {
                var attributeNameInConcept = attributeName;
                if (attributeName.contains("Specific")) {
                    attributeNameInConcept = attributeName.split("_")[0];

                }
                System.out.println("Setting attributes to request: " + attributeName + ", value: "
                        + retrievalResult.getFirst()
                                .getAttForDesc(CBRManager.CONCEPT.getAttributeDesc(attributeNameInConcept))
                                .getValueAsString());

                var value = Float.parseFloat(retrievalResult.getFirst()
                        .getAttForDesc(CBRManager.CONCEPT.getAttributeDesc(attributeNameInConcept)).getValueAsString());

                if (guestCountQuery != guestCountCase) {
                    System.out.println("Found case does not have the same guest count, value gets normalized.");
                    value = normalizeParameterValue(value, guestCountQuery, guestCountCase);
                }
                request.setAttribute(attributeName, value);
            }
        }
        System.out.println("Setting message for query: " + message);
        request.setAttribute("message" + agentType, message);
        appendRequest(request, AttributeMappings.getAnswerAttributesForAgent(agentType));
        AgentToServletStack.QUERY_INSTANCES.get(agentType)
                .remove(AgentToServletStack.QUERY_INSTANCES.get(agentType).size() - 1);

    }

    private float normalizeParameterValue(float value, int guestCountQuery, int guestCountCase) {
        return value * (guestCountQuery / guestCountCase);
    }

    /**
     * Helper method that ensures that previously filled form fields by any agent
     * don't get lost if another agent is queried.
     * 
     * @param request        the current request object.
     * @param excludedParams all parameters that were already handled by agent
     *                       specific implementation.
     * @return the updated request object.
     */
    private HttpServletRequest appendRequest(HttpServletRequest request, String[] excludedParams) {

        Iterator<String> it = request.getParameterNames().asIterator();
        while (it.hasNext()) {
            var paramName = it.next();
            if (!Arrays.asList(excludedParams).contains(paramName)) {
                request.setAttribute(paramName, request.getParameter(paramName));
            }
        }

        return request;
    }
}
