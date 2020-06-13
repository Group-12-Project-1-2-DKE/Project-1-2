package Course.Nodes;

import Course.BaseEquationNode;
import Course.EquationNode;

public class NatLog extends BaseEquationNode {
    public NatLog(String label){
        super(label, 4);
    }

    @Override
    public EquationNode newInstance() {
        return new NatLog("ln");
    }
}
