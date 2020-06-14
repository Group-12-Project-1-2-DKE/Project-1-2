package Course;

import Course.Nodes.EquationRoot;
import Course.Nodes.Multiply;
import Course.Nodes.Number;
import Course.Nodes.Variable;

import java.util.ArrayList;
import java.util.Arrays;

public class Equation {
    private String equation;
    private ArrayList<String> variables = new ArrayList<>();
    private final EquationNode root = new EquationRoot("root");

    public static void main(String[] args) {
        Equation eq = new Equation("3x^(0.5y)");//"0.2x^2 - sin(33y)3x + e - pi");
        ArrayList<String> v = new ArrayList<>();
        v.add("x"); v.add("y");
        eq.setVariables(v);
        System.out.println(eq);
        System.out.println(eq.solve(new double[]{3, 2}));
        //eq.test(eq.root);
        //lukt shit zoals -1*-x*-4*-e  ? - Nopeee ff fixen dus
        // 3x^(0.5y)  ? - Yess. Zorg dat dit blijft werken
    }

    //Method goes through the whole node tree
    public void test(EquationNode node){
        if (node.getPriority() == 4 || node.children().size() == 0){
            System.out.println(node);
        }
        for (int i = 0; i < node.children().size(); i++){
            test(node.children().get(i));
        }
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

    public void parseEquation() throws IllegalArgumentException{
        int depth = 0;
        EquationNode currentNode = root;
        for (int i = 0; i < equation.length(); i++){
            char c = equation.charAt(i);
            EquationNode node = null;
            if (c >= 'a' && c <= 'z'){
                String subtext = parseLetters(i);
                node = NodeFactory.makeNode(subtext);
                if (node == null){
                    if (!variables.contains(subtext)) {
                        variables.add(subtext);
                    }
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
            if (node.getPriority() == 4 && currentNode.getPriority() == 4){
                currentNode = addNode(new Multiply("*"), currentNode);
            }

            currentNode = addNode(node, currentNode);

            System.out.println(this);
        }
    }

    private EquationNode addNode(EquationNode node, EquationNode currentNode){
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
        return node;
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

    public void setEquation(String equation){
        equation = equation.replaceAll(" ","").toLowerCase();
        this.equation = equation;
        parseEquation();
    }

    public double solve(ArrayList<Double> parameters){
        if (parameters.size() < this.variables.size()){
            throw new IllegalArgumentException("More variables found than given parameters\nfound vars: " + this.variables +
                    "\ngives parameters: " + parameters);
        }
        double[] p = new double[parameters.size()];
        for (int i = 0; i < p.length; i++){
            p[i] = parameters.get(i);
        }
        return solve(p);
    }

    public double solve(double[] parameters){
        if (parameters.length < this.variables.size()){
            throw new IllegalArgumentException("More variables found than given parameters\nfound vars: " + this.variables +
                    "\ngives parameters: " + Arrays.toString(parameters));
        }
        return root.solve(variables, parameters);
    }

    public void setVariables(ArrayList<String> variables) throws IllegalArgumentException{
        if (variables.size() < this.variables.size()){
            throw new IllegalArgumentException("More variables found than given\nfound vars: " + this.variables +
                    "\ngives vars: " + variables);
        }
        for (String s : this.variables){
            if (!variables.contains(s)){
                throw new IllegalArgumentException("Not all found variables are in the given variables\nfound vars: "
                + this.variables + "\ngiven vars: " + variables);
            }
        }
        this.variables = variables;
    }

    public String toString(){
        return equation + "\n" + variables + "\n" + root;
    }

}
