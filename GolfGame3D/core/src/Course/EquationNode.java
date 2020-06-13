package Course;

import java.util.ArrayList;

public interface EquationNode {
    public String label();
    public EquationNode parent();
    public ArrayList<EquationNode> children();
    public boolean increaseDepth();
    public void add(EquationNode node);
    public void setParent(EquationNode parent);
    public double solve(ArrayList<String> variables, double[] parameters);
    public int getPriority();
    public EquationNode newInstance();
}
