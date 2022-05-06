package com.BAVDE.atium_mod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DisorientedParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected DisorientedParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);
        this.sprites = spriteSet;

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.quadSize *= 0.75F;
        this.friction = 0.5F;
        this.gravity = 0.0f;

        this.hasPhysics = true;

        this.lifetime = 60 + this.random.nextInt(20);
        this.setSpriteFromAge(spriteSet);

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;

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
        //fades out in last 30 tick of particles life
        if (this.age >= this.lifetime - 30 && this.alpha > 0.01F) {
            //1 / 30(ticks) = vv (make sure number is less than answer)
            this.alpha -= 0.033F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double XSpeed, double YSpeed, double ZSpeed) {
            return new DisorientedParticle(level, x, y, z, this.sprites, XSpeed, YSpeed, ZSpeed);
            //dont forget to change ^
        }
    }
}
