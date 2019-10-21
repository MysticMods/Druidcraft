package com.vulp.druidcraft.registry;

import com.google.common.collect.Maps;
import net.minecraftforge.common.ToolType;

import java.util.Map;
import java.util.regex.Pattern;

public class ToolTypes {
    private static final Pattern VALID_NAME = Pattern.compile("[^a-z_]"); //Only a-z and _ are allowed, meaning names must be lower case. And use _ to separate words.
    private static final Map<String, ToolTypes> values = Maps.newHashMap();

    public static final ToolTypes SICKLE = get("sickle");


    public static ToolTypes get(String name)
    {
        if (VALID_NAME.matcher(name).find())
            throw new IllegalArgumentException("ToolType.create() called with invalid name: " + name);
        return values.computeIfAbsent(name, k -> new ToolTypes(name));
    }

    private final String name;

    private ToolTypes(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    }