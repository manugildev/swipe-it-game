package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import configuration.Settings;
import gameworld.GameWorld;
import helpers.AssetLoader;
import tweens.SpriteAccessor;

/**
 * Created by ManuGil on 15/05/15.
 */
public class Coin extends GameObject {
    private float angle, angleInc = 100;
    private Tween scaleTween;
    private TweenCallback cbScaleDown;
    public boolean scored = false;
    private ParticleEffect particleEffect;

    public Coin(final GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);

        if (Settings.USE_TEXTURES) {
            setColor(Color.WHITE);
        }

        cbScaleDown = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                if (world.isRunning()) {
                    setPosition(10, 10);
                    respawn();
                }
            }
        };

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("misc/explosion.p"), Gdx.files.internal(""));
        particleEffect.setPosition(-100, -100);
        particleEffect.start();
        //respawn();
    }


    @Override
    public void update(float delta) {
        angle += angleInc * delta;
        getSprite().setRotation(angle);
        super.update(delta);
        getSprite().setPosition(getPosition().x - getSprite().getWidth() / 2,
                getPosition().y - getSprite().getHeight() / 2);
        getRectangle().setPosition(getPosition().x - getSprite().getWidth() / 2,
                getPosition().y - getSprite().getHeight() / 2);

        particleEffect.update(delta);

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        //if (world.isRunning()) {
        particleEffect.draw(batch);
        super.render(batch, shapeRenderer);
        //}

    }

    public void effects() {
        scaleTween = Tween.to(getSprite(), SpriteAccessor.SCALE, .4f).target(1.2f).delay(.55f)
                .repeatYoyo(1000000, 0f)
                .ease(TweenEquations.easeInOutSine).start(getManager());
    }

    public void collected() {
        if (particleEffect.isComplete()) {
            if (world.getScore() % 10 == 0 && world.getScore() != 0)
                particleEffect
                        .load(Gdx.files.internal("misc/explosionRed.p"), Gdx.files.internal(""));
            else
                particleEffect.load(Gdx.files.internal("misc/explosion.p"), Gdx.files.internal(""));
            particleEffect.setPosition(getPosition().x, getPosition().y);
            particleEffect.start();
        }
        AssetLoader.success.play();
        scored = true;
        world.addScore(1);
        if (scaleTween != null)
            scaleTween.kill();
        Tween.to(getSprite(), SpriteAccessor.SCALE, .3f).target(0).delay(0).setCallbackTriggers(
                TweenCallback.COMPLETE).setCallback(cbScaleDown)
                .ease(TweenEquations.easeInOutSine).start(getManager());
    }

    public void respawn() {

        if (scaleTween != null)
            scaleTween.kill();
        if (world.getScore() % 10 == 0 && world.getScore() != 0) {
            getSprite().setColor(world.parseColor(Settings.COIN_COLOR_RED));
        } else {
            getSprite().setColor(world.parseColor(Settings.COIN_COLOR));
            if (Settings.USE_TEXTURES) {
                setColor(Color.WHITE);
            }
        }

        scored = false;
        Vector2 rP;
        do {
            rP = world.getGrid().getCenterPoints()
                    .get(MathUtils.random(0, world.getGrid().getCenterPoints().size() - 1));
        } while (world.getDot().getCircle().contains(rP));
        setPosition(rP);
        scale(0, 1, .3f, .1f);
        effects();

    }


    public void finish() {
        if (scaleTween != null)
            scaleTween.kill();
        scaleTween = null;
        scaleZero(.4f, .0f);
    }

    public void reset() {
        scaleTween.kill();
        getSprite().setScale(0);
        respawn();
    }
}
