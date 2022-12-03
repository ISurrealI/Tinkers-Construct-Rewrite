package surreal.ticon.api.util;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

import java.util.Map;
import java.util.Set;

public class ModelHelper {
    public static String getVariantFromState(IBlockState state) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        Set<Map.Entry<IProperty<?>, Comparable<?>>> set = state.getProperties().entrySet();
        for (Map.Entry<IProperty<?>, Comparable<?>> entry : set) {
            builder.append(entry.getKey().getName()).append('=');
            Object value = entry.getValue();
            if (value instanceof IStringSerializable) {
                builder.append(((IStringSerializable) value).getName());
            } else builder.append(value);
            i++;
            if (i < set.size()) builder.append(',');
        }
        return builder.toString();
    }
}
