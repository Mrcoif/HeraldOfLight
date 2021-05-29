package com.mygdx.game.Сrossroad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.GeneralActors.TextureActor;
import com.mygdx.game.HeartStone.ObjectActors.HeartStoneHeroActor;
import com.mygdx.game.MyGame;
import com.mygdx.game.Сrossroad.ObjectActors.CrossroadHeroActor;
import com.mygdx.game.Сrossroad.ObjectActors.StonesActor;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public class CrossroadScreen extends BaseScreen {
    private int textTime = 0;

    public CrossroadScreen(MyGame myGame) {
        super(myGame, 2, 3);
        this.myGame = myGame;

        music = Gdx.audio.newMusic(Gdx.files.internal("HeartStone/music.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.stop();

        renderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof CrossroadHeroActor) {
                if (((CrossroadHeroActor) actor).leftUpScreen || ((CrossroadHeroActor) actor).rightUpScreen) {
                    if (((CrossroadHeroActor) actor).leftUpScreen) {
                            ((CrossroadHeroActor) actor).leftUpScreen = false;
                            myGame.setScreen(myGame.screens[0]);
                    }
                    ((CrossroadHeroActor) actor).stopHero();
                    ((CrossroadHeroActor) actor).setY(((CrossroadHeroActor) actor).getY() - 100);
                }
            }
        }

        if (textTime < 180) {
            textTime += 1;
            myGame.batch.begin();
            myGame.getFont().draw(myGame.batch, "Where am I", 100, 400);
            myGame.batch.end();
        }
    }

    @Override
    public void addActors() {
        stage.addActor(new TextureActor("Crossroad/background.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        stage.addActor(new StonesActor());

        stage.addActor(new CrossroadHeroActor( 100, 115, new Rectangle(235, 213, 230, 102) ));

        stage.addActor(new TextureActor("Crossroad/frontground.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        super.addActors();
    }

    @Override
    protected void drawDebug(){
        Array<Actor> actors = stage.getActors();
        Array.ArrayIterator<Actor> iterator1 = actors.iterator();
        while (iterator1.hasNext()) {
            Actor actor1 = iterator1.next();
            if (actor1 instanceof HeartStoneHeroActor) {
                ((HeartStoneHeroActor) (actor1)).drawDebug(renderer);
            }
        }
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.MAGENTA);
        renderer.rect(235, 215, 230, 100);
        renderer.rect(820, 215, 210, 100);
        renderer.rect(0, 100, 1280, 115);
        renderer.end();
    }
}
