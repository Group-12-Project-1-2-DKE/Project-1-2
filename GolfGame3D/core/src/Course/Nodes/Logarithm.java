package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

public class Logarithm extends BaseEquationNode {//make interface/abstract/parent class for all functions
    public Logarithm(String label){
        super(label, 4);
    }

    @Override
    public EquationNode newInstance() {
        return new Logarithm("log");
    }
}
