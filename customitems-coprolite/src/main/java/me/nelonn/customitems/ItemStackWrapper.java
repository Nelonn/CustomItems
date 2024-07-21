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

package me.nelonn.customitems;

import me.nelonn.customitems.api.*;
import me.nelonn.customitems.utility.ItemStackMixinAccess;
import me.nelonn.flint.path.Key;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemStackWrapper implements AItemStack {
    private final ItemStack handle;

    @Contract("null -> null; !null -> !null")
    public static ItemStackWrapper wrap(ItemStack itemStack) {
        if (itemStack == null) return null;
        return customitems$wrap(itemStack);
    }

    @ApiStatus.Internal
    private static ItemStackWrapper customitems$wrap(@NotNull ItemStack itemStack) {
        return ((ItemStackMixinAccess) (Object) itemStack).customitems$wrap();
    }

    @ApiStatus.Internal
    public ItemStackWrapper(@NotNull ItemStack handle) {
        if (customitems$wrap(handle) != null) {
            throw new UnsupportedOperationException("DO NOT USE THIS CONSTRUCTOR");
        }
        this.handle = handle;
    }

    @Override
    public @NotNull ItemStack unwrap() {
        return this.handle;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull AItem getItem() {
        returnTrueItem:
        {
            CustomData customData = unwrap().get(DataComponents.CUSTOM_DATA);
            if (customData == null) break returnTrueItem;
            CompoundTag tag = customData.getUnsafe();
            if (!tag.contains("id", Tag.TAG_STRING)) break returnTrueItem;
            String idString = tag.getString("id");
            Key key = Key.tryOrNull(idString);
            if (key == null) break returnTrueItem;
            NestedItem nestedItem = CustomItemsAPI.get().itemRegistry().get(key);
            if (nestedItem == null) break returnTrueItem;
            return nestedItem;
        }
        return getTrueItem();
    }

    @Override
    public @NotNull TrueItem getTrueItem() {
        return TrueItem.wrap(this.handle.getItem());
    }

    @Override
    public boolean isEmpty() {
        return this.handle.isEmpty();
    }

    @Override
    public int getCount() {
        return this.handle.getCount();
    }

    @Override
    public void setCount(int count) {
        this.handle.setCount(count);
    }

    @Override
    public String toString() {
        return this.getCount() + " " + this.getItem();
    }
}
