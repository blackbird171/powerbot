package kaz.grapes.task.walking;

import java.util.concurrent.Callable;
import kaz.grapes.constants.tiles;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.TilePath;
import kaz.grapes.constants.*;
import kaz.grapes.task.Task;

public class Toguild extends Task {

	@Override
	public boolean activate() {
		return ctx.backpack.count() == 0
				&& !tiles.GUILD_AREA.contains(ctx.players.local())
				&& ctx.players.local().tile().floor() == 0;
	}

	@Override
	public void execute() {
		System.out.print("Walking to Guild");
		final Tile[] Path = { new Tile(3182, 3444, 0), new Tile(3176, 3449, 0),
				new Tile(3167, 3450, 0), new Tile(3161, 3451, 0),
				new Tile(3156, 3450, 0), new Tile(3147, 3444, 0),
				new Tile(3143, 3443, 0) };

		final TilePath bankpath = ctx.movement.newTilePath(Path);
		if (bankpath.valid()) {
			bankpath.traverse();
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.movement.distance(ctx.players.local().tile(),
							bankpath.end()) <= 20;
				}

			});
		}
		GameObject door = ctx.objects.select().id(Doorstair.DOORIDS)
				.nearest().poll();

		if ((door.inViewport() && door.valid())
				&& !tiles.GUILD_AREA.contains(ctx.players.local())) {
			door.interact("open");
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return tiles.GUILD_AREA.contains(ctx.players.local());
				}

			});

		}

	}
}
