package Course;

import Objects.Ball;
import Physics.Function2D;
import Physics.Vector2D;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

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
        PuttingCourse h = new PuttingCourse("2*x^0.5 + -1*y^-3", new Vector2D(0,0), new Vector2D(10,0),
                new Ball(new Vector2D(0,0), 3, (float)0.5), 0.05, 4, 4);
    }

    /**
     * constructor
     * @param //height
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
        setEquation(equation);
    }


    public ArrayList<ModelInstance> getCourseShape(Model model){
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        ArrayList<ModelInstance> instances = new ArrayList<>();
        modelBuilder.node().id = "groundBalls";
        modelBuilder.part("parcel" , GL20.GL_TRIANGLES, VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal,
                new Material(ColorAttribute.createDiffuse(Color.GREEN))).sphere(0.5f,0.5f,0.5f,5,5);
        model  = modelBuilder.end();

        ModelInstance groundBall = new ModelInstance(model,"groundBalls");
        for(float i = -15f; i<= 15f; i+= 0.3f){
            for(float j = -50; i <= 149; j+= 0.3f){
                groundBall = new ModelInstance(model,"groundBalls");
                groundBall.transform.setTranslation(i,(float)evaluate(new Vector2D(i,j))-0.25f,j);// we just change the height to generate a ground consisting of balls
                instances.add(groundBall);
            }
        }
        return instances;
    }

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

    public void setEquation(String equation){
        this.equation = equation;

        equation = equation.replaceAll(" ","");

        components = parseEquation(equation);
    }

    private String[][][] parseEquation(String eq){
        String[] plus_c = eq.split("\\+");
        String[][][] comps = new String[plus_c.length][][];

        for (int i = 0; i < plus_c.length; i++){
            String p = plus_c[i];
            String[] times_c = p.split("\\*");
            comps[i] = new String[times_c.length][];
            for (int j = 0; j < times_c.length; j++){
                String t = times_c[j];
                String[] power_c = t.split("\\^");
                comps[i][j] = power_c;
            }
        }
        return comps;
    }

    public String getEquation(){
        return equation;
    }

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
