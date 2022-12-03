package surreal.ticon.smeltery.common.block;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import surreal.ticon.TinkersConstruct;
import surreal.ticon.api.block.IHasSubtype;

import javax.annotation.Nonnull;

public abstract class BlockSearedSlab2 extends BlockSlab implements IHasSubtype {
    public static final PropertyEnum<BlockSeared.EnumSeared> TYPE = PropertyEnum.create("type", BlockSeared.EnumSeared.class, input -> input.ordinal() > 7);

    public BlockSearedSlab2() {
        super(Material.ROCK);
        setHardness(3F).setResistance(20F);
        setSoundType(SoundType.METAL);

        setDefaultState(getDefaultState().withProperty(TYPE, BlockSeared.EnumSeared.CREEPERFACE));
    }

    @Nonnull
    @Override
    public String getTranslationKey(int metadata) {
        return "slab." + TinkersConstruct.MODID + ".seared." + (8 + metadata);
    }

    @Nonnull
    @Override
    public IProperty<?> getVariantProperty() {
        return TYPE;
    }

    @Nonnull
    @Override
    public Comparable<?> getTypeForItem(@Nonnull ItemStack stack) {
        return BlockSeared.EnumSeared.values()[(stack.getMetadata() & 7) | 8];
    }

    private PropertyEnum<BlockSeared.EnumSeared> property(int meta) {
        return TYPE;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return isDouble() ? new BlockStateContainer(this, TYPE) : new BlockStateContainer(this, TYPE, HALF);
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        int i = state.getValue(TYPE).ordinal() & 7;
        if (!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP) i |= 8;

        return i;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(TYPE, BlockSeared.EnumSeared.values()[(meta & 7) | 8]);

        if (!this.isDouble())
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);

        return iblockstate;
    }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        for (int i = 0; i < TYPE.getAllowedValues().size(); i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    public static class HalfSlab extends BlockSearedSlab2 {
        @Override
        public boolean isDouble() {
            return false;
        }
    }

    public static class DoubleSlab extends BlockSearedSlab2 {
        @Override
        public boolean isDouble() {
            return true;
        }
    }
}
