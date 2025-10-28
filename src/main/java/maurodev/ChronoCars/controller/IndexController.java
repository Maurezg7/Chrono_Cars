package maurodev.ChronoCars.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import maurodev.ChronoCars.models.Cars;
import maurodev.ChronoCars.services.ICarService;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ViewScoped
@Data
public class IndexController {

    @Autowired
    private ICarService carService;
    private List<Cars> cars;
    private Cars selectedCar;
    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @PostConstruct
    public void init(){
        loadData();
    }

    public void loadData(){
        this.cars = this.carService.listCars();
        this.cars.forEach(car -> {
            logger.info(car.toString());
        });
    }

    public void addCar(){
        this.selectedCar = new Cars();
    }

    public void saveCar(){
        logger.info("Saved car: " + this.selectedCar);
        if(this.selectedCar.getId() == null){
            this.carService.saveCar(this.selectedCar);
            this.cars.add(this.selectedCar);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Added car."));
        }else{
            this.carService.saveCar(this.selectedCar);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Updated car"));
        }
        PrimeFaces.current().executeScript("PF('modalWindowCar').hide()");
        PrimeFaces.current().ajax().update("form-car:messages",
                "form-car:cars-table");
        this.selectedCar = null;
    }

    public void deleteCar(){
        logger.info("Deleted car: " + this.selectedCar);
        this.carService.deleteCar(this.selectedCar);
        this.cars.remove(this.selectedCar);
        this.selectedCar=null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Delete car."));
        PrimeFaces.current().ajax().update("form-car:messages","form-car:cars-table");
    }
}