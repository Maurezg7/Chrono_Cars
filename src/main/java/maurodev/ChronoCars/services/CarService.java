package maurodev.ChronoCars.services;

import maurodev.ChronoCars.models.Cars;
import maurodev.ChronoCars.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements ICarService{
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Cars> listCars() {
        try{
            List<Cars> cars = carRepository.findAll();
            if(cars != null && cars.size() >= 0)
                return cars;
            else
                System.out.println("The list is null.");
                return List.of();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
         System.out.println("Operation finished.");
        }
    }

    @Override
    public Cars searchCarByID(Long id) {
        try{
            if(id >= 0){
                Cars car = this.carRepository.findById(id).orElse(null);
                return car;
            }else{
                logger.info("That car doesn't exist or wrong id.");
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException("Error search the car: " + e.getMessage());
        }
    }

    @Override
    public void saveCar(Cars car) {
        try{
            if(car != null){
                this.carRepository.save(car);
                logger.info("Car added.");
            }else{
                logger.info("You must enter the car information correctly.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving car: " + e.getMessage());
        }

    }

    @Override
    public void deleteCar(Cars car) {
        try{
            if(car.getId() >= 0){
                this.carRepository.deleteById(car.getId());
            }else{
                logger.info("That car doesn't exist or wrong id.");
            }
        }catch (Exception e){
            throw new RuntimeException("Error deleting car: " + e.getMessage());
        }
        this.carRepository.deleteById(car.getId());
    }
}
