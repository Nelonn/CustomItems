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

import me.nelonn.coprolite.api.CoproliteLoader;
import me.nelonn.customitems.api.*;
import me.nelonn.flint.path.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CustomItemsCoprolite implements CustomItemsAPI {
    private final NestedItemRegistry nestedItemRegistry = new NestedItemRegistry();

    public CustomItemsCoprolite() {
        CoproliteLoader.getInstance().getObjectShare().put(CustomItemsAPI.API, this);
    }

    @Override
    public @NotNull NestedItemRegistry itemRegistry() {
        return nestedItemRegistry;
    }

    @Override
    public @Nullable AItem item(@NotNull Key key) {
        return BuiltInRegistries.ITEM.getOptional(ResourceLocation.fromNamespaceAndPath(key.namespace(), key.value()))
                .map(item -> (AItem) TrueItem.wrap(item))
                .orElseGet(() -> itemRegistry().get(key));
    }

    @Override
    @Contract("null -> null; !null -> !null")
    public TrueItem wrap(Item item) {
        return ItemWrapper.wrap(item);
    }

    @Override
    @Contract("null -> null; !null -> !null")
    public AItemStack wrap(ItemStack itemStack) {
        return ItemStackWrapper.wrap(itemStack);
    }
}
