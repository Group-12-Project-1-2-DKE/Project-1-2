package ui;

import java.util.*;
import java.io.*;

public class FileReaders{
    private double gravity;
    private double diameter;
    private double mass;
    private double coeficientOfFriction;
    private double initialSpeed;
    private double tolerance;
    private double xStart;
    private double yStart;
    private double xGoal;
    private double yGoal;
    private String function;
    private double maxVelocity;

    private double xVelocity;
    private double yVelocity;

    public FileReaders(){

    }

    public void read (String fileName){
        FileReader fr = null;
        LinkedList<Double> num = new LinkedList<Double>();
        try {
            fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            while((line = br.readLine())!=null) {       // For each line in the CSV file
                // Read the line, and convert the string to a list of numbers
                String[] numbers = line.split(" ");

                for(int i=0; i<numbers.length; i++){
                    if(numbers[i].matches(".*\\d.*")){
                        double num1 = Double.parseDouble(numbers[i]);
                        num.add(num1);
                    }else if(numbers[i].equals("Function=")){
                        function = numbers[++i];
                    }
                }
            }
            gravity = num.pop();
            diameter = num.pop();
            mass = num.pop();
            coeficientOfFriction = num.pop();
            initialSpeed = num.pop();
            tolerance = num.pop();
            xStart = num.pop();
            yStart = num.pop();
            xGoal = num.pop();
            yGoal = num.pop();
            xVelocity = num.pop();
            yVelocity = num.pop();
            maxVelocity = num.pop();

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

    public void writeFile(String fileName){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName);
            writer.println("Gravity = " + 100);
            writer.println("Mass = ");
            writer.println("Coefficient of friction = ");
            writer.println("Max speed = ");
            writer.println("tolerence for the goal = ");
            writer.println("Start position X = ");
            writer.println("Start position Y = ");
            writer.println("Goal position X = ");
            writer.println("Goal position Y = ");
            writer.println("Ball latest position X = ");
            writer.println("Ball latest position Y = ");

        } catch (IOException e) {
        }finally{
            writer.close();
        }
    }

    // Setters

    public void setGravity(double gravity){
        this.gravity = gravity;
    }

    public void setDiameter(double diameter){
        this.diameter = diameter;
    }

    public void setMass(double mass){
        this.mass = mass;
    }

    public void setCoefficientOfFriction(double coeficientOfFriction){
        this.coeficientOfFriction = coeficientOfFriction;
    }

    public void setInitialSpeed(double speed){
        this.initialSpeed = speed;
    }

    public void setTolerance(double tolerance){
        this.tolerance = tolerance;
    }

    public void setXStart(double xStart){
        this.xStart = xStart;
    }

    public void setYStart(double yStart){
        this.yStart = yStart;
    }

    public void setXGoal(double xGoal){
        this.xGoal = xGoal;
    }

    public void setyGoal(double yGoal){
        this.yGoal = yGoal;
    }

    public void setFunction(String function){
        this.function = function;
    }

    public void setxVelocity(double xVelocity){
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(double yVelocity){
        this.yVelocity = yVelocity;
    }

    public void setMaxVelocity(double maxVelocity){
        this.maxVelocity = maxVelocity;
    }

    // Getters

    public double getGravity(){
        return this.gravity;
    }

    public double getDiameter(){
        return this.diameter;
    }

    public double getMass(){
        return this.mass;
    }

    public double getCoefficientOfFriction(){
        return this.coeficientOfFriction;
    }

    public double getInitialSpeed(){
        return this.initialSpeed;
    }

    public double getTolerance(){
        return this.tolerance;
    }

    public double getXStart(){
        return this.xStart;
    }

    public double getYStart(){
        return this.yStart;
    }

    public double getXGoal(){
        return this.xGoal;
    }

    public double getYGoal(){
        return this.yGoal;
    }

    public String getFunction(){
        return this.function;
    }

    public double getxVelocity(){
        return this.xVelocity;
    }

    public double getyVelocity(){
        return this.yVelocity;
    }

    public double getMaxVelocity(){
        return this.maxVelocity;
    }
}
