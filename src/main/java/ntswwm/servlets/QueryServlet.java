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
                        var param = request.getParameter(paramName);
                        if (param == null) {
                            param = "0.00";
                        } else {
                            if (param.equals("")) {
                                param = "0.00";
                            }
                        }
                        message.addUserDefinedParameter(paramName, param);
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
            if (timeOut > 10000) {
                message = "<span class='success-false'><span class='glyphicon glyphicon-remove'></span>Sorry, the "
                        + agentType + " could not find any party to derive data from.</span>";
                timeOuted = true;
                break;
            }
        }

        if (!timeOuted) {
            if (AgentToServletStack.QUERY_INSTANCES.get(agentType)
                    .get(AgentToServletStack.QUERY_INSTANCES.get(agentType).size() - 1) != null) {

                Pair<Instance, Similarity> retrievalResult = AgentToServletStack.QUERY_INSTANCES.get(agentType)
                        .get(AgentToServletStack.QUERY_INSTANCES.get(agentType).size() - 1);
                message = "<span class='success-true'><span class='glyphicon glyphicon-ok'></span>Successfully derived data from a party with similarity of: "
                        + retrievalResult.getSecond().getRoundedValue() * 100 + "% </span>";

                message += generateDetailsTable(retrievalResult, request, agentType);

                var guestCountQuery = Integer.parseInt(request.getParameter("guestCount"));
                var guestCountCase = Integer.parseInt(retrievalResult.getFirst()
                        .getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("guestCount")).getValueAsString());

                for (String attributeName : AttributeMappings.getAnswerAttributesForAgent(agentType)) {
                    var attributeNameInConcept = attributeName;
                    if (attributeName.contains("Specific")) {
                        attributeNameInConcept = attributeName.split("_")[0];

                    }

                    var value = Float.parseFloat(retrievalResult.getFirst()
                            .getAttForDesc(CBRManager.CONCEPT.getAttributeDesc(attributeNameInConcept))
                            .getValueAsString());

                    if (guestCountQuery != guestCountCase) {
                        System.out.println("Previous value: " + value);
                        System.out.println("Found case does not have the same guest count, value gets normalized.");
                        value = normalizeParameterValue(value, guestCountQuery, guestCountCase);
                    }
                    System.out.println("Setting attributes to request: " + attributeName + ", value: " + value);
                    request.setAttribute(attributeName,
                            Float.parseFloat(String.format("%.2f", value).replace(",", ".")));
                }

                if (guestCountQuery != guestCountCase) {
                    message += "<p>Below values needed to be normalized based on the guest count!<br>" + "Your input: "
                            + guestCountQuery + ", case value: " + guestCountCase + "</p>";
                }
                AgentToServletStack.QUERY_INSTANCES.get(agentType)
                        .remove(AgentToServletStack.QUERY_INSTANCES.get(agentType).size() - 1);
            } else {
                message = "<span class='success-false'><span class='glyphicon glyphicon-remove'></span>Sorry, the "
                        + agentType + " could not find any verified party to derive data from.</span>";
            }
        }
        System.out.println("Setting message for query: " + message);
        request.setAttribute("message" + agentType, message);
        appendRequest(request, AttributeMappings.getAnswerAttributesForAgent(agentType));

    }

    private String generateDetailsTable(Pair<Instance, Similarity> instance, HttpServletRequest request,
            String agentType) {
        String table = "<a data-toggle='collapse' href='#detailsTable" + agentType
                + "' role='button' aria-expanded='false' aria-controls='detailsTable" + agentType + "'>"
                + "View Details!</a>";
        table += "<div class='collapse' id='detailsTable" + agentType
                + "'><div class='card card-body'><table class='table'><thead><tr>";
        table += "<th class='text-center'>Parameter</th><th class='text-center'>Your Input</th><th class='text-center'>Case Value</th><th>Similarity</th><th class='text-center'>Weight</th></thead><tbody>";

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
                    var param = request.getParameter(key);
                    if (param == null) {
                        param = "0.00";
                    } else {
                        if (param.equals("")) {
                            param = "0.00";
                        }
                    }
                    similarity = localFunction
                            .calculateSimilarity(descFloat.getAttribute(Float.parseFloat(param)),
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
        table += "</tbody></table></div></div>";
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
