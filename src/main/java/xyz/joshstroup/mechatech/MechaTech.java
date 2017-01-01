package xyz.joshstroup.mechatech;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import xyz.joshstroup.mechatech.handler.ConfigHandler;
import xyz.joshstroup.mechatech.handler.RecipeHandler;
import xyz.joshstroup.mechatech.item.MechaTechItems;
import xyz.joshstroup.mechatech.block.MechaTechBlocks;

@Mod(modid = MechaTech.MODID, name = MechaTech.NAME, version = MechaTech.VERSION)
public class MechaTech
{
    public static final String MODID = "mechatech";
    public static final String NAME = "Mecha Tech";
    public static final String VERSION = "0.1";

    @Instance(value = MODID)
    public static MechaTech INTSANCE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigHandler.init(event.getSuggestedConfigurationFile());

        MechaTechItems.register();
        MechaTechBlocks.register();

        RecipeHandler.init();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // Do sync clock event handler stuff

        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
    }
}
