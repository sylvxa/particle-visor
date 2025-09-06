package lol.sylvie.visor.config;

import com.mojang.datafixers.util.Function3;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.TranslatableOption;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisorConfig implements Serializable {
    private double closeDistance = 1d;
    private double farDistance = 2d;

    private Anchor distanceAnchor = Anchor.BODY;

    private boolean onlyPotions = true;

    @Getter
    @AllArgsConstructor
    public enum Anchor implements TranslatableOption {
        EYES(0, "particle-visor.option.anchor.eyes", (player, lerped, particle) ->
                lerped.add(0, player.getStandingEyeHeight(), 0).distanceTo(particle)
        ),
        BODY(1, "particle-visor.option.anchor.body", (player, lerped, particle) -> {
            Box box = player.getBoundingBox();
            return new Vec3d(lerped.getX(), Math.clamp(particle.y, box.minY, box.maxY), lerped.getZ()).distanceTo(particle);
        }),
        FEET(2, "particle-visor.option.anchor.feet", (player, lerped, particle) ->
                lerped.distanceTo(particle)
        );

        private final int id;
        private final String translationKey;
        private final Function3<ClientPlayerEntity, Vec3d, Vec3d, Double> function;
    }
}
