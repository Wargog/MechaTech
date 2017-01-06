package xyz.joshstroup.mechatech.util.inventory;

import java.util.HashMap;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class InventoryTileEntity implements IInventory
{
    private TileEntity tileEntity;
    private IInventory inventory;
    private String name;
    private HashMap<Integer, Integer> fields = new HashMap<Integer, Integer>();


    public InventoryTileEntity(TileEntity tileEntity, int slotCount)
    {
        this.tileEntity = tileEntity;
        this.name = tileEntity.getClass().getName();
        this.inventory = new InventoryBasic(getDisplayName(), slotCount);
    }

    @Override
    @MethodsReturnNonnullByDefault
    public String getName()
    {
        return this.name;
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    @MethodsReturnNonnullByDefault
    public ITextComponent getDisplayName()
    {
        return new TextComponentString(this.name);
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.getSizeInventory();
    }

    @Override
    @Nullable
    public ItemStack getStackInSlot(int slot)
    {
        return inventory.getStackInSlot(slot);
    }

    @Override
    @Nullable
    public ItemStack decrStackSize(int slot, int count)
    {
        ItemStack stack = inventory.getStackInSlot(slot);

        if(stack != null)
        {
            if(stack.stackSize<=count)
            {
                inventory.setInventorySlotContents(slot, null);
            }
            else
            {
                stack = stack.splitStack(count);

                if(stack.stackSize==0)
                {
                    inventory.setInventorySlotContents(slot, null);
                }
            }
        }

        return stack;
    }

    @Override
    @Nullable
    public ItemStack removeStackFromSlot(int slot)
    {
        ItemStack stack;
        try {
            stack = inventory.getStackInSlot(slot).copy();
        } catch (NullPointerException e) {
            stack = inventory.getStackInSlot(slot);
        }
        inventory.setInventorySlotContents(slot, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {
        tileEntity.markDirty();
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return !tileEntity.isInvalid() && tileEntity.getDistanceSq(player.posX, player.posY, player.posZ) < 64;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void openInventory(EntityPlayer player) {}

    @Override
    @ParametersAreNonnullByDefault
    public void closeInventory(EntityPlayer player) {}

    @Override
    @ParametersAreNonnullByDefault
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return inventory.isItemValidForSlot(slot, stack);
    }

    @Override
    public int getField(int id)
    {
        return fields.get(id);
    }

    @Override
    public void setField(int id, int value)
    {
        fields.put(id, value);
    }

    @Override
    public int getFieldCount()
    {
        return fields.size();
    }

    @Override
    public void clear()
    {
        // Cycle through slots by slot length and clear each slot
        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            inventory.setInventorySlotContents(i, null);
        }
    }
}
