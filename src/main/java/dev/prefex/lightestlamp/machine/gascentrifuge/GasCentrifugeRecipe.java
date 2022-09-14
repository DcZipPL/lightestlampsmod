package dev.prefex.lightestlamp.machine.gascentrifuge;

import com.google.gson.JsonObject;
import dev.prefex.lightestlamp.init.ModMiscs;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonnullByDefault
public record GasCentrifugeRecipe(Ingredient filter, Ingredient input, ItemStack output, ResourceLocation id) implements Recipe<Container> {

    public boolean matches(Container pInv, Level pLevel) {
        return this.filter.test(pInv.getItem(0)) && this.input.test(pInv.getItem(1));
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack assemble(Container pInv) {
        return this.output.copy();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModMiscs.GLOWSTONE_CENTRIFUGE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModMiscs.GLOWSTONE_CENTRIFUGE_RECIPE_TYPE.get();
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<GasCentrifugeRecipe> {
        @Override
        public GasCentrifugeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            Ingredient f = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "filter"));
            Ingredient i = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "input"));
            ItemStack o = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "output"));
            return new GasCentrifugeRecipe(f,i,o,pRecipeId);
        }

        @Nullable
        @Override
        public GasCentrifugeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient f = Ingredient.fromNetwork(pBuffer);
            Ingredient i = Ingredient.fromNetwork(pBuffer);
            ItemStack o = pBuffer.readItem();
            return new GasCentrifugeRecipe(f,i,o,pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, GasCentrifugeRecipe pRecipe) {
            pRecipe.filter.toNetwork(pBuffer);
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
        }
    }
}