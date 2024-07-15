package smart.pick;

import net.fabricmc.api.ModInitializer;

import org.anti_ad.mc.common.config.CategorizedMultiConfig;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilder;
import org.anti_ad.mc.common.config.options.ConfigBoolean;
import org.anti_ad.mc.ipnext.config.LockedSlotsSettings;

public class SmartPick implements ModInitializer {
	public static ConfigBoolean configSkipLocked;

	@Override
	public void onInitialize() {
		LockedSlotsSettings instance = LockedSlotsSettings.INSTANCE;
		ConfigDeclarationBuilder builder = instance.getBuilder();
		CategorizedMultiConfig innerConfig = builder.getInnerConfig();
		innerConfig.addCategory("Smart Pick Settings");
		configSkipLocked = new ConfigBoolean(true, false);
		configSkipLocked.setKey("skip locked slots when using pick item");
		innerConfig.addConfigOption(configSkipLocked);
	}
}