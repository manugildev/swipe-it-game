package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import configuration.Configuration;

/**
 * Created by ManuGil on 09/03/15.
 */
public class AssetLoader {

    public static Texture logoTexture, dotT, squareT, gridT,
            buttonsT, buttonBackT, dotsT, backgroundT, titleT,
            hudButtonsT, gradientT, coinT,scoreBackT;
    public static TextureRegion logo, square, dot, grid,
            gradient, playButtonUp, rankButtonUp, background,
            title, pauseButton, coin, achieveButtonUp, homeButtonUp,
            shareButtonUp, adsUp, buttonBack, dot1, dot2, dot3,
            dot4, dot5, dot6, soundButton, muteButton, arrow,scoreBack;

    //BUTTONS
    public static TextureRegion buttons;

    public static BitmapFont font, fontS, fontXS, fontB, fontXL;
    private static Preferences prefs;

    //MUSIC
    public static Music music;
    public static Sound click, success, end;


    public static void load1() {
        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        logo = new TextureRegion(logoTexture, 0, 0, logoTexture.getWidth(),
                logoTexture.getHeight());
        backgroundT = new Texture(Gdx.files.internal("background.png"));
        backgroundT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        background = new TextureRegion(backgroundT, 0, 0, backgroundT.getWidth(),
                backgroundT.getHeight());
    }

    public static void load() {
        //LOGO TEXTURE "logo.png"

        square = new TextureRegion(new Texture(Gdx.files.internal("square.png")), 0, 0, 10, 10);

        dotT = new Texture(Gdx.files.internal("dot.png"));
        dotT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        dot = new TextureRegion(dotT, 0, 0, dotT.getWidth(), dotT.getHeight());

        gridT = new Texture(Gdx.files.internal("grid.png"));
        gridT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        grid = new TextureRegion(gridT, 0, 0, gridT.getWidth(), gridT.getHeight());


        coinT = new Texture(Gdx.files.internal("coin.png"));
        coinT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        coin = new TextureRegion(coinT, 0, 0, coinT.getWidth(), coinT.getHeight());

        buttonBackT = new Texture(Gdx.files.internal("button_back.png"));
        buttonBackT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        buttonBack = new TextureRegion(buttonBackT, 0, 0, buttonBackT.getWidth(),
                buttonBackT.getHeight());

        titleT = new Texture(Gdx.files.internal("title.png"));
        titleT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        title = new TextureRegion(titleT, 0, 0, titleT.getWidth(), titleT.getHeight());

        scoreBackT = new Texture(Gdx.files.internal("score_back.png"));
        scoreBackT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        scoreBack = new TextureRegion(scoreBackT, 0, 0, scoreBackT.getWidth(), scoreBackT.getHeight());

               dotsT = new Texture(Gdx.files.internal("dots.png"));
        dotsT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        dot1 = new TextureRegion(dotsT, (dotsT.getWidth() / 2) * 0, 0,
                (dotsT.getWidth() / 2), dotsT.getHeight());
        dot2 = new TextureRegion(dotsT, (dotsT.getWidth() / 2) * 1, 0,
                (dotsT.getWidth() / 2), dotsT.getHeight());

        //LOADING FONT
        Texture tfont = new Texture(Gdx.files.internal("misc/font.png"), true);
        tfont.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);

        fontXL = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont), true);
        fontXL.getData().setScale(2.4f, -2.4f);
        fontXL.setColor(FlatColors.WHITE);

        font = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont), true);
        font.getData().setScale(1.9f, -1.9f);
        font.setColor(FlatColors.WHITE);

        fontB = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont), true);
        fontB.getData().setScale(1.4f, -1.4f);
        fontB.setColor(FlatColors.WHITE);

        fontS = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont), true);
        fontS.getData().setScale(1.2f, -1.2f);
        fontS.setColor(FlatColors.WHITE);

        fontXS = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont), true);
        fontXS.getData().setScale(0.9f, -0.9f);
        fontXS.setColor(FlatColors.WHITE);

        //BUTTONS
        buttonsT = new Texture(Gdx.files.internal("buttons.png"));
        buttonsT.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        //CROP BUTTONS
        playButtonUp = new TextureRegion(buttonsT, 0, 0, 240, 240);
        rankButtonUp = new TextureRegion(buttonsT, 240, 0, 240, 240);
        shareButtonUp = new TextureRegion(buttonsT, 720, 0, 240, 240);
        achieveButtonUp = new TextureRegion(buttonsT, 960, 0, 240, 240);
        adsUp = new TextureRegion(buttonsT, 1200, 0, 240, 240);
        homeButtonUp = new TextureRegion(buttonsT, 480, 0, 240, 240);
        arrow = new TextureRegion(buttonsT, 1200 + 240, 0, 240, 240);
        pauseButton = new TextureRegion(buttonsT, 1200 + 480, 0, 240, 240);


        //PREFERENCES - SAVE DATA IN FILE
        prefs = Gdx.app.getPreferences(Configuration.GAME_NAME);

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

        if (!prefs.contains("games")) {
            prefs.putInteger("games", 0);
        }

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.ogg"));
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/blip_click.wav"));
        success = Gdx.audio.newSound(Gdx.files.internal("sounds/blip_success.wav"));
        end = Gdx.audio.newSound(Gdx.files.internal("sounds/blip_end.wav"));
    }

    public static void dispose() {
        font.dispose();
        fontS.dispose();
        fontXS.dispose();
        fontB.dispose();
        fontXL.dispose();
        click.dispose();
        success.dispose();
        end.dispose();
        dotT.dispose();
        logoTexture.dispose();

    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static void setButtonsClicked(int val) {
        prefs.putInteger("buttonsClicked", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void addGamesPlayed() {
        prefs.putInteger("games", prefs.getInteger("games") + 1);
        prefs.flush();
    }

    public static int getGamesPlayed() {
        return prefs.getInteger("games");
    }

    public static void setAds(boolean removeAdsVersion) {
        prefs = Gdx.app.getPreferences(Configuration.GAME_NAME);
        prefs.putBoolean("ads", removeAdsVersion);
        prefs.flush();
    }

    public static boolean getAds() {
        Gdx.app.log("ADS", prefs.getBoolean("ads") + "");
        return prefs.getBoolean("ads", false);
    }

    public static void prefs() {
        prefs = Gdx.app.getPreferences(Configuration.GAME_NAME);
    }

    public static void setVolume(boolean val) {
        prefs.putBoolean("volume", val);
        prefs.flush();
    }

    public static boolean getVolume() {
        return prefs.getBoolean("volume");
    }


}
