package gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import configuration.Configuration;
import configuration.Settings;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import tweens.Value;

/**
 * Created by ManuGil on 14/05/15.
 */
public class BallManager {

    private final GameWorld world;
    private ArrayList<Vector2> spawnPoints = new ArrayList<Vector2>();
    private ArrayList<Vector2> spawnPointsM = new ArrayList<Vector2>();
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private int pad = -90;
    public Tween gameTween;
    public TweenManager manager;
    private Value timer = new Value();
    private float timers;
    private TweenCallback cbFinish;
    private float time = Settings.INITIAL_TIME_DIFFERENCE;
    private Value timer1 = new Value();
    private boolean timerActivated = true;

    public BallManager(final GameWorld world) {
        this.world = world;
        setUpSpawnPoints();
        manager = new TweenManager();
        balls.clear();
        for (int i = 0; i < Settings.NUMBER_OF_BALLS; i++) {
            Vector2 rPoint = spawnPoints.get(MathUtils.random(0, spawnPoints.size() - 1));
            Ball ball = new Ball(world, rPoint.x, rPoint.y, Settings.BALL_SIZE, Settings.BALL_SIZE,
                    AssetLoader.dot, world.parseColor(Settings.BALL_COLOR), GameObject.Shape.CIRCLE);
            ball.setBallState(Ball.BallState.DEAD);
            balls.add(ball);
        }


        cbFinish = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                world.resetBallManager();
            }
        };
        reset();
    }

    private void reset() {
        timers = 0;
        moveOneBall();
        timerActivated = true;

    }

    public void start() {
        finish();
        world.getCoin().respawn();
        moveOneBall();
    }

    public void stop() {
        gameTween.kill();
    }

    public void update(float delta) {
        timerLogic(delta);
        manager.update(delta);
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).update(delta);
            if (Intersector.overlaps(world.getDot().getCircle(), balls.get(i).getCircle()) && world
                    .isRunning()) {
                world.finishGame();
            }
        }
    }

    private void timerLogic(float delta) {
        if (timerActivated) {
            timers += delta;
            if (time > Settings.TIME_MIN) {
                time -= Settings.TIME_REDUCTION_PER_BALL;
            } else {
                time = Settings.TIME_MIN;
            }
            if (!world.isRunning()) time = Settings.TIME_MIN_MENU;
            if (timers >= time) {
                timers = 0;   // If you reset it to 0 you will loose a few milliseconds every 2 seconds.
                moveOneBall();
            }
        }
    }


    private void moveOneBall() {
        Ball ball;
        do {
            ball = balls.get(MathUtils.random(0, balls.size() - 1));
        } while (ball.getBallState() != Ball.BallState.DEAD);
        Vector2 rP;
        if (world.isRunning()) {
            rP = spawnPoints.get(MathUtils.random(0, spawnPoints.size() - 1));
        } else {
            rP = spawnPointsM.get(MathUtils.random(0, spawnPointsM.size() - 1));
        }
        ball.setPosition(rP);
        ball.setBallState(Ball.BallState.MOVING);
        if (rP.x > world.gameWidth - pad - 50) {
            ball.setVelocity(-Settings.BALL_SPEED, 0);
        } else if (rP.x < 0 + pad + 50) {
            ball.setVelocity(Settings.BALL_SPEED, 0);
        } else if (rP.y > world.gameHeight - pad - 50) {
            ball.setVelocity(0, -Settings.BALL_SPEED);
        } else if (rP.y < 0 + pad + 50) {
            ball.setVelocity(0, Settings.BALL_SPEED);
        }
    }


    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).renderParticles(batch);
        }
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).render(batch, shapeRenderer);
        }
        if (Configuration.DEBUG) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(FlatColors.DARK_BLUE);
            for (int i = 0; i < spawnPoints.size(); i++) {
                shapeRenderer.circle(spawnPoints.get(i).x, spawnPoints.get(i).y, 5);
            }

            shapeRenderer.end();
            batch.begin();
        }
    }

    private void setUpSpawnPoints() {
        Grid grid = world.getGrid();
        //UP
        spawnPoints.add(new Vector2(grid.getPosition().x + 10 + (grid.getSprite().getWidth() / 6),
                world.gameHeight - pad));
        spawnPoints.add(new Vector2(
                grid.getPosition().x + (grid.getSprite().getWidth() / 6) + (grid.getSprite()
                        .getWidth() / 3),
                world.gameHeight - pad));
        spawnPoints.add(new Vector2(
                grid.getPosition().x + (grid.getSprite().getWidth() / 6) - 10 + ((grid.getSprite()
                        .getWidth() / 3) * 2),
                world.gameHeight - pad));

        //DOWN
        spawnPoints.add(new Vector2(grid.getPosition().x + 10 + (grid.getSprite().getWidth() / 6),
                pad));
        spawnPoints.add(new Vector2(
                grid.getPosition().x + (grid.getSprite().getWidth() / 6) + (grid.getSprite()
                        .getWidth() / 3), pad));
        spawnPoints.add(new Vector2(
                grid.getPosition().x + (grid.getSprite().getWidth() / 6) - 10 + ((grid.getSprite()
                        .getWidth() / 3) * 2), pad));

        //LEFT
        spawnPoints
                .add(new Vector2(pad,
                        grid.getPosition().y + 10 + (grid.getSprite().getHeight() / 6)));
        spawnPoints.add(new Vector2(pad,
                grid.getPosition().y + (grid.getSprite().getHeight() / 6) + (grid.getSprite()
                        .getHeight() / 3)));
        spawnPoints.add(new Vector2(pad,
                grid.getPosition().y + (grid.getSprite().getHeight() / 6) - 10 + (grid.getSprite()
                        .getHeight() / 3) * 2));

        //RIGHT
        spawnPoints
                .add(new Vector2(world.gameWidth - pad,
                        grid.getPosition().y + 10 + (grid.getSprite().getHeight() / 6)));
        spawnPoints.add(new Vector2(world.gameWidth - pad,
                grid.getPosition().y + (grid.getSprite().getHeight() / 6) + (grid.getSprite()
                        .getHeight() / 3)));
        spawnPoints.add(new Vector2(world.gameWidth - pad,
                grid.getPosition().y + (grid.getSprite().getHeight() / 6) - 10 + (grid.getSprite()
                        .getHeight() / 3) * 2));

        spawnPointsM.add(spawnPoints.get(0));
        spawnPointsM.add(spawnPoints.get(2));
        spawnPointsM.add(spawnPoints.get(3));
        spawnPointsM.add(spawnPoints.get(5));
        spawnPointsM.add(spawnPoints.get(6));
        spawnPointsM.add(spawnPoints.get(8));
        spawnPointsM.add(spawnPoints.get(9));
        spawnPointsM.add(spawnPoints.get(11));


    }

    public void finish() {
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).setVelocity(new Vector2());
            balls.get(i).scaleZero(.3f, .1f);
        }
        Tween.to(timer1, -1, .4f).target(1).delay(0).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                for (int i = 0; i < balls.size(); i++) {
                    balls.get(i).setPosition(0, 0);
                }
            }
        })
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .ease(TweenEquations.easeInOutSine).start(manager);

        timerActivated = false;
        Tween.to(timer1, -1, 1.2f).target(1).delay(0).setCallback(cbFinish)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }

}
