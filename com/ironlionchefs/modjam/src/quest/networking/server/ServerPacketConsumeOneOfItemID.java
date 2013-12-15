package com.ironlionchefs.modjam.src.quest.networking.server;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.networking.PacketBase;
import com.ironlionchefs.modjam.src.quest.networking.PacketException;
import com.ironlionchefs.modjam.src.quest.networking.client.ClientPacketQuestCompletionStatus;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;
import com.ironlionchefs.modjam.src.quest.saving.QuestSaveHelper;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class ServerPacketConsumeOneOfItemID extends PacketBase
{
	public String username;
	public int itemID;

	public ServerPacketConsumeOneOfItemID(EntityPlayer ep, int itemID)
	{
		this.username = ep.username;
		this.itemID = itemID;
	}

	public ServerPacketConsumeOneOfItemID()
	{
	}

	@Override
	public void write(ByteArrayDataOutput out)
	{
		out.writeUTF(username);
		out.writeInt(itemID);
	}

	@Override
	public void read(ByteArrayDataInput in) throws PacketException
	{
		username = in.readUTF();
		itemID = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side) throws PacketException
	{
		EntityPlayerMP mp = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
		mp.inventory.consumeInventoryItem(itemID);
	}
}