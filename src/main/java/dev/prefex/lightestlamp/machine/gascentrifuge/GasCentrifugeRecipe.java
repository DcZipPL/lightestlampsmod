package dev.prefex.lightestlamp.machine.gascentrifuge;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import dev.prefex.lightestlamp.init.ModMiscs;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@MethodsReturnNonnullByDefault
public record GasCentrifugeRecipe(Ingredient filter, Ingredient input, List<ItemStack> output, ResourceLocation id) implements Recipe<Container> {

    public boolean matches(Container pInv, Level pLevel) {
        return this.filter.test(pInv.getItem(0)) && this.input.test(pInv.getItem(1));
    }

    /**
     * Returns nothing. See assemble() with array.
     */
    @Override
    @Deprecated
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    public ItemStack[] assemble() {
        return this.output.stream().map(ItemStack::copy).toArray(ItemStack[]::new);
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return null;
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

    public static class Serializer implements RecipeSerializer<GasCentrifugeRecipe> {
        private ItemStack orEmpty(JsonObject pJson, String pMemberName){
            ItemStack o = ItemStack.EMPTY;
            try {
                o = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, pMemberName));
            }catch(Exception ignored){}
            return o;
        }

        @Override
        public GasCentrifugeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            Ingredient f = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "filter"));
            Ingredient i = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "input"));
            ItemStack o0 = orEmpty(pJson,"first");
            ItemStack o1 = orEmpty(pJson,"second");
            ItemStack o2 = orEmpty(pJson,"third");
            ItemStack o3 = orEmpty(pJson,"fourth");
            return new GasCentrifugeRecipe(f,i, Lists.newArrayList(o0,o1,o2,o3),pRecipeId);
        }

        @Nullable
        @Override
        public GasCentrifugeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient f = Ingredient.fromNetwork(pBuffer);
            Ingredient i = Ingredient.fromNetwork(pBuffer);
            ItemStack o0 = pBuffer.readItem();
            ItemStack o1 = pBuffer.readItem();
            ItemStack o2 = pBuffer.readItem();
            ItemStack o3 = pBuffer.readItem();
            return new GasCentrifugeRecipe(f,i,Lists.newArrayList(o0,o1,o2,o3),pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, GasCentrifugeRecipe pRecipe) {
            pRecipe.filter.toNetwork(pBuffer);
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output.get(0));
            pBuffer.writeItem(pRecipe.output.get(1));
            pBuffer.writeItem(pRecipe.output.get(2));
            pBuffer.writeItem(pRecipe.output.get(3));
        }
    }
}