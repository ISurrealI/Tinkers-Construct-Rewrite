package surreal.ticon.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import surreal.ticon.smeltery.client.tesr.TankTESR;
import surreal.ticon.smeltery.common.tile.TileTank;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileTank.class, new TankTESR());
    }
}
