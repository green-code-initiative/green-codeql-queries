package test;

@interface Entity {}

@interface OneToMany {}

@interface ManyToMany {}

@Entity
class Item {
    String getName() {
        return "";
    }
}

@Entity
class Order {

    @OneToMany
    private java.util.List<Item> items;

    java.util.List<Item> getItems() {
        return items;
    }
}

class OrderService {

    // BAD: should be detected
    void bad(java.util.List<Order> orders) {
        for (Order order : orders) {
            order.getItems();
        }
    }

    // GOOD: not inside loop
    void good(Order order) {
        order.getItems();
    }
}