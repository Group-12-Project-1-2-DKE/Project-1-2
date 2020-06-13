package Course.Nodes;

import Course.EquationNode;

public class PiConstant extends Number{
    public PiConstant(String label){
        super(label);
        setValue(Math.PI);
    }

    @Override
    public EquationNode newInstance() {
        return new PiConstant("pi");
    }
}
