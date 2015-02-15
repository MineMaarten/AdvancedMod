package com.minemaarten.advancedmod.block;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.minemaarten.advancedmod.AdvancedMod;
import com.minemaarten.advancedmod.GuiHandler;
import com.minemaarten.advancedmod.reference.Reference;
import com.minemaarten.advancedmod.tileentity.TileEntityCamoMine;
import com.minemaarten.advancedmod.utility.Names;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCamoMine extends BlockAdvancedModTileEntity{

    public BlockCamoMine(){
        setBlockName(Names.Blocks.CAMO_MINE);
        setBlockTextureName(Reference.MOD_ID_LOWER + ":" + Names.Blocks.DUTCH_FLAG);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_){
        return new TileEntityCamoMine();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if(!world.isRemote) {
            if(player.isSneaking()) {
                player.openGui(AdvancedMod.instance, GuiHandler.GuiIDs.CAMO_MINE.ordinal(), world, x, y, z);
            } else {
                TileEntityCamoMine te = (TileEntityCamoMine)world.getTileEntity(x, y, z);
                if(te.getCamouflage(side) != null) {
                    ItemStack camoStack = te.getCamouflage(side);
                    te.setCamouflage(null, side);
                    EntityItem itemEntity = new EntityItem(world, x, y, z, camoStack);
                    world.spawnEntityInWorld(itemEntity);
                } else {
                    ItemStack playerItem = player.getCurrentEquippedItem();
                    if(playerItem != null) {
                        ItemStack camoStack = playerItem.splitStack(1);
                        te.setCamouflage(camoStack, side);
                    }
                }
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        TileEntityCamoMine te = (TileEntityCamoMine)world.getTileEntity(x, y, z);
        ItemStack stack = te.getCamouflage(side);
        if(stack != null && stack.getItem() instanceof ItemBlock) {
            Block block = ((ItemBlock)stack.getItem()).field_150939_a;
            return block.getIcon(side, stack.getItemDamage());
        } else {
            return super.getIcon(world, x, y, z, side);
        }

    }
}
