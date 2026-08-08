package dev.prefex.lightestlamp.machine.gascentrifuge;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.prefex.lightestlamp.init.ModMiscs;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public record GasCentrifugeRecipe(
        Ingredient filter,
        Ingredient input,
        ItemStack first,
        ItemStack second,
        ItemStack third,
        ItemStack fourth
) implements Recipe<GasCentrifugeRecipe.Input> {
    @Override
    public boolean matches(Input input, Level level) {
        return filter.test(input.filter()) && this.input.test(input.fuel());
    }

    @Override
    public ItemStack assemble(Input input, HolderLookup.Provider registries) {
        return first.copy();
    }

    public ItemStack[] assemble() {
        return output().stream().map(ItemStack::copy).toArray(ItemStack[]::new);
    }

    public List<ItemStack> output() {
        return List.of(first, second, third, fourth);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return first.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModMiscs.GLOWSTONE_CENTRIFUGE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModMiscs.GLOWSTONE_CENTRIFUGE_RECIPE_TYPE.get();
    }

    public record Input(ItemStack filter, ItemStack fuel) implements RecipeInput {
        @Override
        public ItemStack getItem(int index) {
            return switch (index) {
                case 0 -> filter;
                case 1 -> fuel;
                default -> throw new IndexOutOfBoundsException(index);
            };
        }

        @Override
        public int size() {
            return 2;
        }
    }

    public static final class Serializer implements RecipeSerializer<GasCentrifugeRecipe> {
        private static final MapCodec<GasCentrifugeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("filter").forGetter(GasCentrifugeRecipe::filter),
                Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(GasCentrifugeRecipe::input),
                ItemStack.OPTIONAL_CODEC.optionalFieldOf("first", ItemStack.EMPTY).forGetter(GasCentrifugeRecipe::first),
                ItemStack.OPTIONAL_CODEC.optionalFieldOf("second", ItemStack.EMPTY).forGetter(GasCentrifugeRecipe::second),
                ItemStack.OPTIONAL_CODEC.optionalFieldOf("third", ItemStack.EMPTY).forGetter(GasCentrifugeRecipe::third),
                ItemStack.OPTIONAL_CODEC.optionalFieldOf("fourth", ItemStack.EMPTY).forGetter(GasCentrifugeRecipe::fourth)
        ).apply(instance, GasCentrifugeRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, GasCentrifugeRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, GasCentrifugeRecipe::filter,
                Ingredient.CONTENTS_STREAM_CODEC, GasCentrifugeRecipe::input,
                ItemStack.OPTIONAL_STREAM_CODEC, GasCentrifugeRecipe::first,
                ItemStack.OPTIONAL_STREAM_CODEC, GasCentrifugeRecipe::second,
                ItemStack.OPTIONAL_STREAM_CODEC, GasCentrifugeRecipe::third,
                ItemStack.OPTIONAL_STREAM_CODEC, GasCentrifugeRecipe::fourth,
                GasCentrifugeRecipe::new
        );

        @Override
        public MapCodec<GasCentrifugeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, GasCentrifugeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
