package ntswwm.servlets;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import de.dfki.mycbr.core.Project;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import ntswwm.agents.BudgetAgent;
import ntswwm.agents.CoordinatorAgent;
import ntswwm.agents.DrinksAgent;
import ntswwm.agents.FeedbackAgent;
import ntswwm.agents.FoodAgent;
import ntswwm.bean.CBRManager;
import ntswwm.platform.AgentPlatform;

public class ContextListenerServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Web project started.");
        initializeAgentPlatform();
        initializeCBRManager();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Web project stopped.");

        try {
            for (AgentController ac : AgentPlatform.AGENTS.values()) {
                ac.kill();
            }

        } catch (StaleProxyException spe) {
            spe.printStackTrace();
        }
    }

    /**
     * Initializes all relevant aspects to make the agent platform usable across the
     * whole application. Furthermore starts each agent to have them available.
     */
    private void initializeAgentPlatform() {
        System.out.println("Starting to initialize the agent platform.");
        AgentPlatform.RUNTIME = Runtime.instance();
        AgentPlatform.PROPERTIES = new ExtendedProperties();
        AgentPlatform.PROPERTIES.setProperty(Profile.MAIN_HOST, "localhost");
        AgentPlatform.PROPERTIES.setProperty(Profile.MAIN_PORT, "1097");
        AgentPlatform.PROPERTIES.setProperty(Profile.PLATFORM_ID, "MAS-Partyplanner MainPlatform");
        AgentPlatform.MAIN_PROFILE = new ProfileImpl(AgentPlatform.PROPERTIES);
        AgentPlatform.MAIN_PROFILE.setParameter(Profile.MAIN_HOST, "localhost");
        AgentPlatform.CONTAINER_CONTROLLER = AgentPlatform.RUNTIME.createMainContainer(AgentPlatform.MAIN_PROFILE);
        System.out.println("Done initializing the agent platform.");
        System.out.println("Adding agents to the plaform...");
        AgentPlatform.AGENTS = new HashMap<String, AgentController>();
        try {
            AgentController coordinatorAgent = AgentPlatform.CONTAINER_CONTROLLER.createNewAgent("CoordinatorAgent",
                    CoordinatorAgent.class.getName(), null);
            AgentController drinksAgent = AgentPlatform.CONTAINER_CONTROLLER.createNewAgent("DrinksAgent",
                    DrinksAgent.class.getName(), null);
            AgentController foodAgent = AgentPlatform.CONTAINER_CONTROLLER.createNewAgent("FoodAgent",
                    FoodAgent.class.getName(), null);
            AgentController budgetAgent = AgentPlatform.CONTAINER_CONTROLLER.createNewAgent("BudgetAgent",
                    BudgetAgent.class.getName(), null);
            AgentController feedbackAgent = AgentPlatform.CONTAINER_CONTROLLER.createNewAgent("FeedbackAgent",
                    FeedbackAgent.class.getName(), null);

            AgentPlatform.AGENTS.put("coordinator", coordinatorAgent);
            AgentPlatform.AGENTS.put("drinks", drinksAgent);
            AgentPlatform.AGENTS.put("food", foodAgent);
            AgentPlatform.AGENTS.put("budget", budgetAgent);
            AgentPlatform.AGENTS.put("feedback", feedbackAgent);

            for (AgentController controller : AgentPlatform.AGENTS.values()) {
                controller.start();
            }

        } catch (StaleProxyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Initializes the underlying CBR project by using the provided system property.
     */
    private void initializeCBRManager() {
        try {
            CBRManager.PROJECT = new Project(System.getProperty("project.file"));

            // Necessary because it takes some time until MyCBR fully loads the project
            while (CBRManager.PROJECT.isImporting()) {
                Thread.sleep(1000);
            }

            CBRManager.CONCEPT = CBRManager.PROJECT.getConceptByID("party");
            CBRManager.CASE_BASE = CBRManager.PROJECT.getCB("party-casebase");
        } catch (Exception e) {
            System.err.println("Caught exception while trying to initialize the CBRManager.");
            e.printStackTrace();
            System.exit(-1);
        }

    }

}
