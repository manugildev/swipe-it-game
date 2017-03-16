package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import configuration.Settings;
import gameworld.GameWorld;
import helpers.AssetLoader;

/**
 * Created by ManuGil on 14/05/15.
 */
public class Ball extends GameObject {

    public enum BallState {MOVING, DEAD}

    public BallState ballState;
    private ParticleEffect effect;

    public Ball(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {


        super(world, x, y, width, height, texture, color, shape);
        if (Settings.USE_TEXTURES) {
            getSprite().setRegion(AssetLoader.dot2);
            setColor(Color.WHITE);
        }
        setVelocity(new Vector2());
        if (Settings.SHADOWS)
            setShadow(true);
        setShadowD(3, -3);
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("misc/trail.p"), Gdx.files.internal(""));
        effect.setPosition(-100, -100);
    }

    @Override
    public void update(float delta) {
        if (ballState != BallState.DEAD) {
            super.update(delta);
            getSprite().setPosition(getPosition().x - getSprite().getWidth() / 2,
                    getPosition().y - getSprite().getHeight() / 2);
            getCircle().setPosition(getSprite().getX() + getSprite().getWidth() / 2,
                    getSprite().getY() + getSprite().getHeight() / 2);

            if (Settings.SHADOWS)
                getShadowSprite().setPosition(getShadowSprite().getX() - getSprite().getWidth() / 2,
                        getShadowSprite().getY() - getSprite().getHeight() / 2);
            if (getPosition().x < -100 || getPosition().x > world.gameWidth + 100 || getPosition().y > world.gameHeight + 100 || getPosition().y < -100) {
                setBallState(BallState.DEAD);
            }
            effect.update(delta);
            if (getSprite().getScaleX() == 1) {
                effect.setPosition(getCircle().x, getCircle().y);
            }
        }

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if (ballState != BallState.DEAD) {
            super.render(batch, shapeRenderer);
        }
    }

    public void renderParticles(SpriteBatch batch) {
        if (getSprite().getScaleX() == 1) {
            effect.draw(batch);
        }
    }

    public void setBallState(BallState ballState) {
        this.ballState = ballState;
    }


    public BallState getBallState() {
        return ballState;
    }

}
