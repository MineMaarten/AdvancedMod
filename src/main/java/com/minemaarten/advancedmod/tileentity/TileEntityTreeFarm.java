package com.minemaarten.advancedmod.tileentity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;

public class TileEntityTreeFarm extends TileEntityAdvancedMod{

    private boolean formed;
    private boolean foundInvalidBlock;
    private int inventoriesFound;
    private IInventory inventory;
    private int checkingX, checkingY, checkingZ;

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!worldObj.isRemote) {
            checkMultiblock();
            if(formed) {
                cutTree();
                placeSapling();
            }
        }
    }

    private void placeSapling(){
        if(worldObj.isAirBlock(xCoord, yCoord + 2, zCoord)) {
            if(inventory != null) {
                for(int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    if(stack != null && stack.getItem() instanceof ItemBlock) {
                        Block block = ((ItemBlock)stack.getItem()).field_150939_a;
                        if(block == Blocks.sapling) {
                            worldObj.setBlock(xCoord, yCoord + 2, zCoord, block, stack.getItemDamage(), 3);
                            inventory.decrStackSize(i, 1);
                        }
                    }
                }
            }
        }
    }

    private void cutTree(){
        if(inventory != null) {
            Block block = worldObj.getBlock(xCoord, yCoord + 2, zCoord);
            int offsetY = yCoord + 2;
            while(block.getMaterial() == Material.wood) {
                List<ItemStack> items = block.getDrops(worldObj, xCoord, offsetY, zCoord, worldObj.getBlockMetadata(xCoord, offsetY, zCoord), 0);
                for(ItemStack item : items) {
                    ItemStack remainder = TileEntityHopper.func_145889_a(inventory, item, 0);
                    if(remainder != null) {
                        worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord + 0.5, yCoord + 2.5, zCoord + 0.5, remainder));
                    }
                }
                worldObj.setBlock(xCoord, offsetY, zCoord, Blocks.air);
                offsetY++;
                block = worldObj.getBlock(xCoord, offsetY, zCoord);
            }
        }
    }

    private void checkMultiblock(){
        checkingX++;
        if(checkingX > 1) {
            checkingX = -1;
            checkingY++;
            if(checkingY > 1) {
                checkingY = -1;
                checkingZ++;
                if(checkingZ > 1) {
                    checkingZ = -1;
                    formed = !foundInvalidBlock && inventoriesFound == 1;
                    foundInvalidBlock = false;
                    inventoriesFound = 0;
                }
            }
        }

        if(checkingX == 0 && checkingY == 0 && checkingZ == 0) return;
        Block block = worldObj.getBlock(xCoord + checkingX, yCoord + checkingY, zCoord + checkingZ);

        if(checkingX == 0 && checkingZ == 0 && checkingY == 1) {
            if(block != Blocks.dirt && block != Blocks.grass) {
                foundInvalidBlock = true;
            }
        } else if(checkingY == 1 && checkingX != 0 ^ checkingZ != 0) {
            TileEntity te = worldObj.getTileEntity(xCoord + checkingX, yCoord + checkingY, zCoord + checkingZ);
            if(te instanceof IInventory) {
                inventoriesFound++;
                inventory = (IInventory)te;
            } else if(block != Blocks.iron_block) {
                foundInvalidBlock = true;
            }
        } else if(block != Blocks.iron_block) {
            foundInvalidBlock = true;
        }
    }
}