package viceCity.models.guns;

public class Rifle extends BaseGun{
    private static int DEFAULT_BULLETS_PER_BARREL = 50;
    private static int DEFAULT_TOTAL_BULLETS = 500;
    private static final int DEFAULT_FIRE_BULLETS = 5;

    public Rifle(String name) {
        super(name, DEFAULT_BULLETS_PER_BARREL, DEFAULT_TOTAL_BULLETS);
    }

    @Override
    public int fire() {
        if (getBulletsPerBarrel() > 0 && getTotalBullets() > 0) {
            setTotalBullets(getTotalBullets() - DEFAULT_BULLETS_PER_BARREL);
            setBulletsPerBarrel(DEFAULT_BULLETS_PER_BARREL);
        }

        if (getBulletsPerBarrel() > 0) {
            setBulletsPerBarrel(getBulletsPerBarrel() - DEFAULT_FIRE_BULLETS);
        }

        return DEFAULT_FIRE_BULLETS;
    }
}
