package surreal.ticon.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class TiconBlockStairs extends BlockStairs {
    public TiconBlockStairs(IBlockState modelState) {
        super(modelState);
    }

    public TiconBlockStairs(Block block, int meta) {
        super(block.getStateFromMeta(meta));
    }
}
