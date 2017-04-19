package linle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linle.common.util.ExcelToolkit;
import com.linle.entity.vo.PropertyFeeVO;

public class ExcelDateVerify {
	
	public static void main(String[] args) throws JsonProcessingException {
		String path = "H:/test_data/F6A55430.xls";
		String path2 = "C:/Users/MBENBEN/Desktop/欣景苑数据/杭州欣景苑物业费模板.xls";
		List<OldDate> list1 = OldDateList(path);
//		List<PropertyFeeVO> list2 = NewDate(path2);
//		for (PropertyFeeVO New : list2) {
			for (OldDate old : list1) {
				if(old.getMonth().equals("")){
					System.out.println(old.getRoomNo()+old.getName());
					old.setMonth("12");
				}
//				if(old.getRoomNo().equals(New.getRoomNo())){
//					float month = Float.parseFloat((old.getMonth()));
//					float mj = Float.parseFloat(old.getAcreage());
//					float nh = Float.parseFloat(old.getPrice())+1;
//					float d=month*mj*nh;
//					BigDecimal  b1  = new BigDecimal(d);
//					 float   f1   =   b1.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue(); 
//					 if(New.getPayable().equals("")){
//						 New.setPayable("0");
//					 }
//					if(!(f1==Float.parseFloat(New.getPayable()))){
//						System.out.print("月="+month+" 面积="+mj+" 能耗="+nh);
//						System.out.println("不一样:"+old.getRoomNo()+"新的:"+New.getPayable()+"老的:"+f1);
//					}
//				}
				
			}
//		}
	}
	
	public static List<OldDate> OldDateList(String path){
		List<OldDate> list = new ArrayList<OldDate>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ExcelToolkit<OldDate> util = new ExcelToolkit<OldDate>(OldDate.class);
		list = util.importExcel("14-5", fis, 4, 0);
		return list;
	}
	
	public static List<PropertyFeeVO> NewDate(String path){
		List<PropertyFeeVO> list = new ArrayList<>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ExcelToolkit<PropertyFeeVO> util = new ExcelToolkit<PropertyFeeVO>(PropertyFeeVO.class);
		list = util.importExcel("", fis, 1, 0);
		return list;
	}

}
