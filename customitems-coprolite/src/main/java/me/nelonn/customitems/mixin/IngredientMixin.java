/*
 * Copyright 2023 Michael Neonov
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(Ingredient.class)
public abstract class IngredientMixin {

    @Shadow public boolean exact;

    @Shadow public abstract ItemStack[] getItems();

    @Shadow public abstract boolean isEmpty();

    /**
     * @author Nelonn
     * @reason Prevent using nested items in recipes
     */
    @Overwrite
    public boolean test(@Nullable ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        } else if (this.isEmpty()) {
            return itemstack.isEmpty();
        } else {
            ItemStack[] aitemstack = this.getItems();
            int i = aitemstack.length;

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack1 = aitemstack[j];

                // CraftBukkit start
                if (this.exact) {
                    if (itemstack1.getItem() == itemstack.getItem() && ItemStack.isSameItemSameTags(itemstack, itemstack1)) {
                        return true;
                    }

                    continue;
                }
                // CraftBukkit end
                // CustomItems start
                if (AItemStack.wrap(itemstack1).getItem() == AItemStack.wrap(itemstack).getItem()) {
                // CustomItems end
                    return true;
                }
            }

            return false;
        }
    }
}
