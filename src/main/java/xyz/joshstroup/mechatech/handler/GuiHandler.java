package xyz.joshstroup.mechatech.handler;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import xyz.joshstroup.mechatech.render.gui.GuiCombustionGenerator;

public class GuiHandler implements IGuiHandler
{
    public static HashMap guiScreens = new HashMap();

    public static void initGuiScreens()
    {
        guiScreens.put(0, new GuiCombustionGenerator());
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z)
    {
        if(guiScreens.containsKey(ID))
        {
            return guiScreens.get(ID);
        }

        return null;
    }
}
