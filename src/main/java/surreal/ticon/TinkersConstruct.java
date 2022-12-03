package surreal.ticon;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import surreal.ticon.api.item.IItemModel;
import surreal.ticon.init.ModBlocks;
import surreal.ticon.init.ModItems;
import surreal.ticon.proxy.CommonProxy;

import javax.annotation.Nonnull;

@Mod(modid = TinkersConstruct.MODID, name = TinkersConstruct.NAME, version = TinkersConstruct.VERSION)
public class TinkersConstruct {
    public static final String MODID = "ticon";
    public static final String NAME = "Tinkers' Construct";
    public static final String VERSION = "1.0";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final CreativeTabs TAB = new CreativeTabs(MODID) {
        @Nonnull
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.WHEAT);
        }
    };

    @SidedProxy(serverSide = "surreal.ticon.proxy.CommonProxy", clientSide = "surreal.ticon.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void construction(FMLConstructionEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @SubscribeEvent
    public void registerBlock(RegistryEvent.Register<Block> event) {
        ModBlocks.register(event.getRegistry());
    }

    @SubscribeEvent
    public void registerItem(RegistryEvent.Register<Item> event) {
        ModItems.register(event.getRegistry());
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        for (Item item : ModItems.ITEMS) {
            ((IItemModel) item).registerModel();
        }
    }
}
