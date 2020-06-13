package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

public class Cosine extends BaseEquationNode {
    public Cosine(String label){
        super(label, 4);
    }

    @Override
    public EquationNode newInstance() {
        return new Cosine("cos");
    }
}
