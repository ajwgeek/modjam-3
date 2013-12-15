package com.ironlionchefs.modjam.src.quest.networking.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.Validate;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.ironlionchefs.modjam.src.QuestMod;
import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.networking.PacketBase;
import com.ironlionchefs.modjam.src.quest.networking.PacketException;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;

import cpw.mods.fml.relauncher.Side;

public class PacketUpdateTotalBlocksPlaced extends PacketBase
{
	private String username;
	private String questName;
	private int ItemID;
	private int placed;

	public PacketUpdateTotalBlocksPlaced(EntityPlayer player, Quest quest, int ItemID, int placed)
	{
		Validate.notNull(quest);
		this.questName = quest.getName();
		this.username = player.username;
		this.ItemID = ItemID;
		this.placed = placed;
	}

	public PacketUpdateTotalBlocksPlaced()
	{
	}

	@Override
	public void write(ByteArrayDataOutput out)
	{
		out.writeUTF(username);
		out.writeUTF(questName);
		out.writeInt(ItemID);
		out.writeInt(placed);
	}

	@Override
	public void read(ByteArrayDataInput in) throws PacketException
	{
		username = in.readUTF();
		questName = in.readUTF();
		ItemID = in.readInt();
		placed = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side) throws PacketException
	{
		Validate.isTrue(QuestMod.currentQuestForPlayer.getName().equals(questName));
		QuestMod.currentQuestForPlayer.blocksPlaced = placed;
	}
}