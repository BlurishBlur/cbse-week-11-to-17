package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)
    ,
    @ServiceProvider(service = IGamePluginService.class)
})
public class PlayerControlSystem implements IEntityProcessingService, IGamePluginService {
    
    private Entity player;
    private IEntityProcessingService processor;
    
    public PlayerControlSystem() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans-whiteboard.xml");
        processor = (IEntityProcessingService) context.getBean("playerProcessor");
    }
    
    /*public void setProc(IEntityProcessingService processor) {
        this.processor = processor;
        System.out.println("proc set");
    }*/
    
    @Override
    public void start(GameData gameData, World world) {
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    @Override
    public void process(GameData gameData, World world) {
        processor.process(gameData, world);
    }
    
    private Entity createPlayerShip(GameData gameData) {
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

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }

}
