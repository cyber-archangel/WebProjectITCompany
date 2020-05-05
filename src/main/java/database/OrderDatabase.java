package database;

import java.util.*;
import java.sql.*;
import database.utils.*;
import models.Order;

public class OrderDatabase {

    public void addNewOrder(final String username, final String orderName) {
        try {
            final int NOT_CONFIRMED = 1;
            Objects.requireNonNull(ConnectionPool.getConnection()).createStatement().executeUpdate(insertNewOrder(username, orderName, NOT_CONFIRMED));
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void changeOrderStatus(final int orderID, final int orderStatusID) {
        try {
            Objects.requireNonNull(ConnectionPool.getConnection()).createStatement().executeUpdate(updateOrderStatus(orderID, orderStatusID));
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void changeOrderPrice(final int orderID, final String orderPrice) {
        try {
            Objects.requireNonNull(ConnectionPool.getConnection()).createStatement().executeUpdate(updateOrderPrice(orderID, orderPrice));
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Order> extractManagerOrderData() {
        ResultSet extractedData = ConnectionPool.createResultSet(selectManagerOrderData());
        return setUpOrderList(extractedData);
    }

    public List<Order> extractWorkerOrderData() {
        ResultSet extractedData = ConnectionPool.createResultSet(selectWorkerOrders());
        return setUpOrderList(extractedData);
    }

    public List<Order> extractUserOrders(final String username) {
        ResultSet extractedData = ConnectionPool.createResultSet(selectOrderDataByUsername(username));
        return setUpOrderList(extractedData);
    }

    private List<Order> setUpOrderList(ResultSet extractedData) {
        try {
            List<Order> orders = new ArrayList<>();
            while (extractedData.next())
                orders.add(new Order(extractedData.getInt(1), extractedData.getString(2), extractedData.getString(3), extractedData.getInt(4), extractedData.getString(5)));
            return orders;
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }

    private String insertNewOrder(final String username, final String orderName, final int statusID) {
        return "INSERT INTO ITCompanyDataBase.orderTable (username, orderName, orderPrice, orderStatusID) VALUES ('" + username + "', '" + orderName + "', " + null + ", " + statusID + ")";
    }

    private String updateOrderStatus(final int orderID, final int statusID) {
        return "UPDATE ITCompanyDataBase.orderTable SET orderStatusID = " + statusID + " WHERE orderID = " + orderID;
    }

    private String updateOrderPrice(final int orderID, final String orderPrice) {
        return "UPDATE ITCompanyDataBase.orderTable SET orderPrice = " + orderPrice + " WHERE orderID = " + orderID;
    }

    private String selectOrderDataByUsername(final String username) {
        return "SELECT ITCompanyDataBase.orderTable.orderID, username, orderName, orderPrice, ITCompanyDataBase.orderStatusTable.statusMeaning FROM ITCompanyDataBase.orderTable JOIN ITCompanyDataBase.orderStatusTable ON (ITCompanyDataBase.orderStatusTable.statusID = ITCompanyDataBase.orderTable.orderStatusID) WHERE username = '" + username + "'";
    }

    private String selectWorkerOrders() {
        return "SELECT ITCompanyDataBase.orderTable.orderID, username, orderName, orderPrice, ITCompanyDataBase.orderStatusTable.statusMeaning FROM ITCompanyDataBase.orderTable JOIN ITCompanyDataBase.orderStatusTable ON (ITCompanyDataBase.orderStatusTable.statusID = ITCompanyDataBase.orderTable.orderStatusID) WHERE orderStatusID != 1 AND orderStatusID != 5 ORDER BY orderID";
    }

    private String selectManagerOrderData() {
        return "SELECT ITCompanyDataBase.orderTable.orderID, username, orderName, orderPrice, ITCompanyDataBase.orderStatusTable.statusMeaning FROM ITCompanyDataBase.orderTable JOIN ITCompanyDataBase.orderStatusTable ON (ITCompanyDataBase.orderStatusTable.statusID = ITCompanyDataBase.orderTable.orderStatusID) WHERE orderStatusID != 3 AND orderStatusID != 4 ORDER BY orderID";
    }
}