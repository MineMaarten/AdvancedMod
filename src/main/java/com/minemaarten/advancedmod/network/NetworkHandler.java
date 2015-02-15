package com.minemaarten.advancedmod.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import com.minemaarten.advancedmod.reference.Reference;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler{
    private static SimpleNetworkWrapper INSTANCE;

    public static void init(){
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

        INSTANCE.registerMessage(MessageExplode.class, MessageExplode.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageHandleGuiButtonPress.class, MessageHandleGuiButtonPress.class, 1, Side.SERVER);
        INSTANCE.registerMessage(MessageHandleTextUpdate.class, MessageHandleTextUpdate.class, 2, Side.SERVER);
        INSTANCE.registerMessage(MessageHandleTextUpdate.class, MessageHandleTextUpdate.class, 3, Side.CLIENT);
    }

    public static void sendToServer(IMessage message){
        INSTANCE.sendToServer(message);
    }

    public static void sendTo(IMessage message, EntityPlayerMP player){
        INSTANCE.sendTo(message, player);
    }

    public static void sendToAllAround(IMessage message, TargetPoint point){
        INSTANCE.sendToAllAround(message, point);
    }

    /**
     * Will send the given packet to every player within 64 blocks of the XYZ of the XYZ packet.
     * @param message
     * @param world
     */
    public static void sendToAllAround(MessageXYZ message, World world){
        INSTANCE.sendToAllAround(message, new TargetPoint(world.provider.dimensionId, message.x, message.y, message.z, 64D));
    }

    public static void sendToAll(IMessage message){
        INSTANCE.sendToAll(message);
    }

    public static void sendToDimension(IMessage message, int dimensionId){
        INSTANCE.sendToDimension(message, dimensionId);
    }

}
