package Course;

import Course.Nodes.Plus;

import java.util.ArrayList;

public class NodeFactory {
    private static final ArrayList<EquationNode> prototypes = new ArrayList<>();

    {
        prototypes.add(new Plus());
    }

    public static ArrayList<EquationNode> getPrototypes(){
        return prototypes;
    }

    public static EquationNode makeNode(String label){
        return null;
    }
}
