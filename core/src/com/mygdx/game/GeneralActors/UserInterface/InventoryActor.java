package com.mygdx.game.GeneralActors.UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GeneralActors.HeroActor;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class InventoryActor extends Actor {

    public boolean inventoryIsOpen = false;
    public boolean inventoryIsOpenCanChange = false;

    public Texture slotsTexture;
    private Texture useItemTexture;
    private Texture chestTexture;

    public Vector2 chestCords;
    public Vector2 slotCords;
    public Vector2 useItemCords;

    public float width;
    public float height;
    public float chestHeight = 165;

    public float timeSeconds = 0f;
    public float period = 1f;

    public InventoryActor() {
        slotsTexture = new Texture(Gdx.files.internal("General/UserInterface/Inventory/slots.png"));
        chestTexture = new Texture(Gdx.files.internal("General/UserInterface/Inventory/chest.png"));
        useItemTexture = new Texture(Gdx.files.internal("General/UserInterface/Inventory/useitem.png"));
        width = chestTexture.getWidth();
        height = chestTexture.getHeight();
        
        chestCords = new Vector2(SCREEN_WIDTH - width, SCREEN_HEIGHT - height);
        slotCords = new Vector2(SCREEN_WIDTH +20, SCREEN_HEIGHT - height);
        useItemCords = new Vector2(SCREEN_WIDTH - width * 2, SCREEN_HEIGHT - height);
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isTouched() && inventoryIsOpenCanChange &&
                touchPos.x > chestCords.x &&
                touchPos.y > SCREEN_HEIGHT - chestHeight
        ) {
            inventoryIsOpenCanChange = false;
            inventoryIsOpen = !inventoryIsOpen;
            if (inventoryIsOpen && slotCords.x > SCREEN_WIDTH - getWidth())
                slotCords.x = SCREEN_WIDTH - width;
            if (!inventoryIsOpen && slotCords.x < SCREEN_WIDTH) slotCords.x = SCREEN_WIDTH + 20;
            Array<Actor> actors = getStage().getActors();
            for (Actor actor : actors) {
                if (actor instanceof HeroActor) {
                    ((HeroActor) actor).stopHero();
                }
            }
            timeSeconds = 0;
        }

        timeSeconds += delta;
        if(timeSeconds > period){
            timeSeconds-=period;
            inventoryIsOpenCanChange =true;
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(slotsTexture, slotCords.x, slotCords.y, width, height);
        batch.draw(chestTexture, chestCords.x, chestCords.y, width, height);
        batch.draw(useItemTexture, useItemCords.x, useItemCords.y, width, height);
    }

    public boolean getStatus(){
        return inventoryIsOpen;
    }
}
