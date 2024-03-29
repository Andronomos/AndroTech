package andronomos.androtech.item.tools;

import andronomos.androtech.Constants;
import andronomos.androtech.config.AndroTechConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class NaniteEnhancedAxe extends AxeItem {
	public int tickDelay = Constants.TicksInSeconds.THREE;
	public int tickCounter = 0;

	public NaniteEnhancedAxe(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return AndroTechConfig.NANITE_AXE_DURABILITY.get();
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int itemSlot, boolean isSelected) {
		if (level.isClientSide || !(entity instanceof Player)) return;
		if(this.tickCounter == this.tickDelay) {
			this.tickCounter = 0;
			if(stack.isDamaged()) {
				stack.setDamageValue(stack.getDamageValue() - AndroTechConfig.NANITE_REPAIR_RATE.get());
			}
		}
		this.tickCounter++;
	}
}
