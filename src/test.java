import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) throws IOException {

        List<String> calculate = new ArrayList<>();
        double ThirtyThreeOverTimeHr = 0.0;
        double SixtySevenOverTimeHr = 0.0;
        double TwoOverTimeHr = 0.0;


        String filePath = "/Users/user/Desktop/MON20220901_00016謝宜潔.txt";
        FileInputStream fin = new FileInputStream(filePath);
//        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin, "Big5"));
        String strTmp = "";
        while((strTmp = buffReader.readLine())!=null){
            calculate.add(strTmp);
//          String result = IOUtils.toString(br);
//            System.out.println(strTmp);
        }
        buffReader.close();

        for (int i=0; i< calculate.size();i++){

            String[] data = calculate.get(i).split(",");

            //字串轉時間格式做加減，判斷有無8小時，加班時數多少
            System.out.println(data[7]); //onboard time
            System.out.println(data[8]); //offboard time




        }


    }


}
