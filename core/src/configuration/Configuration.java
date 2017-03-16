package configuration;

/**
 * Created by ManuGil on 09/03/15.
 */

public class Configuration {

    public static final String GAME_NAME = "SWIPE IT";

    public static boolean DEBUG = false;
    public static final boolean SPLASHSCREEN = true;

    //ADMOB IDS
    public static final String AD_UNIT_ID_BANNER = "ca-app-pub-6147578034437241/7353989815";
    public static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-6147578034437241/1307456217";
    public static float AD_FREQUENCY = .9f;

    //In App Purchases
    public static final boolean IAP_ON = true;
    public static final String ENCODED_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmee2t/N6Dk84n5jpLCmc8Iv8rscDnfg4WrlujMJ3WRS5II6EfiAhvx8Q/tpSptypRxV7QAK+zp94Krcs/lQhtzXr2EPS/2f0Rcw/kF0d9lN10zkXFjp5X4iKsn8hwGhvSm9fvaV1LMqd3lIbeHp/EY+x/yYpDTDuXiL6/+Yv7v+ryQAGl3xRIdX1V7CwjffdAihpJd3l9xt8JN5FxyR++kZomlu6lrZG01k5B4QtsJN8LztPx6zvkYVJRwRwaI7dt8Lk4hsbic1FlvoHMWEdjSKeE9+u4B1M+2Bv85fupwML9OIgn2qlCJjKYvsFKMm2HULBxqNsm9ZtYli8rtnn1wIDAQAB";
    public static final String PRODUCT_ID = "removeads";

    //LEADERBOARDS
    public static final String LEADERBOARD_HIGHSCORE = "CgkI2cCP5_kNEAIQAA";
    public static final String LEADERBOARD_GAMESPLAYED = "CgkI2cCP5_kNEAIQAQ";

    //ACHIEVEMENTS IDS Points
    public static final String ACHIEVEMENT_5_P = "CgkI2cCP5_kNEAIQAw";
    public static final String ACHIEVEMENT_10_P = "CgkI2cCP5_kNEAIQBA";
    public static final String ACHIEVEMENT_25_P = "CgkI2cCP5_kNEAIQBQ";
    public static final String ACHIEVEMENT_50_P = "CgkI2cCP5_kNEAIQBg";
    public static final String ACHIEVEMENT_100_P = "CgkI2cCP5_kNEAIQBw";
    public static final String ACHIEVEMENT_200_P = "CgkI2cCP5_kNEAIQCA";
    //GAMES PLAYED
    public static final String ACHIEVEMENT_10_GP = "CgkI2cCP5_kNEAIQCQ";
    public static final String ACHIEVEMENT_25_GP = "CgkI2cCP5_kNEAIQCg";
    public static final String ACHIEVEMENT_50_GP = "CgkI2cCP5_kNEAIQCw";
    public static final String ACHIEVEMENT_100_GP = "CgkI2cCP5_kNEAIQDA";
    public static final String ACHIEVEMENT_200_GP = "CgkI2cCP5_kNEAIQDQ";

    //COLORS
    public static final String COLOR_BACKGROUND_COLOR = "#ecf0f1";


    //TEXTs
    public static final String SCORE_TEXT = "";
    public static final String BEST_TEXT = "Best: ";
    public static final String SCORE_TEXT_MENU = "Score: ";
    public static final String GAMES_PLAYED_TEXT = "Games Played: ";
    public static final String SWIPE_ANYWHERE_TEXT = "Swipe Anywhere";

    //Share Message
    public static final String SHARE_MESSAGE = "Can you beat my High Score at " + GAME_NAME + "? #SwipeIt ";

}
