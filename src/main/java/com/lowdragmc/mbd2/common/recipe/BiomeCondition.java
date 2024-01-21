package com.lowdragmc.mbd2.common.recipe;

import com.google.gson.JsonObject;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import com.lowdragmc.mbd2.api.recipe.MBDRecipe;
import com.lowdragmc.mbd2.api.recipe.RecipeCondition;
import com.lowdragmc.mbd2.api.recipe.RecipeLogic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nonnull;

/**
 * @author KilaBash
 * @date 2022/05/27
 * @implNote DimensionCondition, specific dimension
 */
@Getter
@NoArgsConstructor
public class BiomeCondition extends RecipeCondition {

    public final static BiomeCondition INSTANCE = new BiomeCondition();
    private ResourceLocation biome = new ResourceLocation("dummy");

    public BiomeCondition(ResourceLocation biome) {
        this.biome = biome;
    }

    @Override
    public String getType() {
        return "biome";
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("recipe.condition.biome.tooltip", LocalizationUtils.format("biome.%s.%s", biome.getNamespace(), biome.getPath()));
    }

    @Override
    public boolean test(@Nonnull MBDRecipe recipe, @Nonnull RecipeLogic recipeLogic) {
        Level level = recipeLogic.machine.getLevel();
        if (level == null) return false;
        Holder<Biome> biome = level.getBiome(recipeLogic.machine.getPos());
        return biome.is(this.biome);
    }

    @Override
    public RecipeCondition createTemplate() {
        return new BiomeCondition();
    }

    @Nonnull
    @Override
    public JsonObject serialize() {
        JsonObject config = super.serialize();
        config.addProperty("biome", biome.toString());
        return config;
    }

    @Override
    public RecipeCondition deserialize(@Nonnull JsonObject config) {
        super.deserialize(config);
        biome = new ResourceLocation(
                GsonHelper.getAsString(config, "biome", "dummy"));
        return this;
    }

    @Override
    public RecipeCondition fromNetwork(FriendlyByteBuf buf) {
        super.fromNetwork(buf);
        biome = new ResourceLocation(buf.readUtf());
        return this;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        super.toNetwork(buf);
        buf.writeUtf(biome.toString());
    }

}
