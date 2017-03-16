package gameworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;

import MainGame.ActionResolver;
import MainGame.Smove;
import configuration.Configuration;
import configuration.Settings;
import gameobjects.Background;
import gameobjects.BallManager;
import gameobjects.Coin;
import gameobjects.Dot;
import gameobjects.GameObject;
import gameobjects.Grid;
import gameobjects.HUD;
import gameobjects.Menu;
import helpers.AssetLoader;
import helpers.FlatColors;

/**
 * Created by ManuGil on 09/03/15.
 */

public class GameWorld {

    public final float w;
    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public Smove game;
    public GameWorld world = this;

    //GAME CAMERA
    private GameCam camera;

    //VARIABLES
    private GameState gameState;
    private int score;

    //GAMEOBJECTS
    private Background background;
    private HUD hud;
    private Menu menu;
    private Grid grid;
    private Dot dot;
    private BallManager ballManager;
    private Coin coin;
    private GameObject pauseButton,rectangle,top;

    public GameWorld(Smove game, ActionResolver actionResolver, float gameWidth,
                     float gameHeight, float worldWidth, float worldHeight) {

        this.gameWidth = gameWidth;
        this.w = gameHeight / 100;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.game = game;
        this.actionResolver = actionResolver;

        gameState = GameState.MENU;
        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);

        background = new Background(world, 0, 0, gameWidth, gameHeight, AssetLoader.background);
        resetGame();
        menu.start();
        top = new GameObject(world, -5, -5, gameWidth + 5, gameHeight + 5, AssetLoader.square,FlatColors.WHITE,
                GameObject.Shape.RECTANGLE);
        top.fadeOut(.8f, .1f);

        checkIfMusicWasPlaying();

        if (AssetLoader.getAds()) {
            world.actionResolver.viewAd(false);
        } else {
            world.actionResolver.viewAd(true);
        }

        pauseButton = new GameObject(world, world.gameWidth - 165, world.gameHeight - 160, 120, 120,
                AssetLoader.pauseButton, world.parseColor(Settings.PAUSE_BUTTON_COLOR),
                GameObject.Shape.RECTANGLE);
        pauseButton.getSprite().setRegion(AssetLoader.pauseButton);
        pauseButton.setShadow(true);
        pauseButton.getShadowSprite().setRegion(AssetLoader.pauseButton);

