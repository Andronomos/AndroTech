package andronomos.androtech.inventory;

import andronomos.androtech.Const;
import andronomos.androtech.block.entity.MobKillingPadBE;
import andronomos.androtech.registry.ModBlocks;
import andronomos.androtech.registry.ModContainers;
import andronomos.androtech.util.EnchantmentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MobKillingPadContainer extends BaseContainerMenu {
    private BlockEntity blockEntity;

    public MobKillingPadContainer(int windowId, BlockPos pos, Inventory inventory) {
        super(ModContainers.MOB_KILLING_PAD.get(), windowId, inventory);

        blockEntity =  player.getCommandSenderWorld().getBlockEntity(pos);

        if(blockEntity != null && blockEntity instanceof MobKillingPadBE) {
            final MobKillingPadBE entity = (MobKillingPadBE) blockEntity;
            entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler -> {
                addSlot(new SlotItemHandler(itemHandler, 0, Const.CONTAINER_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 3, 30));
                addSlot(new SlotItemHandler(itemHandler, 1, Const.CONTAINER_SLOT_X_OFFSET + Const.SCREEN_SLOT_SIZE * 5, 30));
            });
        }

        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.MOB_KILLING_PAD.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerEntity, int slotId) {
        ItemStack itemstack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(slotId);

		int containerEnd = Const.CONTAINER_GENERIC_SIZE;

		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();

			if (slotId == 0 || slotId == 1) {
				if (!this.moveItemStackTo(itemstack1, 2, 37, true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if(itemstack1.getItem() == Items.ENCHANTED_BOOK) {
					if(EnchantmentUtil.hasEnchantment(Enchantments.MOB_LOOTING, itemstack1) || EnchantmentUtil.hasEnchantment(Enchantments.FIRE_ASPECT, itemstack1)) {
						if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
							return ItemStack.EMPTY;
						}
					}
				} else if (!this.moveItemStackTo(itemstack1, 0, containerEnd, false)) {
					return ItemStack.EMPTY;
				}
			}
		}

        return itemstack;
    }
}
