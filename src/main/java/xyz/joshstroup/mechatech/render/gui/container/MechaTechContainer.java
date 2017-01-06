package xyz.joshstroup.mechatech.render.gui.container;

import java.util.ArrayList;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import xyz.joshstroup.mechatech.util.inventory.InventoryTileEntity;

public class MechaTechContainer<T extends TileEntity> extends Container
{
    private T tileEntity;

    public IInventory inventory;
    public int slotCount;
    public ArrayList<Slot> playerInventorySlots = new ArrayList<Slot>();

    public MechaTechContainer(InventoryPlayer playerInventory, T tile, int slotCount)
    {
        this.tileEntity = tile;
        this.slotCount = slotCount;
        this.inventory = new InventoryTileEntity(tile, slotCount);
    }

    public void addPlayerSlots(InventoryPlayer playerInventory)
    {
        // Add hotbar slots
        for (int i = 0; i < 9; i++)
        {
            this.playerInventorySlots.add(new Slot(playerInventory, i, 8 + i * 18, 142));
            addSlotToContainer(this.playerInventorySlots.get(i));
        }
        // Add inventory slots
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                this.playerInventorySlots.add(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
                addSlotToContainer(this.playerInventorySlots.get(j + i * 9));
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(EntityPlayer player)
    {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack slotClick(int id, int button, ClickType clickType, EntityPlayer player)
    {
        // TODO: Actually add and remove stuff from player's inventory
        return super.slotClick(id, button, clickType, player);
    }

//    @Override
//    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
//    {
//
//    }
}
