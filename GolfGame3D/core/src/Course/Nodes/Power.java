package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

public class Power extends BaseEquationNode {
    public Power(String label){
        super(label, 3);
    }

    @Override
    public EquationNode newInstance() {
        return new Power("^");
    }
}
