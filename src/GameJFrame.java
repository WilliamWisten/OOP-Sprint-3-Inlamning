import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

public class GameJFrame extends JFrame {

    private JPanel basePanel = new JPanel();
    private JPanel textPanel = new JPanel();
    private JPanel gamePanel = new JPanel();
    private JButton[][] buttons;
    private JFrame game = new JFrame();
    private int numberOfRows;

    public GameJFrame(int rows) {
        game.add(basePanel);
        basePanel.setLayout(new BorderLayout());
        basePanel.add(gamePanel, BorderLayout.NORTH);
        loadPlayingField(rows);

        JButton winGame = winGameButton();
        textPanel.add(winGame);

        JButton newGame = new JButton();
        newGame.setText("New Game");
        newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newGame(rows);
            }
        });
        textPanel.add(newGame);
        basePanel.add(textPanel, BorderLayout.SOUTH);

        JButton gridSize = gridSizeButton();
        textPanel.add(gridSize);

        game.setVisible(true);
        game.setLocationRelativeTo(null);
        game.setDefaultCloseOperation(EXIT_ON_CLOSE);
        game.pack();
    }

    private JButton gridSizeButton() {
        JButton gridSize = new JButton();
        gridSize.setText("Set Rows");
        gridSize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel tempPanel = new JPanel();
                for (int i = 3; i < 10; i++) {
                    JRadioButton radioButton = new JRadioButton();
                    radioButton.setText(String.valueOf(i));
                    radioButton.addActionListener(l -> {
                        numberOfRows = Integer.parseInt(radioButton.getText());
                        basePanel.remove(tempPanel);
                        newGame(numberOfRows);
                    });
                    tempPanel.add(radioButton);
                }
                basePanel.remove(gamePanel);
                basePanel.add(tempPanel, BorderLayout.NORTH);
                basePanel.revalidate();
                basePanel.repaint();
                game.pack();
            }
        });
        return gridSize;
    }

    private JButton winGameButton() {
        JButton winGame = new JButton();
        winGame.setText("Win Game");
        winGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int counter = 1;
                for (JButton[] button : buttons) {
                    for (JButton jButton : button) {
                        jButton.setText(String.valueOf(counter));
                        counter++;
                    }
                }
                gamePanel.revalidate();
                gamePanel.repaint();
            }
        });
        return winGame;
    }

    private void newGame(int rows) {
        gamePanel.removeAll();
        loadPlayingField(rows);
        game.pack();
    }

    private void loadPlayingField(int rows) {
        int totalButtons = rows * rows;
        basePanel.add(gamePanel, BorderLayout.NORTH);
        buttons = new JButton[rows][rows];
        gamePanel.setLayout(new GridLayout(rows, rows));
        ArrayList<String> numbers = new ArrayList<>();
        int squares = rows * rows;
        for (int i = 0; i < squares; i++) {
            numbers.add(String.valueOf(i + 1));
        }
        Collections.shuffle(numbers.subList(0, totalButtons - 1));
        int counter = 0;
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.BLACK);
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setText(numbers.get(counter));
                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JButton temp = (JButton) e.getSource();
                        SwapIndex swapIndex = new SwapIndex();
                        String empty = swapIndex.checkEmpty(buttons, arrayButtonCoordinates(temp), buttons.length);
                        if (!empty.equals(",")) {
                            buttons = swapIndex.swapPosition(buttons, arrayButtonCoordinates(temp), empty);
                            gamePanel.removeAll();
                            for (JButton[] button : buttons) {
                                for (JButton jButton : button) {
                                    gamePanel.add(jButton);
                                    gamePanel.revalidate();
                                    gamePanel.repaint();
                                }
                            }
                            if (swapIndex.checkIfWon(buttons)) {
                                gamePanel.removeAll();
                                JLabel wonGame = new JLabel();
                                wonGame.setText("You have won!");
                                gamePanel.add(wonGame);
                            }
                        }
                    }
                });
                gamePanel.add(buttons[i][j]);
                if (counter == squares - 1) {
                    buttons[i][j].setVisible(false);
                }
                counter++;
            }
        }
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private String arrayButtonCoordinates(JButton button) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (button == buttons[i][j]) {
                    return i + "," + j;
                }
            }
        }
        return null;
    }
}
