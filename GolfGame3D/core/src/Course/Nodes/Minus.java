package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

public class Minus extends BaseEquationNode {
    public Minus(String label){
        super(label, 1);
    }

    @Override
    public EquationNode newInstance() {
        return new Minus("-");
    }
}
