package andronomos.androtech.item;

import andronomos.androtech.Constants;
import andronomos.androtech.config.AndroTechConfig;
import andronomos.androtech.item.base.TickingItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ItemAttractionEmitterItem extends TickingItem {
	private final int range = AndroTechConfig.ITEM_ATTRACTION_EMITTER_RANGE.get();

	public ItemAttractionEmitterItem(Properties properties) {
		super(properties, AndroTechConfig.ITEM_ATTRACTION_EMITTER_TAKE_DAMAGE.get());
	}

	@Override
	public int getTickDelay() {
		return Constants.TicksInSeconds.ONE;
	}

	@Override
	public void onTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		ServerPlayer player = (ServerPlayer)entity;
		AABB area = player.getBoundingBox().inflate(range);
		attractItems(stack, level, player, area);
		attractExperience(stack, level, player, area);
	}

	private void attractItems(ItemStack stack, Level level, ServerPlayer player, AABB area) {
		List<ItemEntity> itemsInRange = level.getEntitiesOfClass(ItemEntity.class, area,
				item -> !item.getPersistentData().contains("PreventRemoteMovement") &&
						(item.getOwner() == null || !item.getOwner().getUUID().equals(player.getUUID()) || !item.isPickable())
		);

		itemsInRange.forEach(item -> {
			if(!isBroken(stack)) {
				item.setNoPickUpDelay();
				item.playerTouch(player);
				if(hasDurability) {
					doDamage(stack, player, AndroTechConfig.ITEM_ATTRACTION_EMITTER_DAMAGE_RATE.get());
				}
			}
		});
	}

	private void attractExperience(ItemStack stack, Level level, ServerPlayer player, AABB area) {
		List<ExperienceOrb> experienceOrbs = level.getEntitiesOfClass(ExperienceOrb.class, area);
		experienceOrbs.forEach(orb -> {
			if(!isBroken(stack)) {
				player.takeXpDelay = 0;
				orb.playerTouch(player);
			}
		});
	}
}
