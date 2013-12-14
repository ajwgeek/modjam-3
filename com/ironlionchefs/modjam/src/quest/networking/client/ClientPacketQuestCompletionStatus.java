package com.ironlionchefs.modjam.src.quest.networking.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.networking.Packet0BasePacket;
import com.ironlionchefs.modjam.src.quest.networking.PacketException;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;

import cpw.mods.fml.relauncher.Side;

public class ClientPacketQuestCompletionStatus extends Packet0BasePacket
{
	private boolean complete;
	private String questName;

	public ClientPacketQuestCompletionStatus(Quest quest, boolean complete)
	{
		this.complete = complete;
		this.questName = quest.getName();
	}

	public ClientPacketQuestCompletionStatus()
	{
	}

	@Override
	public void write(ByteArrayDataOutput out)
	{
		out.writeBoolean(complete);
		out.writeUTF(questName);
	}

	@Override
	public void read(ByteArrayDataInput in) throws PacketException
	{
		complete = in.readBoolean();
		questName = in.readUTF();
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
					j.complete = complete;
				}
			}
		}
	}
}