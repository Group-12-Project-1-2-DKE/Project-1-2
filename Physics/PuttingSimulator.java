

public class PuttingSimulator{
    PuttingCourse course; 
    PhysicsEngine engine; 
    
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

    public void take_shot(Vector2d initial_ball_velocity){
        engine.calculateShot(initial_ball_velocity, course.getBall(), course);
    }
}