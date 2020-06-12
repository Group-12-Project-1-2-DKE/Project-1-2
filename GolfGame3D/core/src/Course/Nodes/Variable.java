package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

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
}
