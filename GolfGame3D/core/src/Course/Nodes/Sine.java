package Course.Nodes;

import Course.EquationNode;

public class Sine extends Function {
    public Sine(String label){
        super(label);
    }

    @Override
    public EquationNode newInstance() {
        return new Sine("sin");
    }

    @Override
    public double solveFunction(double input) {
        return Math.sin(input);
    }
}
