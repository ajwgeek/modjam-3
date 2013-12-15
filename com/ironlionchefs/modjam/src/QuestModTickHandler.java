package com.ironlionchefs.modjam.src;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import org.lwjgl.input.Keyboard;

import com.ironlionchefs.modjam.src.quest.gui.GuiQuestMap;
import com.ironlionchefs.modjam.src.quest.gui.GuiQuestNotification;
import com.ironlionchefs.modjam.src.quest.page.QuestPageAgriculture;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;

public class QuestModTickHandler implements ITickHandler
{
	public static GuiQuestNotification notifier = new GuiQuestNotification(Minecraft.getMinecraft());

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			notifier.updateQuestWindow();
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		return "QuestTickHandler";
	}
}