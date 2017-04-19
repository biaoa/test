package com.linle.system.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linle.BroadbandFee.service.BroadbandFeeService;
import com.linle.common.base.BaseController;
import com.linle.common.util.BigDecimalUtil;
import com.linle.common.util.DateUtil;
import com.linle.common.util.ExcelToolkit;
import com.linle.common.util.StringUtil;
import com.linle.common.util.SysConfig;
import com.linle.community.model.Community;
import com.linle.entity.vo.BroadbandFeeVO;
import com.linle.entity.vo.PropertyFeeVO;
import com.linle.entity.vo.WaterVO;
import com.linle.globalSettings.model.GlobalSettings;
import com.linle.globalSettings.service.GlobalSettingsService;
import com.linle.priceSetting.model.PriceSetting;
import com.linle.priceSetting.service.PriceSettingService;
import com.linle.propertyFee.service.PropertyFeeService;
import com.linle.utilities.service.UtilitiesService;

@Controller
@RequestMapping("/download")
public class DownloadController extends BaseController {
	
	protected final Logger _logger = LoggerFactory.getLogger(getClass());
	
	public final static String URL = SysConfig.APP_FOLDER;
	
	@Autowired
	private UtilitiesService utilitiesService;
	
	@Autowired
	private PropertyFeeService propertyFeeService;
	
	@Autowired
	private BroadbandFeeService broadbandFeeService;
	
	@Autowired
	private PriceSettingService priceSettingService;
	
	@RequestMapping("/template/{type}")
	public ResponseEntity<byte[]> downTemplate(HttpServletRequest request, HttpServletResponse response,@PathVariable String type){
		String fileName = "";
		switch (type) {
		case "water":
			fileName="水费模板";
			break;
		case "electricity":
			fileName="电费模板";
			break;
		case "gas":
			fileName="燃气费模板";
			break;
		case "broadband":
			fileName="宽带费模板";
			break;
		case "cableTelevision":
			fileName="有线电视缴费模板";
			break;
		case "property":
			fileName="物业费模板";
			break;
		case "room":
			fileName="房号模板";
			break;	
		default:
			break;
		}
		if ("".equals(fileName)) {
			return null;
		}
		return downFile("/payment_templet", "/"+fileName+".xls");
	}
	
	//导出excel 
	@RequestMapping("/exportTemplate/{type}")
	public void exportUtilities(HttpServletRequest request, HttpServletResponse response,@PathVariable String type){
		try {
			Community community =  getCommunity();
			int typeId=0;
			String fileName = "";
			String title="";
			switch (type) {
			case "water":
				fileName="水费模板";
				typeId=1;
				title=community.getName()+"小区水费";
				break;
			case "electricity":
				fileName="电费模板";
				typeId=2;
				title=community.getName()+"小区电费";
				break;
			case "gas":
				fileName="燃气费模板";
				typeId=3;
				title=community.getName()+"小区燃气费";
				break;
			case "broadband":
				fileName="宽带费模板";
				typeId=1;
				title=community.getName()+"小区宽带费";
				break;
			case "cableTelevision":
				fileName="有线电视缴费模板";
				typeId=2;
				title=community.getName()+"小区有线电视缴费";
				break;
			case "property":
				fileName="物业费模板";
				title=community.getName()+"小区物业费";
				break;
			default:
				break;
			}
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
			String msg =sdf2.format(new Date());
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(community.getName()+fileName+".xls", "UTF-8"));
			//获取数据
			if("water".equals(type)||"electricity".equals(type)||"gas".equals(type)){
				List<WaterVO> data = new ArrayList<WaterVO>();
				data=organizUtilitiesData(community.getId(),typeId);
				ExcelToolkit<WaterVO> tool = new ExcelToolkit<>(WaterVO.class);
				tool.exportExcel(data, fileName, data.size()+1, response.getOutputStream(),title);
			}else if("property".equals(type)){
				List<PropertyFeeVO> data = new ArrayList<PropertyFeeVO>();
				data=organizPropertyFeeData(community.getId());
				ExcelToolkit<PropertyFeeVO> tool = new ExcelToolkit<>(PropertyFeeVO.class);
				tool.exportExcel(data, fileName, data.size()+1, response.getOutputStream(),title);
			}else if("broadband".equals(type)||"cableTelevision".equals(type)){
				List<BroadbandFeeVO> data = new ArrayList<BroadbandFeeVO>();
				data=organizInternetFeeData(community.getId(),typeId);
				ExcelToolkit<BroadbandFeeVO> tool = new ExcelToolkit<>(BroadbandFeeVO.class);
				tool.exportExcel(data, fileName, data.size()+1, response.getOutputStream(),title);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("出错了", e);
		}
	
	}

