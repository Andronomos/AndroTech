package andronomos.androtech;

public class Constants {
	public static final int PLAYER_HOTBAR_SLOT_COUNT = 9;
	public static final	int PLAYER_HOTBAR_MENU_Y_OFFSET = 142;
	public static final int PLAYER_INVENTORY_MENU_Y_OFFSET = 84;
	public static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	public static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	public static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_ROW_COUNT * PLAYER_INVENTORY_COLUMN_COUNT + PLAYER_HOTBAR_SLOT_COUNT;

	public static final int VANILLA_INVENTORY_FIRST_SLOT_INDEX = 0;
	public static final int VANILLA_INVENTORY_SLOT_COUNT = 27;
	public static final int VANILLA_LARGE_INVENTORY_SLOT_COUNT = 54;

	public static final int MENU_SLOT_X_OFFSET = 8;
	public static final int MENU_SLOT_Y_OFFSET = 16;
	public static final int MENU_SLOT_X_CENTER = 80;
	public static final int MENU_SLOT_SIZE = 16;
	public static final int SCREEN_SLOT_X_OFFSET = 7;
	public static final int SCREEN_SLOT_X_CENTER = 79;
	public static final int SCREEN_SLOT_SIZE = 18;
	public static final int SCREEN_IMAGE_HEIGHT_LARGE = 222;

	public class EnchantmentLevel {
		public final static int I = 1;
		public final static int II = 2;
		public final static int III = 3;
		public final static int IV = 4;
		public final static int V = 5;
	}

	public class EffectAmplifier {
		public final static int I = 0;
		public final static int II = 1;
		public final static int III = 2;
		public final static int IV = 3;
		public final static int V = 4;
	}

	public class TicksInSeconds {
		public final static int ONE = 20;
		public final static int TWO = 40;
		public final static int THREE = 60;
		public final static int FOUR = 80;
		public final static int FIVE = 100;
		public final static int TEN = 200;
	}

	public class TicksInMinutes {
		public final static int ONE = 1200;
		public final static int TWO = 2400;
		public final static int THREE = 3600;
		public final static int FIVE = 6000;
		public final static int TEN = 1200;
		public final static int TWENTY = 2400;
		public final static int THIRTY = 3600;
	}

	public class TicksInHours {
		public final static int ONE = 7200;
	}
}
