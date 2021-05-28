package com.mygdx.game.GeneralActors.UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import static com.mygdx.game.BaseScreen.stuff;
import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class ItemActor extends Actor {

    public Texture texture;
    private InventoryActor inventoryActor;

    public boolean used = false;
    public int place;
    public String itemName;

    private Vector2 cordsShift;
    private Vector2 cordsInInventory;
    private Vector2 cordsInUsed;

    private int time = 0;

    public ItemActor(Texture texture, int place, String itemName, Vector2 cordsShift, float width, float height) {

        this.itemName = itemName;

        inventoryActor = new InventoryActor();

        cordsInInventory = new Vector2(-1000, -1000);
        setPosition(cordsInInventory.x, cordsInInventory.y);

        this.texture = texture;
        this.place = place;

        this.cordsShift = cordsShift;
        setWidth(width);
        setHeight(height);

        cordsInUsed = new Vector2(SCREEN_WIDTH - (inventoryActor.width * 2) + cordsShift.x, SCREEN_HEIGHT - inventoryActor.chestHeight + cordsShift.y);
    }

    @Override
    public void act(float delta) {
        Array<Actor> actors = getStage().getActors();
        for (Actor actor : actors) {
            if (actor instanceof InventoryActor) {
                inventoryActor = (InventoryActor) actor;
            }
        }

        if (used) {
            setPosition(cordsInUsed.x, cordsInUsed.y);
        } else {
            cordsInInventory.set(inventoryActor.slotCords.x+cordsShift.x, inventoryActor.slotCords.y + (inventoryActor.slotsTexture.getHeight() - inventoryActor.chestHeight) / 3 * place+cordsShift.y);
            setPosition(cordsInInventory.x, cordsInInventory.y);
        }

        if (Gdx.input.isTouched() && time > 10 && inventoryActor.getStatus()) {
            if (
                    (
                            used &&
                                    touchPos.x > getX() &&
                                    touchPos.x < getX() + getWidth() &&
                                    touchPos.y > getY() &&
                                    touchPos.y < getY() + getHeight()
                    ) ||
                            (
                                    !used && touchPos.y< SCREEN_HEIGHT - inventoryActor.chestHeight &&
                                            touchPos.x > inventoryActor.slotCords.x &&
                                            touchPos.x < inventoryActor.slotCords.x + inventoryActor.width &&
                                            touchPos.y > inventoryActor.slotCords.y + (inventoryActor.slotsTexture.getHeight() - inventoryActor.chestHeight) / 3 * place + 20 &&
                                            touchPos.y < inventoryActor.slotCords.y + (inventoryActor.slotsTexture.getHeight() - inventoryActor.chestHeight) / 3 * place + (inventoryActor.slotsTexture.getHeight() - inventoryActor.chestHeight) / 3 - 20
                            )
            ) {
                if(used == true) used = false;
                else if(used == false) {
                    boolean sbUsed = false;
                    for(int i = 0; i< stuff.length; i++){
                        if(stuff[i]!=null){
                            if(stuff[i].used) sbUsed = true;
                        }
                    }
                    if(!sbUsed) used = true;
                }
                time = 0;
            }

        }
        if (time <= 1000) time++;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
