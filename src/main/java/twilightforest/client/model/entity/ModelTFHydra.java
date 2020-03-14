// Date: 2/5/2012 10:11:18 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX
package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.boss.EntityTFHydra;
import twilightforest.entity.boss.EntityTFHydraPart;

public class ModelTFHydra<T extends EntityTFHydra> extends SegmentedModel<T> {
	//fields
	ModelRenderer body;
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer tail1;
	ModelRenderer tail2;
	ModelRenderer tail3;
	ModelRenderer tail4;
	ModelRenderer neck1a;
	ModelRenderer neck1b;
	ModelRenderer neck1c;
	ModelRenderer neck1d;
	ModelRenderer head1;
	ModelRenderer jaw1;
	ModelRenderer frill1;
	ModelRenderer neck2a;
	ModelRenderer neck2b;
	ModelRenderer neck2c;
	ModelRenderer neck2d;
	ModelRenderer head2;
	ModelRenderer jaw2;
	ModelRenderer frill2;
	ModelRenderer neck3a;
	ModelRenderer neck3b;
	ModelRenderer neck3c;
	ModelRenderer neck3d;
	ModelRenderer head3;
	ModelRenderer jaw3;
	ModelRenderer frill3;

	public ModelTFHydra() {
		textureWidth = 512;
		textureHeight = 256;

		body = new ModelRenderer(this/*, "body"*/);
		body.setRotationPoint(0F, -12F, 0F);
		body.setTextureOffset(0, 0).addCuboid(-48F, 0F, 0F, 96, 96, 40);
		setRotation(body, 1.22173F, 0F, 0F);

		leg1 = new ModelRenderer(this/*, "leg"*/);
		leg1.setRotationPoint(48F, -24F, 0F);
		leg1.setTextureOffset(0, 136).addCuboid(-16F, 0F, -16F, 32, 48, 32);
		leg1.setTextureOffset(184, 200).addCuboid(-20F, 40F, -20F, 8, 8, 8);
		leg1.setTextureOffset(184, 200).addCuboid(-4F, 40F, -22F, 8, 8, 8);
		leg1.setTextureOffset(184, 200).addCuboid(12F, 40F, -20F, 8, 8, 8);

		leg2 = new ModelRenderer(this/*, "leg"*/);
		leg2.setRotationPoint(-48F, -24F, 0F);
		leg2.mirror = true;
		leg2.setTextureOffset(0, 136).addCuboid(-16F, 0F, -16F, 32, 48, 32);
		leg2.setTextureOffset(184, 200).addCuboid(-20F, 40F, -20F, 8, 8, 8);
		leg2.setTextureOffset(184, 200).addCuboid(-4F, 40F, -22F, 8, 8, 8);
		leg2.setTextureOffset(184, 200).addCuboid(12F, 40F, -20F, 8, 8, 8);

		tail1 = new ModelRenderer(this/*, "tail"*/);
		tail1.setRotationPoint(0F, 6F, 108F);
		tail1.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		tail1.setTextureOffset(128, 200).addCuboid(-2F, -28F, -11F, 4, 24, 24);

		tail2 = new ModelRenderer(this/*, "tail"*/);
		tail2.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		tail2.setTextureOffset(128, 200).addCuboid(-2F, -28F, -11F, 4, 24, 24);
		tail2.setRotationPoint(0F, 7F, 142F);

		tail3 = new ModelRenderer(this/*, "tail"*/);
		tail3.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		tail3.setTextureOffset(128, 200).addCuboid(-2F, -28F, -11F, 4, 24, 24);
		tail3.setRotationPoint(0F, 8F, 176F);

		tail4 = new ModelRenderer(this/*, "tail"*/);
		tail4.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		tail4.setTextureOffset(128, 200).addCuboid(-2F, -28F, -11F, 4, 24, 24);
		tail4.setRotationPoint(0F, 8F, 210F);


		neck1a = new ModelRenderer(this/*, "neck"*/);
		neck1a.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck1a.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck1a.setRotationPoint(0F, -48F, 16F);

		neck1b = new ModelRenderer(this/*, "neck"*/);
		neck1b.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck1b.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck1b.setRotationPoint(0F, -68F, 0F);

		neck1c = new ModelRenderer(this/*, "neck"*/);
		neck1c.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck1c.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck1c.setRotationPoint(0F, -93F, -14F);

		neck1d = new ModelRenderer(this/*, "neck"*/);
		neck1d.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck1d.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck1d.setRotationPoint(0F, -116F, -37F);

		head1 = new ModelRenderer(this/*, "head"*/);
		head1.setTextureOffset(272, 0).addCuboid(-16F, -14F, -32F, 32, 24, 32);
		head1.setTextureOffset(272, 56).addCuboid(-15F, -2F, -56F, 30, 12, 24);
		head1.setTextureOffset(128, 200).addCuboid(-2F, -30F, -12F, 4, 24, 24);
		head1.setRotationPoint(0F, -128F, -53F);

		jaw1 = new ModelRenderer(this/*, "jaw"*/);
		jaw1.setRotationPoint(0F, 10F, -4F);
		jaw1.setTextureOffset(272, 92).addCuboid(-15F, 0F, -48F, 30, 8, 48);
		setRotation(jaw1, 0F, 0F, 0F);
		head1.addChild(jaw1);

		frill1 = new ModelRenderer(this/*, "frill"*/);
		frill1.setRotationPoint(0F, 0F, -10F);
		frill1.setTextureOffset(272, 200).addCuboid(-24F, -40.0F, 0F, 48, 48, 4);
		setRotation(frill1, -0.5235988F, 0F, 0F);
		head1.addChild(frill1);


		neck2a = new ModelRenderer(this/*, "neck"*/);
		neck2a.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck2a.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck2a.setRotationPoint(48F, -48F, 16F);

		neck2b = new ModelRenderer(this/*, "neck"*/);
		neck2b.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck2b.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck2b.setRotationPoint(71F, -68F, 0F);

		neck2c = new ModelRenderer(this/*, "neck"*/);
		neck2c.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck2c.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck2c.setRotationPoint(96F, -93F, -14F);

		neck2d = new ModelRenderer(this/*, "neck"*/);
		neck2d.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck2d.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck2d.setRotationPoint(108F, -116F, -37F);

		head2 = new ModelRenderer(this/*, "head"*/);
		head2.setTextureOffset(272, 0).addCuboid(-16F, -14F, -32F, 32, 24, 32);
		head2.setTextureOffset(272, 56).addCuboid(-15F, -2F, -56F, 30, 12, 24);
		head2.setTextureOffset(128, 200).addCuboid(-2F, -30F, -12F, 4, 24, 24);
		head2.setRotationPoint(108F, -128F, -53F);

		jaw2 = new ModelRenderer(this/*, "jaw"*/);
		jaw2.setRotationPoint(0F, 10F, -4F);
		jaw2.setTextureOffset(272, 92).addCuboid(-15F, 0F, -48F, 30, 8, 48);
		setRotation(jaw2, 0F, 0F, 0F);
		head2.addChild(jaw2);

		frill2 = new ModelRenderer(this/*, "frill"*/);
		frill2.setRotationPoint(0F, 0F, -10F);
		frill2.setTextureOffset(272, 200).addCuboid(-24F, -40.0F, 0F, 48, 48, 4);
		setRotation(frill2, -0.5235988F, 0F, 0F);
		head2.addChild(frill2);


		neck3a = new ModelRenderer(this/*, "neck"*/);
		neck3a.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 31);
		neck3a.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck3a.setRotationPoint(-48F, -48F, 16F);

