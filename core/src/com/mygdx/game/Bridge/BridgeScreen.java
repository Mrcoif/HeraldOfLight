package com.mygdx.game.Bridge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.Bridge.ObjectActors.BridgeHeroActor;
import com.mygdx.game.Bridge.ObjectActors.LeftHeartPartActor;
import com.mygdx.game.Сrossroad.ObjectActors.StonesActor;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.GeneralActors.TextureActor;
import com.mygdx.game.MyGame;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public class BridgeScreen extends BaseScreen {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public BridgeScreen(final MyGame myGame) {
        super(myGame, -1, 1);
        this.myGame = myGame;

        music = Gdx.audio.newMusic(Gdx.files.internal("Bridge/music.mp3"));
        music.setLooping(true);
        music.setVolume(0.06f);
        music.stop();
        renderer = new ShapeRenderer();
    }

    @Override
    public void addActors() {
        stage.addActor(new TextureActor("Bridge/background.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        stage.addActor(new LeftHeartPartActor());

        stage.addActor(new BridgeHeroActor(100, this));

        stage.addActor(new TextureActor("Bridge/water.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        stage.addActor(new TextureActor("Bridge/frontground.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        stage.addActor(new TextureActor("Bridge/grass.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        stage.addActor(new TextureActor("Bridge/light.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        super.addActors();
    }

    @Override
    protected void drawDebug(){
        Array<Actor> actors = stage.getActors();
        Array.ArrayIterator<Actor> iterator1 = actors.iterator();
        while (iterator1.hasNext()) {
            Actor actor1 = iterator1.next();
            if (actor1 instanceof HeroActor) {
                ((HeroActor) (actor1)).drawDebug(renderer);
            }
        }

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.MAGENTA);

        for(float x = 250; x<=987; x+=0.1){
            renderer.point(x, (float) (-0.0008 * (x-550)*(x-550) + 0.0001 * x + 440), 0);
        }

        renderer.rect(230, 250, 757, 310);
        renderer.rect(987, 180, 350, 110);
        shapeRenderer.rect(400, 475, 100, 10);

        renderer.end();
    }
}
