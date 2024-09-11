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

import it.unimi.dsi.fastutil.ints.IntList;
import me.nelonn.customitems.api.AItemStack;
import me.nelonn.customitems.utility.CraftUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
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
    public boolean matches(CraftingContainer inventory, Level world) {
        StackedContents autorecipestackmanager = new StackedContents();
        autorecipestackmanager.initialize((Recipe<?>) (Object) this); // Paper - better exact choice recipes // CustomItems - Mixin class cast
        int i = 0;

        for (int j = 0; j < inventory.getContainerSize(); ++j) {
            ItemStack itemstack = inventory.getItem(j);

            if (!itemstack.isEmpty() && CraftUtil.canBeUsedInCrafts(AItemStack.wrap(itemstack).getItem())) { // CustomItems
                ++i;
                autorecipestackmanager.accountStack(itemstack, 1);
            }
        }

        return i == this.ingredients.size() && autorecipestackmanager.canCraft((Recipe<?>) (Object) this, (IntList) null); // CustomItems - Mixin class cast
    }
}
