package Objects;

import Physics.Vector2D;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import states.PlayState;

public class GameWorld implements GameObject {

    private Ball ball;

    public GameWorld(PlayState state){
        //arbitrary values
        ball = new Ball(new Vector2D(0,0), 15, 5);
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void update(float delta) {

    }
}
