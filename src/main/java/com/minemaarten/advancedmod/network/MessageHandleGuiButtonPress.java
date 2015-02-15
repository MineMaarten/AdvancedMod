package com.minemaarten.advancedmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.minemaarten.advancedmod.tileentity.TileEntityAdvancedMod;

public class MessageHandleGuiButtonPress extends MessageXYZ<MessageHandleGuiButtonPress>{
    private int id;

    public MessageHandleGuiButtonPress(){}

    public MessageHandleGuiButtonPress(TileEntityAdvancedMod te, int id){
        super(te);
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        super.fromBytes(buf);
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        super.toBytes(buf);
        buf.writeInt(id);
    }

    @Override
    public void handleClientSide(MessageHandleGuiButtonPress message, EntityPlayer player){

    }

    @Override
    public void handleServerSide(MessageHandleGuiButtonPress message, EntityPlayer player){
        TileEntity te = message.getTileEntity(player.worldObj);
        if(te instanceof TileEntityAdvancedMod) {
            ((TileEntityAdvancedMod)te).onGuiButtonPress(message.id);
        }
    }

}
