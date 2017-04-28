package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.events.Event;
import dk.sdu.mmmi.cbse.common.events.EventType;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonasteroid.Asteroid;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class)
})
/**
 *
 * @author Niels
 */
public class CollisionDetectionSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            for (Entity collider : world.getEntities(Player.class)) {
                if (checkCollision(collider, asteroid)) {
                    collider.setLife(0);
                }
            }
            for (Entity collider : world.getEntities(Enemy.class)) {
                if (checkCollision(collider, asteroid)) {
                    collider.setLife(0);
                }
            }
            for (Entity bullet : world.getEntities(Bullet.class)) {
                if (checkCollision(bullet, asteroid)) {
                    gameData.addEvent(new Event(EventType.ASTEROID_SPLIT, asteroid.getID()));
                    asteroid.setLife(asteroid.getLife() - 1);
                    bullet.setExpiration(0);
                }
            }
        }
        for (Entity bullet : world.getEntities(Bullet.class)) {
            for (Entity entity : world.getEntities(Player.class)) {
                if (checkCollision(bullet, entity)) {
                    bullet.setExpiration(0);
                    entity.setLife(entity.getLife() - 1);
                }
            }
            for (Entity entity : world.getEntities(Enemy.class)) {
                if (checkCollision(bullet, entity)) {
                    bullet.setExpiration(0);
                    entity.setLife(entity.getLife() - 1);
                }
            }
        }
        for (Entity entity : world.getEntities()) {
            if (entity.getX() < 0) {
                entity.setX(gameData.getDisplayWidth());
            }
            if (entity.getX()
                    > gameData.getDisplayWidth()) {
                entity.setX(0);
            }
            if (entity.getY()
                    < 0) {
                entity.setY(gameData.getDisplayHeight());
            }
            if (entity.getY()
                    > gameData.getDisplayHeight()) {
                entity.setY(0);
            }
        }
    }

    public boolean checkCollision(Entity entity1, Entity entity2) {
        float a = entity1.getX() - entity2.getX();
        float b = entity1.getY() - entity2.getY();

        double c = Math.sqrt((double) (a * a) + (double) (b * b));

        return c < entity1.getRadius() + entity2.getRadius();

        //return Math.sqrt(Math.pow(entity1.getX() - entity2.getX(), 2) + Math.pow(entity1.getY() - entity2.getY(), 2)) < entity1.getRadius() + entity2.getRadius();
    }

}
