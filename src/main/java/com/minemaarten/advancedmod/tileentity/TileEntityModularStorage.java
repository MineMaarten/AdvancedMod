package com.minemaarten.advancedmod.tileentity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.minemaarten.advancedmod.init.ModBlocks;
import com.minemaarten.advancedmod.utility.Log;

public class TileEntityModularStorage extends TileEntityAdvancedMod implements IInventory{

    private static final int SLOTS_PER_BLOCK = 1;
    private ItemStack[] inventory = new ItemStack[SLOTS_PER_BLOCK];

    private TileEntityModularStorage master;
    private boolean isMaster;
    private boolean firstRun = true;

    public boolean isMaster(){
        return isMaster;
    }

    public TileEntityModularStorage getMaster(){
        initializeMultiblockIfNecessary();
        return master;
    }

    private void setMaster(TileEntityModularStorage master, int storages){
        this.master = master;
        boolean wasMaster = isMaster;
        isMaster = master == this;
        if(isMaster) {
            Log.info("Master set to " + storages + " blocks");
            ItemStack[] newInventory = new ItemStack[SLOTS_PER_BLOCK * storages];
            for(int i = 0; i < inventory.length; i++) {
                if(i < newInventory.length) {
                    newInventory[i] = inventory[i];
                } else if(inventory[i] != null) {
                    worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, inventory[i]));
                }
            }
            inventory = newInventory;
        } else if(!isMaster && wasMaster) {
            for(ItemStack stack : inventory) {
                if(stack != null) worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, stack));
            }
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(firstRun) {
            initializeMultiblockIfNecessary();
            firstRun = false;
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        for(ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity te = worldObj.getTileEntity(xCoord + d.offsetX, yCoord + d.offsetY, zCoord + d.offsetZ);
            if(te instanceof TileEntityModularStorage) {
                ((TileEntityModularStorage)te).master = null;
                ((TileEntityModularStorage)te).initializeMultiblockIfNecessary();
            }
        }
        for(ItemStack stack : inventory) {
            if(stack != null) worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, stack));
        }
    }

    private void initializeMultiblockIfNecessary(){
        if(master == null || master.isInvalid()) {
            List<TileEntityModularStorage> connectedStorages = new ArrayList<TileEntityModularStorage>();
            Stack<TileEntityModularStorage> traversingStorages = new Stack<TileEntityModularStorage>();
            TileEntityModularStorage master = this;
            traversingStorages.add(this);
            while(!traversingStorages.isEmpty()) {
                TileEntityModularStorage storage = traversingStorages.pop();
                if(storage.isMaster()) {
                    master = storage;
                }
                connectedStorages.add(storage);
                for(ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
                    TileEntity te = worldObj.getTileEntity(storage.xCoord + d.offsetX, storage.yCoord + d.offsetY, storage.zCoord + d.offsetZ);
                    if(te instanceof TileEntityModularStorage && !connectedStorages.contains(te)) {
                        traversingStorages.add((TileEntityModularStorage)te);
                    }
                }
            }
            Log.info("Setting master to " + master.xCoord + ", " + master.yCoord + ", " + master.zCoord + " for " + connectedStorages.size() + " blocks");
            for(TileEntityModularStorage storage : connectedStorages) {
                storage.setMaster(master, connectedStorages.size());
            }
        }
    }

    @Override
    public void onGuiButtonPress(int id){

    }

    @Override
    public void writeToPacket(ByteBuf buf){

    }

    @Override
    public void readFromPacket(ByteBuf buf){

        //   worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);

        isMaster = tag.getBoolean("isMaster");

        inventory = new ItemStack[tag.getInteger("slots")];
        NBTTagList camoStackTag = tag.getTagList("inventory", 10);

        for(int i = 0; i < camoStackTag.tagCount(); i++) {
            NBTTagCompound t = camoStackTag.getCompoundTagAt(i);
            int index = t.getByte("index");
            if(index >= 0 && index < inventory.length) {
                inventory[index] = ItemStack.loadItemStackFromNBT(t);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);

        tag.setBoolean("isMaster", isMaster);

        tag.setInteger("slots", inventory.length);
        NBTTagList camoStackTag = new NBTTagList();
        for(int i = 0; i < inventory.length; i++) {
            ItemStack stack = inventory[i];
            if(stack != null) {
                NBTTagCompound t = new NBTTagCompound();
                stack.writeToNBT(t);
                t.setByte("index", (byte)i);
                camoStackTag.appendTag(t);
            }
        }
        tag.setTag("inventory", camoStackTag);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory(){
        return isMaster() ? inventory.length : getMaster().getSizeInventory();
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int slot){
        return isMaster ? inventory[slot] : getMaster().getStackInSlot(slot);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int slot, int decreaseAmount){
        if(isMaster()) {
            if(inventory[slot] != null) {
                ItemStack itemstack;

                if(inventory[slot].stackSize <= decreaseAmount) {
                    itemstack = inventory[slot];
                    setInventorySlotContents(slot, null);
                    markDirty();
                    return itemstack;
                } else {
                    itemstack = inventory[slot].splitStack(decreaseAmount);

                    if(inventory[slot].stackSize == 0) {
                        setInventorySlotContents(slot, null);
                    }

                    markDirty();
                    return itemstack;
                }
            } else {
                return null;
            }
        } else {
            return getMaster().decrStackSize(slot, decreaseAmount);
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot){
        if(isMaster()) {
            if(inventory[slot] != null) {
                ItemStack itemstack = inventory[slot];
                inventory[slot] = null;
                return itemstack;
            } else {
                return null;
            }
        } else {
            return getMaster().getStackInSlotOnClosing(slot);
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack){
        if(isMaster()) {
            inventory[slot] = stack;

            if(stack != null && stack.stackSize > getInventoryStackLimit()) {
                stack.stackSize = getInventoryStackLimit();
            }

            markDirty();
        } else {
            getMaster().setInventorySlotContents(slot, stack);
        }
    }

    /**
     * Returns the name of the inventory
     */
    @Override
    public String getInventoryName(){
        return ModBlocks.modularStorage.getUnlocalizedName() + ".name";
    }

    /**
     * Returns if the inventory is named
     */
    @Override
    public boolean hasCustomInventoryName(){
        return false;
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    @Override
    public int getInventoryStackLimit(){
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(){}

    @Override
    public void closeInventory(){}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack){
        return true;
    }

}
