package com.ironlionchefs.modjam.src.quest.networking.server;

import org.apache.commons.lang3.Validate;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.ironlionchefs.modjam.src.QuestMod;
import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.networking.PacketBase;
import com.ironlionchefs.modjam.src.quest.networking.PacketException;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateCurrentQuest;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateQuestCompleted;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;
import com.ironlionchefs.modjam.src.quest.saving.QuestSaveHelper;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketRequestPlayerEndQuest extends PacketBase
{
	public String username;
	public String questName;

	public PacketRequestPlayerEndQuest(EntityPlayer ep, Quest quest)
	{
		this.username = ep.username;
		this.questName = quest.getName();
	}

	public PacketRequestPlayerEndQuest()
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
		saveHelper.setQuestCompletedForPlayer(finalQuest, playerObj, true);
		saveHelper.setCurrentQuestForEntity(null, playerObj);
		PacketDispatcher.sendPacketToPlayer(new PacketUpdateQuestCompleted(finalQuest, true).makePacket(), (Player) playerObj);
		PacketDispatcher.sendPacketToPlayer(new PacketUpdateCurrentQuest(playerObj, null).makePacket(), (Player) playerObj);
	}
}