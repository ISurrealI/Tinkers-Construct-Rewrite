package surreal.ticon.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RenderingHelper {
    static final Minecraft MC = Minecraft.getMinecraft();
    public static final int SKYLIGHT = 240, BLOCKLIGHT = 0;
    public static final int WHITE = 0xFFFFFF;

    public static TextureAtlasSprite getSprite(ResourceLocation location) {
        return MC.getTextureMapBlocks().getAtlasSprite(location.toString());
    }

    public static void renderCuboid(TextureAtlasSprite sprite, BufferBuilder builder, double x, double y, double z, float w, float h, int color, int sl, int bl) {
        renderPlanes(sprite, builder, x, y, z, w, h, color, sl, bl, EnumFacing.values());
    }

    public static void renderPlanes(TextureAtlasSprite sprite, BufferBuilder builder, double x, double y, double z, float w, float h, int color, EnumFacing... facings) {
        for (EnumFacing f : facings) {
            renderPlane(sprite, builder, x, y, z, w, h, color, f);
        }
    }

    public static void renderPlanes(TextureAtlasSprite sprite, BufferBuilder builder, double x, double y, double z, float w, float h, int color, int sl, int bl, EnumFacing... facings) {
        for (EnumFacing f : facings) {
            renderPlane(sprite, builder, x, y, z, w, h, color, sl, bl, f);
        }
    }

    public static  void renderPlane(TextureAtlasSprite sprite, BufferBuilder builder, double x, double y, double z, float w, float h, int color, EnumFacing facing) {
        renderPlane(sprite, builder, x, y, z, w, h, color, SKYLIGHT, BLOCKLIGHT, facing);
    }

    public static void renderPlane(TextureAtlasSprite sprite, BufferBuilder builder, double x, double y, double z, float w, float h, int color, int sl, int bl, EnumFacing facing) {
        MC.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        switch (facing.getAxis()) {
            case X:
                if (facing == EnumFacing.WEST) z += w;
                addVertex(builder, x, y, z, sprite.getMaxU(), sprite.getMaxV(), color, sl, bl);
                addVertex(builder, x+w, y, z, sprite.getMinU(), sprite.getMaxV(), color, sl, bl);
                addVertex(builder, x+w, y+h, z, sprite.getMinU(), sprite.getInterpolatedV(16 - (16*h)), color, sl, bl);
                addVertex(builder, x, y+h, z, sprite.getMaxU(), sprite.getInterpolatedV(16 - (16*h)), color, sl, bl);
                break;
            case Y:
                if (facing == EnumFacing.UP) y += h;
                addVertex(builder, x, y, z, sprite.getMaxU(), sprite.getMinV(), color, sl, bl);
                addVertex(builder, x+w, y, z, sprite.getMaxU(), sprite.getMaxV(), color, sl, bl);
                addVertex(builder, x+w, y, z+w, sprite.getMinU(), sprite.getMaxV(), color, sl, bl);
                addVertex(builder, x, y, z+w, sprite.getMinU(), sprite.getMinV(), color, sl, bl);
                break;
            case Z:
                if (facing == EnumFacing.SOUTH) x += w;
                addVertex(builder, x, y, z, sprite.getMinU(), sprite.getMaxV(), color, sl, bl);
                addVertex(builder, x, y, z+w, sprite.getMaxU(), sprite.getMaxV(), color, sl, bl);
                addVertex(builder, x, y+h, z+w, sprite.getMaxU(), sprite.getInterpolatedV(16 - (16*h)), color, sl, bl);
                addVertex(builder, x, y+h, z, sprite.getMinU(), sprite.getInterpolatedV(16 - (16*h)), color, sl, bl);
                break;
        }
    }

    static void addVertex(BufferBuilder builder, double x, double y, double z, float u, float v, int color, int sl, int bl) {
        builder.pos(x, y, z).color(red(color), green(color), blue(color), alpha(color)).tex(u, v).lightmap(sl, bl).endVertex();
    }

    public static int red(int c) {
        int max = 0xFF;
        if (c >= 0) return (c >> 16) & max;
        return max;
    }

    public static int green(int c) {
        int max = 0xFF;
        if (c >= 0) return (c >> 8) & max;
        return max;
    }

    public static int blue(int c) {
        int max = 0xFF;
        if (c >= 0) return c & max;
        return max;
    }

    public static int alpha(int c) {
        int max = 0xFF;
        if (c > 0xFFFFFF) return (c >> 24) & max;
        return max;
    }
}
