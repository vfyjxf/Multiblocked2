package com.lowdragmc.mbd2.api.machine;

import com.lowdragmc.mbd2.api.recipe.MBDRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IMultiPart extends IMachine {
    /**
     * Can it be shared among multi multiblock.
     */
    default boolean canShared() {
        return true;
    }

    /**
     * Whether it belongs to...
     */
    boolean hasController(BlockPos controllerPos);

    /**
     * Whether it belongs to a formed Multiblock.
     */
    boolean isFormed();

    /**
     * Get all attached controllers
     */
    List<IMultiController> getControllers();

    /**
     * Called when it was removed from a multiblock.
     */
    void removedFromController(IMultiController controller);

    /**
     * Called when it was added to a multiblock.
     */
    void addedToController(IMultiController controller);

    /**
     * Get all available traits for recipe logic.
     */
    List<IRecipeHandlerTrait> getRecipeHandlers();

    /**
     * whether its base model can be replaced by controller when it is formed.
     */
    default boolean replacePartModelWhenFormed() {
        return true;
    }

    /**
     * get part's Appearance. same as IForgeBlock.getAppearance() / IFabricBlock.getAppearance()
     */
    @Nullable
    default BlockState getFormedAppearance(BlockState sourceState, BlockPos sourcePos, Direction side) {
        for (IMultiController controller : getControllers()) {
            var appearance = controller.getPartAppearance(this, side, sourceState, sourcePos);
            if (appearance != null) return appearance;
        }
        return null;
    }

    /**
     * Called per tick in {@link RecipeLogic#handleRecipeWorking()}
     */
    default void onWorking(IMultiController controller) {

    }

    /**
     * Called per tick in {@link RecipeLogic#handleRecipeWorking()}
     */
    default void onWaiting(IMultiController controller) {

    }

    /**
     * Called in {@link RecipeLogic#onRecipeFinish()} before outputs are produced
     */
    default void afterWorking(IMultiController controller) {

    }

    /**
     * Called in {@link RecipeLogic#setupRecipe(MBDRecipe)} ()}
     */
    default void beforeWorking(IMultiController controller) {

    }

    /**
     * Override it to modify recipe on the fly e.g. applying overclock, change chance, etc
     * @param recipe recipe from detected from MBDRecipeType
     * @return modified recipe.
     *         null -- this recipe is unavailable
     */
    default MBDRecipe modifyRecipe(MBDRecipe recipe) {
        return recipe;
    }

}
