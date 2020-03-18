package Physics;

import java.util.Vector;

public class Vector2D{
    double x; 
    double y; 
  
    public Vector2D(double x, double y){
     this.x = x;
     this.y = y; 
    }

    public double getX(){
        return x; 
    }

    public double getY(){
        return y; 
    }

    public Vector2D add(Vector2D vector){
        Vector2D result = new Vector2D(this.getX() + vector.getX(), this.getY()+ vector.getY());
        return result; 
    }

    public Vector2D multiply(double factor){
        return new Vector2D(x * factor, y * factor);
    }

    public double length() {
        double length = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
        return length;
    }
    
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
