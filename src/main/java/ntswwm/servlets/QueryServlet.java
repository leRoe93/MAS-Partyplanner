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
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.FloatDesc;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
import de.dfki.mycbr.core.similarity.ISimFct;
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
            message = "Derived data from a party with similarity of: "
                    + retrievalResult.getSecond().getRoundedValue() * 100 + " %";

            var detailsTable = generateDetailsTable(retrievalResult, request);
            request.setAttribute("details" + agentType, detailsTable);

            var guestCountQuery = Integer.parseInt(request.getParameter("guestCount"));
            var guestCountCase = Integer.parseInt(retrievalResult.getFirst()
                    .getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("guestCount")).getValueAsString());

            for (String attributeName : AttributeMappings.getAnswerAttributesForAgent(agentType)) {
                var attributeNameInConcept = attributeName;
                if (attributeName.contains("Specific")) {
                    attributeNameInConcept = attributeName.split("_")[0];

                }

                var value = Float.parseFloat(retrievalResult.getFirst()
                        .getAttForDesc(CBRManager.CONCEPT.getAttributeDesc(attributeNameInConcept)).getValueAsString());

                if (guestCountQuery != guestCountCase) {
                    System.out.println("Previous value: " + value);
                    System.out.println("Found case does not have the same guest count, value gets normalized.");
                    request.setAttribute("normalized" + agentType,
                            "<br>Below values needed to be normalized based on the guest count!<br>" + "Your input: "
                                    + guestCountQuery + ", case value: " + guestCountCase);

                    value = normalizeParameterValue(value, guestCountQuery, guestCountCase);
                }
                System.out.println("Setting attributes to request: " + attributeName + ", value: " + value);
                request.setAttribute(attributeName, Float.parseFloat(String.format("%.2f", value).replace(",", ".")));
            }
        }
        System.out.println("Setting message for query: " + message);
        request.setAttribute("message" + agentType, message);
        appendRequest(request, AttributeMappings.getAnswerAttributesForAgent(agentType));
        AgentToServletStack.QUERY_INSTANCES.get(agentType)
                .remove(AgentToServletStack.QUERY_INSTANCES.get(agentType).size() - 1);

    }

    private String generateDetailsTable(Pair<Instance, Similarity> instance, HttpServletRequest request) {
        String table = "<table class='table'><thead><tr>";
        table += "<th>Parameter</th><th>Your Input</th><th>Case Value</th><th>Similarity</th><th>Weight</th></thead><tbody>";

        for (int i = 0; i < AttributeMappings.SORTED_ATTRIBUTES_FOR_DETAILS.length; i++) {
            var key = AttributeMappings.SORTED_ATTRIBUTES_FOR_DETAILS[i];
            AttributeDesc desc = CBRManager.CONCEPT.getAttributeDesc(key);
            var requestParam = request.getParameter(key);
            var instanceValue = instance.getFirst().getAttForDesc(desc).getValueAsString();
            ISimFct localFunction;
            double similarity;
            try {
                if (desc instanceof SymbolDesc) {
                    SymbolDesc descSymbol = (SymbolDesc) desc;
                    localFunction = descSymbol.getFct("default function");

                    similarity = localFunction.calculateSimilarity(descSymbol.getAttribute(request.getParameter(key)),
                            instance.getFirst().getAttForDesc(desc)).getRoundedValue();
                } else if (desc instanceof FloatDesc) {
                    FloatDesc descFloat = (FloatDesc) desc;
                    localFunction = descFloat.getFct("default function");
                    similarity = localFunction
                            .calculateSimilarity(descFloat.getAttribute(Float.parseFloat(request.getParameter(key))),
                                    descFloat.getAttribute(Float
                                            .parseFloat(instance.getFirst().getAttForDesc(desc).getValueAsString())))
                            .getRoundedValue();
                } else {
                    IntegerDesc descInt = (IntegerDesc) desc;
                    localFunction = ((IntegerDesc) desc).getFct("default function");
                    similarity = localFunction
                            .calculateSimilarity(descInt.getAttribute(Integer.parseInt(request.getParameter(key))),
                                    descInt.getAttribute(Integer
                                            .parseInt(instance.getFirst().getAttForDesc(desc).getValueAsString())))
                            .getRoundedValue();
                }

                table += "<tr><td>" + key + "</td><td>" + requestParam + "</td><td>" + instanceValue + "</td><td>"
                        + similarity + "</td><td>" + CBRManager.CONCEPT.getActiveAmalgamFct().getWeight(desc)
                        + "</td></tr>";
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        table += "</tbody></table>";
        return table;
    }

    /**
     * Simple method to normalize values based on differences in the guest count.
     * 
     * @param value           the value that needs to be normalized.
     * @param guestCountQuery the guestCount given in the query.
     * @param guestCountCase  the guestCount given in the case.
     * @return the normalized guest count.
     */
    private float normalizeParameterValue(float value, int guestCountQuery, int guestCountCase) {
        return value * ((float) guestCountQuery / (float) guestCountCase);
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
