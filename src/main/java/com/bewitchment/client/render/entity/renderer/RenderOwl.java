package com.bewitchment.client.render.entity.renderer;

import com.bewitchment.client.render.entity.model.ModelOwl;
import com.bewitchment.common.entity.living.familiar.EntityOwl;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderOwl extends RenderLiving<EntityOwl> {

	private static final ResourceLocation[] textures = new ResourceLocation[2];

	static {
		textures[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/owl_0.png");
		textures[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/animals/owl_1.png");
	}

	public RenderOwl(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelOwl(), 0.3f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityOwl entity) {
		return textures[entity.getFamiliarSkin()];
	}

	@Override
	protected void preRenderCallback(EntityOwl entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		GlStateManager.scale(0.6d, 0.6d, 0.6d);
	}

}
