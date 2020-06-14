package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

import java.util.ArrayList;

public class Variable extends BaseEquationNode{
    private String var;

    public Variable(String label){
        super(label, 4);
    }

    @Override
    public EquationNode newInstance() {
        return new Variable("var");
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "var='" + var + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }

    @Override
    public double solve(ArrayList<String> variables, double[] parameters) {
        if (children.size() > 0){
            System.out.println("whoops something probably went wrong\nchildren: " + children);
        }
        double value = 0;
        boolean test = false;
        for (int i = 0; i < variables.size(); i++){
            if (variables.get(i).equals(var)){
                test = true;
                value = parameters[i];
            }
        }
        if (!test){
            System.out.println("Hmmmm. This shouldn't happen...");
        }
        return value;
    }
}
