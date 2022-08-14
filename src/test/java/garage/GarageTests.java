package garage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GarageTests {
    private Garage garage;
    private Car toyota;
    private Car mercedes;
    private Car bmw;

    @Before
    public void setup() {
        this.garage = new Garage();
        this.toyota = new Car("Toyota", 200, 20_000);
        this.mercedes = new Car("Mercedes", 400, 80_000);
        this.bmw = new Car("BMW", 300, 70_000);
    }

    @Test
    public void testAddCarShouldAddCarToList() {
        garage.addCar(toyota);
        garage.addCar(mercedes);

        Assert.assertEquals(2, garage.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithoutCarShouldThrow() {
        garage.addCar(null);
    }

    @Test
    public void testGetCountShouldGiveCorrectCarCount() {
        Assert.assertEquals(0, garage.getCount());

        garage.addCar(toyota);
        Assert.assertEquals(1, garage.getCount());

        garage.addCar(mercedes);
        Assert.assertEquals(2, garage.getCount());
    }

    @Test
    public void testGetCarsShouldReturnListOfCars() {
        garage.addCar(toyota);
        garage.addCar(mercedes);

        List<Car> cars = new ArrayList<>();
        cars.add(toyota);
        cars.add(mercedes);

        Assert.assertEquals(Collections.unmodifiableList(cars), garage.getCars());
    }

    @Test
    public void testFindAllCarsWithMaxSpeedAboveShouldReturnCorrectCars() {
        int speed = 250;
        garage.addCar(toyota);
        garage.addCar(mercedes);
        garage.addCar(bmw);

        Assert.assertEquals(
                List.of(mercedes, bmw),
                garage.findAllCarsWithMaxSpeedAbove(speed));
    }

    @Test
    public void testGetTheMostExpensiveCarShouldReturnCorrectCar() {
        garage.addCar(toyota);
        garage.addCar(mercedes);
        garage.addCar(bmw);

        Assert.assertEquals(mercedes, garage.getTheMostExpensiveCar());
    }

    @Test
    public void testFindAllCarsByBrandShouldReturnCorrectCarsList() {
        garage.addCar(toyota);
        garage.addCar(mercedes);
        garage.addCar(bmw);

        Assert.assertEquals(List.of(toyota), garage.findAllCarsByBrand("Toyota"));
    }


}