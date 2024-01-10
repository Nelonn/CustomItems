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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Unique
    public boolean Entity$fireImmune() {
        Entity self = (Entity) (Object) this;
        return self.getType().fireImmune();
    }

    @Unique
    public boolean customitems$isFireResistant() {
        return AItemStack.wrap(this.getItem()).isFireResistant();
    }

    /**
     * @author Nelonn
     * @reason Nested items can be fire-resistant
     */
    @Overwrite
    public boolean fireImmune() {
        return customitems$isFireResistant() || Entity$fireImmune();
    }

    @Shadow public abstract ItemStack getItem();
}
