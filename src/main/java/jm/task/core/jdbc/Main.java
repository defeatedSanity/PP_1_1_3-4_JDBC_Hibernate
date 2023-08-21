package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Aboba", "Ivanov", (byte) 20);
        userService.saveUser("Alex", "Zhmyh", (byte) 25);
        userService.saveUser("Valera", "Albertovich", (byte) 54);
        userService.saveUser("Denis", "Petrov", (byte) 33);
        System.out.println(userService.getAllUsers());
        userService.removeUserById(2);
        System.out.println(userService.getAllUsers());

        userService.dropUsersTable();

//        System.out.println(userService.getAllUsers());
//        userService.cleanUsersTable();
//        userService.dropUsersTable();

    }
}
