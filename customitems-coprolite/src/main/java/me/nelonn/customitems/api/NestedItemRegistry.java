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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.nelonn.flint.path.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class NestedItemRegistry implements Iterable<NestedItem> {
    private final BiMap<Key, NestedItem> map = HashBiMap.create();

    public NestedItemRegistry() {
    }

    public void register(@NotNull Key name, @NotNull NestedItem type) {
        map.put(name, type);
    }

    public @Nullable NestedItem unregister(@NotNull Key name) {
        return map.remove(name);
    }

    public @Nullable NestedItem get(@NotNull Key name) {
        return map.get(name);
    }

    public @NotNull Optional<NestedItem> getOptional(@NotNull Key name) {
        return Optional.ofNullable(map.get(name));
    }

    public @Nullable Key getKey(@NotNull NestedItem type) {
        return map.inverse().get(type);
    }

    public @NotNull Optional<Key> getKeyOptional(@NotNull NestedItem type) {
        return Optional.ofNullable(map.inverse().get(type));
    }

    public @NotNull Set<Key> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }

    @NotNull
    @Override
    public Iterator<NestedItem> iterator() {
        return Collections.unmodifiableCollection(map.values()).iterator();
    }
}
