package christmasRaces.core;

import christmasRaces.common.ExceptionMessages;
import christmasRaces.common.OutputMessages;
import christmasRaces.core.interfaces.Controller;
import christmasRaces.entities.cars.Car;
import christmasRaces.entities.cars.MuscleCar;
import christmasRaces.entities.cars.SportsCar;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;
import christmasRaces.entities.races.Race;
import christmasRaces.entities.races.RaceImpl;
import christmasRaces.repositories.interfaces.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private Repository<Driver> driverRepository;
    private Repository<Car> carRepository;
    private Repository<Race> raceRepository;

    public ControllerImpl(Repository<Driver> driverRepository,
                          Repository<Car> carRepository, Repository<Race> raceRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public String createDriver(String driverName) {
        if (driverRepository.getByName(driverName) != null) {
            throw new IllegalArgumentException(
                    String.format(ExceptionMessages.DRIVER_EXISTS, driverName));
        }

        Driver driver = new DriverImpl(driverName);
        driverRepository.add(driver);
        return String.format(OutputMessages.DRIVER_CREATED, driver.getName());
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
        if (carRepository.getByName(model) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_EXISTS, model));
        }

        Car car = null;
        if ("Muscle".equals(type)) {
            car = new MuscleCar(model, horsePower);
        } else if ("Sports".equals(type)) {
            car = new SportsCar(model, horsePower);
        }

        carRepository.add(car);
        return String.format(OutputMessages.CAR_CREATED, type + "Car", model);
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {
        Driver driver = driverRepository.getByName(driverName);
        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }

        Car car = carRepository.getByName(carModel);
        if (car == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_NOT_FOUND, carModel));
        }

        driver.addCar(car);
        return String.format(OutputMessages.CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {
        Race race = raceRepository.getByName(raceName);
        if (race == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }

        Driver driver = driverRepository.getByName(driverName);
        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }

        race.addDriver(driver);
        return String.format(OutputMessages.DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String createRace(String name, int laps) {
        if (raceRepository.getByName(name) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_EXISTS, name));
        }

        Race race = new RaceImpl(name, laps);
        raceRepository.add(race);
        return String.format(OutputMessages.RACE_CREATED, name);
    }

    @Override
    public String startRace(String raceName) {
        Race race = raceRepository.getByName(raceName);

        if (race == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        } else if (race.getDrivers().size() < 3) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_INVALID, raceName, 3));
        }

        List<Driver> fastestDrivers = race.getDrivers().stream()
                .sorted((d1, d2) ->
                        Double.compare(d2.getCar().calculateRacePoints(race.getLaps()),
                                d1.getCar().calculateRacePoints(race.getLaps())))
                .limit(3)
                .collect(Collectors.toList());

        raceRepository.remove(race);

        StringBuilder resultStr = new StringBuilder();
        resultStr.append(String.format(OutputMessages.DRIVER_FIRST_POSITION, fastestDrivers.get(0).getName(), raceName))
                .append(System.lineSeparator())
                .append(String.format(OutputMessages.DRIVER_SECOND_POSITION, fastestDrivers.get(1).getName(), raceName))
                .append(System.lineSeparator())
                .append(String.format(OutputMessages.DRIVER_THIRD_POSITION, fastestDrivers.get(2).getName(), raceName));

        return resultStr.toString();
    }


}
