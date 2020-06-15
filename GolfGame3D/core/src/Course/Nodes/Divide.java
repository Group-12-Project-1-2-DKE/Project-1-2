package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

import java.util.ArrayList;

public class Divide extends BaseEquationNode {
    public Divide(String label){
        super(label, 2);
    }

    @Override
    public EquationNode newInstance() {
        return new Divide("/");
    }

    @Override
    public double solve(ArrayList<String> variables, double[] parameters) {
        double value = children.get(0).solve(variables, parameters) / children.get(1).solve(variables, parameters);
        for (int i = 2; i < children.size(); i++){
            value *= children.get(i).solve(variables, parameters);
        }
        return value;
    }
}