		neck3b = new ModelRenderer(this/*, "neck"*/);
		neck3b.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck3b.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck3b.setRotationPoint(-71F, -43F, 0F);

		neck3c = new ModelRenderer(this/*, "neck"*/);
		neck3c.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck3c.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck3c.setRotationPoint(-96F, -33F, -14F);

		neck3d = new ModelRenderer(this/*, "neck"*/);
		neck3d.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck3d.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck3d.setRotationPoint(-108F, -24F, -37F);

		head3 = new ModelRenderer(this/*, "head"*/);
		head3.setTextureOffset(272, 0).addCuboid(-16F, -14F, -32F, 32, 24, 32);
		head3.setTextureOffset(272, 56).addCuboid(-15F, -2F, -56F, 30, 12, 24);
		head3.setTextureOffset(128, 200).addCuboid(-2F, -30F, -12F, 4, 24, 24);
		head3.setRotationPoint(-108F, -24F, -53F);

		jaw3 = new ModelRenderer(this/*, "jaw"*/);
		jaw3.setRotationPoint(0F, 10F, -4F);
		jaw3.setTextureOffset(272, 92).addCuboid(-15F, 0F, -48F, 30, 8, 48);
		setRotation(jaw3, 0.125F, 0F, 0F);
		head3.addChild(jaw3);

