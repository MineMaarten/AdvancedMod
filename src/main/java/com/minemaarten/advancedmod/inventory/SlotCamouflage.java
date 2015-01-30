package com.minemaarten.advancedmod.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCamouflage extends Slot{

    public SlotCamouflage(IInventory inventory, int inventoryIndex, int x, int y){
        super(inventory, inventoryIndex, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack){
        return inventory.isItemValidForSlot(getSlotIndex(), stack);
    }
}
