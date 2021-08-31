package ntswwm.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import ntswwm.agents.DrinksAgent;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/AgentPingServlet")
public class AgentPingServlet extends HttpServlet {

    private static final long serialVersionUID = 8490923752286470389L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgentPingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

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
        System.out.println("ping servlet reached with post");

        ExtendedProperties b = new ExtendedProperties();
        b.setProperty(Profile.MAIN_HOST, "localhost");
        b.setProperty(Profile.MAIN_PORT, "1097");
        b.setProperty(Profile.PLATFORM_ID, "MAS-Partyplanner MainPlatform");
        Profile profile = new ProfileImpl(b);
        Runtime runtime = Runtime.instance();
        profile.setParameter(Profile.MAIN_HOST, "localhost");

        ContainerController containerController = runtime.createMainContainer(profile);
        ArrayList<AgentController> agents = new ArrayList<AgentController>();

        try {
            AgentController drinksAgent = containerController.createNewAgent("DrinksAgent_One",
                    DrinksAgent.class.getName(), null);
            agents.add(drinksAgent);
            drinksAgent.start();

        } catch (StaleProxyException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            request.getRequestDispatcher("/agent_ping.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
