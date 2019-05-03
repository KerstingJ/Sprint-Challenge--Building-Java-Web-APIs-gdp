package com.lambda.sprint.models;

import java.util.concurrent.atomic.AtomicLong;

public class GDP
{
    private static final AtomicLong atl = new AtomicLong();
    private long id;
    private String name;
    private long value;

    public GDP()
    {
    }

    public GDP(String name, long value)
    {
        id = atl.incrementAndGet();
        this.name = name;
        this.value = value;
    }

    public GDP(String name, String value)
    {
        id = atl.incrementAndGet();
        this.name = name;
        this.value = Long.valueOf(value);
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getValue()
    {
        return value;
    }

    public void setValue(long value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "GDP{" + "id=" + id + ", name='" + name + '\'' + ", value=" + value + '}';
    }
}
