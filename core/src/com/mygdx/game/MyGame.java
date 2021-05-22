package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.HeartStone.HeartStoneScreen;
import com.mygdx.game.Island.IslandScreen;
import com.mygdx.game.LakeSide.LakeSideScreen;
import com.mygdx.game.Room.RoomScreen;
import com.mygdx.game.Сrossroad.CrossroadScreen;

public class MyGame extends Game {

    public static float SCREEN_WIDTH = 1280;
    public static float SCREEN_HEIGHT = 720;

    public OrthographicCamera camera;
    public SpriteBatch batch;
    public Stage stage;

    public static Vector3 touchPos;
    public Screen[] screens = new Screen[5];

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.translate(0, 0);
        touchPos = new Vector3();


        screens[0] = new HeartStoneScreen(this);
        screens[1] = new LakeSideScreen(this);
        screens[2] = new IslandScreen(this);
        screens[3] = new CrossroadScreen(this);
        screens[4] = new RoomScreen(this);

        this.setScreen(new MainMenuScreen(this));
//        this.setScreen(screens[4]);
    }

    @Override
    public void render() {
        super.render();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        camera.update();
//        batch.begin();
//        batch.setProjectionMatrix(camera.combined);
//        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

    public Stage getStage(){
        return stage;
    }
}