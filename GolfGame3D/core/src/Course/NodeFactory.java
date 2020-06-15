package Course;

import Course.Nodes.*;
import Course.Nodes.Number;

import java.util.ArrayList;

public class NodeFactory {
    private static final ArrayList<EquationNode> prototypes = new ArrayList<>();

    static {
        prototypes.add(new Plus("+"));
        prototypes.add(new Minus("-"));
        prototypes.add(new Multiply("*"));
        prototypes.add(new Divide("/"));
        prototypes.add(new Power("^"));
        prototypes.add(new Number("num"));
        prototypes.add(new Variable("var"));
        prototypes.add(new EquationRoot("("));
        prototypes.add(new PiConstant("pi"));
        prototypes.add(new EConstant("e"));
        prototypes.add(new Sine("sin"));
        prototypes.add(new Cosine("cos"));
        prototypes.add(new Logarithm("log"));
        prototypes.add(new NatLog("ln"));
    }

    public static EquationNode makeNode(String label){
        for (EquationNode prototype : prototypes){
            if (prototype.label().equals(label)){
                return prototype.newInstance();
            }
        }

        return null;
    }
}
