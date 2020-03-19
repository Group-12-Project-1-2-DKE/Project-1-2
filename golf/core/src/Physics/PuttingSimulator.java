package Physics;

import com.mygdx.game.MyGdxGame;

import Course.PuttingCourse;
import Objects.*;

public class PuttingSimulator{
    PuttingCourse course; 
    PhysicsEngine engine; 
    int shot_counter = 0;
    
    //Main method for testing
    public static void main(String[] args) {
        PuttingCourse course = new PuttingCourse("0.02*x^2 + 0.02*y^2", new Vector2D(4, 0), new Vector2D(0, 0),
        new Ball(new Vector2D(4, 0), 0.9, 1), 0.1, 5, 4);
        EulerSolver e = new EulerSolver();
        e.set_step_size(0.01);
        e.set_fric_coefficient(course.getFrictionCoefficient());
        PhysicsEngine engine = e;
        PuttingSimulator p = new PuttingSimulator(course, engine);
        try{
            long start = System.currentTimeMillis();
            p.take_shot(new Vector2D(2, 0));
            System.out.println(System.currentTimeMillis() - start);
        } catch (StackOverflowError s){
            System.out.println(s);
        }
    }
    
    public PuttingSimulator(PuttingCourse course, PhysicsEngine engine){
        this.course = course; 
        this.engine = engine; 
    }

    public void setBallPosition(Vector2D vector){
        course.getBall().setLocation(vector);
    }

    public Vector2D get_ball_position(){
        return course.getBall().getLocation();
    }

    public void take_shot(Vector2D initial_ball_velocity){
        shot_counter++;
        Vector2D next_velocity = initial_ball_velocity;
        course.getBall().hit();
        while (course.getBall().isHit()){
            next_velocity = engine.calculateShot(next_velocity, course.getBall(), course);
            //MyGdxGame.setBallPosition(course.getBall().getLocation());
        }
        System.out.println("Final ball location: " + course.getBall().getLocation());
    }
}