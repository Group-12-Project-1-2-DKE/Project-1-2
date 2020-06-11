package Course;

import java.util.ArrayList;

public class Equation {
    private String equation;
    private ArrayList<String> variables = new ArrayList<>();
    private EquationNode root;

    public Equation(String equation){
        equation = equation.replaceAll(" ","").toLowerCase();
        this.equation = equation;
        parseEquation();
    }

    public void parseEquation(){
        for (int i = 0; i < equation.length(); i++){
            char c = equation.charAt(i);
            EquationNode node;
            //TODO: add variables to variables
            if (c >= 'a' && c <= 'z'){
                String subtext = parseLetters(i);
                node = NodeFactory.makeNode(subtext);
                if (node == null){
                    variables.add(subtext);
                }//Add Variables node
            }
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

}
