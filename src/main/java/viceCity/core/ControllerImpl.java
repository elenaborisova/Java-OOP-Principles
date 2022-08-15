package viceCity.core;

import christmasRaces.common.OutputMessages;
import viceCity.common.ConstantMessages;
import viceCity.common.ExceptionMessages;
import viceCity.core.interfaces.Controller;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;

import java.util.*;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private Map<String, Player> civilPlayers;
    private Player mainPlayer;
    private Deque<Gun> guns;

    public ControllerImpl() {
        this.mainPlayer = new MainPlayer();
        this.civilPlayers = new LinkedHashMap<>();
        this.guns = new ArrayDeque<>();
    }

    @Override
    public String addPlayer(String name) {
        Player civilPlayer = new CivilPlayer(name);
        civilPlayers.put(name, civilPlayer);

        return String.format(ConstantMessages.PLAYER_ADDED, name);
    }

    @Override
    public String addGun(String type, String name) {
        Gun gun = null;
        if ("Pistol".equals(type)) {
            gun = new Pistol(name);
        } else if ("Rifle".equals(type)) {
            gun = new Rifle(name);
        } else {
            return ConstantMessages.GUN_TYPE_INVALID;
        }

        guns.add(gun);
        return String.format(ConstantMessages.GUN_ADDED, name, type);
    }

    @Override
    public String addGunToPlayer(String name) {
        Gun gun = guns.poll();

        if (gun == null) {
            return ConstantMessages.GUN_QUEUE_IS_EMPTY;
        } else if (mainPlayer.getName().equals(name)) {
            mainPlayer.getGunRepository().add(gun);
            return String.format(ConstantMessages.GUN_ADDED_TO_MAIN_PLAYER, gun.getName(), name);
        } else if (!civilPlayers.containsKey(name)) {
            return ConstantMessages.CIVIL_PLAYER_DOES_NOT_EXIST;
        }

        Player player = civilPlayers.get(name);
        player.getGunRepository().add(gun);

        return String.format(ConstantMessages.GUN_ADDED_TO_CIVIL_PLAYER, gun.getName(), name);
    }

    @Override
    public String fight() {
        Neighbourhood gangNeighbourhood = new GangNeighbourhood();
        gangNeighbourhood.action(mainPlayer, civilPlayers.values());

        boolean allAlive = civilPlayers.values().stream()
                .allMatch(p -> p.getLifePoints() == CivilPlayer.INITIAL_POINTS);

        if (mainPlayer.getLifePoints() == MainPlayer.INITIAL_POINTS && allAlive) {
            return ConstantMessages.FIGHT_HOT_HAPPENED;
        }

        int deadCivilPlayers = (int) civilPlayers.values().stream()
                .filter(p -> !p.isAlive()).count();

        return String.format("A fight happened:\n" +
                "Tommy live points: %d!\n" +
                "Tommy has killed: %d players!\n" +
                "Left Civil Players: %d!\"\n",
                mainPlayer.getLifePoints(),
                deadCivilPlayers,
                civilPlayers.size() - deadCivilPlayers);
    }
}
