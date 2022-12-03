package surreal.ticon.api.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import surreal.ticon.TinkersConstruct;
import surreal.ticon.api.block.IHasSubtype;
import surreal.ticon.api.util.ModelHelper;

import javax.annotation.Nonnull;

public class TiconItemBlock extends ItemBlock implements IItemModel {

    public TiconItemBlock(Block block) {
        super(block);
        this.setRegistryName(block.getRegistryName());
        this.setHasSubtypes(block instanceof IHasSubtype);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public void registerModel() {
        TinkersConstruct.proxy.registerModel(this, stack -> new ModelResourceLocation(block.getRegistryName(), ModelHelper.getVariantFromState(block.getStateFromMeta(stack.getMetadata()))));
    }

    @Nonnull
    @Override
    public String getTranslationKey(@Nonnull ItemStack stack) {
        String name = super.getTranslationKey(stack);
        if (hasSubtypes) name += "." + stack.getMetadata();
        return name;
    }
}
