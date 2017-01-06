package xyz.joshstroup.mechatech.handler;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import xyz.joshstroup.mechatech.render.gui.GuiCombustionGenerator;
import xyz.joshstroup.mechatech.render.gui.container.ContainerCombustionGenerator;
import xyz.joshstroup.mechatech.tileentity.TileEntityCombustionGenerator;

public class GuiHandler implements IGuiHandler
{
    public static HashMap guiScreens = new HashMap();

    private static void initGuiScreens(EntityPlayer player, World world, TileEntity tileEntity)
    {
        guiScreens.put(0, new GuiCombustionGenerator(new ContainerCombustionGenerator(player.inventory, (TileEntityCombustionGenerator) tileEntity)));
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z)
    {
        try {
            initGuiScreens(player, world, world.getTileEntity(new BlockPos(x, y, z)));
        } catch (NullPointerException e) {
            System.out.println("FAILED TO GET TILEENTITY FOR GUI. THIS IS MOST LIKELY THE FAULT OF THE MOD AUTHORS, PLEASE REPORT YOUR CRASH LOG. ABORTING...");
            System.out.println(e.toString());

            System.exit(1);
        }

        if(guiScreens.containsKey(ID))
        {
            return guiScreens.get(ID);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z)
    {
        try {
            initGuiScreens(player, world, world.getTileEntity(new BlockPos(x, y, z)));
        } catch (NullPointerException e) {
            System.out.println("FAILED TO GET TILEENTITY FOR GUI. THIS IS MOST LIKELY THE FAULT OF THE MOD AUTHORS, PLEASE REPORT YOUR CRASH LOG. ABORTING...");
            System.out.println(e.toString());

            System.exit(1);
        }

        if(guiScreens.containsKey(ID))
        {
            return guiScreens.get(ID);
        }

        return null;
    }
}
