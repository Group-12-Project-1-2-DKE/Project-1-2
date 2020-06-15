package Course.Nodes;

import Course.EquationNode;

public class Cosine extends Function {
    public Cosine(String label){
        super(label);
    }

    @Override
    public EquationNode newInstance() {
        return new Cosine("cos");
    }

    @Override
    public double solveFunction(double input) {
        return Math.cos(input);
    }
}
