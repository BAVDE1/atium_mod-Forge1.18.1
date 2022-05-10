package com.BAVDE.atium_mod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ForceFieldParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected ForceFieldParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);
        this.sprites = spriteSet;

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.quadSize *= 0.75F;
        this.friction = 0.8F;
        this.gravity = 0.0f;

        this.hasPhysics = true;

        this.lifetime = 75;
        this.setSpriteFromAge(spriteSet);

        //colour needs to be (255 - rgbNumber)
        this.setColor(98, 47, 4);
        this.alpha = 0.7F;

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

    private void fadeOut() {
        if (this.age >= this.lifetime - 40 && this.alpha > 0.01F) {
            //   1 / 40 = vv (round down)
            this.alpha -= 0.025;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double XSpeed, double YSpeed, double ZSpeed) {
            ForceFieldParticle forceFieldParticle = new ForceFieldParticle(level, x, y, z, sprites , XSpeed, YSpeed, ZSpeed);
            forceFieldParticle.pickSprite(this.sprites);
            return forceFieldParticle;
        }
    }
}
