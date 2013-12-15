package com.ironlionchefs.modjam.src.quest.networking.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.ironlionchefs.modjam.src.QuestMod;
import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.networking.PacketBase;
import com.ironlionchefs.modjam.src.quest.networking.PacketException;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;

import cpw.mods.fml.relauncher.Side;

public class ClientPacketUpdateBlockPlaced extends PacketBase
{
	private String username;
	private String questName;

	public ClientPacketUpdateBlockPlaced(EntityPlayer player, Quest quest, int ItemID)
	{
		if (quest == null)
		{
			this.questName = "null";
		}
		else
		{
			this.questName = quest.getName();
		}
		this.username = player.username;
	}

	public ClientPacketUpdateBlockPlaced()
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
		if (questName.equals("null"))
		{
			QuestMod.currentQuestForPlayer = null;
			return;
		}
		for (QuestPage i : QuestPage.PAGELIST)
		{
			for (Quest j : i.getQuests())
			{
				if (j.getName().equals(questName))
				{
					QuestMod.currentQuestForPlayer = j;
					return;
				}
			}
		}
	}
}