	@Autowired
	private GlobalSettingsService globalSettingsService;
	//组织水费/电费/燃气费 导出Excel数据
	public List<WaterVO> organizUtilitiesData(long communityId,int typeId){
		List<WaterVO> list=utilitiesService.getUtilitiesExportUsers(communityId, typeId);
		List<WaterVO> data = new ArrayList<WaterVO>();
		Map<String, Object> map = new HashMap<>();
		map.put("communityId", communityId);
		map.put("type",typeId);
		BigDecimal gloabPrice= utilitiesService.getSettingPrice(communityId, typeId+"");
		
		for (int i = 0; i < list.size(); i++) {
			WaterVO obj=list.get(i);
			WaterVO water=new WaterVO();
			map.put("houseNumber", obj.getRoomNo());
			//1.单价从单价设置表获取   2.应缴费用：看之前有没有未缴费的单子，若有，且两个价格相同，就将上期抄的上次抄表，当作本期的上次抄表

			//查询该房号之前未缴费的记录总金额填入应缴费用里，上传excel时，再将这些订单状态修改已关闭，缴费记录中的状态“带到下期缴费”
//			BigDecimal sumPayable=utilitiesService.selectBeforeUnPayableSum(map);
			
			water.setName(obj.getName()==null?"":obj.getName()+"");
			water.setRoomNo(obj.getRoomNo());
			water.setRecordTime(DateUtil.getCurrentDateString());//费用时间
			water.setThisMeterReading("");//本次抄表
			water.setActualConsumption("");//实用
			water.setPooledPrice("0");//公摊
			String lastMeterReading=obj.getLastMeterReading();//上期上次抄表
			String thisMeterReading=obj.getThisMeterReading();//上期本次抄表
			String meterReadingValue=thisMeterReading;
			if(BigDecimalUtil.compareTo(gloabPrice, BigDecimal.ZERO)==1){
				if(obj.getStatus()==1){//未交
					//两个价格相同，就将上期抄的上次抄表，当作本期的上次抄表
					if(BigDecimalUtil.compareTo(gloabPrice, BigDecimalUtil.stringToBigDecimal(obj.getPrice()))==0){//价格相等
						meterReadingValue=lastMeterReading;
					}
				}
				water.setPrice((gloabPrice==null?"":gloabPrice)+"");//系统设置的单价
			}else{
				water.setPrice((obj.getPrice()==null?"":obj.getPrice())+"");//单价
			}
			BigDecimal balance=BigDecimalUtil.stringToBigDecimal(obj.getBalance());
			water.setBalance((balance).toString());//结余
			water.setLastMeterReading((meterReadingValue==null?"":meterReadingValue)+"");//上次抄表
			water.setPayable("");//应缴
			data.add(water);	
		}
		return data;
	}
	
	//组织物业 导出Excel数据
	public List<PropertyFeeVO> organizPropertyFeeData(long communityId){
		List<PropertyFeeVO> list=propertyFeeService.getPropertyFeeExportUsers(communityId);
		List<PropertyFeeVO> data = new ArrayList<PropertyFeeVO>();
		for (int i = 0; i < list.size(); i++) {
			PropertyFeeVO obj=list.get(i);
			PropertyFeeVO propertyFee=new PropertyFeeVO();
			propertyFee.setName(obj.getName()==null?"":obj.getName()+"");
			propertyFee.setRoomNo(obj.getRoomNo());
			propertyFee.setAcreage(obj.getAcreage());
			propertyFee.setFeeTime(DateUtil.getCurrentDateString());
			propertyFee.setPrice((obj.getPrice()==null?"":obj.getPrice())+"");//单价
			propertyFee.setPayable("");//应缴
			data.add(propertyFee);	
		}
		return data;
	}

