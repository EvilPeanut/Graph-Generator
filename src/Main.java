import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class Main extends Applet implements AdjustmentListener {
	private static final long serialVersionUID = 4844855624430486548L;
	Graphics graphicsBuffer;
	Image offscreen;
	Scrollbar slider;
	int sliderValue = 1;
	int amplitude = 128;
	int period = 10;
	int horizontalShift = 0;
	int verticalShift = 0;
	
	public void init() {
		setSize(512, 512);
		setBackground(new Color(255, 255, 255, 255));
		offscreen = createImage(getSize().width, getSize().height);
		graphicsBuffer = offscreen.getGraphics();
		slider = new Scrollbar(Scrollbar.HORIZONTAL, 1, 1, 1, 256);
		add(slider);
		slider.addAdjustmentListener(this);
	}
	
	public void paint(Graphics graphics) {
		graphicsBuffer.clearRect(0, 0, getSize().width, getSize().height);
		for (int x = 0; x < 512; x++) {
			if (sliderValue < 128 && (x % sliderValue) != 0) continue;
			offscreen.getGraphics().drawLine(x, getYPoint(x), x + (sliderValue < 128 ? sliderValue : sliderValue - 128), getYPoint(x + (sliderValue < 128 ? sliderValue : sliderValue - 128)));
		}
		offscreen.getGraphics().drawString("Graph generator by Reece Aaron Lecrivain 25/4/13", 10, 20);
		offscreen.getGraphics().drawString("Values above 127 remove the interval limitation", 10, 40);
		if (sliderValue < 128) {
			offscreen.getGraphics().drawString("Graph equation = amplitude * sin(period * (x - horizontalShift)) + verticalShift) using (x % " + sliderValue + ") != 0", 10, 502);
		} else {
			offscreen.getGraphics().drawString("Graph equation = amplitude * sin(period * (x - horizontalShift)) + verticalShift)", 10, 502);
		}
		graphics.drawImage(offscreen, 0, 0, this);
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public int getYPoint(int x) {
		//return (int) ((int) amplitude * Math.sin(period * (x - horizontalShift)) + verticalShift) + 256;
		return (int) ((int) amplitude * Math.tan(period * (x - horizontalShift)) + verticalShift) + 256;
	}

	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		sliderValue = slider.getValue();
		repaint();
	}
}
