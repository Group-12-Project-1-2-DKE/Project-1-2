package Course;

import Course.Nodes.EquationRoot;
import Course.Nodes.Number;
import Course.Nodes.Variable;

import java.util.ArrayList;

public class Equation {
    private String equation;
    private ArrayList<String> variables = new ArrayList<>();
    private final EquationNode root = new EquationRoot("root");

    public static void main(String[] args) {
        Equation eq = new Equation("0.2x^2 - ((sin(33y)))3 + e - pi");
        System.out.println(eq);
    }

    public Equation(String equation){
        equation = equation.replaceAll(" ","").toLowerCase();
        this.equation = equation;
        try {
            parseEquation();
        } catch (IllegalArgumentException e){
            System.out.println("Something went wrong: " + e);
        }
    }

    public void parseEquation(){
        int depth = 0;
        EquationNode currentNode = root;
        for (int i = 0; i < equation.length(); i++){
            char c = equation.charAt(i);
            EquationNode node = null;
            if (c >= 'a' && c <= 'z'){
                String subtext = parseLetters(i);
                node = NodeFactory.makeNode(subtext);
                if (node == null){
                    variables.add(subtext);
                    node = NodeFactory.makeNode("var");
                    ((Variable)node).setVar(subtext);
                }//Add Variables node
                System.out.println(subtext);

                i += subtext.length() - 1;
            } else if ((c >= '0' && c <= '9') || c == '.'){
                String subtext = parseNumbers(i);
                double number = Double.parseDouble(subtext);
                System.out.println(number);

                i += subtext.length() - 1;
                //number/constant node
                node = NodeFactory.makeNode("num");
                ((Number)node).setValue(number);
            }else{
                 if (c == '('){
                    depth++;
                 }
                 System.out.println(c);
                 if (c == ')'){
                     depth--;
                     while (currentNode.getPriority() != 0){
                         currentNode = currentNode.parent();
                     }
                     currentNode = currentNode.parent();
                     continue;
                 }
                 node = NodeFactory.makeNode(Character.toString(c));
            }

            if (node == null){
                throw new IllegalArgumentException("Some part of the equation can't be recognized: " + c);
            }

            /*if (node.getPriority() < 3 && currentNode.getPriority() < 3){
                throw new IllegalArgumentException("Syntax error");
            }//hmmm?*/

            //insert * at e.g 2y

            if (node.getPriority() <= currentNode.getPriority() && node.getPriority() > 0){
                EquationNode p = currentNode.parent();
                while (node.getPriority() != 0 && node.getPriority() <= p.getPriority()){
                    currentNode = p;
                    p = p.parent();
                    System.out.println("p: " + p);
                }
                p.children().remove(p.children().size() - 1);
                p.add(node);
                node.add(currentNode);
            } else{
                currentNode.add(node);
            }
            currentNode = node;

            System.out.println(this);

        }
    }

    private String parseLetters(int index){
        String subString = "";
        for (int i = index; i < equation.length(); i++){
            char c = equation.charAt(i);
            if (c >= 'a' && c <= 'z'){
                subString += c;
            } else{
                break;
            }
        }
        return subString;
    }

    private String parseNumbers(int index){
        String subString = "";
        for (int i = index; i < equation.length(); i++){
            char c = equation.charAt(i);
            if ((c >= '0' && c <= '9') || c == '.'){
                subString += c;
            } else{
                break;
            }
        }
        return subString;
    }

    public void setVariables(ArrayList<String> variables){
        this.variables = variables;
    }

    public String toString(){
        return equation + "\n" + variables + "\n" + root;
    }

}
