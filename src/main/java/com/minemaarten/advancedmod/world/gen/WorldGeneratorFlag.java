package com.minemaarten.advancedmod.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.minemaarten.advancedmod.init.ModBlocks;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraftforge.fluids.FluidRegistry;

public class WorldGeneratorFlag implements IWorldGenerator{

    private final WorldGenMinable flagGen = new WorldGenMinable(ModBlocks.dutchFlag, 32);

    private static final int FLAG_HEIGHT_POLE = 10;         //The height of the flag to generate, must be more than 3

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
        int x = chunkX * 16;
        int z = chunkZ * 16;
        switch(world.provider.dimensionId){
            case 0:
                generateSurface(world, x, z, random);
                break;
            case -1:
                generateNether(world, x, z, random);
                break;
            case 1:
                generateEnd(world, x, z, random);
                break;
            default:
                generateSurface(world, x, z, random);
        }
    }

    private void generateEnd(World world, int x, int z, Random random){

    }

    private void generateNether(World world, int x, int z, Random random){

    }

    private void generateSurface(World world, int x, int z, Random random){
        if(random.nextInt(10) == 0) {
            int randX = x + random.nextInt(16);
            int randZ = z + random.nextInt(16);
            int randY = world.getHeightValue(randX, randZ);

            if(randY <= (world.getHeight() - FLAG_HEIGHT_POLE) && world.getBiomeGenForCoords(randX, randZ) != BiomeGenBase.river && world.getBiomeGenForCoords(randX, randZ) != BiomeGenBase.ocean) {
                Block block = world.getBlock(randX, randY - 1, randZ);
                //if(block != Blocks.water && block != Blocks.lava && block != Blocks.flowing_water && block != Blocks.flowing_lava) {
                if (!(block instanceof BlockLiquid || FluidRegistry.lookupFluidForBlock(block) != null)) {
                    generateFlag(world, randX, randY, randZ);
                }
            }
        }

        for(int i = 0; i < 5; i++) {
            int randX = x + random.nextInt(16);
            int randY = 20 + random.nextInt(40);
            int randZ = z + random.nextInt(16);
            flagGen.generate(world, random, randX, randY, randZ);
        }
    }

    private void generateFlag(World world, int x, int y, int z){
        for(int i = 0; i < FLAG_HEIGHT_POLE; i++) {
            world.setBlock(x, y + i, z, Blocks.fence, 0, 2);
        }
        for(int dx = 0; dx < 3; dx++) {
            for(int dy = 0; dy < 3; dy++) {
                world.setBlock(x + 1 + dx, y + (FLAG_HEIGHT_POLE - 3) + dy, z, ModBlocks.dutchFlag, 0, 2);
            }
        }
    }

}
