package Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Obstacle implements GameObject {
    public static final int width = 50;
    public static final  int fluctuation = 120;
    public static final int tubeGap = 100;
    public static final int opening = 120;

    private Texture topTube;
    private Texture bottomTube;

    private Vector2 positionTopTube;
    private Vector2 positionBottomTube;

    private Random  random;
    private Rectangle topBound;
    private Rectangle bottomBound;

    public Obstacle(float x ){
        topTube = new Texture("");
        bottomTube = new Texture("");
        random = new Random();

        positionTopTube = new Vector2(x,random.nextInt(fluctuation)+ tubeGap+opening);
        positionBottomTube = new Vector2(x,positionTopTube.y-tubeGap-bottomTube.getHeight());

        topBound = new Rectangle(positionTopTube.x,positionBottomTube.y,topTube.getWidth(),topTube.getHeight());
        bottomBound = new Rectangle(positionBottomTube.x, positionBottomTube.y,bottomTube.getWidth(),bottomTube.getHeight());

    }

    public void relocate(float x){
        positionTopTube.set(x, random.nextInt(fluctuation)+tubeGap+opening);
        positionBottomTube.set(x,positionTopTube.y - tubeGap-bottomTube.getHeight());

        topBound.setPosition(positionTopTube.x,positionTopTube.y);
        bottomBound.setPosition(positionBottomTube.x,positionBottomTube.y);
    }

    public boolean collides(Rectangle object){
        boolean output = (object.overlaps(topBound) || object.overlaps(bottomBound));
        return output;
    }

    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void update(float delta) {

    }
}
