package com.BAVDE.atium_mod.particle.custom;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MobDetectionParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected MobDetectionParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);
        this.sprites = spriteSet;

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.quadSize *= 0.75F;
        this.friction = 0.9F;
        this.gravity = 0.0f;

        this.hasPhysics = false;

        this.lifetime = 70;
        this.setSpriteFromAge(spriteSet);

        //colour needs to be (255 - rgbNumber)
        this.setColor(98, 47, 4);
        this.alpha = 0.8F;

        this.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return new ParticleRenderType() {
            @Override
            public void begin(BufferBuilder p_107455_, TextureManager p_107456_) {
                RenderSystem.depthMask(false);
                RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                //vv this makes particles see able through blocks
                RenderSystem.disableDepthTest();
                p_107455_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
            }

            @Override
            public void end(Tesselator p_107458_) {
                p_107458_.end();
            }
        };
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        fadeOut();
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        float f = ((float) this.age + pPartialTick) / (float) this.lifetime;
        f = Mth.clamp(f, 0.0F, 1.0F);
        int i = super.getLightColor(pPartialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int) (f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }
        return j | k << 16;
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
            MobDetectionParticle mobDetectionParticle = new MobDetectionParticle(level, x, y, z, sprites , XSpeed, YSpeed, ZSpeed);
            mobDetectionParticle.pickSprite(this.sprites);
            return mobDetectionParticle;
        }
    }
}
