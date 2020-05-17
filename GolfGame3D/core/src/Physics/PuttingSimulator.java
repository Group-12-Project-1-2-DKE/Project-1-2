package Physics;

import com.mygdx.game.GolfGame;

import Course.PuttingCourse;

import static com.mygdx.game.GolfGame.ball;

public class PuttingSimulator{
    PuttingCourse course;
    PhysicsEngine engine;
    int shot_counter = 0;
    int counter = 0;
    private GolfGame game;

    public PuttingSimulator(PuttingCourse course, PhysicsEngine engine){
        this.course = course;
        this.engine = engine;
        game = new GolfGame();
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

        if (initial_ball_velocity.length() == 0){
            next_velocity = new Vector2D(0.00000001, 0.00000001);
        }
        int cnt = 0;
        while (course.getBall().isHit()){
            next_velocity = engine.calculateShot(next_velocity, course.getBall(), course);

            if (Double.isNaN(next_velocity.getX()) || Double.isNaN(next_velocity.getY())){
                System.out.println("Something went wrong! NaN");
                break;
            }
        }
        System.out.println("Final ball location: " + course.getBall().getLocation());
    }

    public Vector2D take_shotSlowly(Vector2D initial_ball_velocity,int fifty) {
        Vector2D next_velocity = initial_ball_velocity;

        if (course.getBall().isHit() && counter <= fifty){
            counter++;
            if (counter <= 10){
                System.out.println(counter);
                System.out.println(course.getBall().getLocation());
                System.out.println(next_velocity);
                System.out.println("-----------");
            }

            next_velocity = engine.calculateShot(next_velocity, course.getBall(), course);

        }

        if (counter==fifty+1 || !course.getBall().isHit() ) {
            shot_counter++;
            System.out.println("Final ball location: " + course.getBall().getLocation());
            counter=0;
            return null;
        }

        return next_velocity;
    }

    public void updateBall(float x, float y){
        float z = (float) course.evaluate(new Vector2D(x,y));
        ball.transform.setTranslation(x,(float)-(z / course.getBall().getDiameter() /2), y + 1);
    }
}
