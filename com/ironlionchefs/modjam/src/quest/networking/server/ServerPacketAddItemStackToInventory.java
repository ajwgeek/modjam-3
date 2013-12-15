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

public class ServerPacketAddItemStackToInventory extends PacketBase
{
	public String username;
	public int itemID;
	public int dataValue;
	public int amt;

	public ServerPacketAddItemStackToInventory(EntityPlayer ep, ItemStack is)
	{
		this.username = ep.username;
		itemID = is.itemID;
		dataValue = is.getItemDamage();
		amt = is.stackSize;
	}

	public ServerPacketAddItemStackToInventory()
	{
	}

	@Override
	public void write(ByteArrayDataOutput out)
	{
		out.writeUTF(username);
		out.writeInt(itemID);
		out.writeInt(dataValue);
		out.writeInt(amt);
	}

	@Override
	public void read(ByteArrayDataInput in) throws PacketException
	{
		username = in.readUTF();
		itemID = in.readInt();
		dataValue = in.readInt();
		amt = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side) throws PacketException
	{
		ItemStack itemStack = new ItemStack(itemID, amt, dataValue);
		EntityPlayerMP mp = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
		mp.inventory.addItemStackToInventory(itemStack);
	}
}