package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GeneralActors.TextureActor;
import com.mygdx.game.LakeSide.ObjectActors.BoatActor;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class EndScreen implements Screen {

    private MyGame myGame;
    public Stage stage;
    private int time= 0;
    private int imageNum = 0;
    public boolean showImages= true;

    public EndScreen(final MyGame myGame){
        this.myGame = myGame;

        FitViewport viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, myGame.camera);
        stage = new Stage(viewport, myGame.batch);
        stage.addActor(new TextureActor("General/black.png",0, 0, SCREEN_WIDTH, SCREEN_HEIGHT));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        myGame.batch.begin();
        myGame.getFont().draw(myGame.batch, "You win!!!",550, 360);
        myGame.batch.end();

//        if (showImages) {
//            if (time % 60 == 0) {
//                imageNum++;
//                if(imageNum ==4) showImages = false;
//                Array<Actor> actors2 = stage.getActors();
//                for (Actor actor2 : actors2) {
//                    if (actor2 instanceof TextureActor) {
//                        ((TextureActor) actor2).texture = new Texture(Gdx.files.internal("Final/" + imageNum + ".png"));
//                    }
//                }
//            }
//            time++;
//        }
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

    }
}