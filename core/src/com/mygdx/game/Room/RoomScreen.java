package com.mygdx.game.Room;

import com.mygdx.game.BaseScreen;
import com.mygdx.game.MyGame;
import com.mygdx.game.GeneralActors.TextureActor;
import com.mygdx.game.Room.ObjectActors.DoorActor;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.Room.ObjectActors.KeyActor;
import com.mygdx.game.Room.ObjectActors.RoomHeroActor;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public class RoomScreen extends BaseScreen {
    public boolean keyIsPicked = false;

    public RoomScreen(final MyGame myGame){
        super(myGame, -1, 3);
        this.myGame = myGame;

    }

    @Override
    public void addActors() {

        TextureActor backgroundWallActor = new TextureActor("Room/Background/wall.png", 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.addActor(backgroundWallActor);
        TextureActor backgroundFloorActor = new TextureActor("Room/Background/floor.png", 0, 100, SCREEN_WIDTH, 200);
        stage.addActor(backgroundFloorActor);

        DoorActor doorActor = new DoorActor();
        stage.addActor(doorActor);

        if(!keyIsPicked) {
            KeyActor keyActor = new KeyActor();
            stage.addActor(keyActor);
        }

        HeroActor heroActor = new RoomHeroActor(100, 200);
        stage.addActor(heroActor);

        super.addActors();
    }

    public void setKeyIsPicked(){
        keyIsPicked = true;
    }
}
