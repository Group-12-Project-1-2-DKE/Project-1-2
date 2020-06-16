package Course;

import Objects.Ball;
import Physics.Function2D;
import Physics.PuttingSimulator;
import Physics.RungeKuttaSolver;
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
import com.mygdx.game.Variables;

public class PuttingCourse implements Function2D{

    private Vector2D start;
    private Vector2D flag;
    private Ball ball;
    private double friction; //friction coefficient
    private double tolerance; //hole tolerance
    private double maxVelocity;
    private String equation;
    private double limstep = 0.0001;//Larger than 10^-4 or smaller than 10^-5 probably gives more error
    private Equation eq;

    /**
     * Main method for testing
     */
    public static void main(String[] args){
        PuttingCourse h = new PuttingCourse("0.2sin(x0.5)^2 + ln(1+0.1y^2)", new Vector2D(0,0), new Vector2D(10,0),
                new Ball(new Vector2D(0,0), 3, (float)0.5), 0.05, 4, 4);
        RungeKuttaSolver r = new RungeKuttaSolver();
        PuttingSimulator p = new PuttingSimulator(h, r);
        Variables.lowerBound = new Vector2D(-100, -100);
        Variables.upperBound = new Vector2D(100, 100);
        p.take_shot(new Vector2D(3, 2));
        System.out.println(p.get_ball_position());
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
        double x = p.getX();
        double y = p.getY();
        //Five-point centred difference
        double x_derivative = (evaluate(x - 2*limstep, y) - 8*evaluate(x - limstep, y) +
                8*evaluate(x + limstep, y) - evaluate(x + 2*limstep, y))/(12*limstep);
        double y_derivative = (evaluate(x, y - 2*limstep) - 8*evaluate(x, y - limstep) +
                8*evaluate(x, y + limstep) - evaluate(x, y + 2*limstep))/(12*limstep);

        return new Vector2D(x_derivative, y_derivative);
    }

    public double evaluate(double x, double y){
        return evaluate(new Vector2D(x, y));
    }

    /**
     * Calculates the height at current coordinates (aka your vector)
     * @param p coordinate vector
     * @return height
     */
    public double evaluate(Vector2D p){
        return eq.solve(new double[]{p.getX(), p.getY()});
    }

    public void setEquation(String equation){
        this.equation = equation;
        eq = new Equation(equation);
        ArrayList<String> vars = new ArrayList<>();
        vars.add("x"); vars.add("y");
        eq.setVariables(vars);
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
