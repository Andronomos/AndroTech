package andronomos.androtech.network;

import andronomos.androtech.network.packet.SyncSpawnerDnaUnitDrop;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class AndroTechPacketHandler {
	private static final String PROTOCOL_VERSION = "1";

	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(andronomos.androtech.AndroTech.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void register() {
		int messageId = 0;

		INSTANCE.registerMessage(messageId++,
				SyncSpawnerDnaUnitDrop.class,
				SyncSpawnerDnaUnitDrop::encode,
				SyncSpawnerDnaUnitDrop::decode,
				SyncSpawnerDnaUnitDrop::handle);
	}

}