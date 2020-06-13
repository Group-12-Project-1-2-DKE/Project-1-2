package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

import java.util.ArrayList;

public class Number extends BaseEquationNode{
    private double value;

    public Number(String label){
        super(label, 4);
    }

    @Override
    public EquationNode newInstance() {
        return new Number("num");
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Number{" +
                "value=" + value +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }

    @Override
    public double solve(ArrayList<String> variables, double[] parameters) {
        if (children.size() > 0){
            System.out.println("whoops something probably went wrong\nchildren: " + children);
        }
        return value;
    }
}
