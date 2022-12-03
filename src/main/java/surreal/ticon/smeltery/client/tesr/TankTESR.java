package surreal.ticon.smeltery.client.tesr;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import surreal.ticon.TinkersConstruct;
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
            renderTank(state, tank, buffer, x, y, z);
        }
    }

    private void renderTank(IBlockState state, FluidTank tank, BufferBuilder builder, double x, double y, double z) {
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

        if (renderBottom) RenderingHelper.renderCuboid(sprite, builder, x, y, z, w, h, fluidColor);
        else {
            if (renderTop) RenderingHelper.renderPlanes(sprite, builder, x, y, z, w, h, fluidColor, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.UP);
            else RenderingHelper.renderPlanes(sprite, builder, x, y, z, w, h, fluidColor, EnumFacing.HORIZONTALS);
        }
    }
}
