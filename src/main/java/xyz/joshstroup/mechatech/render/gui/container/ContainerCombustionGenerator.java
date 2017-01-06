package xyz.joshstroup.mechatech.render.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import xyz.joshstroup.mechatech.tileentity.TileEntityCombustionGenerator;

public class ContainerCombustionGenerator extends MechaTechContainer<TileEntityCombustionGenerator>
{
    public ContainerCombustionGenerator(InventoryPlayer playerInventory, TileEntityCombustionGenerator tile)
    {
        super(playerInventory, tile, 3);

        this.addSlotToContainer(new Slot(this.inventory, 0, 56, 53));

        addPlayerSlots(playerInventory);
    }
}
