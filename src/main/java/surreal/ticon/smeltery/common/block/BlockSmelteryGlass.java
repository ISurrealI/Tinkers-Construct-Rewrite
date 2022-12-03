package surreal.ticon.smeltery.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import surreal.ticon.api.block.IHasSubtype;
import surreal.ticon.config.FillerConfig;
import surreal.ticon.smeltery.common.tile.TileTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BlockSmelteryGlass extends BlockBreakable implements IHasSubtype {
    public static final PropertyEnum<EnumSmelteryGlass> TYPE = PropertyEnum.create("type", EnumSmelteryGlass.class);

    public BlockSmelteryGlass() {
        super(Material.ROCK, false);
        setHardness(3F).setResistance(20F);
        setSoundType(SoundType.METAL);
        setDefaultState(getDefaultState().withProperty(TYPE, EnumSmelteryGlass.TANK));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null)
            return FluidUtil.interactWithFluidHandler(playerIn, hand, tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing));

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null) {
            FluidTank tank = ((TileTank) tile).getTank();
            if (tank.getFluidAmount() > 0) {
                FluidStack fs = tank.getFluid();
                return fs.getFluid().getLuminosity(fs);
            }
        }

        return 0;
    }

    @Override
    public boolean hasComparatorInputOverride(@Nonnull IBlockState state) {
        return state.getValue(TYPE) != EnumSmelteryGlass.GLASS;
    }

    @Override
    public int getComparatorInputOverride(@Nonnull IBlockState blockState, @Nonnull World worldIn, @Nonnull BlockPos pos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null) {
            FluidTank tank = ((TileTank) tile).getTank();
            return 15 * tank.getFluidAmount() / tank.getCapacity();
        }

        return 0;
    }

    // Item

    @Override
    public boolean removedByPlayer(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {
        this.onBlockHarvested(world, pos, state, player);
        if(willHarvest) this.harvestBlock(world, player, pos, state, world.getTileEntity(pos), player.getHeldItemMainhand());
        world.setBlockToAir(pos);
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityLivingBase placer, @Nonnull ItemStack stack) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null && stack.hasTagCompound()) {
            FluidTank tank = ((TileTank) tile).getTank();
            tank.readFromNBT(stack.getTagCompound());
        }
    }

    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune) {
        TileEntity tile = FillerConfig.tankRetain ? world.getTileEntity(pos) : null;

        if (tile != null) {
            FluidTank tank = ((TileTank) tile).getTank();
            if (tank.getFluidAmount() > 0) {
                NBTTagCompound compound = new NBTTagCompound();
                tank.writeToNBT(compound);
                ItemStack stack = new ItemStack(this, 1, damageDropped(state));
                stack.setTagCompound(compound);
                drops.add(stack);
            }
        } else super.getDrops(drops, world, pos, state, fortune);
    }

    @Override
    public int damageDropped(@Nonnull IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return state.getValue(TYPE) != EnumSmelteryGlass.GLASS ? new TileTank() : super.createTileEntity(world, state);
    }

    @Override
    public boolean hasTileEntity(@Nonnull IBlockState state) {
        return state.getValue(TYPE) != EnumSmelteryGlass.GLASS;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, EnumSmelteryGlass.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        for (int i = 0; i < TYPE.getAllowedValues().size(); i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    public enum EnumSmelteryGlass implements IStringSerializable {
        TANK,
        GAUGE,
        WINDOW,
        GLASS;

        @Nonnull
        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
