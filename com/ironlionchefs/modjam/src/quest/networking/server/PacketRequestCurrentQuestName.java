package com.ironlionchefs.modjam.src.quest.networking.server;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.networking.PacketBase;
import com.ironlionchefs.modjam.src.quest.networking.PacketException;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateCurrentQuest;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;
import com.ironlionchefs.modjam.src.quest.saving.QuestSaveHelper;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketRequestCurrentQuestName extends PacketBase
{
	public String username;

	public PacketRequestCurrentQuestName(String username)
	{
		this.username = username;
	}

	public PacketRequestCurrentQuestName()
	{
	}

	@Override
	public void write(ByteArrayDataOutput out)
	{
		out.writeUTF(username);
	}

	@Override
	public void read(ByteArrayDataInput in) throws PacketException
	{
		username = in.readUTF();
	}

	@Override
	public void execute(EntityPlayer player, Side side) throws PacketException
	{
		EntityPlayer playerObj = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
		Quest quest = new QuestSaveHelper().getCurrentQuestForEntity(playerObj);
		PacketDispatcher.sendPacketToPlayer(new PacketUpdateCurrentQuest(playerObj, quest).makePacket(), (Player) playerObj);
	}
}