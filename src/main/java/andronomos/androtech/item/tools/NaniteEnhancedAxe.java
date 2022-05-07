package andronomos.androtech.item.tools;

import andronomos.androtech.Const;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class NaniteEnhancedAxe extends AxeItem {
	public int tickDelay = Const.TicksInSeconds.THREESECONDS;
	public int tickCounter = 0;
	private int repairAmount = 10;

	public NaniteEnhancedAxe(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return 8124;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		if (level.isClientSide || !(entity instanceof Player)) return;

		if(this.tickCounter == this.tickDelay) {
			this.tickCounter = 0;

			if(stack.isDamaged()) {
				stack.setDamageValue(stack.getDamageValue() - this.repairAmount);
			}
		}

		this.tickCounter++;
	}
}