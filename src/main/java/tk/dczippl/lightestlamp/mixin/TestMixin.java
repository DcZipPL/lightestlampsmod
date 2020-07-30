package tk.dczippl.lightestlamp.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

/*@Mixin(BiomeColors.class)
public class TestMixin {

	//@Inject(method = "func_228359_a_(Lnet/minecraft/world/IBlockDisplayReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/level/ColorResolver;)Z", at = @At("HEAD"), remap = false, cancellable = true)
	/*@Redirect(method = "func_228359_a_(Lnet/minecraft/world/IBlockDisplayReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/level/ColorResolver;)Z", at = @At("HEAD"), remap = false)
	private static int func_228359_a_(IBlockDisplayReader worldIn, BlockPos blockPosIn, ColorResolver colorResolverIn) {
		return Color.BLACK.getRGB();
	}*/

	/*@Inject(method = "getWaterColor(Lnet/minecraft/world/IBlockDisplayReader;Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"), remap = false, cancellable = true)
	public static void getWaterColor(IBlockDisplayReader worldIn, BlockPos blockPosIn, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
		callbackInfoReturnable.setReturnValue(Color.BLACK.getRGB());
	}*./
}*/
/*@Mixin(LightEngine.class)
public class TestMixin<M extends LightDataMap<M>, S extends SectionLightStorage<M>> implements IFakeLightEngine {

	@Shadow
	protected static void setLevel(long sectionPosIn, int level) {}

	public void placeLight(BlockPos pos, int level){
		setLevel(pos.toLong(),level);
	}
}*/