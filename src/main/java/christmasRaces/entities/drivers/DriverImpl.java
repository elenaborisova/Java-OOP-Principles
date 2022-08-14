package christmasRaces.entities.drivers;


import christmasRaces.common.ExceptionMessages;
import christmasRaces.entities.cars.Car;

public class DriverImpl implements Driver{

    private String name;
    private Car car;
    private int numberOfWins;
    private boolean canParticipate;

    public DriverImpl(String name) {
        this.setName(name);
        this.setCanParticipate(canParticipate);
    }


    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null || name.trim().length() < 5) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_NAME, name, 5));
        }
        this.name = name;
    }

    @Override
    public Car getCar() {
        return car;
    }

    @Override
    public int getNumberOfWins() {
        return numberOfWins;
    }

    @Override
    public boolean getCanParticipate() {
        return canParticipate;
    }

    private void setCanParticipate(boolean canParticipate) {
        this.canParticipate = (car != null);
    }

    @Override
    public void addCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException(ExceptionMessages.CAR_INVALID);
        }
        this.car = car;
        this.setCanParticipate(canParticipate);
    }

    @Override
    public void winRace() {
        numberOfWins++;
    }

}
