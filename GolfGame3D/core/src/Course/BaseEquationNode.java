package Course;

import java.util.ArrayList;

public abstract class BaseEquationNode implements EquationNode{
    protected String label;
    protected EquationNode parent;
    protected ArrayList<EquationNode> children = new ArrayList<>();
    protected final int priority;
    protected boolean isFunction = false;

    public BaseEquationNode(String label, int priority){
        this.label = label;
        this.priority = priority;
    }

    @Override
    public String label(){
        return label;
    }

    @Override
    public EquationNode parent(){
        return parent;
    }

    @Override
    public ArrayList<EquationNode> children(){
        return children;
    }

    @Override
    public void add(EquationNode node) {
        children.add(node);
        node.setParent(this);
    }

    @Override
    public void setParent(EquationNode parent) {
        this.parent = parent;
    }

    @Override
    public int getPriority(){
        return priority;
    }

    public boolean isFunction() {
        return isFunction;
    }

    @Override
    public String toString() {
        return "BaseEquationNode{" +
                "label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
