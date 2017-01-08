package xyz.joshstroup.mechatech;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import xyz.joshstroup.mechatech.handler.ConfigHandler;
import xyz.joshstroup.mechatech.handler.GuiHandler;
import xyz.joshstroup.mechatech.handler.RecipeHandler;
import xyz.joshstroup.mechatech.info.ModInfo;
import xyz.joshstroup.mechatech.item.MechaTechItems;
import xyz.joshstroup.mechatech.block.MechaTechBlocks;
import xyz.joshstroup.mechatech.proxy.CommonProxy;
import xyz.joshstroup.mechatech.tileentity.MechaTechTileEntities;

@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class MechaTech
{
    @SidedProxy(clientSide = "xyz.joshstroup.mechatech.proxy.ClientProxy", serverSide = "xyz.joshstroup.mechatech.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Instance(value = ModInfo.MODID)
    public static MechaTech INSTANCE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigHandler.init(event.getSuggestedConfigurationFile());

        System.out.println(ModInfo.NAME + " v" + ModInfo.VERSION + " is in preInit with ID " + ModInfo.MODID);

        MechaTechItems.init();
        MechaTechBlocks.init();

        RecipeHandler.init();

        MechaTechTileEntities.initTileEntities();
        
        MechaTechTileEntities.registerTileEntities();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println("In initialization, loading event handlers, renderers, and GUIs...");

        // GuiHandlers, TileEntities, EventHandlers, Block Models, Packets
        NetworkRegistry.INSTANCE.registerGuiHandler(MechaTech.INSTANCE, new GuiHandler());

        MechaTechItems.initRenderers();
        MechaTechBlocks.initRenderers();

        System.out.println("Finished initialization event, mod is ready to go!");
    }
}
