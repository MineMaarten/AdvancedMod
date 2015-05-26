package com.minemaarten.advancedmod.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.minemaarten.advancedmod.reference.Reference;
import com.minemaarten.advancedmod.tileentity.TileEntityTreeFarm;
import com.minemaarten.advancedmod.utility.Names;

public class BlockTreeFarm extends BlockAdvancedModTileEntity{

    public BlockTreeFarm(){
        setBlockName(Names.Blocks.TREE_FARM);
        setBlockTextureName(Reference.MOD_ID_LOWER + ":" + Names.Blocks.TREE_FARM);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_){
        return new TileEntityTreeFarm();
    }

}
