package com.ironlionchefs.modjam.src.quest.gui;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.Village;
import net.minecraftforge.common.AchievementPage;

import org.apache.commons.lang3.Validate;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.ironlionchefs.modjam.src.QuestMod;
import com.ironlionchefs.modjam.src.quest.Quest;
import com.ironlionchefs.modjam.src.quest.networking.client.ClientPacketQuestCompletionStatus;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketRequestCurrentQuest;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketRequestQuestCompletion;
import com.ironlionchefs.modjam.src.quest.networking.server.ServerPacketPlayerBeginQuest;
import com.ironlionchefs.modjam.src.quest.page.QuestPage;
import com.ironlionchefs.modjam.src.quest.page.QuestPageAgriculture;

@SideOnly(Side.CLIENT)
public class GuiQuestMap extends GuiScreen
{
	private static final int guiMapTop = AchievementList.minDisplayColumn * 24 - 112;
	private static final int guiMapLeft = AchievementList.minDisplayRow * 24 - 112;
	private static final int guiMapBottom = AchievementList.maxDisplayColumn * 24 - 77;
	private static final int guiMapRight = AchievementList.maxDisplayRow * 24 - 77;
	private static final ResourceLocation questTextures = new ResourceLocation("textures/gui/achievement/achievement_background.png");
	protected int paneWidth = 256;
	protected int paneHeight = 202;
	protected int mouseX, mouseY, isMouseButtonDown;
	protected double field_74117_m, field_74115_n, guiMapX, guiMapY, field_74124_q, field_74123_r;
	private int currentQuestSet = 0;
	private GuiSmallButton buttonChangeQuestSet;
	private EntityPlayer entityPlayer;
	private QuestPage page;

	public GuiQuestMap(EntityPlayer ep, QuestPage page)
	{
		this.field_74117_m = this.guiMapX = this.field_74124_q = (double) (QuestPageAgriculture.logging.x * 24 / 2 - 12);
		this.field_74115_n = this.guiMapY = this.field_74123_r = (double) (QuestPageAgriculture.logging.y * 24 - 20 / 2);
		entityPlayer = ep;
		this.page = page;
	}

	public QuestPage getCurrentQuestSet()
	{
		return page;
	}

	public boolean getQuestComplete(Quest quest)
	{
		return quest.complete;
	}

	public boolean canPerformQuest(Quest quest)
	{
		if (quest.parentQuest == null)
		{
			return true;
		}
		else
		{
			return getQuestComplete(quest.parentQuest);
		}
	}

