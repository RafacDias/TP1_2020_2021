import base.Address;
import base.Customer;
import base.Person;
import order.base.OrderStatus;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.exceptions.PositionException;
import order.packing.Color;
import order.shippingorder.ExporterJSON;
import order.shippingorder.ShippingOrder;
import org.json.simple.parser.ParseException;
import packing.Container;
import packing.Item;
import packing.Position;
import packing_gui.PackingGUI;
import shippingorder.IExporter;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h2>
 * Escola Superior de Tecnologia e Gestão (ESTG)<br>
 * Politécnico do Porto (PP)<br>
 * Licenciatura em Engenharia Informática (LEI)<br>
 * Engenharia de Software 2<br>
 * 2020 / 2021<br>
 * </h2>
 *
 */
public class main {

    private final static String DELIMITER = "---------------";

    public static void main(String[] args) {
        System.out.println("Demo");


        Item[] items = {
                new Item(1, 1, 1, "ITEM1", "ITEM1"),
                new Item(2, 2, 2, "ITEM2", "ITEM1"),
                new Item(3, 3, 3, "ITEM3", "ITEM1"),
                new Item(4, 4, 4, "ITEM4", "ITEM1"),
                new Item(5, 5, 5, "ITEM5", "ITEM1"),
                new Item(6, 6, 6, "ITEM1", "ITEM1")
        };

        Address add1 = new Address("street A1", 1, "city A1", "state A1", "country A1");
        Address add2 = new Address("street A2", 2, "city A2", "state A2", "country A2");

        Customer customer = new Customer("John Doe", add1, add1);

        Person destination = new Person("Jane Doe", add2);

        // STEP 2. CREATE CONTAINERS
        //Container 1
        Container container = new Container("c1", 50, 50, 50, 10);
        try {
            container.addItem(items[0], new Position(0, 0, 0), Color.aqua);
        } catch (ContainerException | IllegalArgumentException | PositionException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            container.addItem(items[1], new Position(10, 10, 10), Color.blue);
            container.addItem(items[2], new Position(150, 15, 20), Color.gray);

        } catch (ContainerException | IllegalArgumentException | PositionException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Container 2
        Container container2 = new Container("c2", 50, 50, 50, 10);
        try {
            container2.addItem(items[0], new Position(0, 0, 0), Color.lime);
            container2.addItem(items[1], new Position(10, 10, 10), Color.maroon);
            container2.addItem(items[2], new Position(150, 15, 15), Color.teal);

        } catch (ContainerException | IllegalArgumentException | PositionException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

        //STEP 3. CREATE AND MANAGE ORDER
        ShippingOrder order = new ShippingOrder(customer, destination);
        try {
            order.setStatus(OrderStatus.IN_TREATMENT);
        } catch (OrderException | ContainerException | PositionException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            container.close();
            container2.close();
        } catch (ContainerException | PositionException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            order.addContainer(container);
            order.addContainer(container2);
        } catch (OrderException | ContainerException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //4. Validate
        try {
            order.validate();
        } catch (ContainerException | PositionException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

        //5. Order's conclusion
        try {
            order.setStatus(OrderStatus.CLOSED);
            order.setStatus(OrderStatus.SHIPPED);
        } catch (OrderException | ContainerException | PositionException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

        //6. Exporter usage
        System.out.println(DELIMITER + " Begin Exporter " + DELIMITER);
        IExporter exporter = new ExporterJSON("files/test.json");
        try {
            exporter.export(order);
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(DELIMITER + " End Exporter " + DELIMITER);

        String absolute = (new File("files/test.json")).getAbsolutePath();
        try {
            PackingGUI.render(absolute);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
