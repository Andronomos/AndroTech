package andronomos.androtech.block.ItemIncinerator;

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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ItemIncineratorContainer extends BaseContainerMenu {
    private BlockEntity blockEntity;

    public ItemIncineratorContainer(int windowId, BlockPos pos, Inventory inventory) {
        super(ModContainers.ITEM_INCINERATOR.get(), windowId, inventory);

        blockEntity = this.player.getCommandSenderWorld().getBlockEntity(pos);

        if(blockEntity != null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0,80, 30));
            });
        }

        layoutPlayerInventorySlots(Const.VANILLA_INVENTORY_X, Const.VANILLA_INVENTORY_Y);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.ITEM_INCINERATOR.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        final Slot slot = this.slots.get(slotIndex);
        if(slotIndex > 0 && this.getSlot(0).mayPlace(slot.getItem())) {
            slot.set(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }
}
