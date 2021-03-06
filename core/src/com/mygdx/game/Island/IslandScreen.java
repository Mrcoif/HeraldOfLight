package com.mygdx.game.Island;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.GeneralActors.TextureActor;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;
import com.mygdx.game.Island.ObjectActors.IslandHeroActor;
import com.mygdx.game.Island.ObjectActors.NestActor;
import com.mygdx.game.MyGame;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public class IslandScreen extends BaseScreen {

    private int darkTime = 100000;
    private Sound crackSound = Gdx.audio.newSound(Gdx.files.internal("Island/crackSound.mp3"));
    private Sound sound2 = Gdx.audio.newSound(Gdx.files.internal("Island/stone.mp3"));
    private int textTime = 180;
    private boolean firstSound = true;

    public IslandScreen(final MyGame myGame) {
        super(myGame, -1, -1);
        this.myGame = myGame;

        music = Gdx.audio.newMusic(Gdx.files.internal("Lake/music.mp3"));
        music.setLooping(true);
        music.setVolume(2.0f);
        music.stop();
    }

    @Override
    public void show() {
        super.show();

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        boolean overlap = false;

            Array<Actor> actors2 = stage.getActors();
            for (Actor actor2 : actors2) {
                if (actor2 instanceof ButtonActor) {
                    if (((ButtonActor) actor2).isTouched && ((ButtonActor) actor2).buttonType.equals("explore")) {
                        textTime = 0;
                    }
                }
        }
        if (textTime < 180) {
            textTime += 1;
            myGame.batch.begin();
            myGame.getFont().draw(myGame.batch, "Can I break a nest?", 100, 400);
            myGame.batch.end();
        }

        for (Actor actor2 : actors2) {
            if (actor2 instanceof HeroActor) {
                if (((HeroActor) actor2).getX() < 570) {
                    overlap = true;
                }
            }
        }
        if (overlap) {
            for (Actor actor2 : actors2) {
                if (actor2 instanceof ButtonActor) {
                    if (((ButtonActor) actor2).buttonType.equals("use") && ((ButtonActor) actor2).isTouched) {

                        music.stop();
                        myGame.setScreen(myGame.screens[3]);
                    }
                }
            }
        }

        if (darkTime == 60 && firstSound) {
            firstSound = false;
            crackSound.play();
        }
        if (darkTime > 300) {
            for (Actor actor2 : actors2) {
                if (actor2 instanceof TextureActor) {
                    if (((TextureActor) actor2).getHeight() == SCREEN_HEIGHT + 100) {
                        ((TextureActor) actor2).remove();
                        music.play();
                    }
                }
            }
        }
        if (darkTime < 1000) darkTime++;
    }


    @Override
    public void addActors() {
        stage.addActor(new TextureActor("Island/background.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT - 100));

        stage.addActor(new TextureActor("Island/boat.png", 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT - 100));

        stage.addActor(new NestActor(this));

        stage.addActor(new IslandHeroActor(100, 200));

        super.addActors();
    }

    @Override
    protected void drawDebug() {
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
        for (float x = 0; x <= SCREEN_WIDTH; x += 0.25) {
            renderer.point(x, (float) (50 * Math.sin(0.005 * x + 400) + 170), 0);
            renderer.point(x, (float) (-50 * Math.sin(0.005 * x + 400) + 190), 0);
        }
        renderer.rect(SCREEN_WIDTH / 2 - 50, 500, 100, 10);
        renderer.end();
    }

    public void startNestFalling() {
        stage.addActor(new TextureActor("General/black.png", 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT + 100));
        stopMusic();
        sound2.play();
        darkTime = 0;
    }
}
