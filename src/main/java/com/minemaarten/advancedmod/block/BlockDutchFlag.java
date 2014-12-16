package com.minemaarten.advancedmod.block;

import com.minemaarten.advancedmod.reference.Reference;
import com.minemaarten.advancedmod.utility.Names;

public class BlockDutchFlag extends BlockAdvancedMod{
    public BlockDutchFlag(){
        setBlockName(Names.Blocks.DUTCH_FLAG);
        setBlockTextureName(Reference.MOD_ID_LOWER + ":" + Names.Blocks.DUTCH_FLAG);
    }
}
