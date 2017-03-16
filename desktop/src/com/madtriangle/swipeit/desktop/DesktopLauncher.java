package com.madtriangle.swipeit.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import MainGame.Smove;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Smove";
        config.width= (int) (1080 / 3);
        config.height = (int) (1720 / 3);
        new LwjglApplication(new Smove(new ActionResolverDesktop()), config);
    }
}
