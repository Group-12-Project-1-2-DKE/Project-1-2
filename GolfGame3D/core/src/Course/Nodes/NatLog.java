package Course.Nodes;

import Course.EquationNode;

public class NatLog extends Function {
    public NatLog(String label){
        super(label);
    }

    @Override
    public EquationNode newInstance() {
        return new NatLog("ln");
    }

    @Override
    public double solveFunction(double input) {
        return Math.log(input);
    }
}
