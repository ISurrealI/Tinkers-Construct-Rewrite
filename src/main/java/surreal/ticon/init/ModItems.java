package surreal.ticon.init;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import surreal.ticon.TinkersConstruct;
import surreal.ticon.api.item.IItemModel;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static List<Item> ITEMS = new ArrayList<>();

    public static <T extends Item> T register(String name, T item) {
        if (!(item instanceof IItemModel)) TinkersConstruct.LOGGER.error("Item " + item.getRegistryName() + " doesn't implement IItemModel!");
        ITEMS.add(item.setRegistryName(TinkersConstruct.MODID, name).setTranslationKey(TinkersConstruct.MODID + "." + name));
        return item;
    }

    public static void register(IForgeRegistry<Item> registry) {
        ITEMS.forEach(registry::register);
    }
}
