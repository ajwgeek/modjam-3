package com.ironlionchefs.modjam.src.quest.saving;

import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class QuestSaveHelper
{	
	public boolean getQuestCompletedForPlayer(Quest q, EntityPlayer ep)
	{
		return QuestSaveData.forWorld(ep.worldObj).get(q, ep);
	}
	
	public void setQuestCompletedForPlayer(Quest q, EntityPlayer ep, boolean b)
	{
		QuestSaveData.forWorld(ep.worldObj).set(q, ep, b);
	}
	
	public void setCurrentQuestForEntity(Quest q, EntityPlayer ep)
	{
		QuestSavePlayerData.forWorld(ep.worldObj).set(ep, q);
	}
	
	public Quest getCurrentQuestForEntity(EntityPlayer ep)
	{
		String questName = QuestSavePlayerData.forWorld(ep.worldObj).get(ep);
		for (QuestPage i : QuestPage.PAGELIST)
		{
			for (Quest j : i.getQuests())
			{
				if (questName != null)
				{
					if (questName.equals(j.getName()))
					{
						return j;
					}
				}
			}
		}
		return null;
	}
}