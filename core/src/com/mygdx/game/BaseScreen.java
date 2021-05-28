package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GeneralActors.TextureActor;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;
import com.mygdx.game.GeneralActors.UserInterface.InventoryActor;
import com.mygdx.game.GeneralActors.UserInterface.ItemActor;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public abstract class BaseScreen implements Screen {
    public static ItemActor[] stuff = new ItemActor[4];
    public MyGame myGame;

    protected Music music;
    protected Stage stage;
    protected boolean drawDebug = false;
    protected ShapeRenderer renderer = new ShapeRenderer();

    private int right = 0;
    private int left = 0;

    public BaseScreen(final MyGame myGame, int leftScreenId, int rightScreenId) {
        this.left = leftScreenId;
        this.right = rightScreenId;
        this.myGame = myGame;

        FitViewport viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, myGame.camera);
        stage = new Stage(viewport, myGame.batch);
        addActors();
    }

    @Override
    public void show() {
        music.play();
        Array<Actor> actors = stage.getActors();
        Array.ArrayIterator<Actor> iterator = actors.iterator();
        while (iterator.hasNext()) {
            Actor actor1 = iterator.next();
            if (actor1 instanceof ItemActor) {
                iterator.remove();
            }
        }

        for (int i = 0; i<3; i++) {
            if(stuff[i]!=null)stage.addActor(stuff[i]);
        }

        Array<Actor> actors3 = stage.getActors();
        for (Actor actor3 : actors3) {
            if (actor3 instanceof HeroActor) {
                ((HeroActor) actor3).stopHero();
            }
        }

        Array<Actor> actors2 = stage.getActors();
        for (Actor actor2 : actors2) {
            if (actor2 instanceof ButtonActor) {
                ((ButtonActor) actor2).isTouched = false;
            }
        }

    }

    @Override
    public void render(float delta) {

        boolean changeScreen = false;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        Array<Actor> actors1 = stage.getActors();
        for (Actor actor1 : actors1) {
            if (actor1 instanceof HeroActor) {
                if (((HeroActor) actor1).leftScreen || ((HeroActor) actor1).rightScreen) {
                    changeScreen = true;
                }
            }
        }

        if (changeScreen) {

            Array<Actor> actors2 = stage.getActors();
            for (Actor actor2 : actors2) {
                if (actor2 instanceof ButtonActor) {
                    ((ButtonActor) actor2).isTouched = false;
                }
            }

            Array<Actor> actors3 = stage.getActors();
            for (Actor actor3 : actors3) {
                if (actor3 instanceof HeroActor) {
                    if (((HeroActor) actor3).leftScreen || ((HeroActor) actor3).rightScreen) {
                        changeScreen = false;

                        ((HeroActor) actor3).stopHero();
                        music.stop();

                        if (((HeroActor) actor3).leftScreen) {
                            if (left != -1) {
                                ((HeroActor) actor3).leftScreen = false;
                                myGame.setScreen(myGame.screens[left]);
                                ((HeroActor) actor3).setX(0 + ((HeroActor) actor3).getWidth());
                            }
                        } else {
                            if (right != -1) {
                                ((HeroActor) actor3).rightScreen = false;
                                myGame.setScreen(myGame.screens[right]);
                                ((HeroActor) actor3).setX(SCREEN_WIDTH - ((HeroActor) actor3).getWidth());
                            }
                        }

                    }
                }
            }

        }

        if (drawDebug) drawDebug();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getViewport().setCamera(myGame.camera);
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
        music.dispose();
    }

    public void addActors() {
        stage.addActor(new TextureActor("General/UserInterface/Buttons/buttonsBackground.png", 0, 0, SCREEN_WIDTH, 100));

        stage.addActor(new ButtonActor(100, 0, "General/UserInterface/Buttons/exploreButton.png", "explore"));
        stage.addActor(new ButtonActor(500, 0, "General/UserInterface/Buttons/takeButton.png", "take"));
        stage.addActor(new ButtonActor(900, 0, "General/UserInterface/Buttons/useButton.png", "use"));

        stage.addActor(new InventoryActor());

        for (int i = 0; i<3; i++) {
            if(stuff[i] != null)stage.addActor(stuff[i]);
        }
    }

    public void stopMusic() {
        music.stop();
    }

    protected void drawDebug() {
    }
}
