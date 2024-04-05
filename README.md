# CustomItems

API for creating custom items on top of existing ones, these items called NestedItem

# Example

```java
public final class Items {
    public static final ExampleItem FUEL = register("example", new ExampleItem(true));
    
    public static <T extends NestedItem> T register(String name, T item) {
        CustomItemsAPI.get().itemRegistry().register(Key.of("example-plugin", name), item);
        return item;
    }
}
```

```java
public class ExampleItem extends DefaultNestedItem {
    private final boolean property;

    public ExampleItem(boolean property) {
        this.property = property;
    }

    public boolean getProperty() {
        return property;
    }
    
    @Override
    protected @NotNull ItemStack createDefaultStack() {
        ItemStack stack = new ItemStack(Items.PAPER);
        stack.setHoverName(Component.literal("Example Item")
                .withStyle(Style.EMPTY.withItalic(false)));
        return stack;
    }
}
```

# Credit

Licensed with Apache-2.0 License
