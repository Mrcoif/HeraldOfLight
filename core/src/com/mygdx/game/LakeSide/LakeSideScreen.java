package com.mygdx.game.LakeSide;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.GeneralActors.TextureActor;
import com.mygdx.game.LakeSide.ObjectActors.HeartActor;
import com.mygdx.game.LakeSide.ObjectActors.LakeSideHeroActor;
import com.mygdx.game.LakeSide.ObjectActors.PillarActor;
import com.mygdx.game.MyGame;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class LakeSideScreen extends BaseScreen {

    public LakeSideScreen(final MyGame myGame ) {
        super(myGame, 1, -1);
        this.myGame = myGame;

        music = Gdx.audio.newMusic(Gdx.files.internal("Lake/music.mp3"));
        music.setLooping(true);
        music.setVolume(1.0f);
        music.stop();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Array<Actor> actors2 = stage.getActors();
        for (Actor actor2 : actors2) {
            if (actor2 instanceof PillarActor) {
                if (((PillarActor) actor2).islandScreen) {
                    ((PillarActor) actor2).islandScreen = false;
                    music.stop();
                    myGame.setScreen(myGame.screens[4]);
                }
            }
        }
    }

    @Override
    public void addActors() {
        stage.addActor(new TextureActor("Lake/background.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        stage.addActor(new PillarActor(1));

        stage.addActor(new PillarActor(2));

        stage.addActor(new HeartActor());

        stage.addActor(new TextureActor("Lake/cane.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        stage.addActor(new LakeSideHeroActor(100, 118, new Rectangle(360, 218, 390, 115)));

        stage.addActor(new TextureActor("Lake/frontground.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        super.addActors();
    }

    @Override
    protected void drawDebug() {
        Array<Actor> actors = stage.getActors();
        Array.ArrayIterator<Actor> iterator1 = actors.iterator();
        while (iterator1.hasNext()) {
            Actor actor1 = iterator1.next();
            if (actor1 instanceof PillarActor) {
                ((PillarActor) (actor1)).drawDebug(renderer);
            }
            if (actor1 instanceof LakeSideHeroActor) {
                ((LakeSideHeroActor) (actor1)).drawDebug(renderer);
            }
        }
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.MAGENTA);
        renderer.rect(360, 218, 390, 115);
        for (float x = 0; x <= SCREEN_WIDTH; x += 0.25) {
            renderer.point(x, (float) (60 * Math.sin(0.01 * x - 5) + 280), 0);
        }
        renderer.end();
    }
}
