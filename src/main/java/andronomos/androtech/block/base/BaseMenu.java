package andronomos.androtech.block.base;

import andronomos.androtech.AndroTech;
import andronomos.androtech.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

import static andronomos.androtech.Constants.*;

public abstract class BaseMenu extends AbstractContainerMenu {
	public final BlockEntity blockEntity;
	protected final Level level;
	protected final Inventory inventory;
	protected final Player player;
	protected static int blockEntitySlotCount;

	private final ContainerData data;

	public BaseMenu(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
		super(menuType, containerId);
		checkContainerSize(inventory, inventory.getContainerSize());
		blockEntity = entity;
		this.level = inventory.player.level();
		this.data = data;
		this.inventory = inventory;
		this.player = inventory.player;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		Slot sourceSlot = slots.get(slotIndex);
		if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack sourceStackCopy = sourceStack.copy();

		if (slotIndex < VANILLA_INVENTORY_FIRST_SLOT_INDEX + PLAYER_INVENTORY_SLOT_COUNT) {
			if (!moveItemStackTo(sourceStack, PLAYER_INVENTORY_SLOT_COUNT, PLAYER_INVENTORY_SLOT_COUNT + blockEntitySlotCount, false)) {
				return ItemStack.EMPTY;
			}
		} else if (slotIndex < PLAYER_INVENTORY_SLOT_COUNT + blockEntitySlotCount) {
			if (!moveItemStackTo(sourceStack, VANILLA_INVENTORY_FIRST_SLOT_INDEX, VANILLA_INVENTORY_FIRST_SLOT_INDEX + PLAYER_INVENTORY_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			return ItemStack.EMPTY;
		}

		if (sourceStack.getCount() == 0) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}
		sourceSlot.onTake(player, sourceStack);
		return sourceStackCopy;
	}

	//@Override
	//public void clicked(int slotId, int mouseButton, ClickType clickType, Player player) {
	//	super.clicked(slotId, mouseButton, clickType, player);
	//
	//	if (player.level().isClientSide) {
	//		return;
	//	}
	//
	//	AndroTech.LOGGER.info(String.format("inventory.getContainerSize: %s", inventory.getContainerSize()));
	//	AndroTech.LOGGER.info(String.format("blockEntitySlotCount: %s", blockEntitySlotCount));
	//
	//	player.sendSystemMessage(Component.literal(String.format("Slotid: %s", slotId)));
	//	//player.sendSystemMessage(Component.literal(String.format("mouseButton: %s", mouseButton)));
	//}

	protected void setSlotIndexes(int slotCount) {
		blockEntitySlotCount = slotCount;
	}

	protected void addPlayerInventory() {
		addPlayerInventory(Constants.PLAYER_INVENTORY_MENU_Y_OFFSET);
	}

	protected void addPlayerInventory(int yOffset) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(inventory, j + i * 9 + 9, Constants.MENU_SLOT_X_OFFSET + j * Constants.SCREEN_SLOT_SIZE, yOffset + i * 18));
			}
		}
	}

	protected void addPlayerHotbar() {
		addPlayerHotbar(Constants.PLAYER_HOTBAR_MENU_Y_OFFSET);
	}

	protected void addPlayerHotbar(int yOffset) {
		for (int i = 0; i < 9; ++i) {
			addSlot(new Slot(inventory, i, Constants.MENU_SLOT_X_OFFSET + i * Constants.SCREEN_SLOT_SIZE, yOffset));
		}
	}

	protected void addInventory(IItemHandler handler) {
		for (int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				addSlot(new SlotItemHandler(handler, x + y * 9,
						Constants.MENU_SLOT_X_OFFSET + x * Constants.SCREEN_SLOT_SIZE,
						Constants.SCREEN_SLOT_SIZE + y * Constants.SCREEN_SLOT_SIZE));
			}
		}
	}

	protected void addLargeInventory(IItemHandler handler) {
		for (int y = 0; y < 6; y++) {
			for(int x = 0; x < 9; x++) {
				addSlot(new SlotItemHandler(handler, x + y * 9,
						Constants.MENU_SLOT_X_OFFSET + x * Constants.SCREEN_SLOT_SIZE,
						Constants.SCREEN_SLOT_SIZE + y * Constants.SCREEN_SLOT_SIZE));
			}
		}
	}
}
