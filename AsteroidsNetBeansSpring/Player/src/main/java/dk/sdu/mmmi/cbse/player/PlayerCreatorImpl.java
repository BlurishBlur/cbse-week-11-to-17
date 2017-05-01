package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.commonplayer.IPlayerCreator;
import dk.sdu.mmmi.cbse.commonplayer.Player;

/**
 *
 * @author Niels
 */
public class PlayerCreatorImpl implements IPlayerCreator {

    @Override
    public Entity create(GameData gameData) {
        Entity playerShip = new Player();

        playerShip.setPosition(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);

        playerShip.setMaxSpeed(300);
        playerShip.setAcceleration(200);
        playerShip.setDeacceleration(10);
        
        playerShip.setLife(3);
        
        playerShip.setRadius(5);

        playerShip.setRadians(3.1415f / 2);
        playerShip.setRotationSpeed(3);

        return playerShip;
    }
    
}
