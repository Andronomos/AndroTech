package andronomos.androtech.block.pad.mobkillingpad;

import andronomos.androtech.Const;
import andronomos.androtech.inventory.BaseContainerMenu;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MobKillingPadContainer extends BaseContainerMenu {
    private BlockEntity blockEntity;

    public MobKillingPadContainer(int windowId, BlockPos pos, Inventory inventory) {
        super(ModContainers.MOB_KILLING_PAD.get(), windowId, inventory);

        blockEntity =  player.getCommandSenderWorld().getBlockEntity(pos);

        if(blockEntity != null && blockEntity instanceof MobKillingPadBE mobKillingPad) {
			mobKillingPad.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
                addSlot(new SlotItemHandler(itemHandler, 0, Const.CONTAINER_GENERIC_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 4, 30));
            });
        }

        layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, Const.VANILLA_INVENTORY_Y);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.MOB_KILLING_PAD.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotId) {
        ItemStack itemstack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(slotId);

		int containerEnd = MobKillingPadBE.PAD_SLOTS;

		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();

			if (slotId <= containerEnd) {
				if (!this.moveItemStackTo(itemstack1, containerEnd, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if(itemstack1.getItem() instanceof SwordItem) {
					if (!this.moveItemStackTo(itemstack1, 0, containerEnd, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
		}

        return itemstack;
    }
}
