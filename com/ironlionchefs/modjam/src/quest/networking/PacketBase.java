package com.ironlionchefs.modjam.src.quest.networking;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateCurrentQuest;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateQuestCompleted;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateTotalBlocksBroken;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateTotalBlocksPlaced;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestConsumeFromInventory;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestIncrementExperiance;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestCurrentQuestName;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestQuestCompleted;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestPlayerBeginQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestPlayerEndQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestItemAddToInventory;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestTotalBlocksBroken;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestTotalBlocksBrokenUpdate;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestTotalBlocksPlaced;
import com.ironlionchefs.modjam.src.quest.networking.server.PacketRequestTotalBlocksPlacedUpdate;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public abstract class PacketBase
{
	public static final String CHANNEL = "QUEST";
	private static final BiMap<Integer, Class<? extends PacketBase>> idMap;

	static
	{
		ImmutableBiMap.Builder<Integer, Class<? extends PacketBase>> builder = ImmutableBiMap.builder();
		builder.put(Integer.valueOf(0), PacketUpdateQuestCompleted.class);
		builder.put(Integer.valueOf(1), PacketRequestQuestCompleted.class);
		builder.put(Integer.valueOf(2), PacketUpdateCurrentQuest.class);
		builder.put(Integer.valueOf(3), PacketRequestCurrentQuestName.class);
		builder.put(Integer.valueOf(4), PacketRequestPlayerBeginQuest.class);
		builder.put(Integer.valueOf(5), PacketRequestPlayerEndQuest.class);
		builder.put(Integer.valueOf(6), PacketRequestItemAddToInventory.class);
		builder.put(Integer.valueOf(7), PacketRequestConsumeFromInventory.class);
		builder.put(Integer.valueOf(8), PacketRequestIncrementExperiance.class);
		builder.put(Integer.valueOf(9), PacketRequestTotalBlocksPlaced.class);
		builder.put(Integer.valueOf(10), PacketRequestTotalBlocksPlacedUpdate.class);
		builder.put(Integer.valueOf(11), PacketUpdateTotalBlocksPlaced.class);
		builder.put(Integer.valueOf(12), PacketRequestTotalBlocksBroken.class);
		builder.put(Integer.valueOf(13), PacketRequestTotalBlocksBrokenUpdate.class);
		builder.put(Integer.valueOf(14), PacketUpdateTotalBlocksBroken.class);
		
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