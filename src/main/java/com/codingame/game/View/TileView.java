package com.codingame.game.View;

import com.codingame.game.Model.Item;
import com.codingame.game.Model.StateUpdates.RemoveItemUpdate;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.view.tooltip.TooltipModule;

import java.util.HashMap;
import java.util.Observable;

public class TileView extends MovingView {
    private TooltipModule tooltipModule;

    private Group group;

    private Sprite background;
    private Sprite up;
    private Sprite right;
    private Sprite down;
    private Sprite left;
    private Sprite item;

    private Item tileItem;
    private TileModel model;

    public TileView(GraphicEntityModule entityModule, TooltipModule tooltipModule, TileModel tile) {
        super(entityModule);
        this.tooltipModule = tooltipModule;
        this.model = tile;
        this.tileItem = model.getItem();
        tile.addObserver(this);
        createTileView();
        tooltipModule.registerEntity(group.getId(), new HashMap<>());
    }

    private void createTileView() {
        background = entityModule.createSprite()
                .setImage("tile_background.png")
                .setAnchor(0.5)
                .setZIndex(0);
        group = entityModule.createGroup()
                .setScale(1);
        group.add(background);

        addDirections();
        addItem();
    }

    private void addDirections() {
        if (model.hasDirection(Constants.Direction.UP))
            addUp();
        if (model.hasDirection(Constants.Direction.RIGHT))
            addRight();
        if (model.hasDirection(Constants.Direction.DOWN))
            addDown();
        if (model.hasDirection(Constants.Direction.LEFT))
            addLeft();
    }

    private void addUp() {
        up = entityModule.createSprite()
                .setImage("tile_path.png")
                .setAnchor(0.5)
                .setRotation(Math.toRadians(0))
                .setZIndex(1);
        group.add(up);
    }

    private void addRight() {
        right = entityModule.createSprite()
                .setImage("tile_path.png")
                .setAnchor(0.5)
                .setRotation(Math.toRadians(90))
                .setZIndex(1);
        group.add(right);
    }

    private void addDown() {
        down = entityModule.createSprite()
                .setImage("tile_path.png")
                .setAnchor(0.5)
                .setRotation(Math.toRadians(180))
                .setZIndex(1);
        group.add(down);
    }

    private void addLeft() {
        left = entityModule.createSprite()
                .setImage("tile_path.png")
                .setAnchor(0.5)
                .setRotation(Math.toRadians(270))
                .setZIndex(1);
        group.add(left);
    }

    private void addItem() {
        if (tileItem != null) {
            String itemsPath = "items" + System.getProperty("file.separator") + "item_%s_%d.png";
            String spritePath = String.format(itemsPath, tileItem.getName(), tileItem.getPlayerId());
            item = entityModule.createSprite()
                .setImage(spritePath)
                .setAnchor(0.5)
                .setZIndex(2);
            group.add(item);
        }
    }

    private void removeItem() {
        this.item.setVisible(false);
        group.remove(this.item);
    }

    public void updateView(){
        if (model.getPlayerId() != null) {
            Vector2 pos = Constants.TILE_POSITIONS.get(model.getPlayerId());
            group.setX(pos.getX()).setY(pos.getY());
        } else {
            setMapPos(group, model.getPos());
        }
        tooltipModule.updateExtraTooltipText(group, model.getPos().toString());
    }

    public void update(Observable observable, Object update) {
        super.update(model, update);
        if (update instanceof RemoveItemUpdate)
            removeItem();
    }

    public Entity getEntity() {
        return group;
    }
}
