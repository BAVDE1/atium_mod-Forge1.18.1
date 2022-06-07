package com.BAVDE.atium_mod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InfusionFlameParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected InfusionFlameParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);
        this.sprites = spriteSet;

        this.quadSize *= 0.4F;
        this.friction = 0.9F;
        this.gravity = 0.0f;

        this.hasPhysics = false;

        this.lifetime = 50 + this.random.nextInt(15);
        this.setSpriteFromAge(spriteSet);

        this.alpha = 0.5F;

        this.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        fadeOut();
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return 255;
    }

    private void fadeOut() {
        if (this.age >= this.lifetime - 40 && this.alpha > 0.01F) {
            //   1 / 40 = vv (round down)
            this.alpha -= 0.025;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class FlameProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public FlameProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double XSpeed, double YSpeed, double ZSpeed) {
            InfusionFlameParticle infusionFlameParticle = new InfusionFlameParticle(level, x, y, z, sprites , XSpeed, YSpeed, ZSpeed);

            infusionFlameParticle.yd *= 0.15F;
            if (XSpeed == 0.0D && ZSpeed == 0.0D) {
                infusionFlameParticle.xd *= 0.07F;
                infusionFlameParticle.zd *= 0.07F;
            }

            infusionFlameParticle.pickSprite(this.sprites);
            return infusionFlameParticle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class CraftProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public CraftProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double XSpeed, double YSpeed, double ZSpeed) {
            InfusionFlameParticle infusionCraftParticle = new InfusionFlameParticle(level, x, y, z, sprites , XSpeed, YSpeed, ZSpeed);

            infusionCraftParticle.xd = XSpeed;
            infusionCraftParticle.yd = YSpeed;
            infusionCraftParticle.zd = ZSpeed;

            infusionCraftParticle.pickSprite(this.sprites);
            return infusionCraftParticle;
        }
    }
}
