package xyz.joshstroup.mechatech.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import xyz.joshstroup.mechatech.info.BlockInfo;

public class MechaTechBlocks
{
    public static Block blockCombustionGenerator;

    public static void init()
    {
        blockCombustionGenerator = new BlockCombustionGenerator(Material.IRON, BlockInfo.BLOCK_COMBUSTIONGENERATOR_UNLOCALIZED_NAME);

        register(blockCombustionGenerator);
    }

    private static void register(Block block)
    {
        GameRegistry.register(block);

        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        GameRegistry.register(itemBlock);
    }
}
