package Course;

import Course.Nodes.*;
import Course.Nodes.Number;

import java.util.ArrayList;
import java.util.Arrays;

public class Equation {
    private String equation;
    private final ArrayList<String> variables = new ArrayList<>();
    private final EquationNode root = new EquationRoot("root");

    public static void main(String[] args) {
        Equation eq = new Equation("-0.2x^2 - log(33y)x3 + e +- pi");
        ArrayList<String> v = new ArrayList<>();
        v.add("x"); v.add("y");
        eq.setVariables(v);
        System.out.println(eq);
        System.out.println(eq.solve(new double[]{-3, 4}));
        //eq.test(eq.root);
    }

    //Method goes through the whole node tree
    private void test(EquationNode node){
        if (node.getPriority() == 4 || node.children().size() == 0){//Put here stuff you want to test
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
            System.out.println("Something went wrong when parsing: " + e);
        }
    }

    public void parseEquation() throws IllegalArgumentException{
        EquationNode currentNode = root;
        for (int i = 0; i < equation.length(); i++){
            char c = equation.charAt(i);
            EquationNode node;
            if (c >= 'a' && c <= 'z'){
                String subtext = parseLetters(i);
                node = NodeFactory.makeNode(subtext);
                if (node == null){
                    addVariable(subtext);
                    node = NodeFactory.makeNode("var");
                    ((Variable)node).setVar(subtext);
                }
                System.out.println(subtext);

                i += subtext.length() - 1;

                if (((BaseEquationNode)node).isFunction()){
                    if (equation.charAt(i+1) != '('){
                        throw new IllegalArgumentException("Expected '(' after " + subtext);
                    }
                    int end = getCloseParenthesis(i+2);
                    makeSubEquation(equation.substring(i + 2, end), (SubEquation)node);
                    i = end;
                }
            } else if ((c >= '0' && c <= '9') || c == '.' || (currentNode.getPriority() != 4 && c == '-')){
                String subtext = parseNumbers(i);
                i += subtext.length() - 1;
                if (subtext.equals("-")){
                     subtext = "-1";
                }
                double number = Double.parseDouble(subtext);
                System.out.println(number);
                node = NodeFactory.makeNode("num");
                ((Number)node).setValue(number);
            }else if (c == '('){
                node = new SubEquation("()");
                int end = getCloseParenthesis(i+1);
                makeSubEquation(equation.substring(i + 1, end), (SubEquation)node);
                i = end;
            }else{
                 System.out.println(c);
                 node = NodeFactory.makeNode(Character.toString(c));
            }

            if (node == null){
                throw new IllegalArgumentException("Some part of the equation can't be recognized: " + c);
            }

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

    private int getCloseParenthesis(int index){
        int depth = 1;
        for (int i = index; i < equation.length(); i++){
            if (equation.charAt(i) == '('){
                depth++;
            }
            if (equation.charAt(i) == ')'){
                depth--;
            }
            if (depth == 0){
                return i;
            }
        }
        throw new IllegalArgumentException("Wrong syntax");
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
            if ((c >= '0' && c <= '9') || c == '.' || (i == index && c == '-')){
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
        root.children().clear();
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
        try {
            double value = root.solve(variables, parameters);
            if (Double.isNaN(value)){
                throw new Exception("Result was Not a Number.");
            }
            return value;
        } catch (Exception e){
            System.out.println("Something went wrong when solving: " + e);
            return 0;
        }
    }

    private void makeSubEquation(String partInParentheses, SubEquation node){
        Equation subEquation = new Equation(partInParentheses);
        for (String var : subEquation.variables){
            addVariable(var);
        }
        subEquation.setVariables(variables);
        node.setSubEquation(subEquation);
    }

    private void addVariable(String var){
        if (!variables.contains(var)) {
            variables.add(var);
        }
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
        this.variables.clear();
        this.variables.addAll(variables);
    }

    public String toString(){
        return equation + "\n" + variables + "\n" + root;
    }

}
