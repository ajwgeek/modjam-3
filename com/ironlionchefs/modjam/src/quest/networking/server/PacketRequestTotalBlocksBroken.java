package com.ironlionchefs.modjam.src.quest.networking.server;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.networking.PacketBase;
import com.ironlionchefs.modjam.src.quest.networking.PacketException;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateQuestCompleted;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateTotalBlocksBroken;
import com.ironlionchefs.modjam.src.quest.networking.client.PacketUpdateTotalBlocksPlaced;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;
import com.ironlionchefs.modjam.src.quest.saving.QuestSaveHelper;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketRequestTotalBlocksBroken extends PacketBase
{
	public String username;
	public String questName;
	public int itemID;
	
	public PacketRequestTotalBlocksBroken(EntityPlayer ep, Quest quest, int ItemID)
	{
		this.username = ep.username;
		this.questName = quest.getName();
		this.itemID = ItemID;
	}

	public PacketRequestTotalBlocksBroken()
	{
	}

	@Override
	public void write(ByteArrayDataOutput out)
	{
		out.writeUTF(username);
		out.writeUTF(questName);
		out.writeInt(itemID);
	}

	@Override
	public void read(ByteArrayDataInput in) throws PacketException
	{
		username = in.readUTF();
		questName = in.readUTF();
		itemID = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side) throws PacketException
	{
		for (QuestPage i : QuestPage.PAGELIST)
		{
			for (Quest j : i.getQuests())
			{
				if (j.getName().equals(questName))
				{
					EntityPlayer playerObj = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
					int placed = new QuestSaveHelper().getBrokenBlocksForQuest(j, playerObj, itemID);
					PacketDispatcher.sendPacketToPlayer(new PacketUpdateTotalBlocksBroken(playerObj, j, itemID, placed).makePacket(), (Player) playerObj);
				}
			}
		}
	}
}