	public void initGui()
	{
		PacketDispatcher.sendPacketToServer(new ServerPacketRequestQuestCompletion(entityPlayer.username).makePacket());
		PacketDispatcher.sendPacketToServer(new ServerPacketRequestCurrentQuest(entityPlayer.username).makePacket());
		this.buttonList.clear();
		this.buttonList.add(new GuiSmallButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, I18n.getString("gui.done")));
		this.buttonList.add(buttonChangeQuestSet = new GuiSmallButton(2, (width - paneWidth) / 2 + 24, height / 2 + 74, 125, 20, getCurrentQuestSet().getTitle()));
		buttonChangeQuestSet.enabled = false;
	}

	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.id == 1)
		{
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
			super.actionPerformed(par1GuiButton);
			return;
		}
		else if (par1GuiButton.id == 2)
		{
			currentQuestSet++;
			if (currentQuestSet >= QuestPage.PAGELIST.size())
				currentQuestSet = 0;
			buttonChangeQuestSet.displayString = getCurrentQuestSet().getTitle();
			super.actionPerformed(par1GuiButton);
			return;
		}
	}

	protected void keyTyped(char par1, int par2)
	{
		super.keyTyped(par1, par2);
	}

	public void drawScreen(int par1, int par2, float par3)
	{
		if (Mouse.isButtonDown(0))
		{
			int k = (this.width - this.paneWidth) / 2;
			int l = (this.height - this.paneHeight) / 2;
			int i1 = k + 8;
			int j1 = l + 17;
			if ((this.isMouseButtonDown == 0 || this.isMouseButtonDown == 1) && par1 >= i1 && par1 < i1 + 224 && par2 >= j1 && par2 < j1 + 155)
			{
				if (this.isMouseButtonDown == 0)
				{
					this.isMouseButtonDown = 1;
				}
				else
				{
					this.guiMapX -= (double) (par1 - this.mouseX);
					this.guiMapY -= (double) (par2 - this.mouseY);
					this.field_74124_q = this.field_74117_m = this.guiMapX;
					this.field_74123_r = this.field_74115_n = this.guiMapY;
				}
				this.mouseX = par1;
				this.mouseY = par2;
			}
			if (this.field_74124_q < (double) guiMapTop)
			{
				this.field_74124_q = (double) guiMapTop;
			}
			if (this.field_74123_r < (double) guiMapLeft)
			{
				this.field_74123_r = (double) guiMapLeft;
			}
			if (this.field_74124_q >= (double) guiMapBottom)
			{
				this.field_74124_q = (double) (guiMapBottom - 1);
			}
			if (this.field_74123_r >= (double) guiMapRight)
			{
				this.field_74123_r = (double) (guiMapRight - 1);
			}
		}
		else
		{
			this.isMouseButtonDown = 0;
		}
		this.drawDefaultBackground();
		this.genQuestOverlay(par1, par2, par3);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		this.drawTitle();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public void updateScreen()
	{
		if (QuestMod.currentQuestForPlayer != null)
		{
			entityPlayer.openGui(QuestMod.instance, 5, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
		}
		this.field_74117_m = this.guiMapX;
		this.field_74115_n = this.guiMapY;
		double d0 = this.field_74124_q - this.guiMapX;
		double d1 = this.field_74123_r - this.guiMapY;
		if (d0 * d0 + d1 * d1 < 4.0D)
		{
			this.guiMapX += d0;
			this.guiMapY += d1;
		}
		else
		{
			this.guiMapX += d0 * 0.85D;
			this.guiMapY += d1 * 0.85D;
		}
	}

	protected void drawTitle()
	{
		int i = (this.width - this.paneWidth) / 2;
		int j = (this.height - this.paneHeight) / 2;
		this.fontRenderer.drawString("Quests", i + 15, j + 5, 4210752);
	}

	protected void genQuestOverlay(int par1, int par2, float par3)
	{
		int k = MathHelper.floor_double(this.field_74117_m + (this.guiMapX - this.field_74117_m) * (double) par3);
		int l = MathHelper.floor_double(this.field_74115_n + (this.guiMapY - this.field_74115_n) * (double) par3);
		if (k < guiMapTop)
		{
			k = guiMapTop;
		}
		if (l < guiMapLeft)
		{
			l = guiMapLeft;
		}
		if (k >= guiMapBottom)
		{
			k = guiMapBottom - 1;
		}
		if (l >= guiMapRight)
		{
			l = guiMapRight - 1;
		}
		int i1 = (this.width - this.paneWidth) / 2;
		int j1 = (this.height - this.paneHeight) / 2;
		int k1 = i1 + 16;
		int l1 = j1 + 17;
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_GEQUAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		int i2 = k + 288 >> 4;
		int j2 = l + 288 >> 4;
		int k2 = (k + 288) % 16;
		int l2 = (l + 288) % 16;
		boolean flag = true;
		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		boolean flag4 = true;
		Random random = new Random();
		int i3;
		int j3;
		int k3;
		for (i3 = 0; i3 * 16 - l2 < 155; ++i3)
		{
			float f1 = 0.6F - (float) (j2 + i3) / 25.0F * 0.3F;
			GL11.glColor4f(f1, f1, f1, 1.0F);
			for (k3 = 0; k3 * 16 - k2 < 224; ++k3)
			{
				random.setSeed((long) (1234 + i2 + k3));
				random.nextInt();
				j3 = random.nextInt(1 + j2 + i3) + (j2 + i3) / 2;
				Icon icon = Block.sand.getIcon(0, 0);
				if (j3 <= 37 && j2 + i3 != 35)
				{
					if (j3 == 22)
					{
						if (random.nextInt(2) == 0)
						{
							icon = Block.oreDiamond.getIcon(0, 0);
						}
						else
						{
							icon = Block.oreRedstone.getIcon(0, 0);
						}
					}
					else if (j3 == 10)
					{
						icon = Block.oreIron.getIcon(0, 0);
					}
					else if (j3 == 8)
					{
						icon = Block.oreCoal.getIcon(0, 0);
					}
					else if (j3 > 4)
					{
						icon = Block.stone.getIcon(0, 0);
					}
					else if (j3 > 0)
					{
						icon = Block.dirt.getIcon(0, 0);
					}
				}
				else
				{
					icon = Block.bedrock.getIcon(0, 0);
				}
				this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
				this.drawTexturedModelRectFromIcon(k1 + k3 * 16 - k2, l1 + i3 * 16 - l2, icon, 16, 16);
			}
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		int l3;
		int i4;
		int j4;
		List<Quest> questList = getCurrentQuestSet().getQuests();
		for (i3 = 0; i3 < questList.size(); ++i3)
		{
			Quest quest = questList.get(i3);
			if (quest.parentQuest != null && questList.contains(quest.parentQuest))
			{
				k3 = quest.x * 24 - k + 11 + k1;
				j3 = quest.y * 24 - l + 11 + l1;
				j4 = quest.parentQuest.x * 24 - k + 11 + k1;
				l3 = quest.parentQuest.y * 24 - l + 11 + l1;
				boolean flag5 = this.getQuestComplete(quest);
				boolean flag6 = this.canPerformQuest(quest);
				i4 = Math.sin((double) (Minecraft.getSystemTime() % 600L) / 600.0D * Math.PI * 2.0D) > 0.6D ? 255 : 130;
				int k4 = -16777216;
				if (flag5)
				{
					k4 = -9408400;
				}
				else if (flag6)
				{
					k4 = 65280 + (i4 << 24);
				}
				this.drawHorizontalLine(k3, j4, j3, k4);
				this.drawVerticalLine(j4, j3, l3, k4);
			}
		}
		Quest quest1 = null;
		RenderItem renderitem = new RenderItem();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		int l4;
		int i5;
		for (k3 = 0; k3 < questList.size(); ++k3)
		{
			Quest quest2 = (Quest) questList.get(k3);
			j4 = quest2.x * 24 - k;
			l3 = quest2.y * 24 - l;
			if (j4 >= -24 && l3 >= -24 && j4 <= 224 && l3 <= 155)
			{
				float f2;
				if (this.getQuestComplete(quest2))
				{
					f2 = 1.0F;
					GL11.glColor4f(f2, f2, f2, 1.0F);
				}
				else if (this.canPerformQuest(quest2))
				{
					f2 = Math.sin((double) (Minecraft.getSystemTime() % 600L) / 600.0D * Math.PI * 2.0D) < 0.6D ? 0.6F : 0.8F;
					GL11.glColor4f(f2, f2, f2, 1.0F);
				}
				else
				{
					f2 = 0.3F;
					GL11.glColor4f(f2, f2, f2, 1.0F);
				}
				this.mc.getTextureManager().bindTexture(questTextures);
				i5 = k1 + j4;
				l4 = l1 + l3;
				this.drawTexturedModalRect(i5 - 2, l4 - 2, 0, 202, 26, 26);
				if (!this.canPerformQuest(quest2))
				{
					float f3 = 0.1F;
					GL11.glColor4f(f3, f3, f3, 1.0F);
					renderitem.renderWithColor = false;
				}
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_CULL_FACE);
				renderitem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), quest2.questIcon, i5 + 3, l4 + 3);
				GL11.glDisable(GL11.GL_LIGHTING);
				if (!this.canPerformQuest(quest2))
				{
					renderitem.renderWithColor = true;
				}
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				if (par1 >= k1 && par2 >= l1 && par1 < k1 + 224 && par2 < l1 + 155 && par1 >= i5 && par1 <= i5 + 22 && par2 >= l4 && par2 <= l4 + 22)
				{
					quest1 = quest2;
				}
			}
		}
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(questTextures);
		this.drawTexturedModalRect(i1, j1, 0, 0, this.paneWidth, this.paneHeight);
		GL11.glPopMatrix();
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		super.drawScreen(par1, par2, par3);
		if (quest1 != null)
		{
			String s = I18n.getString(quest1.getName());
			String s1 = quest1.getDescription();
			j4 = par1 + 12;
			l3 = par2 - 4;
			if (this.canPerformQuest(quest1))
			{
				i5 = Math.max(this.fontRenderer.getStringWidth(s), 120);
				l4 = this.fontRenderer.splitStringWidth(s1, i5);
				if (this.getQuestComplete(quest1))
				{
					l4 += 12;
				}
				this.drawGradientRect(j4 - 3, l3 - 3, j4 + i5 + 3, l3 + l4 + 3 + 12, -1073741824, -1073741824);
				this.fontRenderer.drawSplitString(s1, j4, l3 + 12, i5, -6250336);
				if (this.getQuestComplete(quest1))
				{
					this.fontRenderer.drawStringWithShadow("Completed!", j4, l3 + l4 + 4, -7302913);
				}
				else
				{
					if (this.isMouseButtonDown == 1)
					{
						this.mc.displayGuiScreen((GuiScreen) null);
						this.mc.setIngameFocus();
						quest1.onQuestBegin(entityPlayer, entityPlayer.worldObj);
					}
				}
			}
			else
			{
				i5 = Math.max(this.fontRenderer.getStringWidth(s), 120);
				String s2 = "Requires: " + quest1.parentQuest.getName();
				i4 = this.fontRenderer.splitStringWidth(s2, i5);
				this.drawGradientRect(j4 - 3, l3 - 3, j4 + i5 + 3, l3 + i4 + 12 + 3, -1073741824, -1073741824);
				this.fontRenderer.drawSplitString(s2, j4, l3 + 12, i5, -9416624);
			}
			this.fontRenderer.drawStringWithShadow(s, j4, l3, this.canPerformQuest(quest1) ? (-1) : (-8355712));
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.disableStandardItemLighting();
	}

	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
