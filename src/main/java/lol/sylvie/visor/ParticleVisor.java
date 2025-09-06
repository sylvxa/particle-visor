package lol.sylvie.visor;

import lol.sylvie.visor.config.VisorConfig;
import lol.sylvie.visor.config.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.Screen;
import net.uku3lig.ukulib.api.UkulibAPI;
import net.uku3lig.ukulib.config.ConfigManager;

import java.util.function.UnaryOperator;

public class ParticleVisor implements ClientModInitializer, UkulibAPI {
    public static final ConfigManager<VisorConfig> CONFIG_MANAGER = ConfigManager.createDefault(VisorConfig.class, "particle-visor");

    @Override
    public UnaryOperator<Screen> supplyConfigScreen() {
        return ConfigScreen::new;
    }

    @Override
    public void onInitializeClient() {

    }
}
