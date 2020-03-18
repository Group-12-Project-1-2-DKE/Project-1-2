package states;

import Objects.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import input.PlayStateInput;


public class PlayState extends State {
    private GameWorld gameWorld;
    private StateManager stateManager;
    private OrthographicCamera camera;

    public PlayState(StateManager manager){
        super(manager);
        this.manager = manager;
        this.camera = camera;
        gameWorld  = new GameWorld(this);
        Gdx.input.setInputProcessor(new PlayStateInput(this));
    }
    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        gameWorld.render(batch);
    }

    public void moveCameraToLeft(){
        camera.position.x -= 10;
    }

    @Override
    public void update(float delta) {
     gameWorld.update(delta);
     updateCamera();
    }

    public void updateCamera(){
        //if(gameWorld.)
    }

    @Override
    public void handleInput() {

    }
}
