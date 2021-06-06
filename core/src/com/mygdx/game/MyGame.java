package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Bridge.BridgeScreen;
import com.mygdx.game.HeartStone.HeartStoneScreen;
import com.mygdx.game.Island.IslandScreen;
import com.mygdx.game.LakeSide.LakeSideScreen;
import com.mygdx.game.Сrossroad.CrossroadScreen;

public class MyGame extends Game {

    public static float SCREEN_WIDTH = 1280;
    public static float SCREEN_HEIGHT = 720;

    public OrthographicCamera camera;
    public SpriteBatch batch;
    public Stage stage;
    public BitmapFont font;

    public static Vector3 touchPos;
    public BaseScreen[] screens = new BaseScreen[6];

    @Override
    public void create() {
        batch = new SpriteBatch();

        font = new BitmapFont(Gdx.files.internal("Font/font.fnt"), Gdx.files.internal("Font/font.png"), false);
        font.setColor(Color.GOLD);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.translate(0, 0);

        touchPos = new Vector3();

        screens[0] = new HeartStoneScreen(this);
        screens[1] = new CrossroadScreen(this);
        screens[2] = new BridgeScreen(this);
        screens[3] = new LakeSideScreen(this);
        screens[4] = new IslandScreen(this);

//        this.setScreen(new MainMenuScreen(this));
        this.setScreen(screens[3]);
//        this.setScreen(new EndScreen(this));

    }

    @Override
    public void render() {
        super.render();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }

    public BitmapFont getFont(){
        return font;
    }

    public Stage getStage(){
        return stage;
    }
}