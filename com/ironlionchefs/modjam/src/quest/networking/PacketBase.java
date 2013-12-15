package com.ironlionchefs.modjam.src.quest.networking;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ironlionchefs.modjam.src.quest.networking.client.ClientPacketPlayerCurrentQuest;
import com.ironlionchefs.modjam.src.quest.networking.client.ClientPacketQuestCompletionStatus;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketConsumeOneOfItemID;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketRequestCurrentQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketRequestQuestCompletion;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketPlayerBeginQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketPlayerEndQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketAddItemStackToInventory;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public abstract class PacketBase
{
	public static final String CHANNEL = "QUEST";
	private static final BiMap<Integer, Class<? extends PacketBase>> idMap;

	static
	{
		ImmutableBiMap.Builder<Integer, Class<? extends PacketBase>> builder = ImmutableBiMap.builder();
		builder.put(Integer.valueOf(0), ClientPacketQuestCompletionStatus.class);
		builder.put(Integer.valueOf(1), ServerPacketRequestQuestCompletion.class);
		builder.put(Integer.valueOf(2), ClientPacketPlayerCurrentQuest.class);
		builder.put(Integer.valueOf(3), ServerPacketRequestCurrentQuest.class);
		builder.put(Integer.valueOf(4), ServerPacketPlayerBeginQuest.class);
		builder.put(Integer.valueOf(5), ServerPacketPlayerEndQuest.class);
		builder.put(Integer.valueOf(6), ServerPacketAddItemStackToInventory.class);
		builder.put(Integer.valueOf(7), ServerPacketConsumeOneOfItemID.class);
		idMap = builder.build();
	}

	public static PacketBase constructPacket(int packetId) throws PacketException, ReflectiveOperationException
	{
		Class<? extends PacketBase> clazz = idMap.get(Integer.valueOf(packetId));
		if (clazz == null)
		{
			throw new PacketException("Bad Packet ID");
		}
		else
		{
			return clazz.newInstance();
		}
	}

	public final int getPacketId()
	{
		if (idMap.inverse().containsKey(getClass()))
		{
			return idMap.inverse().get(getClass()).intValue();
		}
		else
		{
			throw new RuntimeException("Packet Error");
		}
	}

	public final Packet makePacket()
	{
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeByte(getPacketId());
		write(out);
		return PacketDispatcher.getPacket(CHANNEL, out.toByteArray());
	}

	public abstract void write(ByteArrayDataOutput out);

	public abstract void read(ByteArrayDataInput in) throws PacketException;

	public abstract void execute(EntityPlayer player, Side side) throws PacketException;
}