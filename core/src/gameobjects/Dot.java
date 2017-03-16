package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import configuration.Settings;
import gameworld.GameWorld;
import gameworld.Rumble;
import helpers.AssetLoader;
import tweens.Value;

/**
 * Created by ManuGil on 14/05/15.
 */
public class Dot extends GameObject {

    private Vector2 currentPoint;
    private float slideDistance;
    private float nextX, nextY;
    private Value time = new Value();
    private TweenCallback timeCB, cbFinish;
    private Rumble rumble;

    private enum DotState {IDLE, TRANSITION}

    private DotState dotState = DotState.IDLE;

    public Dot(final GameWorld world, float x, float y, float width, float height,
               TextureRegion texture,
               Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);



        if (Settings.SHADOWS)
            setShadow(true);
        slideDistance = (world.getGrid().getSprite().getWidth() / 3) - 10;
        timeCB = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                dotState = DotState.IDLE;
            }
        };
        cbFinish = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                setPosition(world.getGrid().getPointAtCenter().x - getSprite().getWidth() / 2,
                        world.getGrid().getPointAtCenter().y - getSprite().getWidth() / 2);
                scale(0, 1, .5f, 0f);
                fadeInFromTo(0, 1, .5f, 0f);
            }
        };

        if (Settings.USE_TEXTURES) {
            getSprite().setRegion(AssetLoader.dot1);
            setColor(Color.WHITE);
        }

        this.rumble = new Rumble(world);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (rumble.time > 0)
        rumble.tick(delta, new Vector2(world.gameWidth / 2, world.gameHeight / 2));
    }

    public void slideRight(float delay) {
        nextX = getPosition().x + slideDistance;
        if (isInside(nextX, getPosition().y)) {
            effectX(getPosition().x, nextX, Settings.DOT_SPEED, delay);
        }
    }

    public void slideLeft(float delay) {
        nextX = getPosition().x - slideDistance;
        if (isInside(nextX, getPosition().y)) {
            effectX(getPosition().x, nextX, Settings.DOT_SPEED, delay);
        }

    }

    public void slideUp(float delay) {
        nextY = getPosition().y + slideDistance;
        if (isInside(getPosition().x, nextY)) {
            effectY(getPosition().y, nextY, Settings.DOT_SPEED, delay);
        }
    }

    public void slideDown(float delay) {
        nextY = getPosition().y - slideDistance;
        if (isInside(getPosition().x, nextY)) {
            effectY(getPosition().y, nextY, Settings.DOT_SPEED, delay);
        }
    }

    private boolean isInside(float pointX, float pointY) {

        //rumble.rumble(1f,.2f);
        if (world.getGrid().getRectangle().contains(pointX, pointY) && dotState == DotState.IDLE) {
            dotState = DotState.TRANSITION;
            Tween.to(time, -1, Settings.DOT_SPEED).target(1).setCallback(timeCB)
                    .setCallbackTriggers(TweenCallback.COMPLETE).start(
                    getManager());
            return true;
        } else {

            return false;
        }
    }

    public void finish() {
        AssetLoader.end.play();
        dotState = DotState.IDLE;
        scaleZero(.4f, .1f);
        fadeOut(.4f, .1f);
        Tween.to(time, -1, .4f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).delay(.15f)
                .setCallback(cbFinish).start(getManager());

    }
}
