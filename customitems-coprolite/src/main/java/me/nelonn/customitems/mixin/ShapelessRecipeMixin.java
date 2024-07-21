/*
 * Copyright 2024 Michael Neonov <two.nelonn at gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.nelonn.customitems.mixin;

import me.nelonn.customitems.api.AItemStack;
import me.nelonn.customitems.utility.CraftUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShapelessRecipe.class)
public class ShapelessRecipeMixin {

    @Shadow @Final
    NonNullList<Ingredient> ingredients;

    /**
     * @author Nelonn
     * @reason Prevent using nested items in recipes
     */
    @Overwrite
    public boolean matches(CraftingInput input, Level world) {
        // Paper start - unwrap ternary & better exact choice recipes
        if (input.ingredientCount() != this.ingredients.size()) {
            return false;
        }
        // CustomItems start
        for (ItemStack itemStack : input.items()) {
            if (!CraftUtil.canBeUsedInCrafts(AItemStack.wrap(itemStack).getItem())) {
                return false;
            }
        }
        // CustomItems end
        if (input.size() == 1 && this.ingredients.size() == 1) {
            return this.ingredients.getFirst().test(input.getItem(0));
        }
        input.stackedContents().initializeExtras((Recipe<?>) (Object) this, input); // setup stacked contents for this recipe
        final boolean canCraft = input.stackedContents().canCraft((Recipe<?>) (Object) this, null);
        input.stackedContents().resetExtras();
        return canCraft;
    }
}
