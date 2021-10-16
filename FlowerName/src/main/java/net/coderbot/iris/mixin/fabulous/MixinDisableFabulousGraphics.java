package net.coderbot.iris.mixin.fabulous;

import net.coderbot.iris.Iris;
import net.minecraft.client.GameSettings;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
//import net.minecraft.client.Options;
//import net.minecraft.client.renderer.LevelRenderer;

@OnlyIn(Dist.CLIENT)
@Mixin(WorldRenderer.class)
public class MixinDisableFabulousGraphics {
	@Inject(method = "onResourceManagerReload", at = @At("HEAD"))
	private void iris$disableFabulousGraphicsOnResourceReload(CallbackInfo ci) {
		iris$disableFabulousGraphics();
	}

	// This method is called whenever the user tries to change the graphics mode.
	// We can still revert / intercept the change at the head of the method.
	@Inject(method = "allChanged", at = @At("HEAD"))
	private void iris$disableFabulousGraphicsOnLevelRendererReload(CallbackInfo ci) {
		iris$disableFabulousGraphics();
	}

	@Unique
	private void iris$disableFabulousGraphics() {
		GameSettings options = Minecraft.getInstance().options;

		if (Iris.getIrisConfig().areShadersEnabled()) {
			// Nothing to do here, shaders are disabled.
			return;
		}

		if (options.graphicsMode == GraphicsFanciness.FABULOUS) {
			// Disable fabulous graphics when shaders are enabled.
			options.graphicsMode = GraphicsFanciness.FANCY;
		}
	}
}
