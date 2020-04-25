package models;

public class Order {

    private final int orderID;
    private final String customerName;
    private final String orderName;
    private final int orderPrice;
    private final String orderStatusMeaning;

    public Order(int orderID, String customerName, String orderName, int orderPrice, String orderStatusMeaning) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.orderName = orderName;
        this.orderPrice = orderPrice;
        this.orderStatusMeaning = orderStatusMeaning;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrderName() {
        return orderName;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public String getOrderStatusMeaning() {
        return orderStatusMeaning;
    }
}