		frill3 = new ModelRenderer(this/*, "frill"*/);
		frill3.setRotationPoint(0F, 0F, -10F);
		frill3.setTextureOffset(272, 200).addCuboid(-24F, -40.0F, 0F, 48, 48, 4);
		setRotation(frill3, -0.5235988F, 0F, 0F);
		head3.addChild(frill3);
	}

//	@Override
//	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//		setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//
////		neck1a.render(scale);
////		neck1b.render(scale);
////		neck1c.render(scale);
////		neck1d.render(scale);
////		head1.render(scale);
////		neck2a.render(scale);
////		neck2b.render(scale);
////		neck2c.render(scale);
////		neck2d.render(scale);
////		head2.render(scale);
////		neck3a.render(scale);
////		neck3b.render(scale);
////		neck3c.render(scale);
////		neck3d.render(scale);
////		head3.render(scale);
//	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(
				body,
				leg1,
				leg2,
				tail1,
				tail2,
				tail3,
				tail4
		);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

		leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;

		leg1.rotateAngleY = 0.0F;
		leg2.rotateAngleY = 0.0F;
	}

	//	@Override
//	public void setLivingAnimations(EntityLiving entityliving, float f, float f1, float time) {
//		EntityTFHydra hydra = (EntityTFHydra)entityliving;
//		
//		// position the heads
//		Vec3 vec;
//		
//        vec = Vec3.getVec3Pool().getVecFromPool(0, 0, 7*-16);
//        vec.rotateAroundX((60 * 3.141593F) / 180F);
//        vec.rotateAroundY((0 * 3.141593F) / 180F);
//        head1.setRotationPoint((float)vec.x, (float)vec.y - 48, (float)vec.z);
//		
//        vec = Vec3.getVec3Pool().getVecFromPool(0, 0, 9*-16);
//        vec.rotateAroundX((45 * 3.141593F) / 180F);
//        vec.rotateAroundY((-60 * 3.141593F) / 180F);
//        head2.setRotationPoint((float)vec.x, (float)vec.y - 48, (float)vec.z);
//		
//        vec = Vec3.getVec3Pool().getVecFromPool(0, 0, 9*-16);
//        vec.rotateAroundX((-10 * 3.141593F) / 180F);
//        vec.rotateAroundY((60 * 3.141593F) / 180F);
//        head3.setRotationPoint((float)vec.x, (float)vec.y - 48, (float)vec.z);
//		
//        // rotate the heads
//        if (hydra.head1 != null)
//        {
//			head1.rotateAngleY = getRotationY(hydra, hydra.head1, time);
//			head1.rotateAngleX = getRotationX(hydra, hydra.head1, time);
//	
//			head2.rotateAngleY = getRotationY(hydra, hydra.head3, time);
//			head2.rotateAngleX = getRotationX(hydra, hydra.head3, time);
//	
//			head3.rotateAngleY = getRotationY(hydra, hydra.head2, time);
//			head3.rotateAngleX = getRotationX(hydra, hydra.head2, time) - 0.015F;
//        }
//	}

	public float getRotationY(EntityTFHydra hydra, EntityTFHydraPart whichHead, float time) {

		float yawOffset = hydra.prevRenderYawOffset + (hydra.renderYawOffset - hydra.prevRenderYawOffset) * time;
		float yaw = whichHead.prevRotationYaw + (whichHead.rotationYaw - whichHead.prevRotationYaw) * time;

		return (yaw - yawOffset) / 57.29578F;
	}

	public float getRotationX(EntityTFHydra hydra, EntityTFHydraPart whichHead, float time) {

		return (whichHead.prevRotationPitch + (whichHead.rotationPitch - whichHead.prevRotationPitch) * time) / 57.29578F;
	}
}
