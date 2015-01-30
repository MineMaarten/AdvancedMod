package com.minemaarten.advancedmod.gui;

import net.minecraft.entity.player.InventoryPlayer;

import com.minemaarten.advancedmod.inventory.ContainerCamoMine;
import com.minemaarten.advancedmod.tileentity.TileEntityCamoMine;

public class GuiCamoMine extends GuiAdvancedMod{

    public GuiCamoMine(InventoryPlayer playerInventory, TileEntityCamoMine te){
        super(new ContainerCamoMine(playerInventory, te), "camoMine", te);
    }

}
