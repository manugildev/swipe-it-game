package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import configuration.Configuration;
import configuration.Settings;
import gameworld.GameWorld;
import helpers.AssetLoader;
import ui.Text;

/**
 * Created by ManuGil on 26/04/15.
 */
public class HUD extends GameObject {

    public Text scoreText, bestText;

    public HUD(GameWorld world, float x, float y, float width, float height,
               TextureRegion texture,
               Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        //setShadow(true);
        // getShadowSrite().setAlpha(0.4f);
        scoreText = new Text(world, x, y, width, height-70, AssetLoader.square,
                world.parseColor(Settings.BACK_RECTANGLE_HUD_COLOR,1f), Configuration.SCORE_TEXT + world.getScore(), AssetLoader.fontXL,
                world.parseColor(Settings.SCORE_HUD_COLOR,1f), Settings.HUD_TEXT_Y_DISTANCE,
                Settings.FULL_HUD ? Align.left : Align.center);
        //if (Settings.SHADOWS)
        scoreText.setShadow(true);

        bestText = new Text(world, x, y, width, height, AssetLoader.square,
                world.parseColor(Settings.BACK_RECTANGLE_HUD_COLOR,1f), Configuration.BEST_TEXT + AssetLoader.getHighScore(),
                AssetLoader.fontB, world.parseColor(Settings.BEST_HUD_COLOR,1f), Settings.HUD_TEXT_Y_DISTANCE, Align.left);
        //if (Settings.SHADOWS)
        bestText.setShadow(true);
        bestText.setShadowD(3,-3);
        getSprite().setAlpha(Settings.HUD_ALPHA);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        scoreText.update(delta);
        scoreText.setPosition(getPosition());
        bestText.update(delta);
        bestText.setPosition(getPosition());

    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader,
                       ShaderProgram fontShaderA) {
        super.render(batch, shapeRenderer);
        scoreText.render(batch, shapeRenderer, fontShader,fontShaderA);
        if (Settings.FULL_HUD)
            bestText.render(batch, shapeRenderer, fontShader,fontShaderA);
    }

    public void start() {
        effectY(world.gameHeight, world.gameHeight - getSprite().getHeight()-20, .4f, .9f);
    }

    public Text getScoreText() {
        return scoreText;
    }

    public Text getBestText() {
        return bestText;
    }

    public void finish() {
        effectY(getPosition().y, world.gameHeight + 10, .3f, .3f);
    }

}
