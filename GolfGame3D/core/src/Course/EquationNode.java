package Course;

import java.util.ArrayList;

public interface EquationNode {
    String label();
    EquationNode parent();
    ArrayList<EquationNode> children();
    void add(EquationNode node);
    void setParent(EquationNode parent);
    double solve(ArrayList<String> variables, double[] parameters);
    int getPriority();
    EquationNode newInstance();
}
