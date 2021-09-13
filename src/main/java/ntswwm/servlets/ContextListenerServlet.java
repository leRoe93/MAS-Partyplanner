package ntswwm.servlets;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import ntswwm.agents.BudgetAgent;
import ntswwm.agents.DrinksAgent;
import ntswwm.agents.FeedbackAgent;
import ntswwm.agents.FoodAgent;
import ntswwm.agents.RetrievalAgent;
import ntswwm.platform.AgentPlatform;

public class ContextListenerServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Web project started.");
        initializeAgentPlatform();
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
            AgentController retrievalAgent = AgentPlatform.CONTAINER_CONTROLLER.createNewAgent("RetrievalAgent",
                    RetrievalAgent.class.getName(), null);
            AgentController drinksAgent = AgentPlatform.CONTAINER_CONTROLLER.createNewAgent("DrinksAgent",
                    DrinksAgent.class.getName(), null);
            AgentController foodAgent = AgentPlatform.CONTAINER_CONTROLLER.createNewAgent("FoodAgent",
                    FoodAgent.class.getName(), null);
            AgentController budgetAgent = AgentPlatform.CONTAINER_CONTROLLER.createNewAgent("BudgetAgent",
                    BudgetAgent.class.getName(), null);
            AgentController feedbackAgent = AgentPlatform.CONTAINER_CONTROLLER.createNewAgent("FeedbackAgent",
                    FeedbackAgent.class.getName(), null);
            AgentPlatform.AGENTS.put("retrieval", retrievalAgent);
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

}
