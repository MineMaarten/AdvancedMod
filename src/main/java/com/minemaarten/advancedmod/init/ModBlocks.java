package com.minemaarten.advancedmod.init;

import net.minecraft.block.Block;

import com.minemaarten.advancedmod.block.BlockDutchFlag;
import com.minemaarten.advancedmod.reference.Reference;
import com.minemaarten.advancedmod.utility.Names;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks{
    public static final Block dutchFlag = new BlockDutchFlag();

    public static void init(){
        GameRegistry.registerBlock(dutchFlag, Names.Blocks.DUTCH_FLAG);
    }
}
