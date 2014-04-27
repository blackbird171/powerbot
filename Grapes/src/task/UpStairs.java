package task;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.GameObject;

import constants.Tiles;

public class UpStairs extends Task {

	@Override
	public boolean activate() {
		return ctx.backpack.count() == 0
				&& Tiles.GUILD_AREA.contains(ctx.players.local());
	}

	@Override
	public void execute() {
		GameObject stair = ctx.objects.select().id(constants.DoorStair.stair3Ids).nearest().poll();;
		if (stair.inViewport() && ctx.players.local().tile().floor() == 0) {
			stair.interact("Climb-up");
			System.out.print("Going up stairs");
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return (ctx.players.local().tile().floor() == 1);
				}

			});

		}else{
			ctx.movement.step(stair);
		}
	}
}
