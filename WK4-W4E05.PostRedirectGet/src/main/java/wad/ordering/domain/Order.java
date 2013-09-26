package wad.ordering.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


public class Order {

    private String id;
    @Length(min = 4, max = 30)
    private String name;
    @Length(min = 4, max = 50)
    private String address;
    @NotEmpty
    private String[] items;

    public Order() {
    }

    public Order(String name, String address, String[] items) {
        this.name = name;
        this.address = address;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }
}
