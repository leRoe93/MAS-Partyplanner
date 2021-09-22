package ntswwm.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.dfki.mycbr.core.casebase.Instance;
import ntswwm.bean.CBRManager;
import ntswwm.util.MailManager;

/**
 * Servlet implementation class CreateCaseServlet
 */
@WebServlet("/CreateCaseServlet")
public class CreateCaseServlet extends HttpServlet {

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

        var caseCreationMessage = "Your party has been successfully saved with ID: ";

        var id = UUID.randomUUID().toString();

        // Always takes a consistent enumeration of case ids
        Instance newCase = new Instance(CBRManager.CONCEPT, "party #" + CBRManager.CASE_BASE.getCases().size());

        try {
            for (String key : CBRManager.CONCEPT.getAllAttributeDescs().keySet()) {
                System.out.println("At attribute: " + key);
                if (key.equals("verified")) {
                    newCase.addAttribute(CBRManager.CONCEPT.getAttributeDesc("verified"), "no");
                } else if (key.equals("id")) {
                    newCase.addAttribute(CBRManager.CONCEPT.getAttributeDesc("id"), id);
                    caseCreationMessage += id;
                } else {
                    System.out.println("Value in case creation form: " + request.getParameter(key + "Planner"));
                    newCase.addAttribute(CBRManager.CONCEPT.getAttributeDesc(key),
                            request.getParameter(key + "Planner").toString());
                }
            }
            CBRManager.PROJECT.addInstance(newCase);
            CBRManager.CASE_BASE.addCase(newCase);
            CBRManager.PROJECT.save();

            if (request.getParameter("sendMailCheckBox") != null) {
                var subject = "Party Plan ID: " + id;
                var content = generateEmailContent(newCase);
                MailManager.sendMessage(request.getParameter("email"), subject, content);

            }
        } catch (ParseException e) {
            caseCreationMessage = "Something went wrong while creating your party in our system, please try again!";
            e.printStackTrace();
        }

        request.setAttribute("caseCreationMessage", caseCreationMessage);

        request.getRequestDispatcher("/index.jsp").forward(request, response);

    }

    private String generateEmailContent(Instance instance) {
        var content = "Dear Sir or Madam, <br><br>" + "thank you for using MAS-Partyplanner! <br><br>"
                + "Please find your party details below: <br><br>";

        content += "<table>";

        content += "<tr><td><b>Month<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("month")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Year<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("year")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Expected Guests<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("guestCount")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Occasion<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("occasion")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Location Type<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("locationType")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td colspan=\"2\"><hr/></td></tr>";
        content += "<tr><td><b>Total Budget (€)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("totalBudget")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Food Budget (€)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("foodBudget")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Drinks Budget (€)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("drinksBudget")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Location Budget (€)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("locationBudget")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td colspan=\"2\"><hr/></td></tr>";
        content += "<tr><td><b>Meat Amount (KG)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("meatAmount")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Garnish Amount (KG)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("garnishAmount")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Snacks Amount (KG)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("snacksAmount")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td colspan=\"2\"><hr/></td></tr>";
        content += "<tr><td><b>Beer Amount (L)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("beerAmount")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Wine Amount (L)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("wineAmount")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Spirits Amount (L)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("spiritsAmount")).getValueAsString()
                + "</i></td></tr>";
        content += "<tr><td><b>Softs Amount (L)<b></td><td><i>"
                + instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("softsAmount")).getValueAsString()
                + "</i></td></tr>";

        content += "</table><br><br>";

        content += "Please note, that your party is <b>unverified</b>, please use our feedback service to verify your plan with your real data after the party has happened. <br>"
                + "This way others can also benefit from your experiences! <br>";
        content += "For that you need your <b>party ID</b> <i>(given in subject above)</i>.<br><br>";

        content += "Kind regards, <br><br>The MAS-Partyplanner Team";

        return content;

    }

}
