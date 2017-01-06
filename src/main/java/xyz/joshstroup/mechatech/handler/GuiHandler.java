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
    public static HashMap containers = new HashMap();

    private static void initGuiScreens(EntityPlayer player, World world, TileEntity tileEntity)
    {
        guiScreens.put(0, new GuiCombustionGenerator(new ContainerCombustionGenerator(player.inventory, (TileEntityCombustionGenerator) tileEntity)));

        containers.put(0, new ContainerCombustionGenerator(player.inventory, (TileEntityCombustionGenerator) tileEntity));
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z)
    {
        initGuiScreens(player, world, world.getTileEntity(new BlockPos(x, y, z)));

        if(containers.containsKey(ID))
        {
            return containers.get(ID);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z)
    {
        initGuiScreens(player, world, world.getTileEntity(new BlockPos(x, y, z)));

        if(guiScreens.containsKey(ID))
        {
            return guiScreens.get(ID);
        }

        return null;
    }
}
