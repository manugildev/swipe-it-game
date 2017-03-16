package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import configuration.Configuration;
import configuration.Settings;
import gameworld.GameWorld;
import helpers.FlatColors;

/**
 * Created by ManuGil on 14/05/15.
 */
public class Grid extends GameObject {

    public ArrayList<Vector2> centerPoints = new ArrayList<Vector2>();

    public Grid(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);

        if (Settings.SHADOWS)
        setShadow(true);
        width-=20;height-=20;
        x+=10;y+=10;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                centerPoints.add(new Vector2((x)+(width/(3*2)+(j*width/3)),(y)+(height/(3*2)+(i*height/3))));

            }
        }

    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.render(batch, shapeRenderer);
        if (Configuration.DEBUG) {
            for (int i = 0; i < centerPoints.size(); i++) {
                batch.end();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(FlatColors.DARK_BLUE);
                shapeRenderer.circle(centerPoints.get(i).x, centerPoints.get(i).y, 3);
                shapeRenderer.end();
                batch.begin();
            }
        }
    }

    public void start() {
    }

    public void finish() {
    }

    public ArrayList<Vector2> getCenterPoints(){
        return centerPoints;
    }

    public Vector2 getPointAtCenter(){
        return centerPoints.get(4);
    }


}

