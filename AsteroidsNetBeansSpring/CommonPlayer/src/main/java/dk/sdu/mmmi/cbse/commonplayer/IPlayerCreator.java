package dk.sdu.mmmi.cbse.commonplayer;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Niels
 */
public interface IPlayerCreator {
    
    Entity create(GameData gameData);
    
}
