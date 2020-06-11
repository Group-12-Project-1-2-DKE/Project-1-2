package Objects;

import Physics.Vector2D;

import java.util.ArrayList;

public class Obstacle {

    private Vector2D location;
    private double width;
    private double height;
    private double depth;
    private Vector2D p1,p2,p3,p4,p5,p6,p7,p8;//corners of the obstacle
    private ArrayList<Vector2D> edges;


    public Obstacle(Vector2D location, double width, double height, double depth){
        this.location = location;
        this.width = width;
        this.height = height;
        this.depth = depth;
        p1 = new Vector2D(location.getX(),location.getY());
        p2 = new Vector2D(location.getX() + width, location.getY());
        p3 = new Vector2D(location.getX() , location.getY() + height);
        p4 = new Vector2D(location.getX() + width, location.getY() + height);
        //TODO add other edges using depth
        edges = new ArrayList<>();
        edges.add(p1);
    }

    /**
     * accessor for the position of the obstacle
     * @return the location
     */
    public Vector2D getLocation(){
        return this.location;
    }

    /**
     * getter for the width of the obstacle
     * @return the width
     */
    public double getWidth(){
        return this.width;
    }

    /**
     * getter for the depth of the obstacle
     * @return the depth of the obstacle
     */
    public double getDepth(){
        return this.depth;
    }

    /**
     * getter for height
     * @return the height of the obstacle
     */
    public double getHeight(){
        return this.height;
    }

    /**
     * might be useful for the collision
     * @return an arraylist containing all the edges of the obstacle
     */
    public ArrayList<Vector2D> getEdges(){
        return edges;

    }

}
