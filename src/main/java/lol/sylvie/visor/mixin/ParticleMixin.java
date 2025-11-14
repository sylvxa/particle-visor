package lol.sylvie.visor.mixin;

import lol.sylvie.visor.ParticleVisor;
import lol.sylvie.visor.config.VisorConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BillboardParticle.class)
public abstract class ParticleMixin extends Particle {
    @Unique
    float defaultAlpha = -1;

    @Shadow
    protected float alpha;

    protected ParticleMixin(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Inject(method = "render(Lnet/minecraft/client/particle/BillboardParticleSubmittable;Lnet/minecraft/client/render/Camera;F)V", at = @At("HEAD"), cancellable = true)
    public void render(BillboardParticleSubmittable submittable, Camera camera, float tickProgress, CallbackInfo ci) {
        VisorConfig config = ParticleVisor.CONFIG_MANAGER.getConfig();
        if (!config.isModEnabled()) return;

        if (defaultAlpha == -1) {
            defaultAlpha = this.alpha;
        }

        if (!((Object) this instanceof SpellParticle) && config.isOnlyPotions()) {
            return;
        }

        if (camera.isThirdPerson() && config.isShowInThirdPerson()) return;

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        double closeDistance = config.getCloseDistance();
        double farDistance = config.getFarDistance();
        double distanceDifference = Math.abs(farDistance - closeDistance);

        double distance = config.getDistanceAnchor().getFunction().apply(player, player.getLerpedPos(tickProgress), new Vec3d(this.x, this.y, this.z), camera);

        if (distance < closeDistance) {
            this.alpha = 0; // For compatibility
            ci.cancel();
        } else if (distance > farDistance) {
            this.alpha = defaultAlpha;
        } else {
            this.alpha = ((float) ((distance - closeDistance) / distanceDifference)) * defaultAlpha;
        }
    }
}
