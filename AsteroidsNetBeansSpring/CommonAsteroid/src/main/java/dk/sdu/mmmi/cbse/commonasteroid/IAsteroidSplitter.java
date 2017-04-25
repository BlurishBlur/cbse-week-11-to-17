package dk.sdu.mmmi.cbse.commonasteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Niels
 */
public interface IAsteroidSplitter {
    
    Entity createSplitAsteroid(Entity parent);
    
}
