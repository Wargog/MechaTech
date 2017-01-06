package xyz.joshstroup.mechatech.tileentity;

import java.util.HashMap;

import net.minecraftforge.fml.common.registry.GameRegistry;

import xyz.joshstroup.mechatech.info.TileEntityInfo;

public class MechaTechTileEntities
{
    public static HashMap<Class<? extends net.minecraft.tileentity.TileEntity>, String> tileEntities = new HashMap<Class<? extends net.minecraft.tileentity.TileEntity>, String>();

    public static void initTileEntities()
    {
        tileEntities.put(TileEntityCombustionGenerator.class, TileEntityInfo.TILE_ENTITY_COMBUSTION_GENERATOR_NAME);
    }

    public static void registerTileEntities()
    {
        for(HashMap.Entry<Class<? extends net.minecraft.tileentity.TileEntity>, String> entity : tileEntities.entrySet())
        {
            GameRegistry.registerTileEntity(entity.getKey(), entity.getValue());
        }
    }
}
