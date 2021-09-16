package ntswwm.bean;

import java.util.ArrayList;

import de.dfki.mycbr.core.casebase.Instance;

/**
 * This stack serves the purpose of enabling the agent providing information
 * that a servlet can retrieve it from here.
 * 
 * @author leonr
 *
 */
public class AgentToServletStack {
    public static ArrayList<Instance> FEEDBACK_INSTANCES = new ArrayList<Instance>();
}
