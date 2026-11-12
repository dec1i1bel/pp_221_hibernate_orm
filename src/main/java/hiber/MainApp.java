package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
   public static void main(String[] args) {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

        User user1 = new User("User1", "Lastname1", "user1@mail.ru");
        User user2 = new User("User2", "Lastname2", "user2@mail.ru");
        User user3 = new User("User3", "Lastname3", "user3@mail.ru");
        User user4 = new User("User4", "Lastname4", "user4@mail.ru");

      UserService userService = context.getBean(UserService.class);

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);
      userService.add(user4);

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }

        CarService carService = context.getBean(CarService.class);

        carService.add(new Car("Model_1", 111, user1));
        carService.add(new Car("Model_2", 222, user2));
        carService.add(new Car("Model_3", 222, user3));
        carService.add(new Car("Model_4", 444, user4));

        List<Car> cars = carService.listCars();
        for (Car car : cars) {
            System.out.println("Id = " + car.getId());
            System.out.println("Model = " + car.getModel());
            System.out.println("Series = " + car.getSeries());
            System.out.println("Owner full name = " + car.getUser().getFirstName() + " " + car.getUser().getLastName());
            System.out.println();
        }

        context.close();
    }
}
