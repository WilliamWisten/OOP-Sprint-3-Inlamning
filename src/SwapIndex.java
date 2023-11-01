import javax.swing.*;

public class SwapIndex {

    public JButton[][] swapPosition(JButton[][] test, String firstIndex, String secondIndex){
        int part1FirstIndex = Integer.parseInt(firstIndex.substring(0, firstIndex.indexOf(',')));
        int part2FirstIndex = Integer.parseInt(firstIndex.substring(firstIndex.indexOf(',') + 1));
        int part1SecondIndex = Integer.parseInt(secondIndex.substring(0, secondIndex.indexOf(',')));
        int part2SecondIndex = Integer.parseInt(secondIndex.substring(secondIndex.indexOf(',') + 1));

        JButton button1 = test[part1FirstIndex][part2FirstIndex];
        JButton button2 = test[part1SecondIndex][part2SecondIndex];

        test[part1FirstIndex][part2FirstIndex] = button2;
        test[part1SecondIndex][part2SecondIndex] = button1;

        return test;
    }

    public String checkEmpty(JButton[][] test, String index, int rows){
        int[][] allDirections = { {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        int part1Index = Integer.parseInt(index.substring(0, index.indexOf(',')));
        int part2Index = Integer.parseInt(index.substring(index.indexOf(',') + 1));

        String coordinates = ",";
        int maxSize = rows * rows;

        for(int[] direction : allDirections){
            int tempRow = part1Index + direction[0];
            int tempCol = part2Index + direction[1];
            if (tempRow >= 0 && tempRow < rows && tempCol >= 0 && tempCol < rows){
                if(test[tempRow][tempCol] != null && test[tempRow][tempCol].getText().equals(String.valueOf(maxSize))){
                    coordinates = tempRow + "," + tempCol;
                }
            }
        }
        return coordinates;
    }

    public boolean checkIfWon(JButton[][] buttons){
        int counter = 1;

        for (JButton[] button : buttons) {
            for (JButton jButton : button) {
                if (!jButton.getText().equals(String.valueOf(counter))) {
                    return false;
                }
                counter++;
            }
        }

        return true;
    }
}
