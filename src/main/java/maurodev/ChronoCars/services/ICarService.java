package maurodev.ChronoCars.services;

import maurodev.ChronoCars.models.Cars;

import java.util.List;

public interface ICarService {
    List<Cars> listCars();
    Cars searchCarByID(Long id);
    void saveCar(Cars cars);
    void deleteCar(Long id);
}
