package maurodev.ChronoCars.gui;

import maurodev.ChronoCars.models.Cars;
import maurodev.ChronoCars.services.CarService;
import maurodev.ChronoCars.services.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
public class CarForm extends JFrame{
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel bodyPanel;
    private JPanel buttonsPanel;
    private JTextField textBrand;
    private JTextField textModel;
    private JTextField textColor;
    private JTextField textPrice;
    private JButton SAVEButton;
    private JButton DELETEButton;
    private JButton CLEANButton;
    private JTable carsTable;
    private DefaultTableModel tableCarsModel;
    ICarService carService;
    private Long IDCar;

    @Autowired
    public CarForm(CarService carService){
        this.carService = carService;
        createUIComponents();
        Initialized();
        SAVEButton.addActionListener(e -> saveCar());
        carsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadSelectedClient();
            }
        });
        DELETEButton.addActionListener(e -> deleteCar());
        CLEANButton.addActionListener(e -> cleanForm());
    }

    private void Initialized(){
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
        setTitle("Chrono Cars");
        loadCars();
    }

    private void createUIComponents(){
        this.tableCarsModel = new DefaultTableModel(0, 5){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        if (carsTable != null) {
            String[] headers = {"ID", "Brand", "Model", "Color", "Price"};
            tableCarsModel.setColumnIdentifiers(headers);
            carsTable.setModel(tableCarsModel);
        }else{
            System.out.println("Cars table null.");
        }
    }

    private void loadCars(){
        this.tableCarsModel.setRowCount(0);
        List<Cars> cars = this.carService.listCars();
        cars.forEach(car -> {
            Object[] rowCar = {
                    car.getId(),
                    car.getBrand(),
                    car.getModel(),
                    car.getColor(),
                    car.getPrice()
            };
            this.tableCarsModel.addRow(rowCar);
        });
    }

    private void saveCar(){
        if(textBrand.getText().equals("")){
            showMessage("Provide a brand.");
            textBrand.requestFocusInWindow();
            return;
        }
        if(textModel.getText().equals("")){
            showMessage("Provide a model.");
            textModel.requestFocusInWindow();
            return;
        }
        if(textColor.getText().equals("")){
            showMessage("Provide a color.");
            textColor.requestFocusInWindow();
            return;
        }
        if(textPrice.getText().equals("")){
            showMessage("Provide a price.");
            textPrice.requestFocusInWindow();
            return;
        }

        String brand = textBrand.getText();
        String model = textModel.getText();
        String color = textColor.getText();
        Double price = Double.parseDouble(textPrice.getText());
        Cars car = new Cars(this.IDCar, brand, model, color, price);
        carService.saveCar(car);
        if(this.IDCar == null){
            showMessage("Added new car.");
        }else{
            showMessage("Modified car");
        }
        cleanForm();
        loadCars();
    }

    private void loadSelectedClient(){
        var row = carsTable.getSelectedRow();
        if(row != -1){
            Long id = Long.parseLong(carsTable.getModel().getValueAt(row, 0).toString());
            this.IDCar = id;
            String brand = carsTable.getModel().getValueAt(row, 1).toString();
            this.textBrand.setText(brand);
            String model = carsTable.getModel().getValueAt(row, 2).toString();
            this.textModel.setText(model);
            String color = carsTable.getModel().getValueAt(row, 3).toString();
            this.textColor.setText(color);
            String price = carsTable.getModel().getValueAt(row, 4).toString();
            this.textPrice.setText(price);
        }
    }

    private void deleteCar(){
        var row = carsTable.getSelectedRow();
        if(row != -1){
            String idCarStr = carsTable.getModel().getValueAt(row, 0).toString();
            this.IDCar = Long.parseLong(idCarStr);
            Cars car = new Cars();
            car.setId(IDCar);
            carService.deleteCar(car);
            cleanForm();
            loadCars();
        }else{
            showMessage("You must select a car to delete.");
        }
    }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    private void cleanForm(){
        textBrand.setText("");
        textModel.setText("");
        textColor.setText("");
        textPrice.setText("");
        this.IDCar = null;
        this.carsTable.getSelectionModel().clearSelection();
    }
}
