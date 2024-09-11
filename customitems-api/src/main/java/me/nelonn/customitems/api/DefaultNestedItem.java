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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class DefaultNestedItem implements NestedItem {
    public DefaultNestedItem() {
    }

    @NotNull
    protected abstract ItemStack createDefaultStack();

    @NotNull
    public final AItemStack getDefaultInstance() {
        ItemStack itemStack = createDefaultStack();
        CompoundTag customData = itemStack.getOrCreateTag();
        customData.putString("id", getKey().toString());
        return AItemStack.wrap(itemStack);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return context.getItemInHand().getItem().useOn(context);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        return user.getItemInHand(hand).getItem().use(world, user, hand);
    }

    @Override
    public void inventoryTick(@NotNull AItemStack stack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        stack.getTrueItem().inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public @NotNull Component getDescription() {
        Key key = getKey();
        return Component.translatable("item." + key.namespace() + "." + key.value());
    }

    @Override
    public @NotNull Key getKey() {
        return CustomItemsAPI.get().itemRegistry().getKeyOptional(this).orElseThrow(() ->
                new RuntimeException("Item type '" + this.getClass().getName() + "' is not registered"));
    }

    @Override
    public boolean canBeUsedInCrafts() {
        return false;
    }

    @Override
    public String toString() {
        return getKey().toString();
    }
}
