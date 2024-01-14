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

package me.nelonn.customitems;

import me.nelonn.customitems.api.AItemStack;
import me.nelonn.customitems.api.TrueItem;
import me.nelonn.customitems.utility.ItemMixinAccess;
import me.nelonn.flint.path.Key;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemWrapper implements TrueItem {
    private final Item handle;

    @Contract("null -> null; !null -> !null")
    public static ItemWrapper wrap(Item item) {
        if (item == null) return null;
        return customitems$wrap(item);
    }

    @ApiStatus.Internal
    private static ItemWrapper customitems$wrap(@NotNull Item item) {
        return ((ItemMixinAccess) item).customitems$wrap();
    }

    @ApiStatus.Internal
    public ItemWrapper(@NotNull Item handle) {
        if (customitems$wrap(handle) != null) {
            throw new UnsupportedOperationException("DO NOT USE THIS CONSTRUCTOR");
        }
        this.handle = handle;
    }

    @Override
    @NotNull
    public Item unwrap() {
        return this.handle;
    }

    @Override
    @NotNull
    public AItemStack getDefaultInstance() {
        return AItemStack.wrap(this.handle.getDefaultInstance());
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return this.handle.useOn(context);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        return this.handle.use(world, user, hand);
    }

    @Override
    public void inventoryTick(@NotNull AItemStack stack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        this.handle.inventoryTick(stack.unwrap(), world, entity, slot, selected);
    }

    @Override
    public boolean isFireResistant(@NotNull AItemStack stack) {
        return this.handle.isFireResistant();
    }

    @Override
    public @NotNull Key getKey() {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(this.handle);
        return Key.of(location.getNamespace(), location.getPath());
    }

    @Override
    public String toString() {
        return getKey().toString();
    }
}
