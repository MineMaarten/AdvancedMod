package com.minemaarten.advancedmod.client;

import com.minemaarten.advancedmod.network.MessageExplode;
import com.minemaarten.advancedmod.network.NetworkHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler{
    private Keybindings getPressedKey(){
        for(Keybindings key : Keybindings.values()) {
            if(key.isPressed()) return key;
        }
        return null;
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event){
        Keybindings key = getPressedKey();
        if(key != null) {
            switch(key){
                case EXPLODE:
                    NetworkHandler.sendToServer(new MessageExplode(3));
                    break;
                case EXPLODE_BIG:
                    NetworkHandler.sendToServer(new MessageExplode(30));
                    break;
            }
        }
    }
}
