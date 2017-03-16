package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import configuration.Configuration;
import configuration.Settings;
import gameworld.GameState;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import tweens.SpriteAccessor;
import tweens.Value;
import ui.MenuButton;
import ui.Text;

/**
 * Created by ManuGil on 01/05/15.
 */
public class Menu extends GameObject {
    public Text text, bestText, gamesPlayedText, swipeText, scoreText;
    public ArrayList<MenuButton> menubuttons = new ArrayList<MenuButton>();
    public ArrayList<MenuButton> gameOverButtons = new ArrayList<MenuButton>();
    public ArrayList<GameObject> arrows = new ArrayList<GameObject>();
    private Sprite title;
    private GameObject background, scoreBack;
    private Value timer = new Value();
    public boolean isSwipeable = false;


    public Menu(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);

        background = new GameObject(world, x, y, width, height, AssetLoader.square, Color.BLACK,
                Shape.RECTANGLE);
        background.getSprite().setAlpha(Settings.MENU_BACK_ALPHA);

        text = new Text(world, 0, world.gameHeight / 2 + 250 + 100 + 45 + 100,
                world.gameWidth, 150, AssetLoader.square, FlatColors.WHITE, Configuration.GAME_NAME,
                AssetLoader.fontXL, world.parseColor(Settings.TITLE_TEXT_COLOR, 1f), 10,
                Align.center);

        //if (Settings.SHADOWS)
        text.setShadow(true);

        scoreText = new Text(world, 0, world.gameHeight / 2 - 100,
                world.gameWidth, 200, AssetLoader.square, FlatColors.WHITE,
                Configuration.SCORE_TEXT_MENU + world.getScore(),
                AssetLoader.fontXL, world.parseColor(Settings.SCORE_TEXT_MENU_COLOR, 1f), 10,
                Align.center);

        //if (Settings.SHADOWS)
        scoreText.setShadow(true);


        gamesPlayedText = new Text(world, x, y - 20, width, height, AssetLoader.square,
                world.parseColor(Settings.BACK_RECTANGLE_HUD_COLOR, 1f),
                Configuration.GAMES_PLAYED_TEXT + AssetLoader.getGamesPlayed(),
                AssetLoader.fontB, world.parseColor(Settings.BEST_HUD_COLOR, 1f),
                Settings.HUD_TEXT_Y_DISTANCE, Align.right);
        gamesPlayedText.setShadow(true);
        gamesPlayedText.setShadowD(3, -3);

        bestText = new Text(world, x, y - 20, width, height, AssetLoader.square,
                world.parseColor(Settings.BACK_RECTANGLE_HUD_COLOR, 1f),
                Configuration.BEST_TEXT + AssetLoader.getHighScore(),
                AssetLoader.fontB, world.parseColor(Settings.BEST_TEXT_MENU_COLOR, 1f),
                Settings.HUD_TEXT_Y_DISTANCE, Align.left);
        //if (Settings.SHADOWS)
        bestText.setShadow(true);
        bestText.setShadowD(3, -3);

        swipeText = new Text(world, 0, world.gameHeight / 2 + 200, world.gameWidth, 150,
                AssetLoader.square, FlatColors.WHITE,
                Configuration.SWIPE_ANYWHERE_TEXT, AssetLoader.fontB,
                world.parseColor(Settings.SCORE_TEXT_MENU_COLOR, 1f), 20, Align.center);
        swipeText.setShadow(true);
        swipeText.setShadowD(3, -3);

