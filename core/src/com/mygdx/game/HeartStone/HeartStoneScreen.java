package com.mygdx.game.HeartStone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.HeartStone.ObjectActors.HeartStoneHeroActor;
import com.mygdx.game.MyGame;
import com.mygdx.game.GeneralActors.TextureActor;
import com.mygdx.game.HeartStone.ObjectActors.StoneActor;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public class HeartStoneScreen extends BaseScreen {

    public HeartStoneScreen(final MyGame myGame) {
        super(myGame, 1, -1);
        this.myGame = myGame;

        music = Gdx.audio.newMusic(Gdx.files.internal("HeartStone/music.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        System.out.println(music.toString());
        renderer = new ShapeRenderer();
    }

    @Override
    public void addActors() {
        stage.addActor(new TextureActor("HeartStone/background.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT - 100));

        stage.addActor(new StoneActor());

        stage.addActor(new HeartStoneHeroActor(100));

        stage.addActor(new TextureActor("HeartStone/frontground.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT - 100));

        super.addActors();
    }

    @Override
    protected void drawDebug(){
        Array<Actor> actors = stage.getActors();
        Array.ArrayIterator<Actor> iterator1 = actors.iterator();
        while (iterator1.hasNext()) {
            Actor actor1 = iterator1.next();
            if (actor1 instanceof StoneActor) {
                ((StoneActor) (actor1)).drawDebug(renderer);
            }
            if (actor1 instanceof HeroActor) {
                ((HeroActor) (actor1)).drawDebug(renderer);
            }
        }
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.MAGENTA);
        for(float x = 0; x<=SCREEN_WIDTH; x+=0.1){
            renderer.point(x, (float) (50 * Math.sin(0.002 * x + 0.3) + 170), 0);
        }
        renderer.end();
    }
}
