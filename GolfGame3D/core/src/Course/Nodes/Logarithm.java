package Course.Nodes;

import Course.EquationNode;

public class Logarithm extends Function {
    public Logarithm(String label){
        super(label);
    }

    @Override
    public EquationNode newInstance() {
        return new Logarithm("log");
    }

    @Override
    public double solveFunction(double input) {
        return Math.log10(input);
    }
}