        MenuButton playButton = new MenuButton(world,
                world.gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                world.gameHeight / 2 - 250 - (Settings.PLAY_BUTTON_SIZE) + 0,
                Settings.PLAY_BUTTON_SIZE,
                Settings.PLAY_BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.PLAY_BUTTON_COLOR, 1f),
                Shape.RECTANGLE,
                AssetLoader.playButtonUp);
        MenuButton leaderboardsButton = new MenuButton(world,
                world.gameWidth / 2 - ((Settings.BUTTON_SIZE * 2 + 30 + 15)),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 0,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.RANK_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.rankButtonUp);
        MenuButton achievementButton = new MenuButton(world,
                world.gameWidth / 2 - (15 + Settings.BUTTON_SIZE),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 0,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.ACHIEVEMENT_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.achieveButtonUp);
        MenuButton shareButton = new MenuButton(world,
                world.gameWidth / 2 + (15),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 0,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.SHARE_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.shareButtonUp);
        MenuButton rateButton = new MenuButton(world,
                world.gameWidth / 2 + (15 + 30 + Settings.BUTTON_SIZE),
                world.gameHeight / 2 - 250 - (Settings.BUTTON_SIZE + Settings.PLAY_BUTTON_SIZE + 30) + 0,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.ADS_BUTTON_COLOR, 1f), Shape.RECTANGLE,
                AssetLoader.adsUp);

        menubuttons.add(playButton);
        menubuttons.add(achievementButton);
        menubuttons.add(leaderboardsButton);
        menubuttons.add(shareButton);
        if (Configuration.IAP_ON) menubuttons.add(rateButton);
        else {
            achievementButton.setPosition(
                    achievementButton.getPosition().x + 15 + (Settings.BUTTON_SIZE / 2),
                    achievementButton.getPosition().y);
            leaderboardsButton.setPosition(
                    leaderboardsButton.getPosition().x + 15 + (Settings.BUTTON_SIZE / 2),
                    leaderboardsButton.getPosition().y);
            shareButton.setPosition(
                    shareButton.getPosition().x + 15 + (Settings.BUTTON_SIZE / 2),
                    shareButton.getPosition().y);
        }

        title = new Sprite(AssetLoader.title);
        title.setPosition(text.getPosition().x, text.getPosition().y - 50);
        title.setSize(world.gameWidth,
                world.gameWidth / AssetLoader.title.getRegionWidth() * AssetLoader.title
                        .getRegionHeight());

        //SWIPE ARROWS
        for (int i = 0; i < 7; i++) {
            arrows.add(new GameObject(world, world.gameWidth / 2 - ((3 * 50) + 25) + (50 * i),
                    world.gameHeight / 2 + 200 + 175, 50,
                    50, AssetLoader.arrow, FlatColors.WHITE, Shape.RECTANGLE));
            arrows.get(i).getSprite().setAlpha(0);
            Tween.to(arrows.get(i).getSprite(), SpriteAccessor.ALPHA, .5f).target(1).delay(i * 0.1f)
                    .repeatYoyo(10000, 0f)
                    .ease(TweenEquations.easeInOutSine).start(arrows.get(i).getManager());
        }


        //GAMEOVER
        MenuButton playOverButton = new MenuButton(world,
                world.gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2),
                world.gameHeight / 2 - 250 - (Settings.PLAY_BUTTON_SIZE) + 0-50,
                Settings.PLAY_BUTTON_SIZE,
                Settings.PLAY_BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.PLAY_BUTTON_COLOR, 1f),
                Shape.RECTANGLE,
                AssetLoader.playButtonUp);
        MenuButton homeOverButton = new MenuButton(world,
                world.gameWidth / 2 - (Settings.PLAY_BUTTON_SIZE / 2) - 30 - (Settings.BUTTON_SIZE),
                world.gameHeight / 2 - 250 - (Settings.PLAY_BUTTON_SIZE) + ((Settings.PLAY_BUTTON_SIZE - Settings.BUTTON_SIZE) / 2)-50,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.HOME_BUTTON_COLOR, 1f),
                Shape.RECTANGLE, AssetLoader.homeButtonUp);

        MenuButton adsOverButton = new MenuButton(world,
                world.gameWidth / 2 + (Settings.PLAY_BUTTON_SIZE / 2) + 30,
                world.gameHeight / 2 - 250 - (Settings.PLAY_BUTTON_SIZE) + ((Settings.PLAY_BUTTON_SIZE - Settings.BUTTON_SIZE) / 2)-50,
                Settings.BUTTON_SIZE,
                Settings.BUTTON_SIZE, AssetLoader.buttonBack,
                world.parseColor(Settings.ADS_BUTTON_COLOR, 1f),
                Shape.RECTANGLE, AssetLoader.adsUp);

        if (!Configuration.IAP_ON) {
            adsOverButton.setIcon(AssetLoader.shareButtonUp);
            adsOverButton.setColor(world.parseColor(Settings.SHARE_BUTTON_COLOR));
        }

        gameOverButtons.add(playOverButton);
        gameOverButtons.add(homeOverButton);
        gameOverButtons.add(adsOverButton);

        Tween.to(playButton.getSprite(), SpriteAccessor.SCALE, .25f).target(1.05f).delay(.55f)
                .repeatYoyo(1000000, 0f)
                .ease(TweenEquations.easeInOutSine).start(getManager());
        Tween.to(playOverButton.getSprite(), SpriteAccessor.SCALE, .25f).target(1.05f).delay(.55f)
                .repeatYoyo(1000000, 0f)
                .ease(TweenEquations.easeInOutSine).start(getManager());

        scoreBack = new GameObject(world, ((world.gameWidth - 900) / 2), world.gameHeight / 2 - 100,
                900, 200, AssetLoader.scoreBack, FlatColors.BLACK, Shape.RECTANGLE);
        scoreBack.getSprite().setAlpha(0.8f);
    }

    public void start() {
        world.setGameState(GameState.MENU);
        text.effectY((text.getPosition().y + world.gameHeight), text.getPosition().y, .8f, .1f);
        bestText.effectY((bestText.getPosition().y + world.gameHeight), bestText.getPosition().y,
                .8f, .1f);
        gamesPlayedText.effectY((gamesPlayedText.getPosition().y + world.gameHeight),
                gamesPlayedText.getPosition().y, .8f, .1f);
        swipeText.effectY((swipeText.getPosition().y + world.gameHeight),
                swipeText.getPosition().y, .8f, .1f);
        for (int i = 0; i < menubuttons.size(); i++) {
            menubuttons.get(i).effectY(menubuttons.get(i).getPosition().y - world.gameHeight,
                    menubuttons.get(i).getPosition().y, .8f, .1f);
        }

        for (int i = 0; i < arrows.size(); i++) {
            arrows.get(i).effectY(arrows.get(i).getPosition().y + world.gameHeight,
                    arrows.get(i).getPosition().y, .8f, .1f);
        }
        background.fadeInFromTo(0, Settings.MENU_BACK_ALPHA, .8f, 0f);

        timer.setValue(0);
        Tween.to(timer, -1, 1f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        isSwipeable = true;

                    }
                }).start(getManager());

    }

    public void startGameOver() {
        world.getHUD().finish();
        world.setGameState(GameState.GAMEOVER);
        text.effectY((text.getPosition().y + world.gameHeight), text.getPosition().y, .7f, .25f);
        bestText.effectY((bestText.getPosition().y + world.gameHeight), bestText.getPosition().y,
                .7f, .25f);
        gamesPlayedText.effectY((gamesPlayedText.getPosition().y + world.gameHeight),
                gamesPlayedText.getPosition().y, .7f, .25f);
        swipeText.effectY((swipeText.getPosition().y + world.gameHeight),
                swipeText.getPosition().y, .7f, .25f);
        for (int i = 0; i < gameOverButtons.size(); i++) {
            gameOverButtons.get(i)
                    .effectY(gameOverButtons.get(i).getPosition().y - world.gameHeight,
                            gameOverButtons.get(i).getPosition().y, .7f, .25f);
        }

        for (int i = 0; i < arrows.size(); i++) {
            arrows.get(i).effectY(arrows.get(i).getPosition().y + world.gameHeight,
                    arrows.get(i).getPosition().y, .7f, .25f);
        }
        background.fadeInFromTo(0, Settings.MENU_BACK_ALPHA, .7f, 0f);

        scoreBack.effectY((scoreBack.getPosition().y + world.gameHeight),
                scoreBack.getPosition().y, .7f, .25f);
        scoreText.effectY((scoreText.getPosition().y + world.gameHeight),
                scoreText.getPosition().y, .7f, .25f);

        timer.setValue(0);
        Tween.to(timer, -1, 1f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        isSwipeable = true;
                        if (!AssetLoader.getAds()) {
                            if (Math.random()<Configuration.AD_FREQUENCY) {
                                world.actionResolver.showOrLoadInterstital();
                            }
                        }
                    }
                }).start(getManager());
    }

    public void finish() {
        background.fadeOutFrom(Settings.MENU_BACK_ALPHA, .6f, 0f);
        text.effectY(text.getPosition().y, text.getPosition().y + world.gameHeight, .6f, .1f);
        bestText.effectY(bestText.getPosition().y, bestText.getPosition().y + world.gameHeight, .6f,
                .1f);
        gamesPlayedText.effectY(gamesPlayedText.getPosition().y,
                gamesPlayedText.getPosition().y + world.gameHeight, .6f,
                .1f);
        swipeText.effectY(swipeText.getPosition().y, swipeText.getPosition().y + world.gameHeight,
                .6f, .1f);


        for (int i = 0; i < menubuttons.size(); i++) {
            menubuttons.get(i).effectY(menubuttons.get(i).getPosition().y,
                    menubuttons.get(i).getPosition().y - world.gameHeight, .6f, .1f);
        }

        for (int i = 0; i < gameOverButtons.size(); i++) {
            gameOverButtons.get(i).effectY(gameOverButtons.get(i).getPosition().y,
                    gameOverButtons.get(i).getPosition().y - world.gameHeight, .6f, .1f);
        }

        for (int i = 0; i < arrows.size(); i++) {
            arrows.get(i).effectY(arrows.get(i).getPosition().y,
                    arrows.get(i).getPosition().y + world.gameHeight, .6f, .1f);
        }

        timer.setValue(0);
        Tween.to(timer, -1, .71f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        world.setGameState(GameState.RUNNING);
                    }
                }).start(getManager());

        scoreBack.effectY(scoreBack.getPosition().y, scoreBack.getPosition().y + world.gameHeight,
                .6f, .1f);
        scoreText.effectY(scoreText.getPosition().y, scoreText.getPosition().y + world.gameHeight,
                .6f, .1f);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        background.update(delta);
        text.update(delta);

        bestText.update(delta);
        gamesPlayedText.update(delta);
        swipeText.update(delta);
        title.setPosition(text.getPosition().x, text.getPosition().y - 20);
        scoreBack.update(delta);
        scoreText.update(delta);
        //MENUBUTTONS
        if (world.isMenu()) {
            for (int i = 0; i < menubuttons.size(); i++) {
                menubuttons.get(i).update(delta);
            }
        }
        //GAMEOVERBUTTONS
        if (world.isGameOver()) {
            for (int i = 0; i < gameOverButtons.size(); i++) {
                gameOverButtons.get(i).update(delta);
            }
        }
        for (int i = 0; i < arrows.size(); i++) {
            arrows.get(i).update(delta);
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, ShaderProgram fontShader,
                       ShaderProgram fontShaderA) {
        //super.render(batch, shapeRenderer);
        background.render(batch, shapeRenderer);
        if (Settings.USE_TITLE_TEXTURE) {
            title.draw(batch);
        } else {
            text.render(batch, shapeRenderer, fontShader, fontShaderA);
        }
        bestText.render(batch, shapeRenderer, fontShader, fontShaderA);
        swipeText.render(batch, shapeRenderer, fontShader, fontShaderA);
        //TODO:
        // gamesPlayedText.render(batch, shapeRenderer, fontShader, fontShaderA);
        if (world.isMenu()) {
            for (int i = 0; i < menubuttons.size(); i++) {
                menubuttons.get(i).render(batch, shapeRenderer);
            }
        }

        if (world.isGameOver()) {
            for (int i = 0; i < gameOverButtons.size(); i++) {
                gameOverButtons.get(i).render(batch, shapeRenderer);
            }
            scoreBack.render(batch, shapeRenderer);
            scoreText.render(batch, shapeRenderer, fontShader, fontShaderA);
        }

        for (int i = 0; i < arrows.size(); i++) {
            arrows.get(i).render(batch, shapeRenderer);
        }

    }

    public void startPlayButton() {
        finish();
        world.getBallManager().start();
        world.getHUD().start();
        world.setScore(0);
        world.getCoin().reset();
    }

    public void goToHome() {
        for (int i = 0; i < gameOverButtons.size(); i++) {
            gameOverButtons.get(i).effectY(gameOverButtons.get(i).getPosition().y,
                    gameOverButtons.get(i).getPosition().y - world.gameHeight, .6f, .0f);
        }

        for (int i = 0; i < menubuttons.size(); i++) {
            menubuttons.get(i).effectY(menubuttons.get(i).getPosition().y - world.gameHeight,
                    menubuttons.get(i).getPosition().y, .4f, .0f);
        }

        timer.setValue(0);
        Tween.to(timer, -1, .55f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        world.setGameState(GameState.MENU);
                    }
                }).start(getManager());

        scoreBack.effectY(scoreBack.getPosition().y,
                scoreBack.getPosition().y - world.gameHeight, .6f, .0f);
        scoreText.effectY(scoreText.getPosition().y,
                scoreText.getPosition().y - world.gameHeight, .6f, .0f);

        timer.setValue(0);
        Tween.to(timer, -1, 1f).target(1).setCallbackTriggers(TweenCallback.COMPLETE).setCallback(
                new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        isSwipeable = true;
                    }
                }).start(getManager());
    }

    public ArrayList<MenuButton> getMenuButtons() {
        return menubuttons;
    }

    public ArrayList<MenuButton> getGameOverButtons() {
        return gameOverButtons;
    }


}
