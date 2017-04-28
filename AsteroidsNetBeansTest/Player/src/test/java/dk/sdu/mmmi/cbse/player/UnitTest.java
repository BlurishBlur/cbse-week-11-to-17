package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Niels
 */
public class UnitTest {
    
    private PlayerControlSystem playerControlSystem;
    private Entity player;
    private GameData gameData;
    private World world;
    
    @Before
    public void setUp() throws AWTException {
        playerControlSystem = new PlayerControlSystem();
        
        gameData = mock(GameData.class);
        when(gameData.getDisplayWidth()).thenReturn(0);
        when(gameData.getDisplayHeight()).thenReturn(0);
        when(gameData.getDelta()).thenReturn(60f / 3600f);
        
        GameKeys keys = new GameKeys();
        when(gameData.getKeys()).thenReturn(keys);
        
        player = playerControlSystem.createPlayerShip(gameData);
        
        world = mock(World.class);
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        when(world.getEntities(Player.class)).thenReturn(entities);
    }
    
    @After
    public void tearDown() {
        playerControlSystem = null;
        player = null;
        gameData = null;
        world = null;
    }
    
    @Test
    public void testPlayerMovement() {
        assertTrue(0 == player.getX());
        assertTrue(0 == player.getY());
        gameData.getKeys().setKey(GameKeys.UP, true);
        gameData.getKeys().setKey(GameKeys.RIGHT, true);
        playerControlSystem.process(gameData, world);
        gameData.getKeys().setKey(GameKeys.UP, false);
        gameData.getKeys().setKey(GameKeys.RIGHT, false);
        assertTrue(0 < player.getY());
        assertTrue(0 < player.getX());
    }
    
}
