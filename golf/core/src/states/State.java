package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {

    protected OrthographicCamera camera;
    protected StateManager manager;

    public State(StateManager manager){
        this.manager = manager;
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    public abstract void render(SpriteBatch batch);
    public abstract void update(float delta);
    public abstract void handleInput();
}
