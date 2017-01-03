package xyz.joshstroup.mechatech.render.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;

public class GuiCombustionGenerator extends GuiScreen
{
    public GuiCombustionGenerator()
    {
        super();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void drawScreen(int x, int y, float ticks)
    {
        GL11.glEnable(GL11.GL_BLEND);
        drawDefaultBackground();
        GL11.glDisable(GL11.GL_BLEND);

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
