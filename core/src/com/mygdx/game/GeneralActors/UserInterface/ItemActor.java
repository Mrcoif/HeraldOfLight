package com.mygdx.game.GeneralActors.UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class ItemActor extends Actor {

    public Texture texture;
    private InventoryActor inventoryActor;

    public boolean used = false;
    public int place;
    public String itemName;

    Vector2 cordsInInventory;
    Vector2 cordsInUsed;

    private int time = 0;

    public ItemActor(Texture texture, int place, String itemName) {

        this.itemName = itemName;

        inventoryActor = new InventoryActor();

        cordsInInventory = new Vector2(-1000, -1000);
        setPosition(cordsInInventory.x, cordsInInventory.y);

        cordsInUsed = new Vector2(SCREEN_WIDTH - (inventoryActor.width * 2) + 30, SCREEN_HEIGHT - inventoryActor.chestHeight + 50);

        this.texture = texture;
        this.place = place;

        setWidth((float) (texture.getWidth() / 2));
        setHeight((float) (texture.getHeight() / 2));
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
            cordsInInventory.set(inventoryActor.slotCords.x + 25, inventoryActor.slotCords.y + inventoryActor.height / 3 * place + 45);
            setPosition(cordsInInventory.x, cordsInInventory.y);
        }

        if (Gdx.input.isTouched() && time > 10 && inventoryActor.getStatus()) {
            if (
                    (
                            used &&
                                    touchPos.x > getX() &&
                                    touchPos.x < getX() + texture.getWidth() &&
                                    touchPos.y > getY() &&
                                    touchPos.y < getY() + texture.getHeight()
                    ) ||
                            (
                                    !used &&
                                            touchPos.x > inventoryActor.slotCords.x &&
                                            touchPos.x < inventoryActor.slotCords.x + inventoryActor.width &&
                                            touchPos.y > inventoryActor.slotCords.y + inventoryActor.height / 3 * place &&
                                            touchPos.y < inventoryActor.slotCords.y + inventoryActor.height / 3 * place + inventoryActor.chestHeight
                            )
            ) {
                used = !used;
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
