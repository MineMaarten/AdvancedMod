package com.minemaarten.advancedmod.init;

import com.minemaarten.advancedmod.tileentity.TileEntityCamoMine;
import com.minemaarten.advancedmod.tileentity.TileEntityModularStorage;
import com.minemaarten.advancedmod.tileentity.TileEntityTreeFarm;
import com.minemaarten.advancedmod.utility.Names;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities{

    public static void init(){

        GameRegistry.registerTileEntity(TileEntityCamoMine.class, Names.TileEntities.CAMO_MINE);
        GameRegistry.registerTileEntity(TileEntityModularStorage.class, Names.TileEntities.MODULAR_STORAGE);
        GameRegistry.registerTileEntity(TileEntityTreeFarm.class, Names.TileEntities.TREE_FARM);
    }
}
