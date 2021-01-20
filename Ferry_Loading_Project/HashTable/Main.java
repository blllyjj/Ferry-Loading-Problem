//package HashTable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    int ferryLength;
    List<Integer> carLengths;
    List<Integer> firstK;
    int cars;
    String[] carArrange;
    HashMap<Integer, Boolean> visited;
    int bestK;
    String[] bestAnswer;
    public Main(){

    }

    public void solve() throws Exception {
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(System.in));

//        for (int j = 1; j <= 5 ; j++) {
//            String inputFile = "input" + Integer.toString(j) + ".txt";
//            in = new BufferedReader(new FileReader(inputFile));
            int cases = Integer.parseInt(in.readLine());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cases; i++) {
                sb.append(dealCase(in));
                if (i != cases - 1) {
                    sb.append("\n");
                }
            }
            System.out.print(sb.toString());

//            File file = new File("output" + Integer.toString(j) + ".txt");
//            FileWriter writer = new FileWriter(file);
//            writer.write(sb.toString());
//            writer.close();
//        }
    }

    public String dealCase(BufferedReader in) throws Exception{
        in.readLine();//skip the empty line

        ferryLength = Integer.parseInt(in.readLine()) * 100; // in centimeters
        carLengths = new ArrayList<>();
        firstK = new ArrayList<>();
        firstK.add(0);
        while (true){
            String input = in.readLine();
            if(input.equals("")){
                continue;
            }
            int carLength = Integer.parseInt(input);
            if(carLength == 0){
                break;
            }else{
               carLengths.add(carLength);
                if(firstK.isEmpty()){
                    firstK.add(carLength);
                }else{
                    firstK.add(carLength + firstK.get(firstK.size() - 1));
                }
            }
        }
        cars = carLengths.size();
        carArrange = new String[cars];
        visited = new HashMap<>(cars);
        bestK = -1;
        bestAnswer = new String[cars];

        bestK = BacktrackSolve(0, ferryLength);

        StringBuilder sb = new StringBuilder();
        sb.append(bestK);
        sb.append("\n");
        for (int i = 0; i < bestK; i++) {
            sb.append(bestAnswer[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    public int BacktrackSolve(int currK, int currS){
        if(currK > bestK){
            bestK = currK;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bestK; i++) {
                sb.append(carLengths.get(i));
                sb.append(" ");
                sb.append(carArrange[i]);
                sb.append(" ");
            }
//            System.out.println(sb.toString());
            System.arraycopy(carArrange, 0, bestAnswer, 0, cars);
        }
        if(currK < cars){
            Integer carLength = carLengths.get(currK);
            if(currS >= carLength && visited.get(buildKey(currK, currS - carLength)) == null){
                carArrange[currK] = "port"; // arrange the car currK on the left side
                visited.put(buildKey(currK, currS - carLength), true);
                bestK = BacktrackSolve(currK + 1, currS - carLength);
            }
            if((ferryLength * 2 - currS - firstK.get(currK)) >= carLength && visited.get(buildKey(currK,currS)) == null){
                carArrange[currK] = "starboard";
                visited.put(buildKey(currK,currS), true);
                bestK = BacktrackSolve(currK + 1, currS);
            }
        }
        return bestK;
    }
    private Integer buildKey(int currK, int currS){
        return (currK + 71) * (currS + this.ferryLength);
    }

    public static void main(String[] args) throws Exception{
        Main main = new Main();
        main.solve();
    }
}
