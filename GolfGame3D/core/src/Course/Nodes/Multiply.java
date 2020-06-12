package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

public class Multiply extends BaseEquationNode {
    public Multiply(String label){
        super(label, 2);
    }

    @Override
    public EquationNode newInstance() {
        return new Multiply("*");
    }
}
