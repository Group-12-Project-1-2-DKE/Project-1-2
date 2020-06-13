package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

public class Sine extends BaseEquationNode {
    public Sine(String label){
        super(label, 4);
    }

    @Override
    public EquationNode newInstance() {
        return new Sine("sin");
    }
}