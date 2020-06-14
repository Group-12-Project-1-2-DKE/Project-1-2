package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

import java.util.ArrayList;

public class EquationRoot extends BaseEquationNode {
    public EquationRoot(String label){
        super(label, 0);
    }

    @Override
    public EquationNode newInstance() {
        return new EquationRoot("root");
    }

    @Override
    public double solve(ArrayList<String> variables, double[] parameters) {
        double value = children.get(0).solve(variables, parameters);
        for (int i = 1; i < children.size(); i++){
            value *= children.get(i).solve(variables, parameters);
        }
        return value;
    }
}
