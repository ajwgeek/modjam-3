package com.ironlionchefs.modjam.src.quest.networking.server;

import org.apache.commons.lang3.Validate;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.networking.PacketBase;
import com.ironlionchefs.modjam.src.quest.networking.PacketException;
import com.ironlionchefs.modjam.src.quest.networking.client.ClientPacketPlayerCurrentQuest;
import com.ironlionchefs.modjam.src.quest.networking.client.ClientPacketQuestCompletionStatus;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;
import com.ironlionchefs.modjam.src.quest.saving.QuestSaveHelper;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class ServerPacketPlayerBeginQuest extends PacketBase
{
	public String username;
	public String questName;

	public ServerPacketPlayerBeginQuest(EntityPlayer ep, Quest quest)
	{
		this.username = ep.username;
		this.questName = quest.getName();
	}

	public ServerPacketPlayerBeginQuest()
	{
	}

	@Override
	public void write(ByteArrayDataOutput out)
	{
		out.writeUTF(username);
		out.writeUTF(questName);
	}

	@Override
	public void read(ByteArrayDataInput in) throws PacketException
	{
		username = in.readUTF();
		questName = in.readUTF();
	}

	@Override
	public void execute(EntityPlayer player, Side side) throws PacketException
	{
		EntityPlayer playerObj = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
		Quest finalQuest = null;
		for (QuestPage i : QuestPage.PAGELIST)
		{
			for (Quest j : i.getQuests())
			{
				if (j.getName().equals(questName))
				{
					finalQuest = j;
				}
			}
		}
		QuestSaveHelper saveHelper = new QuestSaveHelper();
		saveHelper.setQuestCompletedForPlayer(finalQuest, playerObj, false);
		saveHelper.setCurrentQuestForEntity(finalQuest, playerObj);
		PacketDispatcher.sendPacketToPlayer(new ClientPacketPlayerCurrentQuest(playerObj, finalQuest).makePacket(), (Player) playerObj);
		PacketDispatcher.sendPacketToPlayer(new ClientPacketQuestCompletionStatus(finalQuest, false).makePacket(), (Player) playerObj);
	}
}