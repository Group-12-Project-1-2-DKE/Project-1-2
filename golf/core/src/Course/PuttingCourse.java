package Course;

import Objects.Ball;
import Physics.Function2D;
import Physics.Vector2D;
import java.util.Arrays;

public class PuttingCourse implements Function2D{
   
    private Vector2D start; 
    private Vector2D flag; 
    private Ball ball; 
    private double friction; //friction coefficient 
    private double tolerance; //hole tolerance 
    private double maxVelocity; 
    private String equation;
    private String[][][] components;
    private double limstep = 0.0000000001;
   
    /**
     * Main method for testing
     */
    public static void main(String[] args){
        Function2D h = new PuttingCourse("0.5*x^3 + y", new Vector2D(0,0), new Vector2D(10,0),
        new Ball(new Vector2D(0,0), 3, 0.5), 0.05, 4, 4);

        System.out.println(h.evaluate(new Vector2D(1, 1)));
        System.out.println(h.gradient(new Vector2D(1, 1)));
    }
    
     /**
      * constructor
      * @param height 
      * @param start
      * @param flag
      * @param ball
      * @param friction
      * @param maxVelocity
      * @param tolerance
      */
    public PuttingCourse(String equation, Vector2D start, Vector2D flag, Ball ball, double friction, double maxVelocity, double tolerance){ 
        this.start = start; 
        this.flag = flag; 
        this.ball = ball; 
        this.friction = friction; 
        this.maxVelocity = maxVelocity;
        this.tolerance = tolerance;
        this.equation = equation;

        equation = equation.replaceAll(" ","");

        String[] plus_c = equation.split("\\+");
        components = new String[plus_c.length][][];

        for (int i = 0; i < plus_c.length; i++){
            String p = plus_c[i];
            String[] times_c = p.split("\\*");
            components[i] = new String[times_c.length][];
            for (int j = 0; j < times_c.length; j++){
                String t = times_c[j];
                String[] power_c = t.split("\\^");
                components[i][j] = power_c;
            }
        }
        //System.out.println(Arrays.deepToString(components));
    }
     
    /*@Override
     /**
      * methof to evaluate the height
      * @return the height as a double 
     */ 
    /*public double evaluateHeight(Vector2D vector){
        //TODO complete the method
        return 0; 
    }
    /**
     *  method to evaluate the slope 
     * @return 
     *//*
    @Override 
    public Vector2D gradient(Vector2D vector){
        double x0 = vector.getX(); 
        double y0 = vector.getY();
        //TODO complete this method
        

    }*/

    /**
     * Calculates gradient at current coordinates (aka your vector)
     * @param p
     * @return gradient
     */
    public Vector2D gradient(Vector2D p){
        double x_derivative = (evaluate(new Vector2D(p.getX() + limstep, p.getY())) - evaluate(p)) / limstep;
        double y_derivative = (evaluate(new Vector2D(p.getX(), p.getY() + limstep)) - evaluate(p)) / limstep;

        return new Vector2D(x_derivative, y_derivative);
    }

    /**
     * Calculates the height at current coordinates (aka your vector)
     * @param p
     * @return height
     */
    public double evaluate(Vector2D p){
        double total = 0;

        for (int i = 0; i < components.length; i++){
            double subtotal = 1;
            for (int j = 0; j < components[i].length; j++){
                double subbtotal = getNumber(components[i][j][components[i][j].length - 1], p);
                
                for (int k = components[i][j].length - 2; k >= 0 ; k--){
                    subbtotal = Math.pow(getNumber(components[i][j][k], p), subbtotal);
                }

                subtotal *= subbtotal;
            }
            total += subtotal;
        }
        
        return total;
    }

    /**
     * Some code so I don't have to copy and paste everything:
     * If s equals "x" or "y", it returns the x or y value of the given vector.
     * If s equals a double, it returns the double.
     * If s equals neither (which it shouldn't), it returns 0 and prints an error message.
     * @param s
     * @param p
     * @return double in String s
     */
    public double getNumber(String s, Vector2D p){
        try{
            return Double.parseDouble(s);
        } catch (Exception e){ //Didn't know the right exception, so I just put Exception here
            //System.out.println("oof");
            if ("x".equals(s)){
                return p.getX();
            } else if ("y".equals(s)){
                return p.getY();
            } else{
                System.out.println("Part of the equation cannot be recognized.");
            }
        }
        return 0;
    }

    public String getEquation(){
        return equation;
    }

    /**
     * method that evaluates the friction
     * @return friction coefficient 
     *//*
    @Override
    public double evaluateFriction(){
        //TODO complete the method
        return 0; 
    } */

    public void setLimstep(double limstep){
        this.limstep = limstep;
    }

    public double getLimstep(){
        return limstep;
    }

    /**
     * getter for the ball
     * @return ball
     */
    public Ball getBall(){
        return ball; 
    }

    /**
     * getter for the aimed position
     * @return flag
     */
    public Vector2D getFlag(){
        return flag; 
    }

    /**
     * getter for the starting position
     * @return start position
     */
    public Vector2D getStart(){
        return start; 
    }

    /**
     * getter for the friction coefficient
     * @return friction coefficient
     */
    public double getFrictionCoefficient(){
        return friction; 
    }

    /**
     * getter for the makximum velocity
     * @return maximum velocity
     */
    public double getMaxVelocity(){
        return maxVelocity; 
    }
    
    /**
     * getter for the hole tolerance coefficient 
     * @return the tolerance value
     */
    public double getTolerance(){
        return tolerance;
    }
}