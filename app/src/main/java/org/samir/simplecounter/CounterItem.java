package org.samir.simplecounter;

public class CounterItem {
    private int id;
    private String name;
    private int count;


    public CounterItem(String name, int count) {
        this.id = Utils.getId();
        this.name = name;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
