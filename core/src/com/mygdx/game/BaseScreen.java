package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

    protected MyGame myGame;
    protected Stage stage;

    private int right = 0;
    private int left = 0;
    protected boolean drawDebug = false;
    protected ShapeRenderer renderer = new ShapeRenderer();

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
        Array<Actor> actors = stage.getActors();
        Array.ArrayIterator<Actor> iterator = actors.iterator();
        while (iterator.hasNext()) {
            Actor actor = iterator.next();
            if (actor instanceof ItemActor) {
                iterator.remove();
            }
        }

        for (int i = 0; stuff[i] != null; i++) {
            stage.addActor(stuff[i]);
        }

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof HeroActor) {
                if (((HeroActor) actor).leftScreen || ((HeroActor) actor).rightScreen) {
                    if (((HeroActor) actor).leftScreen) {
                        if(left != -1){
                            ((HeroActor) actor).leftScreen = false;
                            myGame.setScreen(myGame.screens[left]);
                            ((HeroActor) actor).setX(((HeroActor) actor).getX() + ((HeroActor) actor).getWidth());
                        }
                    } else {
                        if(right != -1) {
                            ((HeroActor) actor).rightScreen = false;
                            myGame.setScreen(myGame.screens[right]);
                            ((HeroActor) actor).setX(((HeroActor) actor).getX() - ((HeroActor) actor).getWidth());
                        }
                    }
                    ((HeroActor) actor).stopHero();
                    ((HeroActor) actor).setY(((HeroActor) actor).start.y);
                }
            }
        }
        if(drawDebug) drawDebug();
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
    }

    public void addActors() {
        stage.addActor(new TextureActor("General/UserInterface/Buttons/buttonsBackground.png", 0, 0, SCREEN_WIDTH, 100));

        stage.addActor(new ButtonActor(100, 0, "General/UserInterface/Buttons/exploreButton.png", "explore"));
        stage.addActor(new ButtonActor(500, 0, "General/UserInterface/Buttons/takeButton.png", "take"));
        stage.addActor(new ButtonActor(900, 0, "General/UserInterface/Buttons/useButton.png", "use"));

        stage.addActor(new InventoryActor());

        for (int i = 0; stuff[i] != null; i++) {
            stage.addActor(stuff[i]);
        }
    }

    protected void drawDebug(){}
}
