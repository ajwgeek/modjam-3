package com.ironlionchefs.modjam.src.quest.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.ironlionchefs.modjam.src.quest.Quest;

import net.minecraft.block.Block;

public class QuestPage implements IQuestPage
{
	public static List<QuestPage> PAGELIST = Arrays.asList((QuestPage) new QuestPageFarmer(), (QuestPage) new QuestPageBlacksmith(), (QuestPage) new QuestPageButcher());

	@Override
	public String getTitle()
	{
		return null;
	}

	@Override
	public List<Quest> getQuests()
	{
		return null;
	}

	@Override
	public Quest getByName(String s)
	{
		return null;
	}
}