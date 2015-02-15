package com.minemaarten.advancedmod.network;

import net.minecraft.entity.player.EntityPlayer;

import com.minemaarten.advancedmod.AdvancedMod;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public abstract class MessageBase<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ>{

    @Override
    public REQ onMessage(REQ message, MessageContext ctx){
        if(ctx.side == Side.SERVER) {
            handleServerSide(message, ctx.getServerHandler().playerEntity);
        } else {
            handleClientSide(message, AdvancedMod.proxy.getClientPlayer());
        }
        return null;
    }

    /**
     * Handle a packet on the client side. Note this occurs after decoding has completed.
     * @param message
     * @param player the player reference
     */
    public abstract void handleClientSide(REQ message, EntityPlayer player);

    /**
     * Handle a packet on the server side. Note this occurs after decoding has completed.
     * @param message
     * @param player the player reference
     */
    public abstract void handleServerSide(REQ message, EntityPlayer player);
}
