package Objects;

import Physics.Vector2D;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import states.PlayState;
import states.StateManager;

public class GameWorld implements GameObject {
    PlayState state;

    private Ball ball;
    private Texture ballFrame1, ballFrame2, ballFrame3;
    private static final float startX = 400;
    private  static final float startY = 250;
    private Animation ballw;
    private Vector2 ballPosition = new Vector2(40,50);
    private float time = 0;
    private Array<Obstacle> obstacles = new Array<>();

    private TextureRegion ballreg ;
    private static final float fluctuation = -30;
    private Texture hole;

    public GameWorld(PlayState state){
        this.state = state;
        //arbitrary values
        ball = new Ball(new Vector2D(0,0), 15, 5);
    }

    public void create(){

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void update(float delta) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        delta += deltaTime;
        // add ball movement
        if(Gdx.input.justTouched()){
            System.out.println("a new play is created");
          //  manager.pushState(new PlayState(manager));
        }
       // if statemnt for camera to follow the ball
        if(state.getCamera().position.x - ballPosition.x >700 + ballreg.getRegionWidth()){
            state.getCamera().position.x = ballPosition.x - 20; //change the numbers if necessary
        }
    }
}
