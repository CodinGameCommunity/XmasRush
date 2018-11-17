package com.codingame.game;

import com.codingame.game.Model.PlayerModel;
import com.codingame.game.Utils.Constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testCreateGetModel() {
        Player player = new Player();
        int id = player.getIndex();
        PlayerModel model = player.createPlayer();
        assertEquals(id, model.id);
        assertEquals(model, player.getPlayer());
    }

    @Test
    public void testGetModel() {
        Player player = new Player();
        int id = player.getIndex();
        assertEquals(id, player.createPlayer().id);
    }
}