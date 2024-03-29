package andronomos.androtech.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class InventoryHelper {
	public static boolean isFull(IItemHandler handler) {
		AtomicBoolean isFull = new AtomicBoolean(true);

		for(int i = 0; i <= handler.getSlots() - 1; i++) {
			ItemStack itemstack = handler.getStackInSlot(i);

			if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize())
			{
				isFull.set(false);
				break;
			}
		}

		return isFull.get();
	}

	public static ItemStack insert(ItemStack stack, ItemStackHandler itemHandler, boolean simulate) {
		AtomicReference<ItemStack> returnStack = new AtomicReference<>(stack.copy());
		for(int i = 0; i < itemHandler.getSlots() && !returnStack.get().isEmpty(); ++i) {
			returnStack.set(itemHandler.insertItem(i, returnStack.get(), simulate));
		}
		return returnStack.get();
	}
}
