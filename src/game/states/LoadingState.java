package game.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.ingame.world.World;
import game.main.Handler;
import game.other.grid.Grid;
import game.ui.UIAnimation;
import game.ui.UIComponent.AnimationType;
import game.ui.components.UIButton;
import game.ui.components.UIInputField;

public class LoadingState extends State {

	//BufferedImage testImg;
	//Grid g;

	public LoadingState(Handler handler) {
		super(handler);

		handler.setWorld(World.flatGenerator(handler, 10, 10, 30, 30));
		//testImg = handler.getResource("grass").getImage();
		//g = new Grid(10, 10, 30, 30, 10, Color.GRAY);

		UIButton btn = new UIButton(50, 50, 150, 70);
		btn.addAnimation(AnimationType.ON_HOVER, new UIAnimation(30, new Rectangle(40, 40, 170, 90), Color.RED));
		btn.addAnimation(AnimationType.ON_HOVER_LEAVE, new UIAnimation(30, new Rectangle(50, 50, 150, 70), Color.BLUE));
		uiHandler.addComp(btn);

		UIButton btn2 = new UIButton(70, 70, 150, 70);
		btn2.addAnimation(AnimationType.ON_HOVER, new UIAnimation(30, new Rectangle(60, 60, 170, 90), Color.RED));
		btn2.addAnimation(AnimationType.ON_HOVER_LEAVE,
				new UIAnimation(30, new Rectangle(70, 70, 150, 70), Color.GREEN));
	}

	@Override
	public void tick() throws Exception {
		handler.getWorld().tick();
	}

	@Override
	public void render(Graphics g) {
		handler.getWorld().render(g);
	}

}
