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

package me.nelonn.customitems.api;

import me.nelonn.flint.path.Key;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

public interface CustomItemsAPI {
    String ID = "customitems";
    String API = ID + ":api";

    AtomicReference<CustomItemsAPI> INSTANCE = new AtomicReference<>();

    static @NotNull CustomItemsAPI get() {
        return INSTANCE.get();
    }

    @NotNull NestedItemRegistry itemRegistry();

    // found item with that key in real and nested items
    @Nullable AItem item(@NotNull Key key);

    @Contract("null -> null; !null -> !null")
    TrueItem wrap(Item item);

    @Contract("null -> null; !null -> !null")
    AItemStack wrap(ItemStack itemStack);
}
