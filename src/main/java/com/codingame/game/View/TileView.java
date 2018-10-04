package com.codingame.game.View;

import com.codingame.game.Item;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Utils;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

import static com.codingame.game.Utils.Utils.graphicEntityModule;

public class TileView {
    private Group group;

    private Sprite background;
    private Sprite up;
    private Sprite right;
    private Sprite down;
    private Sprite left;
    private Sprite item;

    public TileView() {
        background = graphicEntityModule.createSprite()
                .setImage("tile_background.png")
                .setAnchor(0.5)
                .setZIndex(Constants.MapLayers.BACKGROUND.asValue());
        graphicEntityModule.commitEntityState(0, background);
        group = graphicEntityModule.createGroup()
                .setScale(1);
        group.add(background);
    }

    public void addUp() {
        if (up != null) throw  new RuntimeException();
        up = graphicEntityModule.createSprite()
                .setImage("tile_path.png")
                .setAnchor(0.5)
                .setRotation(Math.toRadians(0))
                .setZIndex(Constants.MapLayers.TILES.asValue());
        graphicEntityModule.commitEntityState(0, up);
        group.add(up);
    }

    public void addRight() {
        if (right != null) throw  new RuntimeException();
        right = graphicEntityModule.createSprite()
                .setImage("tile_path.png")
                .setAnchor(0.5)
                .setRotation(Math.toRadians(90))
                .setZIndex(Constants.MapLayers.TILES.asValue());
        graphicEntityModule.commitEntityState(0, right);
        group.add(right);
    }

    public void addDown() {
        if (down != null) throw  new RuntimeException();
        down = graphicEntityModule.createSprite()
                .setImage("tile_path.png")
                .setAnchor(0.5)
                .setRotation(Math.toRadians(180))
                .setZIndex(Constants.MapLayers.TILES.asValue());
        graphicEntityModule.commitEntityState(0, down);
        group.add(down);
    }

    public void addLeft() {
        if (left != null) throw  new RuntimeException();
        left = graphicEntityModule.createSprite()
                .setImage("tile_path.png")
                .setAnchor(0.5)
                .setRotation(Math.toRadians(270))
                .setZIndex(Constants.MapLayers.TILES.asValue());
        graphicEntityModule.commitEntityState(0, left);
        group.add(left);
    }

    public void addItem(Item item) {
        String itemsPath = "items" + System.getProperty("file.separator") + "item_%s_%d.png";
        String spritePath = String.format(itemsPath, item.getName(), item.getPlayerId());
        this.item = graphicEntityModule.createSprite()
                .setImage(spritePath)
                .setAnchor(0.5)
                .setZIndex(Constants.MapLayers.ITEMS.asValue());
        graphicEntityModule.commitEntityState(0, this.item);
        group.add(this.item);
    }

    public void removeItem() {
        this.item.setVisible(false);
        group.remove(this.item);
    }

    public void setPosInMap(Vector2 pos, double time) {
        Utils.setPosInMap(group, pos, time);
    }

    public void setSamePosInMap(double time) {
        group.setX(group.getX() + 1);

        graphicEntityModule.commitEntityState(time, group);
    }

    public void setPosAbsolute(Vector2 pos, double time) {
        group.setX(pos.getX()).setY(pos.getY());

        graphicEntityModule.commitEntityState(time, group);
    }

    public void setSamePosAbsolute(double time) {
        group.setX(group.getX() + 1);

        graphicEntityModule.commitEntityState(time, group);
    }

    public void rotate(int numTimes) {
        for (int  i = 0; i < numTimes; i++) {
            Sprite temp = up;
            up = left;
            left = down;
            down = right;
            right = temp;
        }
        double angle = Math.toRadians(90 * numTimes);
        if (up != null) up.setRotation(up.getRotation() + angle);
        if (right != null) right.setRotation(right.getRotation() + angle);
        if (down != null) down.setRotation(down.getRotation() + angle);
        if (left != null) left.setRotation(left.getRotation() + angle);
    }

    public void init(TileModel tile) {
        if (tile.hasUp()) addUp();
        if (tile.hasRight()) addRight();
        if (tile.hasDown()) addDown();
        if (tile.hasLeft()) addLeft();
        if (tile.hasItem()) addItem(tile.item);

        graphicEntityModule.commitEntityState(0, group);
    }
}
