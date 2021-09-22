package ntswwm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntswwm.util.MailManager;

/**
 * Servlet implementation class SendMailServlet
 */
@WebServlet("/SendMailServlet")
public class SendMailServlet extends HttpServlet {

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

        var subject = "Party Plan ID: " + request.getParameter("caseId");
        var content = "Dear Sir or Madam, \n\n" + "thank you for using MAS-Partyplanner! \n\n"
                + "Please find your party details below: \n\n" + "Kind regards, \n\n" + "The MAS-Partyplanner Team";

        MailManager.sendMessage(request.getParameter("email"), subject, content);

        request.getRequestDispatcher("/index.jsp").forward(request, response);

    }

}
