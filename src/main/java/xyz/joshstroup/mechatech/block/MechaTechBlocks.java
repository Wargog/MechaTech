package xyz.joshstroup.mechatech.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import xyz.joshstroup.mechatech.info.BlockInfo;
import xyz.joshstroup.mechatech.info.ModInfo;


public class MechaTechBlocks
{
    private static ArrayList<Block> blockList = new ArrayList<Block>();

    public static Block blockCombustionGenerator;

    public static ArrayList<Block> getBlockList()
    {
        return blockList;
    }

    public static void init()
    {
        blockCombustionGenerator = new BlockCombustionGenerator(Material.IRON, BlockInfo.BLOCK_COMBUSTIONGENERATOR_UNLOCALIZED_NAME);

        register(blockCombustionGenerator);
    }

    public static void initRenderers()
    {
        for (Block block : blockList) {
            registerRenderer(block);
        }
    }

    private static void register(Block block)
    {
        blockList.add(block);

        GameRegistry.register(block);

        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        GameRegistry.register(itemBlock);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRenderer(Block block)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),
                                                                               0,
                                                                               new ModelResourceLocation(ModInfo.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
    }
}
