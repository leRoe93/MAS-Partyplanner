package ntswwm.agents;

import java.util.ArrayList;
import java.util.List;

import de.dfki.mycbr.core.ICaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import jade.core.Agent;
import ntswwm.behaviours.RetrievalBehaviour;

public class RetrievalAgent extends Agent {

    /**
     * 
     */
    private static final long serialVersionUID = -1378286095267597345L;

    Project project;
    Concept concept;
    ICaseBase caseBase;

    protected void setup() {
        System.out.println("Retrieval Agent with ID: " + getAID().getName() + " is ready!");

        System.out.println(System.getProperty("project.file"));
        // System.out.println(RetrievalAgent.class.getResource(""));
        try {
            project = new Project(System.getProperty("project.file"));

            // Necessary because it takes some time until MyCBR fully loads the project
            while (project.isImporting()) {
                Thread.sleep(1000);
            }

            concept = project.getConceptByID("party");
            // A sorted list containing the instance with respective global similarity to
            // the query
            caseBase = project.getCB("party-casebase");

            System.out.println("Case base size: " + caseBase.getCases().size());
            for (Instance instance : caseBase.getCases()) {
                System.out.println("-----------------------------------------");
                System.out.println("### INSTANCE NAME: " + instance.getName());
                System.out.println("### GUEST COUNT: "
                        + instance.getAttForDesc(concept.getAttributeDesc("guestCount")).getValueAsString());
                System.out.println("### PARTY DATE: "
                        + instance.getAttForDesc(concept.getAttributeDesc("date")).getValueAsString());
            }

            addBehaviour(new RetrievalBehaviour());
        } catch (Exception e) {
            System.err.println("An exception occured whilst opening MyCBR project or processing the query: " + e);
        }
    }

    private List<Pair<Instance, Similarity>> performRetrieval() {
        // TODO: return will be "retrieval.getResult()"
        return new ArrayList<Pair<Instance, Similarity>>();
    }

    public String ping() {
        return "Hello, this is the retrieval agent with ID: " + getAID().getName();
    }
}
