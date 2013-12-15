package com.ironlionchefs.modjam.src.quest.saving;

import org.apache.commons.lang3.Validate;

import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;
import com.ironlionchefs.modjam.src.quest.tracker.Tracker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class QuestSaveHelper
{
	public int getPlacedBlocksForQuest(Quest q, EntityPlayer ep, int ItemID)
	{
		Validate.isTrue(q.trackers.contains(Tracker.BLOCKPLACED));
		return QuestDataBlocksPlaced.forWorld(ep.worldObj).get(q, ep, ItemID);	
	}
	
	public void setPlacedBlocksForQuest(Quest q, EntityPlayer ep, int ItemID, int count)
	{
		Validate.isTrue(q.trackers.contains(Tracker.BLOCKPLACED));
		QuestDataBlocksPlaced.forWorld(ep.worldObj).set(q, ep, ItemID, count);
	}
	
	public boolean getQuestCompletedForPlayer(Quest q, EntityPlayer ep)
	{
		return QuestDataCompletion.forWorld(ep.worldObj).get(q, ep);
	}

	public void setQuestCompletedForPlayer(Quest q, EntityPlayer ep, boolean b)
	{
		QuestDataCompletion.forWorld(ep.worldObj).set(q, ep, b);
	}

	public void setCurrentQuestForEntity(Quest q, EntityPlayer ep)
	{
		QuestDataPerPlayer.forWorld(ep.worldObj).set(ep, q);
	}

	public Quest getCurrentQuestForEntity(EntityPlayer ep)
	{
		String questName = QuestDataPerPlayer.forWorld(ep.worldObj).get(ep);
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