package maurodev.ChronoCars;

import com.formdev.flatlaf.FlatDarculaLaf;
import maurodev.ChronoCars.gui.CarForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class ChronoCarsSwing {
    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        ConfigurableApplicationContext contextSpring = new SpringApplicationBuilder(ChronoCarsSwing.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

        SwingUtilities.invokeLater(() -> {
            CarForm carForm = contextSpring.getBean(CarForm.class);
            carForm.setVisible(true);
        });
    }
}
