package ntswwm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jade.wrapper.AgentController;
import ntswwm.platform.AgentPlatform;

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

        String agentControllersHTMLString = "<ol>";
        for (AgentController controller : AgentPlatform.AGENTS) {
            agentControllersHTMLString += "<li>" + controller.getClass().getName() + "</li>";
        }
        agentControllersHTMLString += "</ol>";

        try {
            request.setAttribute("agentControllers", agentControllersHTMLString);
            request.getRequestDispatcher("/agent_ping.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
