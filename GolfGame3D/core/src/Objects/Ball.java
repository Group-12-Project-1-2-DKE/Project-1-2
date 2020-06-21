package Objects;

import Physics.Vector2D;

/**
 * class Ball is used to describe a ball in the putting simulator
 */
public class Ball {

    private Vector2D location;
    private double mass;
    private Vector2D velocity;

    private boolean atRest = true;
    private boolean isHit = false;

    private Vector2D lastPosition;

    /**
     * constructor for a ball
     * @param location is the position vector of the ball
     * @param mass     is the mass of the given ball
     * @param d        is the diameter of the given ball
     */
    public Ball(Vector2D location, double mass, float d) {
        this.location = location;
        this.mass = mass;
    }

    /**
     * getter for the mass
     * @return the mass
     */
    public double getMass() {
        return mass;
    }

    /**
     * getter for the location
     * @return the location vector of the ball
     */
    public Vector2D getLocation() {
        return location;
    }

    /**
     * setter for the location
     * @param newLocation is the desired location
     */
    public void setLocation(Vector2D newLocation) {
        location = newLocation;
    }

    /**
     * getter for the velocity
     * @return the velocity of the ball
     */
    public Vector2D getVelocity() {
        return velocity;
    }

    /**
     * setter for the velocity
     * @param newVelocity is the desired velocity
     */
    public void setVelocity(Vector2D newVelocity) {
        velocity = newVelocity;
    }

    /**
     * method that applies a given force to move a ball
     */
    public void hit() {
        atRest = false;
        isHit = true;
    }

    /**
     * method to see if the ball is at rest (velocity = (0,0))
     * @return true if it is at rest
     */
    public boolean isAtRest() {
        return atRest;
    }

    /**
     * Set the state of the ball (at rest or moving)
     * @param ballState a boolean that says if the ball is moving or not.
     */
    public void setAtRest(boolean ballState){
        this.atRest = ballState;
    }

    /**
     * method to see if the ball has a certain velocity
     * @return true if it is applied a force
     */
    public boolean isHit() {
        return isHit;
    }

    /**
     * method to set the velocity of the ball to zero
     */
    public void putAtRest() {
        velocity = new Vector2D(0, 0);
        atRest = true;
        isHit = false;
    }

    /**
     * Set the position of the ball as the last known position.
     */
    public void setLastPosition(){
        this.lastPosition = location;
    }

    /**
     * @return the last position of the ball
     */
    public Vector2D getLastPosition() {
        return lastPosition;
    }
}