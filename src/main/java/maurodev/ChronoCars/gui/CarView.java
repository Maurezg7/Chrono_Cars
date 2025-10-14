package maurodev.ChronoCars.gui;

import maurodev.ChronoCars.models.Cars;
import maurodev.ChronoCars.services.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class CarView {
    private static final Logger logger = LoggerFactory.getLogger(CarView.class);
    private static final String NEW_LINE = System.lineSeparator();

    @Autowired
    private CarService carService;

    public void init() {
        boolean exit = false;
        Scanner console = new Scanner(System.in);

        while(!exit){
            short option = this.showOptions(console);
            exit = this.executeOptions(console, option);
        }
        console.close();
    }

    private short showOptions(Scanner console){
        logger.info(NEW_LINE + "1. Read cars." + NEW_LINE +
                "2. Search car by id." + NEW_LINE +
                "3. Save car." + NEW_LINE +
                "4. Update car." + NEW_LINE +
                "5. Delete car." + NEW_LINE +
                "6. Exit." + NEW_LINE +
                ">: ");
        short option = console.nextShort();
        console.nextLine();
        return option;
    }

    private boolean executeOptions(Scanner console, short option){
        boolean exit = false;

        switch(option){
            case 1 -> {
                logger.info(NEW_LINE + "--- Car list ---");
                List<Cars> cars = carService.listCars();
                if(cars.isEmpty()) {
                    logger.info(NEW_LINE + "No cars found." + NEW_LINE);
                } else {
                    cars.forEach(car -> logger.info(car.toString()));
                }
            }
            case 2 -> {
                logger.info(NEW_LINE + "--- Search car ---");
                logger.info(NEW_LINE + "Enter ID: ");
                Long id = console.nextLong();
                console.nextLine();

                Cars car = carService.searchCarByID(id);
                if(car != null) {
                    logger.info(NEW_LINE + "Car found: " + car.toString());
                } else {
                    logger.info(NEW_LINE + "Car not found with ID: " + id);
                }
            }
            case 3 -> {
                logger.info(NEW_LINE + "--- Save car ---");

                logger.info(NEW_LINE + "Brand: ");
                String brand = console.nextLine();

                logger.info(NEW_LINE + "Model: ");
                String model = console.nextLine();

                logger.info(NEW_LINE + "Color: ");
                String color = console.nextLine();

                logger.info(NEW_LINE + "Price: ");

                try {
                    Double price = console.nextDouble();
                    console.nextLine();

                    Cars car = new Cars();
                    car.setBrand(brand);
                    car.setModel(model);
                    car.setColor(color);
                    car.setPrice(price);
                    carService.saveCar(car);
                } catch (Exception e) {
                    logger.error("Invalid price format. Please enter a valid number.");
                    console.nextLine();
                }
            }
            case 4 -> {
                logger.info(NEW_LINE + "--- Update car ---");
                logger.info(NEW_LINE + "Enter id: ");
                Long carID = console.nextLong();
                console.nextLine();

                Cars car = carService.searchCarByID(carID);
                if (car != null){
                    logger.info(NEW_LINE + "Current car: " + car.toString());

                    logger.info(NEW_LINE + "Enter new brand (current: " + car.getBrand() + "): ");
                    String brand = console.nextLine();
                    if(!brand.trim().isEmpty()) car.setBrand(brand);

                    logger.info(NEW_LINE + "Enter new model (current: " + car.getModel() + "): ");
                    String model = console.nextLine();
                    if(!model.trim().isEmpty()) car.setModel(model);

                    logger.info(NEW_LINE + "Enter new color (current: " + car.getColor() + "): ");
                    String color = console.nextLine();
                    if(!color.trim().isEmpty()) car.setColor(color);

                    logger.info(NEW_LINE + "Enter new price (current: " + car.getPrice() + "): ");
                    String priceInput = console.nextLine();
                    if(!priceInput.trim().isEmpty()) {
                        try {
                            car.setPrice(Double.parseDouble(priceInput));
                        } catch (NumberFormatException e) {
                            logger.error(NEW_LINE + "Invalid price format, keeping current price");
                        }
                    }

                    carService.saveCar(car);
                    logger.info(NEW_LINE + "Car updated: " + car.toString());
                } else {
                    logger.info(NEW_LINE + "Car not found with ID: " + carID);
                }
            }
            case 5 -> {
                logger.info(NEW_LINE + "--- Delete car ---");
                logger.info(NEW_LINE + "ID car: ");
                Long carID = console.nextLong();
                console.nextLine();

                Cars car = carService.searchCarByID(carID);
                if(car != null) {
                    carService.deleteCar(carID);
                    logger.info(NEW_LINE + "Car deleted successfully!");
                } else {
                    logger.info(NEW_LINE + "Car not found with ID: " + carID);
                }
            }
            case 6 -> {
                logger.info(NEW_LINE + "App finished.");
                exit = true;
            }
            default -> {
                logger.info(NEW_LINE + "Invalid option: " + option);
            }
        }
        return exit;
    }
}