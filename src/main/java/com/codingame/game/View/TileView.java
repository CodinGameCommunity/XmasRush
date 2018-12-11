package com.codingame.game.View;

import com.codingame.game.Model.Item;
import com.codingame.game.Model.StateUpdates.PoppedUpdate;
import com.codingame.game.Model.StateUpdates.PushedUpdate;
import com.codingame.game.Model.StateUpdates.RemoveItemUpdate;
import com.codingame.game.Model.StateUpdates.ShowFrameUpdate;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Constants.Direction;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Circle;
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
    private Circle itemBackground;

    private boolean showFrame = false;
    private boolean highlight = false;

    private Item tileItem;
    private TileModel model;
    private TileState state = TileState.STILL;
    
    private enum TileState{
        STILL,
        MOVED,
        POPPED,
        PUSHED;
    }
    
    private Direction tempDirection;

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
            itemBackground = entityModule.createCircle()
                    .setZIndex(0)
                    .setScale(0.3)
                    .setAlpha(0.7);
            itemGroup.add(itemBackground);
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

    public void updateView(){
        if (model.getPlayerId() != null) {
            //always show frame for player's tiles
            frame.setVisible(true);
            entityModule.commitEntityState(0, frame);

            
            if (this.state == TileState.POPPED) {
                entityModule.commitEntityState(0.3, group);
                Vector2 nextPos = Vector2.fromViewSpaceToMapSpace(new Vector2(group.getX(),group.getY()));
                nextPos.add(tempDirection.asVector());
                setMapPos(group, nextPos);
              //player tile is on top of everything
                group.setZIndex(4);
                entityModule.commitEntityState(0.6, group);
                
            }
            Vector2 pos = Constants.TILE_POSITIONS.get(model.getPlayerId());
            group.setX(pos.getX()).setY(pos.getY());
        } else {
            //only show frames for moving map tiles
            if (showFrame) {
                frame.setVisible(true);
                entityModule.commitEntityState(0, frame);
                frame.setVisible(false);
                showFrame = false;
            }            
            if (this.state == TileState.PUSHED) {
              //player tile is on top of everything
                group.setZIndex(4);
                entityModule.commitEntityState(0, group);
                Vector2 nextPos = new Vector2(model.getPos());
                nextPos.add(tempDirection.getOpposite().asVector());
                setMapPos(group, nextPos);
                entityModule.commitEntityState(0.3, group);
              //get pushed tile zIndex back
                group.setZIndex(1);
                setMapPos(group, model.getPos());
                entityModule.commitEntityState(0.6, group);
            } else if (this.state == TileState.MOVED) {
                entityModule.commitEntityState(0.3, group);
                setMapPos(group, model.getPos());
                entityModule.commitEntityState(0.6, group);
            }
            setMapPos(group, model.getPos());
            
        }
        
        this.state = TileState.STILL;

        String tooltipText = model.getPos().toTooltip();
        if (model.hasItem()) {
            //add highlight once
            if (!highlight && model.getItem().getHighlight()){
                int highlightColor = tileItem.getHighlightColor();
                itemBackground.setFillColor(highlightColor);
                entityModule.commitEntityState(0, itemBackground);
                highlight = true;
            }
            tooltipText += '\n' + model.getItem().toTooltip();
        }
        tooltipModule.updateExtraTooltipText(group, tooltipText);
    }

    public void update(Observable observable, Object update) {
        super.update(model, update);
        if (update instanceof RemoveItemUpdate)
            removeItem();
        else if (update instanceof ShowFrameUpdate) {
            showFrame = true;
            if(this.state == TileState.STILL) {
                this.state = TileState.MOVED;
            }
        } else if(update instanceof PushedUpdate) {
            this.state = TileState.PUSHED;
            this.tempDirection = ((PushedUpdate) update).getDirection();
        }else if(update instanceof PoppedUpdate) {
            
            this.state = TileState.POPPED;
            this.tempDirection = ((PoppedUpdate) update).getDirection();
        }
    }

    public Entity getEntity() {
        return group;
    }
}
