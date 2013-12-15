package com.ironlionchefs.modjam.src;

import com.ironlionchefs.modjam.src.quest.gui.GuiQuestActive;
import com.ironlionchefs.modjam.src.quest.gui.GuiQuestMap;
import com.ironlionchefs.modjam.src.quest.gui.GuiQuestNotification;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;
import com.ironlionchefs.modjam.src.quest.page.QuestPageFarmer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class QuestModGuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case 0:
				return new GuiQuestMap(player, QuestPage.PAGELIST.get(0));
			case 3:
				return new GuiQuestMap(player, QuestPage.PAGELIST.get(1));
			case 4:
				return new GuiQuestMap(player, QuestPage.PAGELIST.get(2));
			case 5:
				return new GuiQuestActive(player);
			default:
				return null;
		}
	}
}