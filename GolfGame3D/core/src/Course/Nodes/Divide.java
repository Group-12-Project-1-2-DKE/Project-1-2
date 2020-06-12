package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

public class Divide extends BaseEquationNode {
    public Divide(String label){
        super(label, 2);
    }

    @Override
    public EquationNode newInstance() {
        return new Divide("/");
    }
}
