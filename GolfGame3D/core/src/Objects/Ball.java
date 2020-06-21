package Objects;

import Physics.Vector2D;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GolfGameNoMaze;

/**
 * class Ball is used to describe a ball in the putting simulator
 */
public class Ball {

    private Vector2D location;
    private double mass;
    private float diameter;
    private Vector2D velocity;
    private Vector2 position;

    private boolean atRest = true;
    private boolean isHit = false;

    private TextureRegion ballTexture;
    private ModelInstance model;

    private Vector2D lastPosition;

    /**
     * constructor for a ball
     *
     * @param location is the position vector of the ball
     * @param mass     is the mass of the given ball
     * @param d        is the diameter of the given ball
     */
    public Ball(Vector2D location, double mass, float d) {
        this.location = location;
        this.mass = mass;
        this.diameter = d;
    }

    public Ball(float x, float y, float diameter, TextureRegion reg) {
        this.position.x = x;
        this.position.y = y;
        this.diameter = diameter;
        this.ballTexture = reg;
    }

    /**
     * getter for the mass
     *
     * @return the mass
     */
    public double getMass() {
        return mass;
    }

    /**
     * method to set the mass of the ball
     *
     * @param newMass is the desired mass
     */
    public void setMass(double newMass) {
        mass = newMass;
    }

    /**
     * getter for the location
     *
     * @return the location vector of the ball
     */
    public Vector2D getLocation() {
        return location;
    }

    /**
     * setter for the location
     *
     * @param newLocation is the desired location
     */
    public void setLocation(Vector2D newLocation) {
        location = newLocation;
    }

    /**
     * getter for the velocity
     *
     * @return the velocity of the ball
     */
    public Vector2D getVelocity() {
        return velocity;
    }

    /**
     * setter for the velocity
     *
     * @param newVelocity is the desired velocity
     */
    public void setVelocity(Vector2D newVelocity) {
        velocity = newVelocity;
    }

    /**
     * method that applies a given force to move a ball
     *
     */
    public void hit() {
        atRest = false;
        isHit = true;
    }

    /**
     * method to see if the ball is at rest (velocity = (0,0))
     *
     * @return true if it is at rest
     */
    public boolean isAtRest() {
        return atRest;
    }

    /**
     * method to see if the ball has a certain velocity
     *
     * @return true if it is applied a force
     */
    public boolean isHit() {
        return isHit;
    }

    /**
     * method to set the velocity of the ball to zero
     */
    public void putAtRest() {
        Vector2D stoppedVelocity = new Vector2D(0, 0);
        velocity = stoppedVelocity;
        atRest = true;
        isHit = false;
    }

    public ModelInstance getModel() {
        return model;
    }

    /**
     * might be useful for collision
     * @return the top-left 'corner' of the ball
     */
    public Vector2D getTopLeftEdge(){
        Vector2D topLeft = new Vector2D((float)location.getX() - diameter /2, (float)location.getY() + diameter/2);
        return topLeft;
    }

    /**
     *
     * @return bottom-right 'corner' of the ball
     */
    public Vector2D getBottomRightEdge(){
        Vector2D bottomRight = new Vector2D((float) location.getX() + diameter /2,(float)location.getY() - diameter/2);
        return bottomRight;
    }

    public boolean isInWater(){
        if(GolfGameNoMaze.getCourse().evaluate(this.location) < 0){
            return true;
        }
        return false;
    }

    public void setLastPosition(){
        this.lastPosition = location;
    }

    /**
     *
     * @return the last position of the ball
     */
    public Vector2D getLastPosition() {
        return lastPosition;
    }

    /**public void createModel(){
        Model model;
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();


    }*/
}