package com.codingame.game.View;

import com.codingame.game.Model.Item;
import com.codingame.game.Model.StateUpdates.RemoveItemUpdate;
import com.codingame.game.Model.StateUpdates.PlayerTilePosUpdate;
import com.codingame.game.Model.StateUpdates.ShowFrameUpdate;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.view.tooltip.TooltipModule;

import java.util.Observable;

public class TileView extends MovingView {
    private TooltipModule tooltipModule;

    private Group group;

    private Sprite decors;
    private Sprite directions;
    private Sprite background;
    private Sprite frame;
    private Group itemGroup;

    private boolean showFrame = false;
    // popped/pushed tile temporary position
    private Vector2 tempPos;

    private Item tileItem;
    private TileModel model;

    public TileView(GraphicEntityModule entityModule, TooltipModule tooltipModule, TileModel tile) {
        super(entityModule);
        this.tooltipModule = tooltipModule;
        this.model = tile;
        this.tileItem = model.getItem();
        tile.addObserver(this);

        createTileView();
        tooltipModule.registerEntity(group);
    }

    private void createTileView() {
        decors = entityModule.createSprite()
                .setImage(String.format("decors_%s", model.getPattern()))
                .setBaseWidth(Constants.TILE_SIZE)
                .setBaseHeight(Constants.TILE_SIZE)
                .setAnchor(0.5)
                .setZIndex(1);
        directions = entityModule.createSprite()
                .setImage(String.format("paths_%s", model.getPattern()))
                .setBaseWidth(Constants.TILE_SIZE)
                .setBaseHeight(Constants.TILE_SIZE)
                .setAnchor(0.5)
                .setZIndex(2);
        frame = entityModule.createSprite()
                .setImage("frame.png")
                .setBaseWidth(Constants.TILE_SIZE)
                .setBaseHeight(Constants.TILE_SIZE)
                .setAnchor(0.5)
                .setZIndex(2)
                .setVisible(false);
        background = entityModule.createSprite()
                .setImage("tile_background.png")
                .setBaseWidth(Constants.TILE_SIZE)
                .setBaseHeight(Constants.TILE_SIZE)
                .setAlpha(0.3)
                .setAnchor(0.5)
                .setZIndex(0);
        group = entityModule.createGroup()
                .setScale(1);
        group.add(decors, directions, frame, background);

        addItem();
    }

    private void addItem() {
        if (tileItem != null) {
            itemGroup = entityModule.createGroup().setZIndex(2);
            itemGroup.add(entityModule.createCircle()
                    .setScale(0.3)
                    .setAlpha(0.7)
                    .setZIndex(0));
            String spritePath = String.format("item_%s_%d", tileItem.getName(), tileItem.getPlayerId());
            itemGroup.add(entityModule.createSprite()
                .setImage(spritePath)
                .setAnchor(0.5)
                .setZIndex(1));
            group.add(itemGroup);
        }
    }

    private void removeItem() {
        itemGroup.setScale(0);
        entityModule.commitEntityState(1, itemGroup);
        group.remove(this.itemGroup);
    }

    /* pushed tile travels from player's tile pos to map from time 0 to time 0.3
    *  map tiles move from time 0.3 to time 0.6
    *  popped tile travels to the respective pos from time 0.6 to time 1
    */
    public void updateView(){
        if (model.getPlayerId() != null) {
            //always show frame for player's tile
            frame.setVisible(true);
            entityModule.commitEntityState(0, frame);
            //map tile -> player's tile case
            if (tempPos != null) {
                entityModule.commitEntityState(0.3, group);
                setMapPos(group, tempPos);
                //player's tile is on top of everything
                group.setZIndex(4);
                entityModule.commitEntityState(0.6, group);
            }
            Vector2 pos = Constants.TILE_POSITIONS.get(model.getPlayerId());
            group.setX(pos.getX()).setY(pos.getY());
        } else {
            //moving map tiles
            if (showFrame) {
                frame.setVisible(true);
                entityModule.commitEntityState(0, frame);
                //player's tile -> map tile case
                if (tempPos != null) {
                    setMapPos(group, tempPos);
                    group.setZIndex(4);
                }
                entityModule.commitEntityState(0.3, group);
                group.setZIndex(1); //get pushed tile zIndex back
                setMapPos(group, model.getPos());
                entityModule.commitEntityState(0.6, group);
                frame.setVisible(false);
                showFrame = false;
            } else
                setMapPos(group, model.getPos());
        }
        tempPos = null;

        String tooltipText = model.getPos().toTooltip();
        if (model.hasItem())
            tooltipText += '\n' + model.getItem().toTooltip();
        tooltipModule.updateExtraTooltipText(group, tooltipText);
    }

    public void update(Observable observable, Object update) {
        super.update(model, update);
        if (update instanceof RemoveItemUpdate)
            removeItem();
        else if (update instanceof ShowFrameUpdate)
            showFrame = true;
        else if (update instanceof PlayerTilePosUpdate) {
            tempPos = ((PlayerTilePosUpdate) update).getPos();
        }
    }

    public Entity getEntity() {
        return group;
    }
}
