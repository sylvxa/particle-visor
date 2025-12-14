package lol.sylvie.visor.config;

import com.mojang.datafixers.util.Function4;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.uku3lig.ukulib.config.option.StringTranslatable;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisorConfig implements Serializable {
    private double closeDistance = 1d;
    private double farDistance = 2d;

    private boolean modEnabled = true;

    private Anchor distanceAnchor = Anchor.CAMERA;

    private boolean onlyPotions = true;
    private boolean showInThirdPerson = false;


    @Getter
    @AllArgsConstructor
    public enum Anchor implements StringTranslatable {
        EYES(0, "particle-visor.option.anchor.eyes", (player, lerped, particle, camera) ->
                lerped.add(0, player.getEyeHeight(), 0).distanceTo(particle)
        ),
        BODY(1, "particle-visor.option.anchor.body", (player, lerped, particle, camera) -> {
            AABB box = player.getBoundingBox();
            return new Vec3(lerped.x(), Math.clamp(particle.y, box.minY, box.maxY), lerped.z()).distanceTo(particle);
        }),
        FEET(2, "particle-visor.option.anchor.feet", (player, lerped, particle, camera) ->
                lerped.distanceTo(particle)
        ),
        CAMERA(3, "particle-visor.option.anchor.camera", (player, lerped, particle, camera) ->
                camera.position().distanceTo(particle)
        );

        private final int id;
        private final String translationKey;
        private final Function4<LocalPlayer, Vec3, Vec3, Camera, Double> function;

        @Override
        public String getName() {
            return name();
        }
    }
}
