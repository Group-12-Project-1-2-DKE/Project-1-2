package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

import java.util.ArrayList;

public class Minus extends BaseEquationNode {
    public Minus(String label){
        super(label, 1);
    }

    @Override
    public EquationNode newInstance() {
        return new Minus("-");
    }

    @Override
    public double solve(ArrayList<String> variables, double[] parameters) {
        double value = children.get(0).solve(variables, parameters) - children.get(1).solve(variables, parameters);
        for (int i = 2; i < children.size(); i++){
            value *= children.get(i).solve(variables, parameters);
        }
        return value;
    }
}
