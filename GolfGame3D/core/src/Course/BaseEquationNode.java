package Course;

import java.util.ArrayList;

public abstract class BaseEquationNode implements EquationNode{
    protected String label;
    protected EquationNode parent;
    protected ArrayList<EquationNode> children;

    public BaseEquationNode(){

    }

    public String label(){
        return label;
    }

    public EquationNode parent(){
        return parent;
    }

    public ArrayList<EquationNode> children(){
        return children;
    }
}
