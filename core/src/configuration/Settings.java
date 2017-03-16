package configuration;

/**
 * Created by ManuGil on 23/04/15.
 */

public class Settings {

    //GRID
    public static final int GRID_SIZE = 375;

    //DOT
    public static final float DOT_SPEED = .08f;
    public static final int DOT_SIZE = 70;

    //MOVING BALLS
    public static final int NUMBER_OF_BALLS = 25;
    public static final float BALL_SPEED = 250f;
    public static final int BALL_SIZE = 75;

    public static final float INITIAL_TIME_DIFFERENCE = 1.5f;
    public static final float TIME_MIN = .9f;
    public static final float TIME_REDUCTION_PER_BALL = .05f;
    public static float TIME_MIN_MENU = 0.4f;

    //COINS
    public static final float COIN_SIZE = 40;

    //GENERAL
    public static final boolean SHADOWS = false;
    public static final boolean COLOR_CHANGING_BACKGROUND = true;

    //HUD
    public static final float HUD_ALPHA = .0f;
    public static final boolean FULL_HUD = true; //When true shows Score: and Best:, when false only points at Center
    public static final float HUD_SIZE = 250;
    public static final float HUD_TEXT_Y_DISTANCE = 18;

    //MENU
    public static final float PLAY_BUTTON_SIZE = 170;
    public static final float BUTTON_SIZE = 150;
    public static final boolean REMOVE_CIRCLE_BUTTONS = false; //Set to true if you have textures in buttons.png
    public static float MENU_BACK_ALPHA = .4f;
    public static final float MUSIC_VOLUME = .8f;

    public static final boolean USE_TITLE_TEXTURE = true;
    public static final boolean USE_TEXTURES = false;

    ////////COLORS/////////
    public static final String COIN_COLOR = "#2980b9";
    public static final String COIN_COLOR_RED = "#c0392b";
    public static String BALL_COLOR = "#2c3e50";
    public static final String GRID_COLOR = "FFFFFF";

    ////////COLORS/////////
    //TEXTS
    public static final String TITLE_TEXT_COLOR = "#FFFFFF";
    public static final String SCORE_TEXT_MENU_COLOR = "#FFFFFF";
    public static final String BEST_TEXT_MENU_COLOR = "#ecf0f1";

    ////////COLORS/////////
    //MENU
    public static final String BEST_HUD_COLOR = "#FFFFFF";
    public static final String SCORE_HUD_COLOR = "#FFFFFF";
    public static final String BACK_RECTANGLE_HUD_COLOR = "#bdc3c7";
    public static final String PAUSE_BUTTON_COLOR = "#FFFFFF";
    //GAMEOVER
    public static final String HOME_BUTTON_COLOR = "FFFFFF";

    ////////COLORS/////////
    //BUTTONS
    public static final String PLAY_BUTTON_COLOR = "#FFFFFF";
    public static final String RANK_BUTTON_COLOR = "#FFFFFF";
    public static final String ACHIEVEMENT_BUTTON_COLOR = "#FFFFFF";
    public static final String SHARE_BUTTON_COLOR = "#FFFFFF";
    public static final String ADS_BUTTON_COLOR = "#FFFFFF";


}
