package surreal.ticon.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraftforge.registries.IForgeRegistry;
import surreal.ticon.TinkersConstruct;
import surreal.ticon.api.block.TiconBlockStairs;
import surreal.ticon.api.item.TiconItemBlock;
import surreal.ticon.smeltery.common.block.*;
import surreal.ticon.smeltery.common.item.ItemSearedSlab;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static List<Block> BLOCKS = new ArrayList<>();

    public static Block SEARED_STONE = register("seared_stone", new BlockSeared());
    public static Block SMELTERY = register("smeltery_block", new BlockSmeltery());
    public static Block SMELTERY_GLASS = register("smeltery_glass", new BlockSmelteryGlass());

    public static Block SEARED_SLAB = registerSlab("seared_slab", new BlockSearedSlab.HalfSlab(), new BlockSearedSlab.DoubleSlab());
    public static Block SEARED_SLAB2 = registerSlab("seared_slab_2", new BlockSearedSlab2.HalfSlab(), new BlockSearedSlab2.DoubleSlab());

    public static Block SEARED_STAIRS_STONE = register("seared_stairs_stone", new TiconBlockStairs(SEARED_STONE, 0));
    public static Block SEARED_STAIRS_COBBLESTONE = register("seared_stairs_cobblestone", new TiconBlockStairs(SEARED_STONE, 1));
    public static Block SEARED_STAIRS_BALLER = register("seared_stairs_baller", new TiconBlockStairs(SEARED_STONE, 2));
    public static Block SEARED_STAIRS_BRICKS = register("seared_stairs_bricks", new TiconBlockStairs(SEARED_STONE, 3));
    public static Block SEARED_STAIRS_BRICKS_CRACKED = register("seared_stairs_bricks_cracked", new TiconBlockStairs(SEARED_STONE, 4));
    public static Block SEARED_STAIRS_BRICKS_FANCY = register("seared_stairs_bricks_fancy", new TiconBlockStairs(SEARED_STONE, 5));
    public static Block SEARED_STAIRS_BRICKS_SQUARE = register("seared_stairs_bricks_square", new TiconBlockStairs(SEARED_STONE, 6));
    public static Block SEARED_STAIRS_ROAD = register("seared_stairs_road", new TiconBlockStairs(SEARED_STONE, 7));
    public static Block SEARED_STAIRS_CREEPERFACE = register("seared_stairs_creeperface", new TiconBlockStairs(SEARED_STONE, 8));
    public static Block SEARED_STAIRS_BRICKS_TRIANGLE = register("seared_stairs_bricks_triangle", new TiconBlockStairs(SEARED_STONE, 9));
    public static Block SEARED_STAIRS_BRICKS_SMALL = register("seared_stairs_bricks_small", new TiconBlockStairs(SEARED_STONE, 10));
    public static Block SEARED_STAIRS_TILES = register("seared_stairs_tiles", new TiconBlockStairs(SEARED_STONE, 11));

    public static <T extends Block> T register(String name, T block) {
        String translationKey = TinkersConstruct.MODID + "." + name;
        BLOCKS.add(block.setRegistryName(TinkersConstruct.MODID, name).setTranslationKey(translationKey).setCreativeTab(TinkersConstruct.TAB));
        ModItems.ITEMS.add(new TiconItemBlock(block));
        return block;
    }

    public static void register(IForgeRegistry<Block> registry) {
        BLOCKS.forEach(registry::register);
    }

    public static BlockSlab registerSlab(String name, BlockSlab slabHalf, BlockSlab slabDouble) {
        String translationKey = TinkersConstruct.MODID + "." + name;
        BLOCKS.add(slabHalf.setRegistryName(TinkersConstruct.MODID, name).setTranslationKey(translationKey).setCreativeTab(TinkersConstruct.TAB));
        BLOCKS.add(slabDouble.setRegistryName(TinkersConstruct.MODID, name + "_double").setTranslationKey(translationKey));
        ModItems.ITEMS.add(new ItemSearedSlab(slabHalf, slabHalf, slabDouble).setRegistryName(slabHalf.getRegistryName()));
        return slabHalf;
    }
}
