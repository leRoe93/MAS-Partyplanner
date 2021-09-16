package ntswwm.bean;

import java.util.ArrayList;

import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;

/**
 * This stack serves the purpose of enabling the agent providing information
 * that a servlet can retrieve it from here.
 * 
 * @author leonr
 *
 */
public class AgentToServletStack {
    public static ArrayList<Instance> FEEDBACK_INSTANCES = new ArrayList<Instance>();
    public static ArrayList<Pair<Instance, Similarity>> BUDGET_AGENT_INSTANCES = new ArrayList<Pair<Instance, Similarity>>();
    public static ArrayList<Pair<Instance, Similarity>> FOOD_AGENT_INSTANCES = new ArrayList<Pair<Instance, Similarity>>();
    public static ArrayList<Pair<Instance, Similarity>> DRINKS_AGENT_INSTANCES = new ArrayList<Pair<Instance, Similarity>>();
}
