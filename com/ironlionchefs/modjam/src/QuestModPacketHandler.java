package com.ironlionchefs.modjam.src;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.ironlionchefs.modjam.src.quest.networking.PacketBase;
import com.ironlionchefs.modjam.src.quest.networking.PacketException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class QuestModPacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		try
		{
			EntityPlayer entityPlayer = (EntityPlayer) player;
			ByteArrayDataInput in = ByteStreams.newDataInput(packet.data);
			int packetId = in.readUnsignedByte();
			PacketBase questPacket = PacketBase.constructPacket(packetId);
			questPacket.read(in);
			questPacket.execute(entityPlayer, entityPlayer.worldObj.isRemote ? Side.CLIENT : Side.SERVER);
		}
		catch (PacketException e)
		{
			if (player instanceof EntityPlayerMP)
			{
				((EntityPlayerMP) player).playerNetServerHandler.kickPlayerFromServer("Packet Exception");
			}
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException("Reflection Error!", e);
		}
	}
}