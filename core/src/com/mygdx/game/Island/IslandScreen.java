package com.mygdx.game.Island;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.GeneralActors.TextureActor;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;
import com.mygdx.game.Island.ObjectActors.IslandHeroActor;
import com.mygdx.game.MyGame;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public class IslandScreen extends BaseScreen {

    public IslandScreen(final MyGame myGame) {
        super(myGame, 0, 0);
        this.myGame = myGame;

        music = Gdx.audio.newMusic(Gdx.files.internal("Lake/music.mp3"));
        music.setLooping(true);
        music.setVolume(2.0f);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Array<Actor> actors2 = stage.getActors();
        for (Actor actor2 : actors2) {
            if (actor2 instanceof ButtonActor) {
                if (((ButtonActor) actor2).buttonType.equals("use") && ((ButtonActor) actor2).isTouched) {
                    myGame.setScreen(myGame.screens[1]);
                }
            }
        }
    }

    @Override
    public void addActors() {
        stage.addActor(new TextureActor("Island/background.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100));

        stage.addActor(new IslandHeroActor(100, 200));

        super.addActors();
    }

    @Override
    protected void drawDebug(){
        Array<Actor> actors = stage.getActors();
        Array.ArrayIterator<Actor> iterator1 = actors.iterator();
        while (iterator1.hasNext()) {
            Actor actor1 = iterator1.next();
            if (actor1 instanceof IslandHeroActor) {
                ((IslandHeroActor) (actor1)).drawDebug(renderer);
            }
        }

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.MAGENTA);
        for(float x = 0; x<= SCREEN_WIDTH; x+=0.25){
            renderer.point(x, (float) (50 * Math.sin(0.005 * x + 400) + 170), 0);
            renderer.point(x, (float) (-50 * Math.sin(0.005 * x + 400) + 190), 0);
        }
        renderer.end();
    }
}
