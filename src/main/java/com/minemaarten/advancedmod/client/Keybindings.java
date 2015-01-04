package com.minemaarten.advancedmod.client;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

public enum Keybindings{

    EXPLODE("key.advancedMod.explode", Keyboard.KEY_G), EXPLODE_BIG("key.advancedMod.explodeBig", Keyboard.KEY_H);

    private final KeyBinding keybinding;

    private Keybindings(String keyName, int defaultKeyCode){
        keybinding = new KeyBinding(keyName, defaultKeyCode, "key.categories.advancedMod");
    }

    public KeyBinding getKeybind(){
        return keybinding;
    }

    public boolean isPressed(){
        return keybinding.isPressed();
    }

}
