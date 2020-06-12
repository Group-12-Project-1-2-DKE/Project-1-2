package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

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
}
