import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display extends JFrame {

    private JPanel panel;
    private JLabel label;
    private Game game;
    private JMenuBar menuBar;
    private ActionListener bombCountListener, fieldSizeListener, quitListener;


    public static void main(String[] args) {
        new Display().setVisible(true);
    }

    Display() {
        game = new Game();
        game.start();
        initLabel();
        initPanel();
        initFrame();
        initListeners();
        initMenuBar();
        setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Игра");
        menuBar.add(gameMenu);

        JMenuItem bombsMenu = new JMenuItem("Количество бомб");
        bombsMenu.addActionListener(bombCountListener);
        gameMenu.add(bombsMenu);

        JMenuItem fieldSizeMenu = new JMenuItem("Размер поля");
        fieldSizeMenu.addActionListener(fieldSizeListener);
        gameMenu.add(fieldSizeMenu);
        gameMenu.addSeparator();

        JMenuItem quitMenu = new JMenuItem("Выйти");
        quitMenu.addActionListener(quitListener);
        gameMenu.add(quitMenu);
    }

    private void initListeners() {
        bombCountListener = e -> onBombCount();
        fieldSizeListener = e -> onFieldSize();
        quitListener = e -> onQuit();
    }

    private void onFieldSize() {
        String cols = JOptionPane.showInputDialog(this, "Введите количество столбцов",
                "Широта", JOptionPane.PLAIN_MESSAGE);
        if (cols == null) return;
        if (cols.isEmpty() || !isDigit(cols)) {
            JOptionPane.showMessageDialog(this, "Вы ввели некорректные данные." + '\n' +
                    "Введите количество столбцов в цифрах", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String rows = JOptionPane.showInputDialog(this, "Введите количество рядов",
                "Высота", JOptionPane.PLAIN_MESSAGE);
        if (rows == null) return;
        if (rows.isEmpty() || !isDigit(cols)) {
            JOptionPane.showMessageDialog(this, "Вы ввели некорректные данные." + '\n' +
                    "Введите количество рядов в цифрах", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        game.changeFieldSize(cols, rows);
        setSize(new Dimension(Game.hexWidth * 3 / 4 * Game.COLS + Game.hexWidth * 11 / 16,
                Game.hexHeight * (Game.ROWS + 1) + Game.hexHeight * 14/16 + 38));
        game.start();
        panel.repaint();
    }


    private void onQuit() {
        String[] vars = {"Да", "Нет"};
        int result = JOptionPane.showOptionDialog(this, "Вы уверены что хотите выйти?",
                "Confirm exit", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, vars, "Да");
        if (result == JOptionPane.YES_OPTION)
            System.exit(0);
    }


    private void onBombCount() {
        String s = JOptionPane.showInputDialog(this, "Введите количество бомб",
                "Количество бомб", JOptionPane.PLAIN_MESSAGE);
        if (s == null) return;
        if (s.isEmpty() || !isDigit(s)) {
            JOptionPane.showMessageDialog(this, "Вы ввели некорректные данные." + '\n' +
                    "Введите количество бомб в цифрах", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        game.changeBombCount(s);
        game.start();
        panel.repaint();
    }

    private boolean isDigit(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void initLabel() {
        label = new JLabel();
        label.setText("Good luck!");
        add(label, BorderLayout.SOUTH);
    }

    private void initFrame() {
        setTitle("Hex sweeper");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(new Dimension(Game.hexWidth * 3 / 4 * Game.COLS + Game.hexWidth * 11 / 16,
                Game.hexHeight * (Game.ROWS + 1) + Game.hexHeight * 14/16 + 38));
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.red);
                for (int x = 0; x < Game.COLS; x++)
                    for (int y = 0; y < Game.ROWS; y++)
                        game.getField()[x][y].paint(g, x, y);
            }
        };

        panel.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(x, y);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(x, y);
                label.setText(getText());
                panel.repaint();
            }
        });
        panel.setBackground(Color.lightGray);
        add(panel);
    }

    private String getText() {
        return switch (game.getGameState()) {
            case PLAYING -> "Keep going!";
            case BOOMED -> "OOps.... You lost. Wanna try again?";
            case WON -> "You won! Congratulations!!!";
        };
    }
}
