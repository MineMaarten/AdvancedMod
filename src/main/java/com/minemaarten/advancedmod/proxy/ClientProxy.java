package com.minemaarten.advancedmod.proxy;

import com.minemaarten.advancedmod.client.KeyInputHandler;
import com.minemaarten.advancedmod.client.Keybindings;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy{

    @Override
    public void preInit(){
        registerKeybinds();
    }

    private void registerKeybinds(){
        FMLCommonHandler.instance().bus().register(new KeyInputHandler());
        for(Keybindings key : Keybindings.values()) {
            ClientRegistry.registerKeyBinding(key.getKeybind());
        }
    }

    @Override
    public void init(){

    }

    @Override
    public void postInit(){

    }

}
