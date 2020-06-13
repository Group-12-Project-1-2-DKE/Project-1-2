package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

import java.util.ArrayList;

public class Power extends BaseEquationNode {
    public Power(String label){
        super(label, 3);
    }

    @Override
    public EquationNode newInstance() {
        return new Power("^");
    }

    @Override
    public double solve(ArrayList<String> variables, double[] parameters) {
        double value = Math.pow(children.get(0).solve(variables, parameters), children.get(1).solve(variables, parameters));
        for (int i = 2; i < children.size(); i++){
            value *= children.get(i).solve(variables, parameters);
        }
        System.out.println(label + ": " + value);
        return value;
    }
}
