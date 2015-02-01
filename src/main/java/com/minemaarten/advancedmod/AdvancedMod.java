package com.minemaarten.advancedmod;

import com.minemaarten.advancedmod.init.ModBlocks;
import com.minemaarten.advancedmod.init.ModTileEntities;
import com.minemaarten.advancedmod.network.DescriptionHandler;
import com.minemaarten.advancedmod.network.NetworkHandler;
import com.minemaarten.advancedmod.proxy.CommonProxy;
import com.minemaarten.advancedmod.reference.Reference;
import com.minemaarten.advancedmod.utility.Log;
import com.minemaarten.advancedmod.world.gen.WorldGeneratorFlag;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class AdvancedMod{
    @Mod.Instance(Reference.MOD_ID)
    public static AdvancedMod instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ModBlocks.init();
        ModTileEntities.init();
        proxy.preInit();
        GameRegistry.registerWorldGenerator(new WorldGeneratorFlag(), 0);
        NetworkHandler.init();
        DescriptionHandler.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        Log.info("Pre Initialization Complete!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        proxy.init();
        Log.info("Initialization Complete!");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit();
        Log.info("Post Initialization Complete!");
    }
}
