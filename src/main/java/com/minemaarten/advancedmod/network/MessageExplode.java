package com.minemaarten.advancedmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageExplode extends MessageBase<MessageExplode>{

    private float explosionSize;

    public MessageExplode(){}

    public MessageExplode(float explosionSize){
        this.explosionSize = explosionSize;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        explosionSize = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeFloat(explosionSize);
    }

    @Override
    public void handleClientSide(MessageExplode message, EntityPlayer player){

    }

    @Override
    public void handleServerSide(MessageExplode message, EntityPlayer player){
        player.worldObj.createExplosion(player, player.posX, player.posY, player.posZ, message.explosionSize, true);
    }

}
