package surreal.ticon.smeltery.client.tesr;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import surreal.ticon.api.util.RenderingHelper;
import surreal.ticon.smeltery.common.block.BlockSmelteryGlass;
import surreal.ticon.smeltery.common.tile.TileTank;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class TankTESR extends FastTESR<TileTank> {

    @Override
    public void renderTileEntityFast(TileTank te, double x, double y, double z, float partialTicks, int destroyStage, float partial, @Nonnull BufferBuilder buffer) {
        FluidTank tank = te.getTank();
        if (tank.getFluid() != null) {
            IBlockState state = te.getWorld().getBlockState(te.getPos());
            renderTank(te.getWorld(), te.getPos(), state, tank, buffer, x, y, z);
        }
    }

    private void renderTank(World world, BlockPos pos, IBlockState state, FluidTank tank, BufferBuilder builder, double x, double y, double z) {
        FluidStack fs = tank.getFluid();
        Fluid fluid = tank.getFluid().getFluid();
        int fluidColor = fluid.getColor(fs);

        TextureAtlasSprite sprite = RenderingHelper.getSprite(fluid.getStill(fs));
        float offset = 0.0005F;
        float h = ((float) tank.getFluidAmount() / tank.getCapacity()) - (offset * 2);
        float w = 1F - (offset * 2);

        boolean renderBottom = state.getValue(BlockSmelteryGlass.TYPE) != BlockSmelteryGlass.EnumSmelteryGlass.TANK;
        boolean renderTop = !renderBottom && h < 0.999;

        x += offset;
        y += offset;
        z += offset;

        if (fluid.isGaseous(fs)) {
            int r = RenderingHelper.red(fluidColor);
            int g = RenderingHelper.green(fluidColor);
            int b = RenderingHelper.blue(fluidColor);
            int a = (int) (RenderingHelper.alpha(fluidColor) * ((float) tank.getFluidAmount() / tank.getCapacity()));
            fluidColor = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8)  | (b & 0xFF);
            h = 1F - (offset * 2);
        } else if (fluid.getDensity(fs) <= 0)
            y += (1F - (offset * 2)) - h;

        int blockLight = MathHelper.clamp((state.getPackedLightmapCoords(world, pos) & 0xFFFF) + fluid.getLuminosity(world, pos), 0, 240);

        if (renderBottom) RenderingHelper.renderCuboid(sprite, builder, x, y, z, w, h, fluidColor, RenderingHelper.SKYLIGHT, blockLight);
        else {
            if (renderTop)
                RenderingHelper.renderPlanes(sprite, builder, x, y, z, w, h, fluidColor, RenderingHelper.SKYLIGHT, blockLight, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.UP);
            else RenderingHelper.renderPlanes(sprite, builder, x, y, z, w, h, fluidColor, RenderingHelper.SKYLIGHT, blockLight, EnumFacing.HORIZONTALS);
        }
    }
}
