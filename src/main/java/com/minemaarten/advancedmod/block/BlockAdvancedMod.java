package com.minemaarten.advancedmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAdvancedMod extends Block{

    public BlockAdvancedMod(Material material){
        super(material);
    }

    public BlockAdvancedMod(){
        this(Material.rock);
    }

}
