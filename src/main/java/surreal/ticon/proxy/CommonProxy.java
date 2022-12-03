package surreal.ticon.proxy;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import surreal.ticon.TinkersConstruct;
import surreal.ticon.smeltery.common.tile.TileTank;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        registerTileEntities();
    }

    public void registerModel(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    public void registerModel(Item item, ItemMeshDefinition definition) {
        ModelLoader.setCustomMeshDefinition(item, definition);
    }

    private void registerTileEntities() {
        GameRegistry.registerTileEntity(TileTank.class, new ResourceLocation(TinkersConstruct.MODID, "tank"));
    }
}
