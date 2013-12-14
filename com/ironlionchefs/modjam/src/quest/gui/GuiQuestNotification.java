package com.ironlionchefs.modjam.src.quest.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.ironlionchefs.modjam.src.quest.Quest;

@SideOnly(Side.CLIENT)
public class GuiQuestNotification extends Gui
{
	private static final ResourceLocation textures = new ResourceLocation("textures/gui/achievement/achievement_background.png");
	private Minecraft theGame;
	private int windowWidth;
	private int windowHeight;
	private String getText = "Quest Completed";
	private String startText = "Quest Acquired";
	private String questName;
	private Quest theQuest;
	private long questTime;
	private RenderItem itemRender;
	private boolean haveQuest;

	public GuiQuestNotification(Minecraft par1Minecraft)
	{
		this.theGame = par1Minecraft;
		this.itemRender = new RenderItem();
	}

	public void questComplete(Quest quest)
	{
		this.questName = quest.getName();
		this.questTime = Minecraft.getSystemTime();
		this.theQuest = quest;
		this.haveQuest = false;
	}

	public void questStarted(Quest quest)
	{
		this.questName = quest.getName();
		this.questTime = Minecraft.getSystemTime();
		this.theQuest = quest;
		this.haveQuest = true;
	}

	private void updateAchievementWindowScale()
	{
		GL11.glViewport(0, 0, this.theGame.displayWidth, this.theGame.displayHeight);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		this.windowWidth = this.theGame.displayWidth;
		this.windowHeight = this.theGame.displayHeight;
		ScaledResolution scaledresolution = new ScaledResolution(this.theGame.gameSettings, this.theGame.displayWidth, this.theGame.displayHeight);
		this.windowWidth = scaledresolution.getScaledWidth();
		this.windowHeight = scaledresolution.getScaledHeight();
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double) this.windowWidth, (double) this.windowHeight, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}

	public void updateQuestWindow()
	{
		if (this.theQuest != null && this.questTime != 0L)
		{
			double d0 = (double) (Minecraft.getSystemTime() - this.questTime) / 3000.0D;
			if (!this.haveQuest && (d0 < 0.0D || d0 > 1.0D))
			{
				this.questTime = 0L;
			}
			else
			{
				this.updateAchievementWindowScale();
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDepthMask(false);
				double d1 = d0 * 2.0D;
				if (d1 > 1.0D)
				{
					d1 = 2.0D - d1;
				}
				d1 *= 4.0D;
				d1 = 1.0D - d1;
				if (d1 < 0.0D)
				{
					d1 = 0.0D;
				}
				d1 *= d1;
				d1 *= d1;
				int i = this.windowWidth - 160;
				int j = 0 - (int) (d1 * 36.0D);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				this.theGame.getTextureManager().bindTexture(textures);
				GL11.glDisable(GL11.GL_LIGHTING);
				this.drawTexturedModalRect(i, j, 96, 202, 160, 32);
				if (this.haveQuest)
				{
					this.theGame.fontRenderer.drawString(this.startText, i + 30, j + 7, -256);
					this.theGame.fontRenderer.drawString(this.questName, i + 30, j + 18, -1);
				}
				else
				{
					this.theGame.fontRenderer.drawString(this.getText, i + 30, j + 7, -256);
					this.theGame.fontRenderer.drawString(this.questName, i + 30, j + 18, -1);
				}
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_COLOR_MATERIAL);
				GL11.glEnable(GL11.GL_LIGHTING);
				this.itemRender.renderItemAndEffectIntoGUI(this.theGame.fontRenderer, this.theGame.getTextureManager(), this.theQuest.questIcon, i + 8, j + 8);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}
		}
	}
}
