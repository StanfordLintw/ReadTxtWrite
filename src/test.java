import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test {

    // 建立文字檔路徑
//    private static String path = "D:/";
    private static String path = "/Users/user/Desktop/";
    private static String filenameTemp;

    public static void main(String[] args) throws IOException, ParseException {

        List<String> calculate = new ArrayList<>();
        double ThirtyThreeOverTimeHr = 0.0;
        double SixtySevenOverTimeHr = 0.0;
        double TwoOverTimeHr = 0.0;

        // 這邊之後要修改成，去特定目錄資料夾下，讀取所有的txt文字檔，
        // 並將最後結果寫到新建立的文字檔內呈現

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

            //先建立txt檔案:
//            creatTxtFile(data[0]);


            String OnboardTime = data[7].trim();
            String OffboardTime = data[8].trim();


            if(!(data[7].contains("  ") || data[8].contains("  ")))
            {
                long min = CalculateOverTime(OnboardTime,OffboardTime);
                System.out.println(min);

                if(min >=25 && min <= 30) {
                    ThirtyThreeOverTimeHr += 0.5;

                }else if(min > 30 && min < 60){
                    ThirtyThreeOverTimeHr+=(double) (min/60);

                }else if(min >= 60 && min < 120){
                    ThirtyThreeOverTimeHr+=(double) (min/60);

                }else if(min >= 120 && min < 180){
                    if(min == 120)
                        ThirtyThreeOverTimeHr+=2;
                    else {
                        min = min - 120;
                        ThirtyThreeOverTimeHr+=2;
                        SixtySevenOverTimeHr+=(double) (min/60);
                    }
                }else if(min >= 180 && min < 240){
                    if(min == 180){
                        ThirtyThreeOverTimeHr+=2;
                        SixtySevenOverTimeHr+=1;
                    } else {
                        min = min - 180;
                        ThirtyThreeOverTimeHr+=2;
                        SixtySevenOverTimeHr+=1;
                        SixtySevenOverTimeHr+=(double) (min/60);
                    }
                }else if(min >= 240 ){
                    if(min == 240){
                        ThirtyThreeOverTimeHr+=2;
                        SixtySevenOverTimeHr+=2;
                    } else {
                        min = min - 240;
                        ThirtyThreeOverTimeHr+=2;
                        SixtySevenOverTimeHr+=2;
                        TwoOverTimeHr+=(double) (min/60);
                    }
                }
            }
        }

        System.out.println("平日加班1.33時數為:"+ThirtyThreeOverTimeHr+" H");
        System.out.println("平日加班1.67時數為:"+SixtySevenOverTimeHr+" H");
        System.out.println("平日加班2.00時數為:"+TwoOverTimeHr+" H");

        String dataStr = "平日加班1.33時數為:"+ThirtyThreeOverTimeHr+" H\n"
                        +"平日加班1.67時數為:"+SixtySevenOverTimeHr+" H\n"
                        +"平日加班2.00時數為:"+TwoOverTimeHr+" H";

//        writeTxtFile(dataStr);

    }

    /**
     * 計算加班時數
     *
     * @param onboardTime
     *
     * @param offboardTime
     *
     * @throws ParseException
     */
    private static long CalculateOverTime(String onboardTime, String offboardTime) throws ParseException {

        String standardDate="2022/10/14 ";
        String stantdardOffboardTime = "2022/10/14 17:00";

        String startTime = standardDate+onboardTime;
        String endTime = standardDate+offboardTime;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        //取得兩個時間

        Date dt1 =sdf.parse(stantdardOffboardTime);

        Date dt2 =sdf.parse(endTime);


        //取得兩個時間的Unix時間

        Long ut1=dt1.getTime();

        Long ut2=dt2.getTime();

        //相減獲得兩個時間差距的毫秒

        Long timeP=ut2-ut1;//毫秒差

        Long overTime=timeP/(1000*60);//分差

        return overTime;
    }

    /**
     * 建立檔案
     *
     * @throws IOException
     */
    public static boolean creatTxtFile(String name) throws IOException {
        boolean flag = false;
        filenameTemp = path + name + ".txt";
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /**
     * 寫檔案
     *
     * @param newStr
     *            新內容
     * @throws IOException
     */
    public static boolean writeTxtFile(String newStr) throws IOException {
        // 先讀取原有檔案內容，然後進行寫入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;

        try {
            // 檔案路徑
            File file = new File(filenameTemp);
            // 將檔案讀入輸入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 儲存該檔案原有的內容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行與行之間的分隔符 相當於“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自動生成 catch 塊
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

}



