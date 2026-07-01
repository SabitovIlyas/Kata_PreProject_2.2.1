package hiber;

import hiber.config.AppConfig;
import hiber.exceptions.NonUniqueResultReturnFirstException;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        CarService carService = context.getBean(CarService.class);
        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru",
                new Car("Model1", 1)));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru",
                new Car("Model2", 2)));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru",
                new Car("Model44", 3)));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru",
                new Car("Model4", 4)));

        List<User> users = userService.listUsers();
        for (User user : users) {
            printUser(user);
        }

        User foundUser = null;
        String message ="";
        try {
            foundUser = userService.getUserByCarModelAndCarSeries("Model1", 1);
        } catch (NoResultException e) {
           message = "Пользователь не найден";
        } catch (NonUniqueResultReturnFirstException e) {
           message = "Найдено " + e.resultCount + " пользователей. Возвращаем первое соответствие:";
           foundUser = (User) e.firstElement;
        }

        printUser(foundUser, message);

        context.close();
    }

    private static void printUser(User user, String message) {
        System.out.println(message);
        if (user != null) {
            printUser(user);
        }
    }

    private static void printUser(User user) {
        System.out.println("Id = " + user.getId());
        System.out.println("First Name = " + user.getFirstName());
        System.out.println("Last Name = " + user.getLastName());
        System.out.println("Email = " + user.getEmail());
        System.out.println("Car Id = " + (user.getCar() != null ? user.getCar().getId() : ""));
        System.out.println("Car Model = " + (user.getCar() != null ? user.getCar().getModel() : ""));
        System.out.println("Car Series = " + (user.getCar() != null ? user.getCar().getSeries() : ""));
        System.out.println();
    }
}
