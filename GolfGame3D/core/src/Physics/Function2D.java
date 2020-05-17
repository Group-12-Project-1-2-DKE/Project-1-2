package Physics;

public interface Function2D{
    public Vector2D gradient(Vector2D p);
    public double evaluate(Vector2D p);
}