package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.Player;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class GangNeighbourhood implements Neighbourhood {
    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        Collection<Gun> gunsRepo = mainPlayer.getGunRepository().getModels();
        Deque<Gun> guns = new ArrayDeque<>(gunsRepo);
        Deque<Player> players = new ArrayDeque<>(civilPlayers);

        while (!guns.isEmpty() && !players.isEmpty()) {
            Gun gun = guns.poll();
            Player player = players.poll();

            while (gun.canFire() && player.isAlive()) {
                player.takeLifePoints(gun.fire());
            }

            if (!gun.canFire()) {
                guns.poll();
            }
            if (!player.isAlive()) {
                players.poll();
            }
        }


        for (Player civilPlayer : players) {
            Collection<Gun> civilGunsRepo = civilPlayer.getGunRepository().getModels();
            Deque<Gun> civilGuns = new ArrayDeque<>(civilGunsRepo);
            Gun gun = civilGuns.poll();

            while (gun != null && gun.canFire() && mainPlayer.isAlive()) {
                mainPlayer.takeLifePoints(gun.fire());
            }

            if (!gun.canFire()) {
                guns.poll();
            }
            if (!mainPlayer.isAlive()) {
                return;
            }
        }

    }
}
