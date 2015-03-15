package com.minemaarten.advancedmod;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.minemaarten.advancedmod.event.AdvancedModEventHandler;
import com.minemaarten.advancedmod.init.ModBlocks;
import com.minemaarten.advancedmod.init.ModTileEntities;
import com.minemaarten.advancedmod.network.DescriptionHandler;
import com.minemaarten.advancedmod.network.NetworkHandler;
import com.minemaarten.advancedmod.proxy.CommonProxy;
import com.minemaarten.advancedmod.reference.Reference;
import com.minemaarten.advancedmod.tileentity.TileEntityCamoMine;
import com.minemaarten.advancedmod.utility.Log;
import com.minemaarten.advancedmod.world.gen.WorldGeneratorFlag;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
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
        MinecraftForge.EVENT_BUS.register(new AdvancedModEventHandler());//For registering events from the net.miencraftforge.event package.
        FMLCommonHandler.instance().bus().register(new AdvancedModEventHandler());//For registering events from the cpw.mods.fml.gameevent package.
        FMLInterModComms.sendMessage(Reference.MOD_ID, "camoMineBlacklist", new ItemStack(Blocks.stone));
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

    @Mod.EventHandler
    public void onIMCMessages(IMCEvent event){
        Log.info("Receiving IMC");
        for(IMCMessage message : event.getMessages()) {
            if(message.key.equalsIgnoreCase("camoMineBlacklist")) {
                if(message.isItemStackMessage()) {
                    ItemStack blacklistedStack = message.getItemStackValue();
                    if(blacklistedStack.getItem() != null) {
                        TileEntityCamoMine.camouflageBlacklist.add(blacklistedStack);
                        Log.info(String.format("Mod %s added %s to be blacklisted as camouflage for the Camo Mine", message.getSender(), blacklistedStack.toString()));
                    } else {
                        throw new IllegalStateException(String.format("ItemStack tried to be used in registry by the mod %s has a null item.", message.getSender()));
                    }
                } else {
                    Log.warn(String.format("Mod %s sent a non-ItemStack message, where an ItemStack message was expected.", message.getSender()));
                }
            } else {
                Log.warn(String.format("Mod %s used an invalid IMC key: %s", message.getSender(), message.key));
            }
        }
    }
}
