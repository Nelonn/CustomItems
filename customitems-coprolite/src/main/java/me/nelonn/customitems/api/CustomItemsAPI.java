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

package me.nelonn.customitems.api;

import me.nelonn.coprolite.api.CoproliteLoader;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface CustomItemsAPI {
    String ID = "customitems";
    String API = ID + ":api";

    static @NotNull CustomItemsAPI get() {
        return (CustomItemsAPI) CoproliteLoader.getInstance().getObjectShare().get(API);
    }

    @NotNull NestedItemRegistry itemRegistry();

    @Contract("null -> null; !null -> !null")
    TrueItem wrap(Item item);

    @Contract("null -> null; !null -> !null")
    AItemStack wrap(ItemStack itemStack);
}
