package com.minemaarten.advancedmod.init;

import com.minemaarten.advancedmod.tileentity.TileEntityCamoMine;
import com.minemaarten.advancedmod.utility.Names;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities{

    public static void init(){

        GameRegistry.registerTileEntity(TileEntityCamoMine.class, Names.TileEntities.CAMO_MINE);
    }
}
