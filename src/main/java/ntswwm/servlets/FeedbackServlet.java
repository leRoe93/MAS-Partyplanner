package ntswwm.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.dfki.mycbr.core.model.AttributeDesc;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import jade.wrapper.gateway.JadeGateway;
import ntswwm.bean.AgentToServletStack;
import ntswwm.bean.CBRManager;
import ntswwm.platform.AgentPlatform;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {

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

        var id = request.getParameter("id");
        var message = "Successfully retrieved party ID: " + id
                + " - please adjust the above values and verify the party!";

        JadeGateway.init(null, AgentPlatform.PROPERTIES);
        try {
            JadeGateway.execute(new OneShotBehaviour() {

                @Override
                public void action() {
                    System.out.println("Sending message ");
                    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    message.addReceiver(new AID("FeedbackAgent", AID.ISLOCALNAME));
                    message.setContent(id);
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
        while (AgentToServletStack.FEEDBACK_INSTANCES.size() == 0) {
            System.out.println("Feedback instance is null");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            timeOut += sleepTime;

            // Need exit condition based on time, if there is no case in the stack
            if (timeOut == 5000) {
                message = "Sorry, we could not find any party for ID: " + id + " - please check your input.";
                timeOuted = true;
                break;
            }
        }

        // Only if an instance has been found
        if (!timeOuted) {
            HashMap<String, AttributeDesc> attributeDescs = CBRManager.CONCEPT.getAllAttributeDescs();

            for (String attributeName : attributeDescs.keySet()) {
                request.setAttribute(attributeName, AgentToServletStack.FEEDBACK_INSTANCES.get(0)
                        .getAttForDesc(attributeDescs.get(attributeName)).getValueAsString());
            }

            AgentToServletStack.FEEDBACK_INSTANCES.remove(0);
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("/feedback.jsp").forward(request, response);
    }
}
