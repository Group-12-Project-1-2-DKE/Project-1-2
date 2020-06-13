package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

public class Plus extends BaseEquationNode {
    public Plus(String label){
        super(label, 1);
    }

    @Override
    public EquationNode newInstance() {
        return new Plus("+");
    }
}