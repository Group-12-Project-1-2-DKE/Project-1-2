package states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class StateManager {

    private Stack<State> states;


    public StateManager(){
        states = new Stack<State>();
    }


    public void render(SpriteBatch batch){
       states.peek().render(batch);
    }

    public void update(float delta){
        states.peek().update(delta);
        states.peek().handleInput();

    }

    public void pushState(State state){
        states.push(state);
    }

    public void popState(){
        states.pop();
    }
}
