package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
    private static AnnotationConfigApplicationContext context;
    private static UserService userService;
    private static CarService carService;
    private static List<User> users;
    private static List<Car> cars;
    
    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        createServices();
        addUsers();
        addCars();
        getLists();
        printLists();
        addCarsToUsers();
        printUsersWithCars();
        printUserByCar("Model_3", 222);

        context.close();
    }

    private static void createServices() {
        userService = context.getBean(UserService.class);
        carService = context.getBean(CarService.class);
    }

    private static void addUsers() {
        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));
    }

    private static void addCars() {
        carService.add(new Car("Model_1", 111));
        carService.add(new Car("Model_2", 222));
        carService.add(new Car("Model_3", 222));
        carService.add(new Car("Model_4", 444));
    }

    private static void getLists() {
        users = userService.listUsers();
        cars = carService.listCars();
    }

    private static void printLists() {
        users.forEach(System.out::println);
        cars.forEach(System.out::println);
    }

    private static void addCarsToUsers() {
        for (int i = 0; i < users.size(); i++) {
            Car car = cars.get(i);

            if (car == null) {
                continue;
            }

            User user = users.get(i);
            user.setCar(car);
            userService.update(user);
        }
    }

    private static void printUsersWithCars() {
        users.forEach(System.out::println);
    }

    private static void printUserByCar(String model, int series) {
        System.out.println(userService.findByCar(model, series).toString());
    }
}
