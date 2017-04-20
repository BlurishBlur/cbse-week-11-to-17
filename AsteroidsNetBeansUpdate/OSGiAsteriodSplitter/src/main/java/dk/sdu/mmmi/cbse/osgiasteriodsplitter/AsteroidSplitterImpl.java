package dk.sdu.mmmi.cbse.osgiasteriodsplitter;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.commonasteroid.Asteroid;
import dk.sdu.mmmi.cbse.commonasteroid.IAsteroidSplitter;

/**
 *
 * @author Niels
 */
public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
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
    
}
