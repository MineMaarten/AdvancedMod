package com.minemaarten.advancedmod.thirdparty.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minemaarten.advancedmod.init.ModBlocks;
import com.minemaarten.advancedmod.tileentity.TileEntityCamoMine;

public class WailaCamoHandler implements IWailaDataProvider{

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config){
        TileEntityCamoMine te = (TileEntityCamoMine)accessor.getTileEntity();
        int side = accessor.getSide().ordinal();
        ItemStack stack = te.getCamouflage(side);
        if(stack != null && stack.getItem() instanceof ItemBlock) {
            return stack;
        } else {
            return new ItemStack(ModBlocks.camoMine);
        }
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config){
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config){
        NBTTagCompound tag = accessor.getNBTData();
        String target = tag.getString("target");
        if(!target.equals("")) {
            currenttip.add(I18n.format("advancedMod.waila.camoMine.target") + " " + EnumChatFormatting.GREEN + target);
        }
        int timer = tag.getInteger("timer");
        if(timer == 0) {
            currenttip.add(I18n.format("advancedMod.waila.camoMine.primed"));
        } else if(timer == -1) {
            currenttip.add(I18n.format("advancedMod.waila.camoMine.notPrimed"));
        } else {
            currenttip.add(I18n.format("advancedMod.waila.camoMine.primingIn") + " " + timer);
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config){
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z){
        TileEntityCamoMine tile = (TileEntityCamoMine)te;
        tag.setString("target", tile.getTarget());
        tag.setInteger("timer", tile.getTimer());
        return tag;
    }

}
