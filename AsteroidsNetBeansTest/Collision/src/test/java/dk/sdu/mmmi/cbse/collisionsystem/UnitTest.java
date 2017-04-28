package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.commonasteroid.Asteroid;
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

    private CollisionDetectionSystem collisionDetectionSystem;
    private Entity asteroid;
    private Entity player;
    private GameData gameData;
    private World world;

    @Before
    public void setUp() throws AWTException {
        collisionDetectionSystem = new CollisionDetectionSystem();

        asteroid = new Asteroid();
        asteroid.setRadius(5);
        asteroid.setPosition(0, 0);
        player = new Player();
        player.setPosition(100, 100);
        player.setRadius(5);
        player.setLife(1);

        gameData = mock(GameData.class);

        world = mock(World.class);
        List<Entity> asteroids = new ArrayList<>();
        asteroids.add(asteroid);
        when(world.getEntities(Asteroid.class)).thenReturn(asteroids);
        List<Entity> players = new ArrayList<>();
        players.add(player);
        when(world.getEntities(Player.class)).thenReturn(players);
        List<Entity> entities = asteroids;
        entities.addAll(players);
        when(world.getEntities()).thenReturn(entities);
    }

    @After
    public void tearDown() {
        collisionDetectionSystem = null;
        asteroid = null;
        player = null;
        gameData = null;
        world = null;
    }

    @Test
    public void testCollisionDetection() {
        assertFalse(collisionDetectionSystem.checkCollision(asteroid, player));
        player.setPosition(0, 0);
        assertTrue(collisionDetectionSystem.checkCollision(asteroid, player));
    }

    @Test
    public void testCollisionHandling() {
        assertTrue(1 == player.getLife());
        player.setPosition(0, 0);
        collisionDetectionSystem.process(gameData, world);
        assertTrue(0 == player.getLife());
    }

}
