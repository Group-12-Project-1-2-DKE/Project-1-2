package Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class obstacleTest implements GameObject {
    Vector2 position = new Vector2();
 TextureRegion pic;
 boolean passed;

 public obstacleTest(float x, float y, TextureRegion pic){
     this.position.x = x;
     this.position.y = y;
     this.pic = pic;
 }


    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void update(float delta) {

    }
}
