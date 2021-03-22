import java.util.Scanner;

public class Compass {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();

        int distCounterClockWise;
        int distClockwise;
        int shortDist;

        if(num1>num2){
            distClockwise = 360-num1+num2;
            distCounterClockWise =  num1-num2;

            shortDist = distClockwise <= distCounterClockWise ? distClockwise : -distCounterClockWise;
        }

        else{
            distClockwise = num2-num1;
            distCounterClockWise = 360-num2+num1;

            shortDist = distClockwise <= distCounterClockWise ? distClockwise : -distCounterClockWise;
        }

        System.out.println(shortDist);

    }
}
