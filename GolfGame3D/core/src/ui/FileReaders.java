package ui;

import com.mygdx.game.Variables;
//import org.graalvm.compiler.lir.Variable;

import java.util.*;
import java.io.*;

public class FileReaders{
    //private double gravity;
            //private double diameter;
    //private double mass;
            //private double coeficientOfFriction;
            //private double initialSpeed;
    //private double tolerance;
    //private double xStart;
    //private double yStart;
    //private double xGoal;
    //private double yGoal;
    //private String function;
    //private double maxVelocity;

    //private double xVelocity;
    //private double yVelocity;

    public FileReaders(){
    }

    public void read (String fileName){
        // Get the path of the project on the computer
        String path = System.getProperty("user.dir");
        // Specify he path to be the path of the file
        path = path + "\\core\\src\\ui\\" + fileName;
        System.out.println(path);
        File file = new File(path);//path + "\\core\\src\\ui\\" + fileName);

        // If the file doesn't exist print error.
        if (!file.exists()) {
            System.out.println("System couldnt file source file!");
            System.out.println("Application will explode");
        }

        FileReader fr = null;
        LinkedList<Float> num = new LinkedList<Float>();
        try {
            fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            while((line = br.readLine())!=null) {       // For each line in the CSV file
                // Read the line, and convert the string to a list of numbers
                String[] numbers = line.split(" ");

                for(int i=0; i<numbers.length; i++){
                    if(numbers[i].matches(".*\\d.*")){
                        float num1 = Float.parseFloat(numbers[i]);
                        num.add(num1);
                    }else if(numbers[i].equals("Function=")){
                        Variables.function = numbers[++i];
                    }
                }
            }
            Variables.gravity = num.pop();
            Variables.ballMass = num.pop();
            Variables.tolerance = num.pop();
            Variables.startX = num.pop();
            Variables.startY = num.pop();
            Variables.goalX = num.pop();
            Variables.goalY = num.pop();
            Variables.shootX = num.pop();
            Variables.shootY = num.pop();

            // Exeptions.
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fr != null){
                try{
                    fr.close();
                } catch(IOException e){

                }
            }
        }
    }

//    public void writeFile(String fileName){
//        PrintWriter writer = null;
//        try {
//            writer = new PrintWriter(fileName);
//            writer.println("Gravity = ");
//            writer.println("Mass = ");
//            writer.println("Coefficient of friction = ");
//            writer.println("Max speed = ");
//            writer.println("tolerence for the goal = ");
//            writer.println("Start position X = ");
//            writer.println("Start position Y = ");
//            writer.println("Goal position X = ");
//            writer.println("Goal position Y = ");
//            writer.println("Ball latest position X = ");
//            writer.println("Ball latest position Y = ");
//
//        } catch (IOException e) {
//        }finally{
//            writer.close();
//        }
//    }
}
