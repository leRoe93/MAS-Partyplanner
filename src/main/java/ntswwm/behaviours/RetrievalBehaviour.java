package ntswwm.behaviours;

import java.text.ParseException;

import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.AmalgamationFct;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ntswwm.bean.AgentToServletStack;
import ntswwm.bean.CBRManager;

public class RetrievalBehaviour extends CyclicBehaviour {

    /**
     * 
     */
    private static final long serialVersionUID = -4550436136565114827L;

    String agentType;

    public RetrievalBehaviour(String agentType) {
        super();
        this.agentType = agentType;
    }

    @Override
    public void action() {
        // TODO Auto-generated method stub
        ACLMessage msg = myAgent.receive();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("[" + this.agentType + "] Waiting for retrieval request...");

        if (msg != null) {
            // var agentType = msg.getUserDefinedParameter("agentType");
            System.out
                    .println("[" + this.agentType + "] Received retrieval request from agent type: " + this.agentType);
            setActiveAmalgamationFct(this.agentType);

            AgentToServletStack.QUERY_INSTANCES.get(this.agentType).add(getMostSimilarCase(msg));
        }

    }

    private void setActiveAmalgamationFct(String agentType) {

        // TODO: get real amalgam functions from the mycbr project

        for (AmalgamationFct function : CBRManager.CONCEPT.getAvailableAmalgamFcts()) {
            if (function.getName().contains(agentType)) {
                System.out.println(
                        "Picked amalgamation function '" + function.getName() + "' for agent type: " + agentType);
                CBRManager.CONCEPT.setActiveAmalgamFct(function);
            }
        }
    }

    private Pair<Instance, Similarity> getMostSimilarCase(ACLMessage msg) {
        Retrieval ret = new Retrieval(CBRManager.CONCEPT, CBRManager.CASE_BASE);
        ret.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);
        Instance query = ret.getQueryInstance();

        for (String paramName : CBRManager.CONCEPT.getAllAttributeDescs().keySet()) {
            System.out.println(
                    "User defined value for param '" + paramName + " is: " + msg.getUserDefinedParameter(paramName));
            if (msg.getUserDefinedParameter(paramName) != null && !msg.getUserDefinedParameter(paramName).isBlank()) {
                try {
                    query.addAttribute(CBRManager.CONCEPT.getAttributeDesc(paramName),
                            msg.getUserDefinedParameter(paramName));
                } catch (ParseException e) {
                    System.err.println("Cannot add attribute '" + paramName + " to the My CBR query!");
                    e.printStackTrace();
                }
            }
        }

        ret.start();

        System.out.println("Descending retrieved cases to find a verified one");
        // Try to find a verified case by descending the retrieved cases
        for (int i = 0; i < ret.getResult().size(); i++) {
            if (ret.getResult().get(i).getFirst().getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("verified"))
                    .getValueAsString().equals("yes")) {
                return ret.getResult().get(i);
            }
        }

        return null;
    }

}
