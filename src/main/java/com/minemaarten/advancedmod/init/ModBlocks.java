package com.minemaarten.advancedmod.init;

import com.minemaarten.advancedmod.reference.Reference;
import com.minemaarten.advancedmod.utility.Log;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks{

    public static void init(){
        Log.info("Modblocks initialized");
    }
}
