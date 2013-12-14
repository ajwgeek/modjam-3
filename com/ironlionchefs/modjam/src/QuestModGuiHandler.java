package com.ironlionchefs.modjam.src;

import com.ironlionchefs.modjam.src.quest.gui.GuiQuestActive;
import com.ironlionchefs.modjam.src.quest.gui.GuiQuestMap;
import com.ironlionchefs.modjam.src.quest.gui.GuiQuestNotification;

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
		switch(ID)
		{
			case 0:
				return new GuiQuestMap(player);
			case 1:
				return new GuiQuestActive(player);
			default:
				return null;
		}
	}
}