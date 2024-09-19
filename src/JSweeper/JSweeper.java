
package JSweeper;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import javax.swing.*;

public class JSweeper extends JFrame //наследование для использования методов и команд установки параметров фрейма
{
	private Game game;
	
	
	private JPanel panel;
	private JLabel label;
	
	private final int COLS = 9;
	private final int ROWS = 9;
	private final int BOMBS = 10;
	private final int IMAGE_SIZE = 50;

	public static void main(String[] args) {
		new JSweeper();//создание экземпляра программы
	}
	
	private JSweeper ()//конструктор
	{
		game = new Game (COLS, ROWS, BOMBS);
		game.start();
		setImages ();
		initLabel();
		initPanel ();//установка параметров панели
		initFrame ();//вызов инициализации фрейма (изменений ниже)
	}

	private void initLabel() //инициализация метки состояния игры
	{
		label = new JLabel("Welcome!");
		add (label, BorderLayout.SOUTH);
	}
	
	private void initPanel() //инициализация панели
	{
		panel = new JPanel() 
		{
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				for (Coord coord : Ranges.getAllCoords()) 
				{
					g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
				}
			}
		};
		
		panel.addMouseListener (new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				int x = e.getX() / IMAGE_SIZE;
				int y = e.getY() / IMAGE_SIZE;
				Coord coord = new Coord (x,y);
				if (e.getButton() == MouseEvent.BUTTON1)
					game.pressLeftButton(coord);
				if (e.getButton() == MouseEvent.BUTTON3)
					game.pressRightButton(coord);
				if (e.getButton() == MouseEvent.BUTTON2)
					game.start();
				label.setText(getMessage());
				panel.repaint();
			}

		});
		
		
		panel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE, Ranges.getSize().y*IMAGE_SIZE)); //устанавливаем размер окна
		add (panel);
	}
	
	private String getMessage() {
		switch (game.getState())
		{
		case PLAYED: return "Think twice!";
		case BOMBED: return "YOU LOSE! BIG BA-DA-BOOM!";
		case WINNER: return "CONGRATULATIONS!";
		default:	 return "Welcome!";
		}
	}
	
	private void initFrame ()
	{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//действие по умолчанию при закрытии программы
		setTitle ("Java Sweeper");//устанавливаем заголовок программы
		setResizable(false);//запрещаем ихменение размеров окна
		setVisible(true);//включаем видимость формы
		pack ();
		setLocationRelativeTo(null);//устанавливаем окошко по центру
	}
	
	private void setImages () {
		for(Box box: Box.values())
			box.image = getImage(box.name().toLowerCase());
	}
	
	private Image getImage(String name) {
		String filename = "/img/" + name + ".png";
		ImageIcon icon = new ImageIcon (getClass().getResource(filename));
		return icon.getImage();
	}
	
}
