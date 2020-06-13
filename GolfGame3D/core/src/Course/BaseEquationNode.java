package Course;

import java.util.ArrayList;

public abstract class BaseEquationNode implements EquationNode{
    protected String label;
    protected EquationNode parent;
    protected ArrayList<EquationNode> children = new ArrayList<>();
    protected boolean increaseDepth;
    protected boolean depthIncreased;//Only for numbers, vars?
    protected final int priority;

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
    public boolean increaseDepth() {
        return increaseDepth;
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

    @Override
    public String toString() {
        return "BaseEquationNode{" +
                "label='" + label + '\'' +
                ", children=" + children +
                '}';
    }

    @Override
    public double solve(ArrayList<String> variables, double[] parameters) {
        return 0;
    }
}
