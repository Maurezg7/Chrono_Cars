package maurodev.ChronoCars.repository;

import maurodev.ChronoCars.models.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Cars, Long> { }
