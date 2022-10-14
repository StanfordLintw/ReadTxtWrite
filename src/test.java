import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test {

    public static void main(String[] args) throws IOException, ParseException {

        List<String> calculate = new ArrayList<>();
        double ThirtyThreeOverTimeHr = 0.0;
        double SixtySevenOverTimeHr = 0.0;
        double TwoOverTimeHr = 0.0;


        String filePath = "/Users/user/Desktop/MON20220901_00016謝宜潔.txt";
        FileInputStream fin = new FileInputStream(filePath);
//        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin, "Big5"));
        String strTmp ;
        while((strTmp = buffReader.readLine())!=null){
            calculate.add(strTmp);
        }
        buffReader.close();

        for (int i=3; i< calculate.size();i++){


            String[] data = calculate.get(i).split(",");
//            System.out.println(calculate.get(i));
            //字串轉時間格式做加減，判斷有無8小時，加班時數多少
            System.out.println(data[0]);
            String OnboardTime = data[7].trim();
            String OffboardTime = data[8].trim();


            if(!(data[7].contains("  ") || data[8].contains("  ")))
            {
                long min = CalculateOverTime(OnboardTime,OffboardTime);
                System.out.println(min);

            }





        }


    }

    private static long CalculateOverTime(String onboardTime, String offboardTime) throws ParseException {

        String standardDate="2022/10/14 ";
        String stantdardOffboardTime = "2022/10/14 17:00";

        String startTime = standardDate+onboardTime;
        String endTime = standardDate+offboardTime;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        //取的兩個時間

        Date dt1 =sdf.parse(stantdardOffboardTime);

        Date dt2 =sdf.parse(endTime);

        System.out.println(dt1);
        System.out.println(dt2);

        //取得兩個時間的Unix時間

        Long ut1=dt1.getTime();

        Long ut2=dt2.getTime();

        System.out.println(ut1);
        System.out.println(ut2);

        //相減獲得兩個時間差距的毫秒

        Long timeP=ut2-ut1;//毫秒差

//        Long sec=timeP/1000;//秒差

        Long overTime=timeP/(1000*60);//分差

        return overTime;
    }


}
