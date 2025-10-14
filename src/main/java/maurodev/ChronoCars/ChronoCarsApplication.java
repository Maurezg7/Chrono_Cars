package maurodev.ChronoCars;

import maurodev.ChronoCars.gui.CarView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChronoCarsApplication implements CommandLineRunner {
    @Autowired
    private CarView carView;

	public static void main(String[] args) {
        SpringApplication.run(ChronoCarsApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        carView.init();
    }
}
