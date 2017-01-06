package xyz.joshstroup.mechatech.render.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import xyz.joshstroup.mechatech.tileentity.TileEntityCombustionGenerator;
import xyz.joshstroup.mechatech.util.inventory.SlotOutput;

public class ContainerCombustionGenerator extends MechaTechContainer<TileEntityCombustionGenerator>
{
	public static final int FUEL = 0, OUTPUT = 1;
	
    public ContainerCombustionGenerator(InventoryPlayer playerInventory, TileEntityCombustionGenerator tile)
    {
        super(playerInventory, tile, 2);

        this.addSlotToContainer(new SlotFurnaceFuel(this.inventory, FUEL, 56, 35));
        this.addSlotToContainer(new SlotOutput(playerInventory.player, this.inventory, OUTPUT, 116, 35));

        addPlayerSlots(playerInventory);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
    	ItemStack itemstack = null;
    	Slot slot = (Slot) this.inventorySlots.get(index);
    	
    	if(slot != null && slot.getHasStack())
    	{
    		ItemStack itemstack1 = slot.getStack();
    		itemstack = itemstack1.copy();
    		
    		if(index == OUTPUT)
    		{
    			if(!this.mergeItemStack(itemstack1, OUTPUT + 1, OUTPUT + 36 + 1, true))
    			{
    				return null;
    			}

    			slot.onSlotChange(itemstack1, itemstack);
    		}
    		else if(index != FUEL)
    		{
        		if(TileEntityFurnace.isItemFuel(itemstack1))
        		{
        			if(!this.mergeItemStack(itemstack1, FUEL, FUEL + 1, false))
        			{
        				return null;
        			}
        		}
        	}
        	else if(index == FUEL)
        	{
        		if (!this.mergeItemStack(itemstack1, FUEL + 1, FUEL + 36 +1, true))
    			{
    				return null;
    			}

    			slot.onSlotChange(itemstack1, itemstack);
        	}
        	else if(index >= OUTPUT + 1 && index < OUTPUT + 28)
        	{
        		if(!this.mergeItemStack(itemstack1, OUTPUT + 28, OUTPUT + 37, false))
        		{
        			return null;
        		}
        	}
    		
    		if(itemstack1.stackSize == 0)
    		{
    			slot.putStack((ItemStack) null);
    		}
    		else
			{
    			slot.onSlotChanged();
    		}
    		
    		if(itemstack1.stackSize == itemstack.stackSize)
    		{
    			return null;
    		}

    		slot.onPickupFromSlot(playerIn, itemstack1);
    	}

    	return itemstack;
    }
}
