package Course.Nodes;

import Course.BaseEquationNode;
import Course.Equation;
import Course.EquationNode;

import java.util.ArrayList;

public class SubEquation extends BaseEquationNode {
    Equation subEquation;

    public SubEquation(String label){
        super(label, 4);
    }

    @Override
    public EquationNode newInstance() {
        return new SubEquation("()");
    }

    public void setSubEquation(Equation subEquation) {
        this.subEquation = subEquation;
    }

    public Equation getSubEquation() {
        return subEquation;
    }

    @Override
    public double solve(ArrayList<String> variables, double[] parameters) {
        return subEquation.solve(parameters);
    }
}
