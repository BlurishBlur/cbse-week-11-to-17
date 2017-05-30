package dk.sdu.mmmi.cbse.osgiasteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonasteroid.Asteroid;
import dk.sdu.mmmi.cbse.commonasteroid.IAsteroidSplitter;
/**
 *
 * @author Niels
 */
public class AsteroidControlSystem implements IEntityProcessingService {

    private IAsteroidSplitter asteroidSplitter;

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
                        Entity newAsteroid = asteroidSplitter.createSplitAsteroid(asteroid);
                        world.addEntity(newAsteroid);
                        //asteroids.add(newAsteroid);
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

    public void setAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = asteroidSplitter;
    }

    public void removeAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = null;
        System.out.println("AsteroidSplitter remove");
    }

    
}
