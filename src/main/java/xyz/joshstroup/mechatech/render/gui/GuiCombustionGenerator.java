package xyz.joshstroup.mechatech.render.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;

import xyz.joshstroup.mechatech.info.ModInfo;
import xyz.joshstroup.mechatech.render.gui.container.ContainerCombustionGenerator;

public class GuiCombustionGenerator extends GuiContainer
{
    private ContainerCombustionGenerator container;
    private static final ResourceLocation background = new ResourceLocation("mechatech:textures/gui/container/combustion_generator.png");

    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;

    public GuiCombustionGenerator(ContainerCombustionGenerator container)
    {
        super(container);

        this.container = container;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void drawScreen(int x, int y, float ticks)
    {
    	super.drawScreen(x, y, ticks);
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    @Override
    public void keyTyped(char c, int key)
    {
        try {
            super.keyTyped(c, key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }
}