	//组织宽带费，有限电视 导出Excel数据
	public List<BroadbandFeeVO> organizInternetFeeData(long communityId,int typeId){
		List<BroadbandFeeVO> list=broadbandFeeService.getBroadbandFeeExportUsers(communityId,typeId);
		List<BroadbandFeeVO> data = new ArrayList<BroadbandFeeVO>();
		for (int i = 0; i < list.size(); i++) {
			BroadbandFeeVO obj=list.get(i);
			BroadbandFeeVO broadbandFeeVO=new BroadbandFeeVO();
			broadbandFeeVO.setName(obj.getName()==null?"":obj.getName()+"");//业主名称
			broadbandFeeVO.setHouseNumber(obj.getHouseNumber());//门牌号
			broadbandFeeVO.setPayable(obj.getPayable());//支付金额
			broadbandFeeVO.setFeeTime(DateUtil.getCurrentDateString());//费用时间
			data.add(broadbandFeeVO);	
		}
		return data;
	}
	
	private ResponseEntity<byte[]> downFile(String path,String filename) {
		path = request.getSession().getServletContext().getRealPath("/") + "/" + path + filename;
		try {
			filename = URLEncoder.encode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			_logger.error("中文转码失败：", e);
		}
		// Http响应头
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.add("Content-Disposition", "attachment;filename=" + filename);

		try {
			File f = new File(path);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(f), headers, HttpStatus.OK);
		} catch (Exception e) {
			_logger.error("文件下载失败：", e);
		}
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "error.txt");
		return new ResponseEntity<byte[]>("文件不存在.".getBytes(), headers, HttpStatus.OK);
	}
	
	
	@RequestMapping("/downFileImg")
	public ResponseEntity<byte[]> downFileImg(String fileName,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		String str =new String(fileName.getBytes("iso-8859-1"),"UTF-8");
		return downFile2(str);
	}

	private ResponseEntity<byte[]> downFile2(String fileName) {
//		fileName=fileName+".jpg";
		String path= SysConfig.DISK_FOLDER +SysConfig.QRCODEROOM_FOLDER+fileName;//+".jpg"
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			_logger.error("中文转码失败：", e);
		}
		// Http响应头
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.add("Content-Disposition", "attachment;filename=" + fileName);

		try {
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.OK);
		} catch (Exception e) {
			_logger.error("文件下载失败：", e);
		}
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "error.txt");
		return new ResponseEntity<byte[]>("文件不存在.".getBytes(), headers, HttpStatus.OK);
	}

	
//	@RequestMapping(value = "/app", method = RequestMethod.GET)
//	public ResponseEntity<byte[]> app(HttpServletRequest request, HttpServletResponse response) {
//		String userAgentStr = request.getHeader("User-Agent");
//		UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
//		Browser browser = userAgent.getBrowser();
//		OperatingSystem os = userAgent.getOperatingSystem();
//
//		_logger.debug(browser.getName());
//		if (OperatingSystem.IOS == os.getGroup()) {// ios
//			try {
//				response.sendRedirect("https://itunes.apple.com/cn/app/id");
//				return null;
//			} catch (IOException e) {
//				_logger.error("重定向到IOS提醒：", e);
//			} // 暂未开放
//		} else {// 默认下载android
//			return downFile(URL);
//		}
//		HttpHeaders headers = new HttpHeaders();
//		return new ResponseEntity<byte[]>("文件不存在.".getBytes(), headers, HttpStatus.OK);
//	}
	
	

//	@RequestMapping(value = "/app/{type}", method = RequestMethod.GET)
//	public ResponseEntity<byte[]> app(@PathVariable String type, HttpServletResponse response,
//			HttpServletRequest request) {
//		if (type.equals("ios")) {
//			try {
//				response.sendRedirect("https://itunes.apple.com/cn/app/id");
//				return null;
//			} catch (IOException e) {
//				e.printStackTrace(); _logger.error("出错了", e);
//			}
//		} else {
//			return downFile(URL,"");
//		}
//		return null;
//	}

}
