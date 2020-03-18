package Objects;

import Physics.Vector2D;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.math.Vector2;


/**
 * class Ball is used to describe a ball in the putting simulator
 */
public class Ball implements GameObject{

    private Vector2D location;
    private double mass;
    private float diameter;
    private Vector2D velocity;
    private Vector2 position;

    private boolean atRest = true;
    private boolean isHit = false;

    private TextureRegion ballTexture;
    private ModelInstance model;
    /**
     * constructor for a ball
     * @param location is the position vector of the ball
     * @param mass is the mass of the given ball
     * @param diameter is the diameter of the given ball
     */
    public Ball(Vector2D location, double mass,float diameter){
        this.location = location;
        this.mass = mass;
        this.diameter = diameter;
    }
    public Ball(float x, float y , float diameter, TextureRegion reg){
        this.position.x = x;
        this.position.y = y;
        this.diameter = diameter;
        this.ballTexture = reg;
    }

    public Ball(Physics.Vector2D vector2D, int mass, int diameter) {
    }

    /**
     * getter for the mass
     * @return the mass
     */
    public double getMass(){
        return mass;
    }
    /**
     * method to set the mass of the ball
     * @param newMass is the desired mass
     */
    public void setMass(double newMass){
        mass = newMass;
    }

    /**
     * getter for the location
     * @return the location vector of the ball
     */
    public Vector2D getLocation(){
        return location;
    }
    /**
     * setter for the location
     * @param newLocation is the desired location
     */
    public void setLocation(Vector2D newLocation){
        location = newLocation;
    }
    /**
     * getter for the diameter of the ball
     * @return the diameter of the ball
     */
    public double getDiameter(){
        return diameter;
    }
    /**
     * setter for the diameter
     * @param newDiameter is the desired diameter
     */
    public void setDiameter(float newDiameter){
        diameter = newDiameter;
    }
    /**
     * getter for the velocity
     * @return the velocity of the ball
     */
    public Vector2D getVelocity(){
        return velocity;
    }
    /**
     * setter for the velocity
     * @param newVelocity is the desired velocity
     */
    public void setVelocity(Vector2D newVelocity){
        velocity = newVelocity;
    }
    /**
     * method that applies a given force to move a ball
     * @param force a velocity vector that represents the force applied to the ball
     */
    public void hit(Vector2D force){

        this.velocity = force;
        atRest = false;
        isHit = true;
    }
    /**
     * method to see if the ball is at rest (velocity = (0,0))
     * @return true if it is at rest
     */
    public boolean isAtRest(){
        return atRest;
    }
    /**
     * method to see if the ball has a certain velocity
     * @return true if it is applied a force
     */
    public boolean isHit(){
        return isHit;
    }
    /**
     * method to set the velocity of the ball to zero
     */
    public void putAtRest(){
        Vector2D stoppedVelocity = new Vector2D(0, 0);
        velocity = stoppedVelocity;
        atRest = true;

    }

    public ModelInstance getModel() {
        return model;
    }

    /*public void createModel(){
        ModelBuilder builder = new ModelBuilder();
        Model sphere;
        sphere = builder.createSphere(diameter,diameter,diameter,50,50,
                new Material(TextureAttribute.createDiffuse(ballTexture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

        model = new ModelInstance(sphere,location.x, location.y + (diameter / 2));
    }*/
    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void update(float delta) {

    }
}
