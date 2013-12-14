package com.ironlionchefs.modjam.src.quest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ironlionchefs.modjam.src.quest.page.QuestPage;

public interface IQuest
{
	public void onQuestBegin(EntityPlayer player, World world);

	public void onQuestEnd(EntityPlayer player, World world);
	
	public String getDescription();

	public String getName();
	
	public QuestPage getPage();
}