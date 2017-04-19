package linle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.linle.common.util.SMSutil;
import com.linle.entity.SmsInterface;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class Main {
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	   public static List<List<String>>  createList(List<String> targe,int size) {  
	        List<List<String>> listArr = new ArrayList<List<String>>();  
	        //获取被拆分的数组个数  
	        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;  
	        for(int i=0;i<arrSize;i++) {  
	            List<String>  sub = new ArrayList<String>();  
	            //把指定索引数据放入到list中  
	            for(int j=i*size;j<=size*(i+1)-1;j++) {  
	                if(j<=targe.size()-1) {  
	                    sub.add(targe.get(j));  
	                }  
	            }  
	            listArr.add(sub);  
	        }  
	        return listArr;  
	    } 
	   
	   public static String listToString(List<String> stringList){
	        if (stringList==null) {
	            return null;
	        }
	        StringBuilder result=new StringBuilder();
	        boolean flag=false;
	        for (String string : stringList) {
	            if (flag) {
	                result.append(",");
	            }else {
	                flag=true;
	            }
	            result.append(string);
	        }
	        return result.toString();
	    }
	public static void main(String[] args) throws Exception {
		String phone="15009911796";
		String	templateCode = "SMS_18190186";
		String param = "";
		String	smsFreeSignName = "邻乐社区";
//		String result1=SMSutil.sendMsg(smsFreeSignName, param, phone, templateCode);
//		System.out.println(result1);
		
		
		//F:\sms
	       File file = new File("F:/sms/ExcelDemo.xls");
	       String[][] result = getData(file, 1);
	       List<String> list=new ArrayList<>();
	       List<String> list2=new ArrayList<>();
	       for(int j=1;j<result.length;j++) {
	    	   String str=result[j][0];
	    	   if(str.trim().length()>11){
	    		   str=str.substring(0,11);
	    		   if(str.length()!=11){
	    			   continue;
	    		   }
	    	   }else if(str.trim().length()<11){
	    		   list2.add(str);
	    	   }
	    	   
	    	   if(str.length()==11){
	    		   list.add(str.trim());
    		   }
	    	  
	        }
//	       System.out.println("size="+list.size());
//	       System.out.println(list);
	       List<List<String>> bigList= createList(list,100);
	       
//	       System.out.println(bigList.size());
//	       System.out.println(bigList);
//	       for (int i = 0; i < list.size(); i++) {
//			if(list.get(i).length()!=11){
//				System.out.println(list.get(i));
//			}
//	       }
	       
	       for (List<String> list3 : bigList) {
	    	   String  str= listToString(list3);
	    	   System.out.println(str);
		       String result1=SMSutil.sendMsg(smsFreeSignName, param, str, templateCode);
			   System.out.println(result1);
	       }
	      
	    
//	       for (int i = 0; i < bigList.size(); i++) {
//	    	   List<String> list3=bigList.get(13);
//	    	   str= list3.toString();
////	    	   System.out.println("list3="+list3.size());
////	    	   System.out.println(i);
//	       }
//	       str=str.substring(1, str.lCength()-1);
	    
//	       String str="15972950646,15084446954";
//	       String result1=SMSutil.sendMsg(smsFreeSignName, param, str, templateCode);
//		   System.out.println(result1);
		  	  
		  	  
//	       System.out.println(list.subList(0, 10));
//		   System.out.println("11位数="+list);
//		   System.out.println("8位数="+list2);
//	       for (String str : list) {
//	    	   if(str!=null&&str.matches("^[0-9]+$")){
//	    	    	System.out.print(str+"\n");
//	    		 }else{
//	    			 System.out.print("非数字="+str+"\n");
//	    		 }
//	       }
	/*	BigDecimal num=new BigDecimal(0);
		if(num.compareTo(BigDecimal.ZERO)==1){//大于0
			System.out.println("11");
		}else{
			System.out.println("22");
		}
		System.out.println(num.compareTo(BigDecimal.ZERO));*/
		/*String startTime = "2016-11-01";
		int month=diff(startTime);
        System.out.println(month);
		
		// 活动结束了  油和大米不送了
        // 1楼 1.1 2楼 1.2 超过2楼 1.4
        // 4幢3单元 1-2楼 1.1 超过2楼 1.2
        //1374.336   1374.33   1374.34    1374.349 1374.4  1374.3 //计算金额四舍五入保留一位
        //不足一个月 按一个月计算
        *//** 
         *12 * 117.88 * 1.4=1980.4
         *24 * 117.88 * 1.4=3960.8
         *36*=5759.2
         *//*
        double d= month *117.88 * 1.4 ;
        BigDecimal   b   =   new   BigDecimal(d);  
        double   f1   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();  
        System.out.println(f1);*/
//        System.out.println(2*1640.4);//年*年费
//		int a=5%5;
//		System.out.println(a);
//		int money =generatePreferentialMoney(1l);
		
//		int money = huodong();
//		System.out.println("最终优惠金额money="+money); 
//		for (int i = 0; i < 1000; i++) {
//			System.out.println(testRandom(10, 6));
//		}
		
//		for (int i = 0; i < 1000; i++) {
//			Random ne = new Random();
//			int n5 = ne.nextInt(100);
//			System.out.println(n5);
//		}
		
	}
	 public static String[][] getData(File file, int ignoreRows)
	           throws FileNotFoundException, IOException {
	       List<String[]> result = new ArrayList<String[]>();
	       int rowSize = 0;
	       BufferedInputStream in = new BufferedInputStream(new FileInputStream(
	              file));
	       // 打开HSSFWorkbook
	       POIFSFileSystem fs = new POIFSFileSystem(in);
	       HSSFWorkbook wb = new HSSFWorkbook(fs);
	       HSSFCell cell = null;
	       for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
	           HSSFSheet st = wb.getSheetAt(sheetIndex);
	           // 第一行为标题，不取
	           for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
	              HSSFRow row = st.getRow(rowIndex);
	              if (row == null) {
	                  continue;
	              }
	              int tempRowSize = row.getLastCellNum() + 1;
	              if (tempRowSize > rowSize) {
	                  rowSize = tempRowSize;
	              }
	              String[] values = new String[rowSize];
	              Arrays.fill(values, "");
	              boolean hasValue = false;
	              for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
	                  String value = "";
	                  cell = row.getCell(columnIndex);
	                  if (cell != null) {
	                     // 注意：一定要设成这个，否则可能会出现乱码
//	                     cell.setEncoding(HSSFCell.ENCODING_UTF_16);
	                     switch (cell.getCellType()) {
	                     case HSSFCell.CELL_TYPE_STRING:
	                         value = cell.getStringCellValue();
	                         break;
	                     case HSSFCell.CELL_TYPE_NUMERIC:
	                         if (HSSFDateUtil.isCellDateFormatted(cell)) {
	                            Date date = cell.getDateCellValue();
	                            if (date != null) {
	                                value = new SimpleDateFormat("yyyy-MM-dd")
	                                       .format(date);
	                            } else {
	                                value = "";
	                            }
	                         } else {
	                            value = new DecimalFormat("0").format(cell
	                                   .getNumericCellValue());
	                         }
	                         break;
	                     case HSSFCell.CELL_TYPE_FORMULA:
	                         // 导入时如果为公式生成的数据则无值
	                         if (!cell.getStringCellValue().equals("")) {
	                            value = cell.getStringCellValue();
	                         } else {
	                            value = cell.getNumericCellValue() + "";
	                         }
	                         break;
	                     case HSSFCell.CELL_TYPE_BLANK:
	                         break;
	                     case HSSFCell.CELL_TYPE_ERROR:
	                         value = "";
	                         break;
	                     case HSSFCell.CELL_TYPE_BOOLEAN:
	                         value = (cell.getBooleanCellValue() == true ? "Y"
	                                : "N");
	                         break;
	                     default:
	                         value = "";
	                     }
	                  }
	                  if (columnIndex == 0 && value.trim().equals("")) {
	                     break;
	                  }
	                  values[columnIndex] = rightTrim(value);
	                  hasValue = true;
	              }

	              if (hasValue) {
	                  result.add(values);
	              }
	           }
	       }
	       in.close();
	       String[][] returnArray = new String[result.size()][rowSize];
	       for (int i = 0; i < returnArray.length; i++) {
	           returnArray[i] = (String[]) result.get(i);
	       }
	       return returnArray;
	    }

	    /**
	     * 去掉字符串右边的空格
	     * @param str 要处理的字符串
	     * @return 处理后的字符串
	     */
	     public static String rightTrim(String str) {
	       if (str == null) {
	           return "";
	       }
	       int length = str.length();
	       for (int i = length - 1; i >= 0; i--) {
	           if (str.charAt(i) != 0x20) {
	              break;
	           }
	           length--;
	       }
	       return str.substring(0, length);
	    }
	public static int generatePreferentialMoney(Long activityId) {
		// FIXME 这里因为时间问题就暂时写死了优惠金额的代码，以后有时间要改过来
		// FIXME 特殊逻辑 每5个 人 出一个5快的
//		List<PreferentialActivityRecord> list = recordService.getPreferentialActivityRecord(activityId);
		int money = huodong();
		if (10 % 5 == 0) {
			if (money < 5) {
				return 5;
			}
		}
		return money;
	}

	public static int huodong() {
		Random ne = new Random();
		int n5 = ne.nextInt(100);
		int m = 0; // 结果数字
		if (n5 < 80) { // 80%几率 1-5元
			m = testRandom(4, 1);
			System.out.println("80%几率 1-5元");
		} else { // 20%几率 5-10元
			m = testRandom(10, 5);
			System.out.println("20%几率 5-10元");
		}
		System.out.println("产生随机数n5="+n5);
		System.out.println("优惠金额m="+m);
		return m;
	}

	public static int testRandom(int max, int min) {
		Random random = new Random();
		int result = random.nextInt(max - min + 1) + min;
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

	
	
		
	
	
	
	
	
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static int diff(String startTime){
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
//	                    System.out.println(i+"-"+(monthStart+j));
	                }else{
//	                    System.out.println(i+"-0"+(monthStart+j));
	                }
	            }
	             
	    }
	        return month;
	}
	/**
	 * 判断是否为最新版本方法 将版本号根据.切分为int数组 比较
	 * 
	 * @param localVersion
	 *            本地版本号
	 * @param onlineVersion
	 *            线上版本号
	 *            
	 *            0:等于
	 *            1:小于
	 *            2：大于
	 * @return
	 */
	public static int compareVersion2(String localVersion, String onlineVersion)
	{
	    if (localVersion.equals(onlineVersion))
	    {
	        return 0;
	    }
	    String[] localArray = localVersion.split("\\.");
	    String[] onlineArray = onlineVersion.split("\\.");
	    
	    int length = localArray.length < onlineArray.length ? localArray.length : onlineArray.length;
	 
	    if(localArray.length > onlineArray.length){
	    	 return 0;
	    }
	    for (int i = 0; i < length; i++)
	    {
	        if (Integer.parseInt(onlineArray[i]) > Integer.parseInt(localArray[i]))
	        {
	            return 1;
	        }
	        else if (Integer.parseInt(onlineArray[i]) < Integer.parseInt(localArray[i]))
	        {
	            return 2;
	        }
	        // 相等 比较下一组值
	    }
	 
	    return 1;
	}
	 public static int compareVersion(String s1, String s2){
         if( s1 == null && s2 == null )
             return 0;
         else if( s1 == null )
             return -1;
         else if( s2 == null )
             return 1;

         String[]
             arr1 = s1.split("[^a-zA-Z0-9]+"),
             arr2 = s2.split("[^a-zA-Z0-9]+")
         ;

         int i1, i2, i3;

         for(int ii = 0, max = Math.min(arr1.length, arr2.length); 
        		 ii <= max; ii++){
             if( ii == arr1.length )
                 return ii == arr2.length ? 0 : -1;
             else if( ii == arr2.length )
                 return 1;

             try{
                 i1 = Integer.parseInt(arr1[ii]);
             }
             catch (Exception x){
                 i1 = Integer.MAX_VALUE;
             }

             try{
                 i2 = Integer.parseInt(arr2[ii]);
             }
             catch (Exception x){
                 i2 = Integer.MAX_VALUE;
             }

             if( i1 != i2 ){
                 return i1 - i2;
             }

             i3 = arr1[ii].compareTo(arr2[ii]);

             if( i3 != 0 )
                 return i3;
         }

         return 0;
     }
	
}
