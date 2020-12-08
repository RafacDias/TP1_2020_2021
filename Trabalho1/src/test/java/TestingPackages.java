import base.Address;
import base.Customer;
import base.Person;
import order.base.OrderStatus;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.exceptions.PositionException;
import order.packing.Color;
import order.shippingorder.ShippingOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import packing.Container;
import packing.Item;
import packing.Position;
import packing_gui.PackingGUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestingPackages {
    Address addressA, addressB;
    Person destination;
    Customer customer;
    Position positionA, positionB, positionC, positionD;
    Item itemA, itemB, itemC, itemD;
    Container containerA, containerB;
    ShippingOrder order;

    @BeforeEach
    void setUp() throws PositionException {
        this.addressA = new Address("street A1", 1, "city A1", "state A1", "country A1");
        this.addressB = new Address("street A2", 2, "city A2", "state A2", "country A2");

        this.destination = new Person("Jane Doe", this.addressB);

        this.customer = new Customer("John Doe", this.addressA, this.addressA);

        this.positionA = new Position(0, 0, 0);
        this.positionB = new Position(0, 0, 10);
        this.positionC = new Position(0, 0, 20);
        this.positionD = new Position(0, 0, 15);

        this.itemA = new Item(1, 1, 1, "ITEM1", "ITEM1");
        this.itemB = new Item(2, 2, 2, "ITEM2", "ITEM1");
        this.itemC = new Item(3, 3, 3, "ITEM3", "ITEM1");
        this.itemD = new Item(4, 4, 4, "ITEM4", "ITEM1");

        this.containerA = new Container("c1", 50, 50, 50, 10);
        this.containerB = new Container("c2", 50, 50, 50, 10);

        this.order = new ShippingOrder(this.customer, this.destination);
    }


    /**
     * Test Case ID: TC#1.1
     * @throws PositionException
     */
    @Test
    void testValidSetX() throws PositionException {
        this.positionB.setX(0);
        assertEquals(0, this.positionB.getX(), "10 should be 0");
        this.positionB.setX(5);
        assertEquals(5, this.positionB.getX(), "0 should be 5");
        this.positionB.setX(15);
        assertEquals(15, this.positionB.getX(), "5 should be 15");
        this.positionB.setX(50);
        assertEquals(50, this.positionB.getX(), "15 should be 50");
    }

    /**
     *Test Case ID: TC#1.2
     * @throws PositionException
     */
    @Test
    void testInvalidSetX() throws PositionException {
        assertThrows(PositionException.class, () -> this.positionB.setX(-1));
        assertThrows(PositionException.class, () -> this.positionB.setX(-15));
    }


    /**
     * Test Case ID: TC#2.1
     * @throws PositionException
     */
    @Test
    void testValidSetY() throws PositionException {
        this.positionB.setY(0);
        assertEquals(0, this.positionB.getY(), "0 should be 0");
        this.positionB.setY(5);
        assertEquals(5, this.positionB.getY(), "0 should be 5");
        this.positionB.setY(15);
        assertEquals(15, this.positionB.getY(), "5 should be 15");
        this.positionB.setY(50);
        assertEquals(50, this.positionB.getY(), "15 should be 50");
    }


    /**
     * Test Case ID: TC#2.2
     * @throws PositionException
     */
    @Test
    void testInvalidSetY() throws PositionException {
        assertThrows(PositionException.class, () -> this.positionB.setY(-1));
        assertThrows(PositionException.class, () -> this.positionB.setY(-15));
    }

    /**
     * Test Case ID: TC#3.1
     * @throws PositionException
     */
    @Test
    void testValidSetZ() throws PositionException {
        this.positionB.setZ(0);
        assertEquals(0, this.positionB.getZ(), "0 should be 0");
        this.positionB.setZ(5);
        assertEquals(5, this.positionB.getZ(), "0 should be 5");
        this.positionB.setZ(15);
        assertEquals(15, this.positionB.getZ(), "5 should be 15");
        this.positionB.setZ(50);
        assertEquals(50, this.positionB.getZ(), "15 should be 50");
    }


    /**
     * Test Case ID: TC#3.2
     * @throws PositionException
     */
    @Test
    void testInvalidSetZ() throws PositionException {
        assertThrows(PositionException.class, () -> this.positionB.setZ(-1));
        assertThrows(PositionException.class, () -> this.positionB.setZ(-15));
    }


    /**
     * Test Case ID: TC#4.1
     * @throws ContainerException
     * @throws PositionException
     */
    @Test
    void testValidAddItem() throws ContainerException, PositionException {
        int volume_to_occupy=this.containerA.getOccupiedVolume()+this.itemA.getVolume()+this.itemB.getVolume()+this.itemC.getVolume();
        int correct_remaining_volume=this.containerA.getVolume()-volume_to_occupy;
        assertEquals(true, this.containerA.addItem(this.itemA, this.positionA, Color.aqua), "ItemPacked (itemA, positionA, Color.aqua) should be added to Container");
        assertEquals(true, this.containerA.addItem(this.itemB, this.positionB, Color.blue), "ItemPacked (itemB, positionB, Color.blue) should be added to Container");
        assertEquals(true, this.containerA.addItem(this.itemC, this.positionC, Color.gray), "ItemPacked (itemC, positionC, Color.gray) should be added to Container");
        assertEquals(volume_to_occupy, this.containerA.getOccupiedVolume(), "Occupied Volume should be updated after adding new items");
        assertEquals(correct_remaining_volume, this.containerA.getRemainingVolume(), "Remaining Volume should be updated after adding new items");
        Item copyOfItemA = this.itemA;
        assertEquals(false, this.containerA.addItem(copyOfItemA, new Position(0, 0, 50), Color.black), "Copy of itemA should not be added to Container");
    }


    /**
     * Test Case ID: TC#4.2
     * @throws PositionException
     * @throws ContainerException
     */
    @Test
    void testInvalidAddItem() throws PositionException, ContainerException {
        assertThrows(ContainerException.class, () -> this.containerA.addItem(null, this.positionA, Color.aqua));
        assertThrows(ContainerException.class, () -> this.containerA.addItem(this.itemA, null, Color.aqua));
        assertThrows(ContainerException.class, () -> this.containerA.addItem(this.itemA, this.positionA, null));
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.close();
        assertThrows(ContainerException.class, () -> this.containerA.addItem(this.itemB, this.positionB, Color.blue));
    }


    /**
     * Test Case ID: TC#5.1
     * @throws ContainerException
     */
    @Test
    void testValidRemoveItem() throws ContainerException {
        int correct_occupied_volume=this.containerA.getOccupiedVolume();
        int correct_remaining_volume=this.containerA.getRemainingVolume();
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        assertEquals(true, this.containerA.removeItem(this.itemA), "ItemA should be removed from ContainerA");
        assertEquals(correct_occupied_volume, this.containerA.getOccupiedVolume(), "Occupied Volume after removal should be updated");
        assertEquals(correct_remaining_volume, this.containerA.getRemainingVolume(), "Remaining Volume should be updated after removal of item");
        assertEquals(false, this.containerA.removeItem(this.itemB), "ItemB shouldn't be removed from ContainerA");
    }


    /**
     * Test Case ID: TC#5.2
     * @throws ContainerException
     * @throws PositionException
     */
    @Test
    void testInvalidRemoveItem() throws ContainerException, PositionException {
        assertThrows(ContainerException.class, () -> this.containerA.removeItem(null));
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.close();
        assertThrows(ContainerException.class, () -> this.containerA.removeItem(this.itemA));
    }


    /**
     * Test Case ID: TC#6.1
     * @throws ContainerException
     * @throws PositionException
     */
    @Test
    void testValidCloseContainer() throws ContainerException, PositionException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.close();
    }


    /**
     * Test Case ID: TC#6.2
     * @throws ContainerException
     */
    @Test
    void testInvalidCloseContainer() throws ContainerException, PositionException {
        Container containerC=this.containerA;
        Container containerD=this.containerA;
        Item longItem = new Item(100, 1, 1, "ITEM_LONG", "ITEM_LONG");
        Item fatItem = new Item(100, 100, 100, "ITEM_FAT", "ITEM_FAT");
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionA, Color.blue);
        assertThrows(PositionException.class, () -> this.containerA.close());
        this.containerB.addItem(this.itemC, new Position(0, 0, 1000), Color.aqua);
        assertThrows(PositionException.class, () -> this.containerB.close());
        containerC.addItem(longItem, this.positionA, Color.aqua);
        assertThrows(PositionException.class, () -> containerC.close());
        containerD.addItem(fatItem, this.positionA, Color.aqua);
        assertThrows(ContainerException.class, () -> containerD.close());
    }


    /**
     * Test Case ID: TC#7.0
     * @throws ContainerException
     */
    @Test
    void testGetItem() throws ContainerException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        assertEquals(this.itemA, this.containerA.getItem(this.itemA.getReference()), "getItem() with the reference value of itemA as parameter should return itemA");
        assertEquals(this.itemB, this.containerA.getItem(this.itemB.getReference()), "getItem() with the reference value of itemB as parameter should return itemB");
    }


    /**
     * Test Case ID: TC#8.1
     * @throws ContainerException
     * @throws PositionException
     */
    @Test
    void testValidateValidContainer() throws ContainerException, PositionException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        this.containerA.addItem(this.itemC, this.positionC, Color.gray);
        this.containerA.validate();
    }


    /**
     * Test Case ID: TC#8.2
     * @throws ContainerException
     * @throws PositionException
     */
    @Test
    void testValidateInvalidContainer() throws ContainerException, PositionException {
        Container containerC=this.containerA;
        Container containerD=this.containerA;
        Item longItem = new Item(100, 1, 1, "ITEM_LONG", "ITEM_LONG");
        Item fatItem = new Item(100, 100, 100, "ITEM_FAT", "ITEM_FAT");
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionA, Color.blue);
        assertThrows(PositionException.class, () -> this.containerA.validate());
        this.containerB.addItem(this.itemA, new Position(0, 0, 1000), Color.aqua);
        assertThrows(PositionException.class, () -> this.containerB.validate());
        containerC.addItem(longItem, this.positionA, Color.aqua);
        assertThrows(PositionException.class, () -> containerC.validate());
        containerD.addItem(fatItem, this.positionA, Color.aqua);
        assertThrows(ContainerException.class, () -> containerD.validate());
    }


    /**
     * Test Case ID: TC#9.1
     * @throws ContainerException
     * @throws PositionException
     * @throws OrderException
     */
    @Test
    void testValidAddContainer() throws ContainerException, PositionException, OrderException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        this.containerA.addItem(this.itemC, this.positionC, Color.gray);
        this.containerA.close();
        Container copyOfContainerA=this.containerA;
        this.order.setStatus(OrderStatus.IN_TREATMENT);
        assertEquals(true, this.order.addContainer(this.containerA), "ContainerA should be added to ShippingOrder");
        assertEquals(false, this.order.addContainer(copyOfContainerA), "A copy of ContainerA shouldn't be added to ShippingOrder");
    }


    /**
     * Test Case ID: TC#9.2
     * @throws PositionException
     * @throws ContainerException
     * @throws OrderException
     */
    @Test
    void testInvalidAddContainer() throws PositionException, ContainerException, OrderException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        this.containerA.addItem(this.itemC, this.positionC, Color.gray);
        this.order.setStatus(OrderStatus.IN_TREATMENT);
        assertThrows(ContainerException.class, () -> this.order.addContainer(null));
        assertThrows(ContainerException.class, () -> this.order.addContainer(this.containerA));
        this.containerA.close();
        this.order.setStatus(OrderStatus.CANCELLED);
        assertThrows(OrderException.class, () -> this.order.addContainer(this.containerA));
    }


    /**
     * Test Case ID: TC#10.0
     * @throws ContainerException
     * @throws PositionException
     * @throws OrderException
     */
    @Test
    void testExistsContainer() throws ContainerException, PositionException, OrderException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        this.containerA.addItem(this.itemC, this.positionC, Color.gray);
        this.containerA.close();
        this.order.setStatus(OrderStatus.IN_TREATMENT);
        this.order.addContainer(this.containerA);
        assertEquals(true, this.order.existsContainer(this.containerA), "ContainerA should be found in the ShippingOrder");
        assertEquals(false, this.order.existsContainer(this.containerB), "ContainerB shouldn't be found in the ShippingOrder");
    }


    /**
     * Test Case ID: TC#11.0
     * @throws ContainerException
     * @throws OrderException
     * @throws PositionException
     */
    @Test
    void testFindContainer() throws ContainerException, OrderException, PositionException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        this.containerA.addItem(this.itemC, this.positionC, Color.gray);
        this.containerA.close();
        this.order.setStatus(OrderStatus.IN_TREATMENT);
        this.order.addContainer(this.containerA);
        this.containerB.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerB.addItem(this.itemB, this.positionB, Color.blue);
        this.containerB.addItem(this.itemD, this.positionD, Color.gray);
        this.containerB.close();
        this.order.addContainer(this.containerB);
        assertEquals(0, this.order.findContainer(this.containerA.getReference()), "Index of ContainerA should be 0");
        assertEquals(1, this.order.findContainer(this.containerB.getReference()), "Index of ContainerB should be 1");
    }


    /**
     * Test Case ID: TC#12.0
     * @throws ContainerException
     * @throws OrderException
     * @throws PositionException
     */
    @Test
    void testValidateContainers() throws ContainerException, OrderException, PositionException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        this.containerA.addItem(this.itemC, this.positionC, Color.gray);
        this.containerA.close();
        this.order.setStatus(OrderStatus.IN_TREATMENT);
        this.order.addContainer(this.containerA);
        this.order.validate();
    }


    /**
     * Test Case ID: TC#13.1
     * @throws ContainerException
     * @throws OrderException
     * @throws PositionException
     */
    @Test
    void testValidRemoveContainer() throws ContainerException, OrderException, PositionException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        this.containerA.addItem(this.itemC, this.positionC, Color.gray);
        this.containerA.close();
        this.order.setStatus(OrderStatus.IN_TREATMENT);
        this.order.addContainer(this.containerA);
        assertEquals(true, this.order.removeContainer(this.containerA), "ContainerA should be removed from ShippingOrder");
        assertEquals(false, this.order.removeContainer(this.containerB), "ContainerB shouldn't be removed from ShippingOrder");
    }


    /**
     * Test Case ID: TC#13.2
     * @throws ContainerException
     * @throws OrderException
     * @throws PositionException
     */
    @Test
    void testInvalidRemoveContainer() throws ContainerException, OrderException, PositionException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        this.containerA.addItem(this.itemC, this.positionC, Color.gray);
        this.containerA.close();
        this.order.setStatus(OrderStatus.IN_TREATMENT);
        this.order.addContainer(this.containerA);
        this.order.setStatus(OrderStatus.CANCELLED);
        assertThrows(OrderException.class, () -> this.order.removeContainer(null));
        assertThrows(OrderException.class, () -> this.order.removeContainer(this.containerA));
    }


    /**
     * Test Case ID: TC#14.1
     * @throws ContainerException
     * @throws OrderException
     * @throws PositionException
     */
    @Test
    void testValidSetStatus() throws ContainerException, OrderException, PositionException {
        ShippingOrder empty=this.order;
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        this.containerA.addItem(this.itemC, this.positionC, Color.gray);
        this.containerA.close();
        this.order.setStatus(OrderStatus.IN_TREATMENT);
        assertEquals(OrderStatus.IN_TREATMENT, this.order.getStatus(), "Status of Order should be 'IN_TREATMENT'");
        this.order.addContainer(this.containerA);
        this.order.setStatus(OrderStatus.CLOSED);
        assertEquals(OrderStatus.CLOSED, this.order.getStatus(), "Status of Order should be 'CLOSED'");
        this.order.setStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, this.order.getStatus(), "Status of Order should be 'SHIPPED'");
        empty.setStatus(OrderStatus.AWAITS_TREATMENT);
        empty.setStatus(OrderStatus.IN_TREATMENT);
        assertEquals(OrderStatus.IN_TREATMENT, empty.getStatus(), "Empty ShippingOrder status should be 'IN_TREATMENT'");
        empty.setStatus(OrderStatus.CLOSED);
        assertEquals(OrderStatus.IN_TREATMENT, empty.getStatus(), "Empty ShippingOrder status shouldn't be 'CLOSED'");
    }


    /**
     * Test Case ID: TC#14.2
     * @throws ContainerException
     * @throws PositionException
     * @throws OrderException
     */
    @Test
    void testInvalidSetStatus() throws ContainerException, PositionException, OrderException {
        this.containerA.addItem(this.itemA, this.positionA, Color.aqua);
        this.containerA.addItem(this.itemB, this.positionB, Color.blue);
        this.containerA.addItem(this.itemC, this.positionC, Color.gray);
        this.containerA.close();
        this.order.setStatus(OrderStatus.AWAITS_TREATMENT);
        assertThrows(OrderException.class, () -> this.order.setStatus(OrderStatus.CLOSED));
        assertThrows(OrderException.class, () -> this.order.setStatus(OrderStatus.SHIPPED));
        this.order.setStatus(OrderStatus.IN_TREATMENT);
        assertThrows(OrderException.class, () -> this.order.setStatus(OrderStatus.AWAITS_TREATMENT));
        assertThrows(OrderException.class, () -> this.order.setStatus(OrderStatus.SHIPPED));
        this.order.addContainer(this.containerA);
        this.order.setStatus(OrderStatus.CLOSED);
        assertThrows(OrderException.class, () -> this.order.setStatus(OrderStatus.AWAITS_TREATMENT));
        assertThrows(OrderException.class, () -> this.order.setStatus(OrderStatus.IN_TREATMENT));
    }


    /**
     *Test Case ID: TC#15.0
     */
    @Test
    void testValidateValidJSON(){
        PackingGUI graphics = new PackingGUI();
        assertEquals(true, graphics.validate("\\Trabalho1\\files\\test.json"), "JSON file should be considered valid.");
        assertEquals(false, graphics.validate("\\Trabalho1\\files\\invalid.json"), "JSON file should be considered invalid.");
    }
}