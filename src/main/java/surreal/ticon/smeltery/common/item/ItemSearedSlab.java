package surreal.ticon.smeltery.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemSlab;
import surreal.ticon.TinkersConstruct;
import surreal.ticon.api.item.IItemModel;
import surreal.ticon.api.util.ModelHelper;

public class ItemSearedSlab extends ItemSlab implements IItemModel {
    public ItemSearedSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
        super(block, singleSlab, doubleSlab);
    }

    @Override
    public void registerModel() {
        TinkersConstruct.proxy.registerModel(this, stack -> new ModelResourceLocation(block.getRegistryName(), ModelHelper.getVariantFromState(block.getStateFromMeta(stack.getMetadata()))));
    }
}
