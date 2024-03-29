package andronomos.androtech.registry;

import andronomos.androtech.AndroTech;
import andronomos.androtech.block.itemattractor.ItemAttractorBlockEntity;
import andronomos.androtech.block.damagepad.DamagePadBlockEntity;
import andronomos.androtech.block.itemincinerator.ItemIncineratorBlockEntity;
import andronomos.androtech.block.wirelessredstone.redstonetransmitter.RedstoneSignalTransmitterBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AndroTech.MODID);

	public static final RegistryObject<BlockEntityType<DamagePadBlockEntity>> DAMAGE_PAD_BE = BLOCK_ENTITIES
			.register("damage_pad_be", () -> BlockEntityType.Builder
			.of(DamagePadBlockEntity::new, BlockRegistry.DAMAGE_PAD.get())
			.build(null));

	public static final RegistryObject<BlockEntityType<ItemAttractorBlockEntity>> ITEM_ATTRACTOR_BE = BLOCK_ENTITIES
			.register("item_attractor_be", () -> BlockEntityType.Builder
					.of(ItemAttractorBlockEntity::new, BlockRegistry.ITEM_ATTRACTOR.get())
					.build(null));

	public static final RegistryObject<BlockEntityType<RedstoneSignalTransmitterBlockEntity>> REDSTONE_SIGNAL_TRANSMITTER_BE = BLOCK_ENTITIES
			.register("redstone_signal_transmitter_be", () -> BlockEntityType.Builder
					.of(RedstoneSignalTransmitterBlockEntity::new, BlockRegistry.REDSTONE_SIGNAL_TRANSMITTER.get())
					.build(null));

	public static final RegistryObject<BlockEntityType<ItemIncineratorBlockEntity>> ITEM_INCINERATOR_BE = BLOCK_ENTITIES
			.register("item_incinerator_be", () -> BlockEntityType.Builder
					.of(ItemIncineratorBlockEntity::new, BlockRegistry.ITEM_INCINERATOR.get())
					.build(null));
}
