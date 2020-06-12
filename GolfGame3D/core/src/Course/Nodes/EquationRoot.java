package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

public class EquationRoot extends BaseEquationNode {
    public EquationRoot(String label){
        super(label, 0);
    }

    @Override
    public EquationNode newInstance() {
        return new EquationRoot("root");
    }
}
