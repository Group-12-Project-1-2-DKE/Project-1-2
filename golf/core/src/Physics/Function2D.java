package Physics;

public interface Function2D{
    //I think this class is just the height function
    
    public Vector2D gradient(Vector2D p);
    public double evaluate(Vector2D p);
    //double evaluateXDerivative(double x, double y); 
    //double evaluateYDerivative(double x, double y); 
    //double evaluateHeight(Vector2D p);
    //double evaluateFriction(Vector2D p);
}