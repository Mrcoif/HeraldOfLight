package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GeneralActors.TextureActor;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class MainMenuScreen implements Screen {

    private MyGame myGame;
    private Texture texture;
    public  Stage stage;
    private float startX;
    private  float startY;

    public MainMenuScreen(final MyGame myGame){
        this.myGame = myGame;
        texture = new Texture(Gdx.files.internal("MainMenu/startButton.png"));
        startX = SCREEN_WIDTH/2-texture.getWidth()/2;
        startY = SCREEN_HEIGHT/2-texture.getHeight()/2;

        FitViewport viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, myGame.camera);
        stage = new Stage(viewport, myGame.batch);
        stage.addActor(new TextureActor("MainMenu/startButton.png",startX, startY, texture.getWidth(), texture.getHeight()));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isTouched() &&
                touchPos.x > startX &&
                touchPos.x < startX + texture.getWidth() &&
                SCREEN_HEIGHT - touchPos.y > startY &&
                SCREEN_HEIGHT - touchPos.y < startY + texture.getHeight()
        ) {
            myGame.setScreen(myGame.screens[1]);
            dispose();
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
