import java.sql.*;

public class modify {
    public static int changeItem(String name, String category, String value) throws SQLException {
        String statement = "";

        String host = "jdbc:mysql://localhost/grocery_store";

        Connection con = DriverManager.getConnection(host, "root", "cameron1");

        String query = "SELECT * FROM inventory";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            if (rs.getString("Name").equals(name)) {
                if (category == "Quantity") {
                    int newValue = Integer.parseInt(value) + rs.getInt(4);
                    statement = "UPDATE inventory SET Quantity = " + newValue + " WHERE Name = '" + name + "'";
                } else if (category == "Producer") {
                    String newValue = value;
                    statement = "UPDATE inventory SET Producer = '" + newValue + "' WHERE Name = '" + name + "'";
                } else if (category == "ID") {
                    int newValue = Integer.parseInt(value);
                    statement = "UPDATE inventory SET ID = " + newValue + " WHERE Name = '" + name + "'";
                } else if (category == "Name") {
                    String newValue = value;
                    statement = "UPDATE inventory SET Name = '" + newValue + "' WHERE Name = '" + name + "'";
                } else if (category == "Price") {
                    double newValue = Double.parseDouble(value);
                    statement = "UPDATE inventory SET Price = " + newValue + " WHERE Name = '" + name + "'";
                }
            }
        }

        rs.beforeFirst();

        if (statement != "") {
            PreparedStatement stat = con.prepareStatement(statement);
            stat.execute();
            System.out.println("Changed item: " + name);
            con.close();
            return 1;
        } else {
            System.out.println("No item with name " + name + " found.");
            con.close();
            return 0;
        }
    }

    public static double getPrice(String item) throws SQLException {
        double price = 0;

        String host = "jdbc:mysql://localhost/grocery_store";

        Connection con = DriverManager.getConnection(host, "root", "cameron1");

        String query = "SELECT * FROM inventory";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next())
            if (rs.getString("Name").equals(item))
                price = rs.getDouble(3);

        String temp = String.format("%.2f", price);
        price = Double.parseDouble(temp);

        con.close();

        return price;
    }

    public static void newItem(product thing) throws SQLException {
        String host = "jdbc:mysql://localhost/grocery_store";

        Connection con = DriverManager.getConnection(host, "root", "cameron1");

        String statement = "INSERT INTO inventory VALUES (" + thing.getID() + ", '" + thing.getName() + "', " + thing.getPrice() + ", " + thing.getQuantity() + ", '" + thing.getProducer() + "')";

        PreparedStatement stat = con.prepareStatement(statement);

        stat.execute();

        con.close();
    }

    public static void newOrder(order thing) throws SQLException {
        String host = "jdbc:mysql://localhost/grocery_store";

        Connection con = DriverManager.getConnection(host, "root", "cameron1");

        String statement = "INSERT INTO orders VALUES (" + thing.getID() + ", '" + thing.getDate() + "', " + thing.getPrice() + ")";

        PreparedStatement stat = con.prepareStatement(statement);

        stat.execute();

        con.close();
    }

    public static int getOrderNum() throws SQLException {
        String host = "jdbc:mysql://localhost/grocery_store";

        Connection con = DriverManager.getConnection(host, "root", "cameron1");

        int num = 0;

        String query = "SELECT * FROM orders";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next())
            num++;

        con.close();

        return num;
    }

    public static void newUser(user thing) throws SQLException {
        String host = "jdbc:mysql://localhost/grocery_store";

        Connection con = DriverManager.getConnection(host, "root", "cameron1");

        String statement = "INSERT INTO users VALUES (" + thing.getID() + ", '" + thing.getName() + "', '" + thing.getStatus() + "', '" + thing.getPassword() + "', '" + thing.getPicture() + "')";

        PreparedStatement stat = con.prepareStatement(statement);

        stat.execute();

        con.close();
    }

    public static int changeUser(String name, String category, String value) throws SQLException {
        String statement = "";

        String host = "jdbc:mysql://localhost/grocery_store";

        Connection con = DriverManager.getConnection(host, "root", "cameron1");

        String query = "SELECT * FROM users";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while(rs.next())
        {
            if (rs.getString("Name").equals(name))
            {
                if (category == "ID") {
                    int newValue = Integer.parseInt(value);
                    statement = "UPDATE users SET ID = " + newValue + " WHERE Name = '" + name + "'";
                }
                else if (category == "Name") {
                    statement = "UPDATE users SET Name = '" + value + "' WHERE Name = '" + name + "'";
                }
                else if (category == "Status") {
                    statement = "UPDATE users SET Status = '" + value + "' WHERE Name = '" + name + "'";
                }
                else if (category == "Password") {
                    statement = "UPDATE users SET Password = '" + value + "' WHERE Name = '" + name + "'";
                }
                else if (category == "Picture") {
                    statement = "UPDATE users SET Picture = '" + value + "' WHERE Name = '" + name + "'";
                }
            }
        }

        rs.beforeFirst();

        if(statement != "")
        {
            PreparedStatement stat = con.prepareStatement(statement);
            stat.execute();
            System.out.println("Changed user: " + name);
            con.close();
            return 1;
        }
        else {
            System.out.println("No user with name " + name + " found.");
            con.close();
            return 0;
        }
    }

    public void deleteUser(String username) throws SQLException {
        String host = "jdbc:mysql://localhost/grocery_store";

        Connection con = DriverManager.getConnection(host, "root", "cameron1");

        String statement = "DELETE FROM users WHERE Name = '" + username + "'";

        PreparedStatement stat = con.prepareStatement(statement);

        stat.execute();

        con.close();
    }
}
