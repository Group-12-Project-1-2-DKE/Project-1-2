package Physics;

import Course.PuttingCourse;

public class PuttingSimulator{
    PuttingCourse course;
    PhysicsEngine engine;
    int shot_counter = 0;
    int counter = 0;
    int stepsPerRender = 1;

    public PuttingSimulator(PuttingCourse course, PhysicsEngine engine){
        this.course = course;
        this.engine = engine;
    }

//    public void setBallPosition(Vector2D vector){
//        course.getBall().setLocation(vector);
//    }

    public Vector2D get_ball_position(){
        return course.getBall().getLocation();
    }

    public PhysicsEngine getEngine() {
        return engine;
    }

    public void take_shot(Vector2D initial_ball_velocity){
        shot_counter++;
        Vector2D next_velocity = initial_ball_velocity;
        course.getBall().hit();

        if (initial_ball_velocity.length() == 0){
            next_velocity = new Vector2D(0.00000001, 0.00000001);
        }
        while (course.getBall().isHit()){
            next_velocity = engine.calculateShot(next_velocity, course.getBall(), course);

            if (Double.isNaN(next_velocity.getX()) || Double.isNaN(next_velocity.getY())){
                System.out.println("Something went wrong! NaN");
                break;
            }
        }
        System.out.println("Final ball location: " + course.getBall().getLocation());
    }


    public Vector2D take_shotSlowly(Vector2D initial_ball_velocity) {
        //Vector2D next_velocity = initial_ball_velocity;
        //     course.getBall().setLocation(initial_ball_velocity);
        if (initial_ball_velocity.length() == 0){
            initial_ball_velocity = new Vector2D(0.00000001, 0.00000001);
        }
        //if (course.getBall().isHit() && counter <= fifty){
        //counter++;
            /*if (counter <= 10){
                System.out.println(counter);
                System.out.println(course.getBall().getLocation());
                System.out.println(next_velocity);
                System.out.println("-----------");
            }*/

        Vector2D next_velocity = initial_ball_velocity;
        for (int i = 0; i < stepsPerRender; i++) {
            if (course.getBall().isAtRest()){
                shot_counter++;
                return null;
            }
            next_velocity = engine.calculateShot(next_velocity, course.getBall(), course);
            if (Double.isNaN(next_velocity.getX()) || Double.isNaN(next_velocity.getY())){
                System.out.println("Something went wrong! NaN");
            }
        }

        //}

        //if (counter==fifty+1 || !course.getBall().isHit() ) {
        //    shot_counter++;
        //    System.out.println("Final ball location: " + course.getBall().getLocation());
        //    counter=0;
        //    return null;
        //}
        if (course.getBall().isAtRest()){
            shot_counter++;
            return null;
        }
        // course.getBall().setLocation(next_velocity);
        return next_velocity;
    }
}
