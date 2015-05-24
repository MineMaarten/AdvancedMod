package com.minemaarten.advancedmod.init;

import net.minecraft.block.Block;

import com.minemaarten.advancedmod.block.BlockCamoMine;
import com.minemaarten.advancedmod.block.BlockDutchFlag;
import com.minemaarten.advancedmod.block.BlockModularStorage;
import com.minemaarten.advancedmod.block.BlockTreeFarm;
import com.minemaarten.advancedmod.reference.Reference;
import com.minemaarten.advancedmod.utility.Log;
import com.minemaarten.advancedmod.utility.Names;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks{
    public static final Block dutchFlag = new BlockDutchFlag();
    public static final Block camoMine = new BlockCamoMine();
    public static final Block modularStorage = new BlockModularStorage();
    public static final Block treeFarm = new BlockTreeFarm();

    public static void init(){
        GameRegistry.registerBlock(dutchFlag, Names.Blocks.DUTCH_FLAG);
        GameRegistry.registerBlock(camoMine, Names.Blocks.CAMO_MINE);
        GameRegistry.registerBlock(modularStorage, Names.Blocks.MODULAR_STORAGE);
        GameRegistry.registerBlock(treeFarm, Names.Blocks.TREE_FARM);

        Log.info("Modblocks initialized");
    }
}
