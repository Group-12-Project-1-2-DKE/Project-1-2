package Course.Nodes;

import Course.EquationNode;

public class EConstant extends Number{
    public EConstant(String label){
        super(label);
        setValue(Math.E);
    }

    @Override
    public EquationNode newInstance() {
        return new EConstant("e");
    }
}
