package andronomos.androtech.block.damagepad;

import andronomos.androtech.block.BaseMenu;
import andronomos.androtech.inventory.server.RestrictedSlotHandler;
import andronomos.androtech.registry.BlockRegistry;
import andronomos.androtech.registry.ItemRegistry;
import andronomos.androtech.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class DamagePadMenu extends BaseMenu {
	public DamagePadMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
		this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(inventory.getContainerSize()));
	}

	public DamagePadMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(MenuTypeRegistry.DAMAGE_PAD_MENU.get(), containerId, inventory, entity, data);
		addPlayerInventory();
		addPlayerHotbar();
		if(entity instanceof DamagePadBlockEntity damagePadBlockEntity) {
			damagePadBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
				addSlot(new RestrictedSlotHandler(itemHandler, 0, 56, 30, ItemRegistry.SHARPNESS_AUGMENT.get().getDefaultInstance(), DamagePadBlock.AUGMENT_STACK_LIMIT));
				addSlot(new RestrictedSlotHandler(itemHandler, 1, 81, 30, ItemRegistry.LOOTING_AUGMENT.get().getDefaultInstance(), DamagePadBlock.AUGMENT_STACK_LIMIT));
				addSlot(new RestrictedSlotHandler(itemHandler, 2, 106, 30, ItemRegistry.FIRE_AUGMENT.get().getDefaultInstance(), DamagePadBlock.AUGMENT_STACK_LIMIT));
				//addSlot(new RestrictedSlotHandler(itemHandler, 3, 118, 30, ItemRegistry.SMITE_AUGMENT.get().getDefaultInstance(), DamagePadBlock.AUGMENT_STACK_LIMIT));
			});
		}
		setSlotIndexes(DamagePadBlock.SLOTS);
		//addDataSlots(data);
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(this.level, blockEntity.getBlockPos()), player, BlockRegistry.DAMAGE_PAD.get());
	}
}
