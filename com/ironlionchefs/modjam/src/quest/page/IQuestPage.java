package com.ironlionchefs.modjam.src.quest.page;

import java.util.List;

import com.ironlionchefs.modjam.src.quest.Quest;

public interface IQuestPage
{
	public String getTitle();

	public List<Quest> getQuests();

	public Quest getByName(String s);
}