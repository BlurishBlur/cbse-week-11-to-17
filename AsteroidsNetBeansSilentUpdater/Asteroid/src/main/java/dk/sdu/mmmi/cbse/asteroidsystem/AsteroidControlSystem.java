package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonasteroid.Asteroid;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)
    ,
    @ServiceProvider(service = IGamePluginService.class)
})
/**
 *
 * @author Niels
 */
public class AsteroidControlSystem implements IEntityProcessingService, IGamePluginService {
    
    private List<Entity> asteroids = new ArrayList<>();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            float dt = gameData.getDelta();
            // set position
            asteroid.setRadians(asteroid.getRadians() + asteroid.getRotationSpeed() * dt);

            asteroid.setDx(asteroid.getDx() + (float) Math.cos(asteroid.getRadians()) * dt);
            asteroid.setDy(asteroid.getDy() + (float) Math.sin(asteroid.getRadians()) * dt);

            asteroid.setX(asteroid.getX() + asteroid.getDx() * dt);
            asteroid.setY(asteroid.getY() + asteroid.getDy() * dt);

            for (Event event : gameData.getEvents(EventType.ASTEROID_SPLIT, asteroid.getID())) {
                if (asteroid.getLife() > 0) {
                    for (int i = 0; i < 2; i++) {
                        Entity splitAsteroid = createSplitAsteroid(asteroid);
                        world.addEntity(splitAsteroid);
                    }
                }
                world.removeEntity(asteroid);
                gameData.removeEvent(event);
            }

            // set shape
            setShape(asteroid);
        }
    }

    private void setShape(Entity asteroid) {
        int size = (int) (asteroid.getRadius() * 0.6f) + 1;
        float[] shapex = new float[size];
        float[] shapey = new float[size];
        float angle = 0;

        for (int i = 0; i < size; i++) {
            shapex[i] = asteroid.getX() + (float) (Math.cos(angle + asteroid.getRadians()) * asteroid.getDists()[i]);
            shapey[i] = asteroid.getY() + (float) (Math.sin(angle + asteroid.getRadians()) * asteroid.getDists()[i]);
            angle += (3.1415f * 2) / size;
        }

        asteroid.setShapeX(shapex);
        asteroid.setShapeY(shapey);
    }
    
    public Entity createSplitAsteroid(Entity parent) {
        Entity asteroid = new Asteroid();
        asteroid.setPosition(parent.getX(), parent.getY());
        asteroid.setRadius(parent.getRadius() / 2);
        asteroid.setLife(1);
        asteroid.setMaxSpeed(((float) Math.random() * 120) + 10);
        asteroid.setRadians((float) Math.random() * 3.1415f * 2);
        asteroid.setRotationSpeed((int) (Math.random() * 3) - 1); //-1, 0 eller 1
        asteroid.setDx((float) Math.cos(asteroid.getRadians()) * asteroid.getMaxSpeed());
        asteroid.setDy((float) Math.sin(asteroid.getRadians()) * asteroid.getMaxSpeed());
        int size = (int) (asteroid.getRadius() / 2) + 5;
        float[] dists = new float[size];
        for (int i = 0; i < size; i++) {
            dists[i] = (float) (Math.random() * (asteroid.getRadius() / 2)) + (asteroid.getRadius() / 2);
        }
        asteroid.setDists(dists);
        return asteroid;
    }
    
    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid;
        for (Event event : gameData.getEvents()) {
            if (event.getType().equals(EventType.ASTEROID_SPLIT)) { // creates two new, smaller asteroids
                for (int i = 0; i < 2; i++) {
                    asteroid = createSplittingAsteroid(world.getEntity(event.getEntityID()));
                    world.addEntity(asteroid);
                    asteroids.add(asteroid);
                }
                return;
            }
        }
        for (int i = 0; i < (int) (Math.random() * 5) + 1; i++) { // creates a random amount of asteroids between 1 and 6, at the start
            asteroid = createWholeAsteroid(gameData);
            world.addEntity(asteroid);
            asteroids.add(asteroid);
        }
    }

    private Entity createSplittingAsteroid(Entity parent) {
        Entity asteroid = new Entity();
        asteroid.setPosition(parent.getX(), parent.getY());
        asteroid.setRadius(parent.getRadius() / 2);
        asteroid.setLife(1);
        setAsteroidAttributes(asteroid);
        return asteroid;
    }

    private Entity createWholeAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        asteroid.setPosition((float) Math.random() * gameData.getDisplayWidth(), (float) Math.random() * gameData.getDisplayHeight());
        asteroid.setRadius(((int) (Math.random() * 10) + 10));
        asteroid.setLife(2);
        setAsteroidAttributes(asteroid);
        return asteroid;
    }

    private void setAsteroidAttributes(Entity asteroid) {
        asteroid.setMaxSpeed(((float) Math.random() * 120) + 10);
        asteroid.setRadians((float) Math.random() * 3.1415f * 2);
        asteroid.setRotationSpeed((int) (Math.random() * 3) - 1); //-1, 0 eller 1
        asteroid.setDx((float) Math.cos(asteroid.getRadians()) * asteroid.getMaxSpeed());
        asteroid.setDy((float) Math.sin(asteroid.getRadians()) * asteroid.getMaxSpeed());
        int size = (int) (asteroid.getRadius() / 2) + 5;
        float[] dists = new float[size];
        for (int i = 0; i < size; i++) {
            dists[i] = (float) (Math.random() * (asteroid.getRadius() / 2)) + (asteroid.getRadius() / 2);
        }
        asteroid.setDists(dists);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : asteroids) {
            world.removeEntity(asteroid);
        }
    }
}
