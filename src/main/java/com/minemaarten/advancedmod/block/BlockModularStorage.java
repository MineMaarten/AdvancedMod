package com.minemaarten.advancedmod.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.minemaarten.advancedmod.reference.Reference;
import com.minemaarten.advancedmod.tileentity.TileEntityModularStorage;
import com.minemaarten.advancedmod.utility.Names;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
            // player.openGui(AdvancedMod.instance, GuiHandler.GuiIDs.CAMO_MINE.ordinal(), world, x, y, z);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        /* TileEntityCamoMine te = (TileEntityCamoMine)world.getTileEntity(x, y, z);
         ItemStack stack = te.getCamouflage(side);
         if(stack != null && stack.getItem() instanceof ItemBlock) {
             Block block = ((ItemBlock)stack.getItem()).field_150939_a;
             return block.getIcon(side, stack.getItemDamage());
         } else {*/
        return super.getIcon(world, x, y, z, side);
        //}

    }
}
