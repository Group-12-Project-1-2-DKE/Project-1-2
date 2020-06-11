package Course;

import java.util.ArrayList;

public interface EquationNode {
    public String label();
    public EquationNode parent();
    public ArrayList<EquationNode> children();
}
