package andronomos.androtech.block.collisioneffect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CollisionEffectAcceleration implements ICollisionEffect {
	private final double velocity;

	public CollisionEffectAcceleration(double velocity) {
		this.velocity = velocity;
	}

	@Override
	public void onCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
		Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
		entity.setDeltaMovement(entity.getDeltaMovement().add(this.velocity * (direction.getStepX() * 1.5), 0, this.velocity * (direction.getStepZ() * 1.5)));
	}
}
