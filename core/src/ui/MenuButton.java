package ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import configuration.Settings;
import gameobjects.GameObject;
import gameworld.GameWorld;
import helpers.FlatColors;
import tweens.Value;

/**
 * Created by ManuGil on 14/03/15.
 */
public class MenuButton extends GameObject {

    private Color color;
    private Value time = new Value();
    private Sprite icon, iconShadow;

    public MenuButton(final GameWorld world, float x, float y, float width, float height,
                      TextureRegion texture, Color color, Shape shape, TextureRegion buttonIcon) {
        super(world, x, y, width, height, texture, color, shape);
        this.color = color;

        if (Settings.SHADOWS)
            setShadow(true);
        icon = new Sprite(buttonIcon);
        icon.setPosition(getPosition().x, getPosition().y);
        icon.setSize(width, height);
        if (!Settings.REMOVE_CIRCLE_BUTTONS)
            icon.setScale(0.8f, 0.8f);
        icon.setOriginCenter();

        if (Settings.SHADOWS) {
            iconShadow = new Sprite(buttonIcon);
            iconShadow.setPosition(getPosition().x, getPosition().y);
            iconShadow.setSize(width, height);
            if (!Settings.REMOVE_CIRCLE_BUTTONS)
                iconShadow.setScale(0.8f, 0.8f);
            iconShadow.setOriginCenter();
            iconShadow.setColor(getShadowSprite().getColor());
            iconShadow.setAlpha(getShadowSprite().getColor().a);
        }

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.render(batch, shapeRenderer);
        if (Settings.SHADOWS) iconShadow.setAlpha(getShadowSprite().getColor().a);
        if (isPressed) {
            //icon.setAlpha(.5f);
            getSprite().setColor(FlatColors.GREY);
        } else {
            // icon.setAlpha(1f);
            getSprite().setColor(color);
        }
        if (Settings.SHADOWS) iconShadow.draw(batch);
        icon.draw(batch);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        icon.setPosition(getPosition().x, getPosition().y);
        icon.setScale(getSprite().getScaleX());

        if (Settings.SHADOWS) iconShadow.setPosition(getPosition().x + 5, getPosition().y - 5);
    }


    public void setIcon(TextureRegion icon1) {
        icon.setRegion(icon1);
    }
}