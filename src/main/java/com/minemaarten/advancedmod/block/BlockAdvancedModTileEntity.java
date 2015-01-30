package com.minemaarten.advancedmod.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockAdvancedModTileEntity extends BlockContainer{
    public BlockAdvancedModTileEntity(Material material){
        super(material);
    }

    public BlockAdvancedModTileEntity(){
        this(Material.rock);
    }

}
