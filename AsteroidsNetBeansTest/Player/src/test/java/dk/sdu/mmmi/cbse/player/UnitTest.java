package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
    private Robot robot;
    private GameData gameData;
    private World world;
    
    @Before
    public void setUp() throws AWTException {
        playerControlSystem = new PlayerControlSystem();
        
        gameData = mock(GameData.class);
        when(gameData.getDisplayWidth()).thenReturn(0);
        when(gameData.getDisplayHeight()).thenReturn(0);
        
        when(gameData.getDelta()).thenReturn(60f / 3600f);
        
        player = playerControlSystem.createPlayerShip(gameData);
        
        world = mock(World.class);
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        when(world.getEntities(Player.class)).thenReturn(entities);
        
        robot = new Robot();
    }
    
    @After
    public void tearDown() {
        
    }
    
    @Test
    public void testPlayerMove() {
        assertTrue(0 == player.getY());
        robot.keyPress(KeyEvent.VK_W);
        playerControlSystem.process(gameData, world);
        robot.keyRelease(KeyEvent.VK_W);
        assertTrue(0 < player.getY());
    }
    
}
