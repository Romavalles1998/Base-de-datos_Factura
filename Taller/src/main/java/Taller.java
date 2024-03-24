import java.util.Scanner;
import java.sql.*;

public class Taller {
    private static java.sql.Connection con;
    private static int currentScreen = 0;
    private static Integer userId = -1;
    private static String userName = "";

    public static void main(String[] args) throws SQLException {
        int option;
        String host = "jdbc:sqlite:src/main/resources/Taller";
        con = java.sql.DriverManager.getConnection(host);
        while (true) {
            printMenu();
            option = getOption();
            if (option == 0) break;
            if (currentScreen == 0) {
                switch (option) {
                    case 1:
                        allCars();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        register();
                        break;
                }
            } else {
                switch (option) {
                    case 1:
                        myCars();
                        break;
                    case 2:
                        car();
                        break;
                    case 3:
                        othersCars();
                        break;
                    case 4:
                        logout();
                        break;
                }
            }
        }
    }

    private static int getOption() {
        Scanner sc = new Scanner(System.in);
        int option = -1;
        try {
            option = Integer.parseInt(sc.next());
            if ((currentScreen == 0 && option > 3) || (currentScreen == 1 && option > 6)) {
                System.out.println("Incorrect option");
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("Incorrect option");
        }
        return option;
    }

    private static void printMenu() {
        System.out.println("------------------------------------------------------------------------------------------");
        if (currentScreen == 0) {
            System.out.println("0 Exit | 1 All Cars | 2 Login | 3 Register");
        } else {
            System.out.println("0 Exit | 1 My Cars | 2 New Car | 3 Other's Car | 4 Logout " + userName);
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }

    private static void login() throws SQLException {
        Scanner sc = new Scanner(System.in);
        //Coger datos
        System.out.println("Name:");
        String name = sc.nextLine();

        //Hacer la consulta
        String query = "SELECT * FROM usuario WHERE nombre = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        //Actuar en consecuencia
        if (rs.next()) {
            userId = rs.getInt("usuario_id");
            userName = rs.getString("nombre");
            currentScreen = 1;
        } else {
            System.out.println("User not found");
        }

    }

    private static void logout() {
        currentScreen = 0;
        userName = "";
        userId = -1;
    }

    private static void allCars() throws SQLException {
        //Hacer la consulta
        String query = "SELECT c.marca, u.nombre " +
                " FROM coche as c INNER JOIN usuario as u " +
                " ON c.usuario_id = u.usuario_id";
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            printPost(rs);
        }
    }

    private static void printPost(ResultSet rs) throws SQLException {
        System.out.println(rs.getString("marca") + " User:" + rs.getString("nombre"));
    }

    private static void myCars() throws SQLException {
        //Hacer la consulta
        String query = "SELECT c.marca, u.nombre " +
                " FROM coche as c INNER JOIN usuario as u " +
                " ON c.usuario_id = u.usuario_id WHERE c.usuario_id = ?";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, userId);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            printPost(rs);
        }
    }

    private static void othersCars() throws SQLException {
        //Hacer la consulta
        String query = "SELECT c.marca, u.nombre " +
                " FROM coche as c INNER JOIN usuario as u " +
                " ON c.usuario_id = u.usuario_id WHERE c.usuario_id != ?";

        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, userId);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            printPost(rs);
        }
    }

    private static void register() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name: ");
        String name = sc.nextLine();

        System.out.println("Lastname: ");
        String lastname = sc.nextLine();

        String query = "INSERT INTO usuario (nombre, apellido) VALUES (?, ?)";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, name);
        st.setString(2, lastname);
        st.executeUpdate();
    }


    private static void car() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Car: ");
        String car = sc.nextLine();

        String query = "INSERT INTO coche (marca, usuario_id) VALUES (?, ?)";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, car);
        st.setInt(2, userId);
        st.executeUpdate();

        System.out.println("Car created");

    }
}

