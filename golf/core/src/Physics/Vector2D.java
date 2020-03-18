package Physics;

import java.util.Vector;

public class Vector2D{
    float x;
    float y;
    public Vector2D(float x, float y){
        this.x = x;
        this.y= y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public Vector2D add(Vector2D vector){
        Vector2D result = new Vector2D(this.getX() + vector.getX(), this.getY()+ vector.getY());
        return result;
    }


}
