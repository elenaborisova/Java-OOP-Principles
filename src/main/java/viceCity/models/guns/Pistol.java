package viceCity.models.guns;

public class Pistol extends BaseGun{
    private static int DEFAULT_BULLETS_PER_BARREL = 10;
    private static int DEFAULT_TOTAL_BULLETS = 100;
    private static final int DEFAULT_FIRE_BULLETS = 1;


    public Pistol(String name) {
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
