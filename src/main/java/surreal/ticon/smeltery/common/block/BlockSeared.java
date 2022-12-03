package surreal.ticon.smeltery.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import surreal.ticon.api.block.IHasSubtype;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockSeared extends Block implements IHasSubtype {
    public static PropertyEnum<EnumSeared> TYPE = PropertyEnum.create("type", EnumSeared.class);

    public BlockSeared() {
        super(Material.ROCK);
        setDefaultState(getDefaultState().withProperty(TYPE, EnumSeared.STONE));
        setHardness(3F).setResistance(20F);
        setSoundType(SoundType.METAL);
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, EnumSeared.values()[meta]);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        for (int i = 0; i < EnumSeared.values().length; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    public enum EnumSeared implements IStringSerializable {
        STONE,
        COBBLESTONE,
        BALLER,
        BRICKS,
        BRICKS_CRACKED,
        BRICKS_FANCY,
        BRICKS_SQUARE,
        ROAD,
        CREEPERFACE,
        BRICKS_TRIANGLE,
        BRICKS_SMALL,
        TILES;

        @Nonnull
        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