        rectangle = new GameObject(world,0,0,world.gameWidth,world.gameHeight,AssetLoader.square,Color.BLACK,
                GameObject.Shape.RECTANGLE);
        rectangle.getSprite().setAlpha(0);

    }

    private void checkIfMusicWasPlaying() {
        if (AssetLoader.getVolume()) {
            AssetLoader.music.setLooping(true);
            AssetLoader.music.play();
            AssetLoader.music.setVolume(Settings.MUSIC_VOLUME);
            AssetLoader.setVolume(true);
        }

    }

    public void update(float delta) {
        background.update(delta);
        hud.update(delta);
        grid.update(delta);
        rectangle.update(delta);
        if (!isPaused()) {
            dot.update(delta);
            coin.update(delta);
            ballManager.update(delta);
        }
        menu.update(delta);
        top.update(delta);
        pauseButton.update(delta);
        collisions();
    }

    private void collisions() {
        if (Intersector
                .overlaps(world.getDot().getCircle(), world.getCoin().getRectangle()) && !world
                .getCoin().scored) {
            world.getCoin().collected();
        }
    }

    public void render(SpriteBatch batcher, ShapeRenderer shapeRenderer, ShaderProgram fontShader,
                       ShaderProgram fontShaderA) {
        background.render(batcher, shapeRenderer);
        //if (!isGameOver())
        hud.render(batcher, shapeRenderer, fontShader, fontShaderA);
        grid.render(batcher, shapeRenderer);
        dot.render(batcher, shapeRenderer);
        coin.render(batcher, shapeRenderer);
        ballManager.render(batcher, shapeRenderer);
        rectangle.render(batcher,shapeRenderer);
        if (isRunning() || isPaused())
            pauseButton.render(batcher, shapeRenderer);
        menu.render(batcher, shapeRenderer, fontShader, fontShaderA);
        //muteButton.draw(batcher);
        //TODO: Solve THIS
        top.render(batcher, shapeRenderer);


        if (Configuration.DEBUG) {
            batcher.setShader(fontShader);
            batcher.setShader(null);
        }
    }

    public void finishGame() {
        saveScoreLogic();
        gameState = GameState.MENU;
        hud.finish();
        resetMenu();
        menu.startGameOver();
        coin.finish();
        dot.finish();
        ballManager.finish();
        //checkAchievements();

    }

    private void saveScoreLogic() {
        AssetLoader.addGamesPlayed();
        int gamesPlayed = AssetLoader.getGamesPlayed();

        // GAMES PLAYED ACHIEVEMENTS!
        actionResolver.submitScore(score);
        actionResolver.submitGamesPlayed(gamesPlayed);
        if (score > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(score);
        }
        checkAchievements();
    }

    private void checkAchievements() {
        if (actionResolver.isSignedIn()) {
            if (score >= 5) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_5_P);
            if (score >= 10) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_10_P);
            if (score >= 25) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_25_P);
            if (score >= 50) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_P);
            if (score >= 100) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_100_P);
            if (score >= 200) actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_P);

            int gamesPlayed = AssetLoader.getGamesPlayed();
            // GAMES PLAYED
            if (gamesPlayed >= 10)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_10_GP);
            if (gamesPlayed >= 25)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_25_GP);
            if (gamesPlayed >= 50)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_GP);
            if (gamesPlayed >= 100)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_100_GP);
            if (gamesPlayed >= 200)
                actionResolver.unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_GP);


        }
    }

    public void startGame() {
        score = 0;
        gameState = GameState.RUNNING;
    }


    public GameCam getCamera() {
        return camera;
    }

    public int getScore() {
        return score;
    }


    public void addScore(int i) {
        score += i;
        hud.getScoreText().setText(Configuration.SCORE_TEXT + score);
        if (score > AssetLoader.getHighScore()) {
            hud.getBestText().setText(Configuration.BEST_TEXT + score);
        }
    }

    public static Color parseColor(String hex, float alpha) {
        String hex1 = hex;
        if (hex1.indexOf("#") != -1) {
            hex1 = hex1.substring(1);
        }
        Color color = Color.valueOf(hex1);
        color.a = alpha;
        return color;
    }

    public static Color parseColor(String hex) {
        String hex1 = hex;
        if (hex1.indexOf("#") != -1) {
            hex1 = hex1.substring(1);
        }
        Color color = Color.valueOf(hex1);
        color.a = 1f;
        return color;
    }

    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }

    public boolean isGameOver() {
        return gameState == GameState.GAMEOVER;
    }

    public boolean isMenu() {
        return gameState == GameState.MENU;
    }

    public boolean isPaused() {
        return gameState == GameState.PAUSE;
    }

    public Smove getGame() {
        return game;
    }

    public void resetGame() {
        score = 0;

        hud = new HUD(world, 0, world.gameHeight, gameWidth,
                Settings.HUD_SIZE, AssetLoader.square,
                world.parseColor(Settings.BACK_RECTANGLE_HUD_COLOR, 1f),
                GameObject.Shape.RECTANGLE);
        grid = new Grid(world, gameWidth / 2 - (Settings.GRID_SIZE / 2),
                world.gameHeight / 2 - (Settings.GRID_SIZE / 2), Settings.GRID_SIZE,
                Settings.GRID_SIZE, AssetLoader.grid, world.parseColor(Settings.GRID_COLOR),
                GameObject.Shape.RECTANGLE);
        dot = new Dot(world, gameWidth / 2 - (Settings.DOT_SIZE / 2),
                gameHeight / 2 - (Settings.DOT_SIZE / 2), Settings.DOT_SIZE, Settings.DOT_SIZE,
                AssetLoader.dot, FlatColors.WHITE,
                GameObject.Shape.CIRCLE);
        coin = new Coin(world, -Settings.COIN_SIZE, -Settings.COIN_SIZE, Settings.COIN_SIZE,
                Settings.COIN_SIZE, AssetLoader.coin, world.parseColor(Settings.COIN_COLOR),
                GameObject.Shape.RECTANGLE);
        resetBallManager();
        resetBall();
        resetMenu();
    }

    public void resetMenu() {
        menu = new Menu(world, 0, 0, gameWidth, gameHeight, AssetLoader.square, FlatColors.WHITE,
                GameObject.Shape.RECTANGLE);
    }

    public void resetBall() {
        score = 0;

    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public HUD getHUD() {
        return hud;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setScore(int score) {
        this.score = score;
        hud.getScoreText().setText(Configuration.SCORE_TEXT + score);
        if (score > AssetLoader.getHighScore()) {
            hud.getBestText().setText(Configuration.BEST_TEXT + score);
        }
    }

    public Dot getDot() {
        return dot;
    }

    public Grid getGrid() {
        return grid;
    }

    public BallManager getBallManager() {
        return ballManager;
    }

    public Coin getCoin() {
        return coin;
    }

    public void resetBallManager() {
        ballManager = new BallManager(world);
    }

    public GameObject getPauseButton() {
        return pauseButton;
    }

    public void setPauseMode() {
        gameState = GameState.PAUSE;
        pauseButton.getSprite().setRegion(AssetLoader.playButtonUp);
        pauseButton.getShadowSprite().setRegion(AssetLoader.playButtonUp);
        rectangle.fadeInFromTo(0,Settings.MENU_BACK_ALPHA,.3f,.0f);
    }


    public void setToRunning() {
        gameState = GameState.RUNNING;
        pauseButton.getSprite().setRegion(AssetLoader.pauseButton);
        pauseButton.getShadowSprite().setRegion(AssetLoader.pauseButton);
        rectangle.fadeOutFrom(Settings.MENU_BACK_ALPHA,.3f,.0f);
    }
}
