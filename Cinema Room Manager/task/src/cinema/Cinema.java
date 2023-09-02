package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        showHall();
    }
    static int chooseRow;
    static int chooseSeat;
    static int row;
    static int seat;

    static int TOTAL_NUMBER_OF_SEAT;

    static int seatCounter;

    static int currentIncome;

    static char[][] seatMap;
    static char[][] seatMap(int row, int seat) {
        char indiSeat[][] = new char[row][seat];

        for (int i=0; i<indiSeat.length; i++) {
            for (int j=0; j<indiSeat[i].length; j++) {
                indiSeat[i][j] = 'S';
            }
        }
        return indiSeat;
    }

    static void printSeatMap(char[][] seatMap) {
        System.out.println("\nCinema:");
        System.out.print(" ");
        for (int i = 1; i<=seatMap[0].length; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        int rowNo = 0;
        for(int i=0; i<seatMap.length; i++) {
            System.out.print(++rowNo);
            for(int j=0; j<seatMap[i].length; j++) {
                System.out.print(" " + seatMap[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    static void showHall() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int numberOfRow = scanner.nextInt();
        row = numberOfRow;
        System.out.println("Enter the number of seats in each row:");
        int numberOfSeatInEachRow = scanner.nextInt();
        seat = numberOfSeatInEachRow;
        TOTAL_NUMBER_OF_SEAT = row * seat;
//        char[][] seatMap = seatMap(numberOfRow, numberOfSeatInEachRow);
//        printSeatMap(seatMap);

        printMenu();

//        seatMap = selectYourSeat(seatMap);

//        printSeatMap(seatMap);
    }

    static int calculateProfitBasedOnTicketSold(int numberOfRow, int numberOfSeatInEachRow) {
        int totalNumberOfSeats = numberOfRow * numberOfSeatInEachRow;
        return totalNumberOfSeats <= 60 ? totalNumberOfSeats * 10 : calculateProfitBasedOnTicketSoldForLargeRoom(
                numberOfRow, totalNumberOfSeats, numberOfSeatInEachRow
        );
    }

    static int calculateProfitBasedOnTicketSoldForLargeRoom(int numberOfRow, int totalNumberOfSeats, int numberOfSeatInEachRow) {
        int numberOfRowsInFront = numberOfRow / 2;
        int numberOfRowsInBack = numberOfRow - numberOfRowsInFront;
        int totalNumberOfSeatsInFront = numberOfRowsInFront * numberOfSeatInEachRow;
        int totalNumberOfSeatsInBack = numberOfRowsInBack * numberOfSeatInEachRow;

        int incomeFromFrontHalf = totalNumberOfSeatsInFront * 10;
        int incomeFromBackHalf = totalNumberOfSeatsInBack * 8;
        return incomeFromFrontHalf + incomeFromBackHalf;
    }

    static int ticketPrice(int row, int seat, int chooseRow) {
        int frontRow = row/2;
        int totalSeat = row * seat;
        if (totalSeat <= 60) {
            return 10;
        }
        else if (totalSeat > 60 && ++chooseRow <= frontRow) {
            return 10;
        }
        else {
            return 8;
        }
    }


    static char[][] selectYourSeat(char[][] seatMap) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("\nEnter a row number:");
            int selectedRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int selectedSeat = scanner.nextInt();

            if ((selectedRow < 0 || selectedRow > row) || (selectedSeat < 0 || selectedSeat > seat)) {
                System.out.println("Wrong input!");
                continue;
            }

            chooseRow = selectedRow;
            chooseSeat = selectedSeat;
            if (seatMap[--chooseRow][--chooseSeat] != 'B') {
                seatMap[chooseRow][chooseSeat] = 'B';
                seatCounter++;
            } else {
                System.out.println("That ticket has already been purchased!");
                continue;
            }
            flag = false;
        }
        return seatMap;
    }

    static void printStatistics(int seatCounter, int TOTAL_NUMBER_OF_SEAT, int currentIncome) {
        float bookedSeatPercentage = ((float)seatCounter / TOTAL_NUMBER_OF_SEAT) * 100;
        int totalIncome = calculateProfitBasedOnTicketSold(row, seat);
        System.out.printf("\nNumber of purchased tickets: %d\n", seatCounter);
        System.out.printf("Percentage: %.2f%c\n", bookedSeatPercentage, '%');
        System.out.printf("Current income: %c%d\n", '$', currentIncome);
        System.out.printf("Total income: %c%d\n", '$', totalIncome);
    }

    static void printMenu() {
        boolean flag = true;
        seatMap = seatMap(row, seat);
        while (flag) {
            System.out.println("\n1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit");

            Scanner scanner = new Scanner(System.in);
            int menu = scanner.nextInt();
            switch (menu) {
                case 1 :
                    printSeatMap(seatMap);
                    break;
                case 2 :
                    seatMap = selectYourSeat(seatMap);
                    int tikectPrice = ticketPrice(row, seat, chooseRow);
                    currentIncome += tikectPrice;
                    System.out.println("\nTicket price: $"+tikectPrice);
                    break;
                case 3 :
                    printStatistics(seatCounter, TOTAL_NUMBER_OF_SEAT, currentIncome);
                    break;
                case 0 :
                    flag = false;
                    break;
            }
        }
    }
}