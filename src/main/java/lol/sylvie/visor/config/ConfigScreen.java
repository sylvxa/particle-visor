package lol.sylvie.visor.config;

import lol.sylvie.visor.ParticleVisor;
import net.minecraft.client.gui.screens.Screen;
import net.uku3lig.ukulib.config.option.CyclingOption;
import net.uku3lig.ukulib.config.option.SliderOption;
import net.uku3lig.ukulib.config.option.WidgetCreator;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

public class ConfigScreen extends AbstractConfigScreen<VisorConfig> {
    public ConfigScreen(Screen parent) {
        super("particle-visor.config", parent, ParticleVisor.CONFIG_MANAGER);
    }

    @Override
    protected WidgetCreator[] getWidgets(VisorConfig config) {
        return new WidgetCreator[] {
            new SliderOption("particle-visor.config.close_distance", config.getCloseDistance(), config::setCloseDistance, SliderOption.DEFAULT_VALUE_TO_TEXT, 0d, 16d),
            new SliderOption("particle-visor.config.far_distance", config.getFarDistance(), config::setFarDistance, SliderOption.DEFAULT_VALUE_TO_TEXT, 0d, 16d),
            CyclingOption.ofBoolean("particle-visor.config.mod_enabled", config.isModEnabled(), config::setModEnabled),
            CyclingOption.ofTranslatableEnum("particle-visor.config.anchor", VisorConfig.Anchor.class, config.getDistanceAnchor(), config::setDistanceAnchor),
            CyclingOption.ofBoolean("particle-visor.config.potions_only", config.isOnlyPotions(), config::setOnlyPotions),
            CyclingOption.ofBoolean("particle-visor.config.show_in_third_person", config.isShowInThirdPerson(), config::setShowInThirdPerson)
        };
    }
}
