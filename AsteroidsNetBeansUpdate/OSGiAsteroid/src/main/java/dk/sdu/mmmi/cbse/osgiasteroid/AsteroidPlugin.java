/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgiasteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonasteroid.Asteroid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Niels
 */
public class AsteroidPlugin implements IGamePluginService {

    private List<Entity> asteroids = new ArrayList<>();

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < (int) (Math.random() * 5) + 1; i++) { // creates a random amount of asteroids between 1 and 6, at the start
            Entity asteroid = createWholeAsteroid(gameData);
            world.addEntity(asteroid);
            asteroids.add(asteroid);
        }
    }

    private Entity createWholeAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        asteroid.setPosition((float) Math.random() * gameData.getDisplayWidth(), (float) Math.random() * gameData.getDisplayHeight());
        asteroid.setRadius(((int) (Math.random() * 10) + 10));
        asteroid.setLife(2);
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
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : asteroids) {
            world.removeEntity(asteroid);
        }
    }

}
