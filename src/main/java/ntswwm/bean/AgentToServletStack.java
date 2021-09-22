package ntswwm.bean;

import java.util.ArrayList;
import java.util.HashMap;

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
    public static HashMap<String, ArrayList<Pair<Instance, Similarity>>> QUERY_INSTANCES = new HashMap<String, ArrayList<Pair<Instance, Similarity>>>();
}
