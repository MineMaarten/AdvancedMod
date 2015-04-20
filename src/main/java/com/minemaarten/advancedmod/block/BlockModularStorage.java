package com.minemaarten.advancedmod.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.minemaarten.advancedmod.reference.Reference;
import com.minemaarten.advancedmod.tileentity.TileEntityModularStorage;
import com.minemaarten.advancedmod.utility.Log;
import com.minemaarten.advancedmod.utility.Names;

public class BlockModularStorage extends BlockAdvancedModTileEntity{

    public BlockModularStorage(){
        setBlockName(Names.Blocks.MODULAR_STORAGE);
        setBlockTextureName(Reference.MOD_ID_LOWER + ":" + Names.Blocks.MODULAR_STORAGE);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_){
        return new TileEntityModularStorage();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if(!world.isRemote) {
            TileEntityModularStorage storage = (TileEntityModularStorage)world.getTileEntity(x, y, z);
            TileEntityModularStorage master = storage.getMaster();
            Log.info("master storage:");
            for(int i = 0; i < master.getSizeInventory(); i++) {
                Log.info(i + ", " + master.getStackInSlot(i));
            }
        }
        return true;
    }

}
