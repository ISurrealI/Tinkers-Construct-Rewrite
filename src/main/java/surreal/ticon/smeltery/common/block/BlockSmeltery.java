package surreal.ticon.smeltery.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import surreal.ticon.api.block.IHasSubtype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockSmeltery extends Block implements IHasSubtype {
    public static final PropertyEnum<EnumSmeltery> TYPE = PropertyEnum.create("type", EnumSmeltery.class);

    public BlockSmeltery() {
        super(Material.ROCK);
        setHardness(3F).setResistance(20F);
        setSoundType(SoundType.METAL);
        setDefaultState(getDefaultState().withProperty(TYPE, EnumSmeltery.SMELTERY));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return super.createTileEntity(world, state);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, EnumSmeltery.values()[meta]);
    }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        for (int i = 0; i < TYPE.getAllowedValues().size(); i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    public enum EnumSmeltery implements IStringSerializable {
        SMELTERY,
        FURNACE,
        TINKER_TANK,
        FAUCET,
        CHANNEL,
        TABLE,
        BASIN,
        DRAIN;

        @Nonnull
        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
