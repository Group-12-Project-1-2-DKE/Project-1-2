package Physics;

public class Vector2D{
    private double x;
    private double y;

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

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public Vector2D add(Vector2D vector){
        return new Vector2D(this.getX() + vector.getX(), this.getY()+ vector.getY());
    }

    public Vector2D multiply(double factor){
        return new Vector2D(x * factor, y * factor);
    }

    public double length() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
