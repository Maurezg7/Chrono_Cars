package maurodev.ChronoCars;

import maurodev.ChronoCars.models.Cars;
import maurodev.ChronoCars.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
class ChronoCarsApplicationTests {

    @Autowired
    private CarRepository carRepository;

    @Test
    void contextLoads() {
        assertNotNull(carRepository);
    }

    @Test
    void testListCars() {
        try {
            List<Cars> cars = carRepository.findAll();

            assertNotNull(cars, "La lista de cars no debería ser null");
            assertTrue(true, "El tamaño de la lista debería ser >= 0");

            System.out.println("Número de cars encontrados: " + cars.size());
            cars.forEach(car -> System.out.println(car.toString()));

        } catch (Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}