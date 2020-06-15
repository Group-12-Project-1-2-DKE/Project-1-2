package Course.Nodes;

import java.util.ArrayList;

public abstract class Function extends SubEquation {
    public Function(String label){
        super(label);
        isFunction = true;
    }

    @Override
    public double solve(ArrayList<String> variables, double[] parameters) {
        return solveFunction(super.solve(variables, parameters));
    }

    public abstract double solveFunction(double input);
}
