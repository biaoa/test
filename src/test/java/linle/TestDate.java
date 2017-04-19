package linle;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.linle.common.util.DateUtil;

public class TestDate {

	public static void main(String[] args) throws ParseException {
		
		Integer i1 = 127;
		Integer i2 = 127;
		System.out.println(i1==i2);
		System.out.println(i1.equals(i2));
		
		String startTime = "2015-09-01";
        String endTime = "2017-07-31";

        String[] arg1 = startTime.split("-");
        String[] arg2 = endTime.split("-");
        int year1 = Integer.valueOf(arg1[0]);
        int year2 = Integer.valueOf(arg2[0]);
        int month1 = Integer.valueOf(arg1[1]);
        int month2 = Integer.valueOf(arg2[1]);
        int month = 0;
        for (int i = year1; i <= year2; i++) {
        	if(year1 == year2){
        		month = month2 - month1+1;
        		break;
        	}
            int monthCount = 12;
            int monthStart = 1;
            if (i == year1) {
                monthStart = month1;
                monthCount = 12-monthStart+1;
            } else if (i == year2) {
                monthCount = month2;
            }
            for(int j = 0; j < monthCount; j++){
            	month++;
                int temp = monthStart+j;
                if(temp >=10){
//                    System.out.println(i+"-"+(monthStart+j));
                }else{
//                    System.out.println(i+"-0"+(monthStart+j));
                }
            }
             
    }
//        System.out.println(month);
		
		// 活动结束了  油和大米不送了
        // 1楼 1.1 2楼 1.2 超过2楼 1.4
        // 4幢3单元 1-2楼 1.1 超过2楼 1.2
        //1374.336   1374.33   1374.34    1374.349 1374.4  1374.3 //计算金额四舍五入保留一位
        //不足一个月 按一个月计算
        double d=24*113.72*1.4;
        BigDecimal   b   =   new   BigDecimal(d);  
        double   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();  
//        System.out.println(f1);
//        Date d1 = new Date();
//        System.out.println(DateUtil.DateAddByType(d1, "2").toLocaleString());
    }
		
}	
