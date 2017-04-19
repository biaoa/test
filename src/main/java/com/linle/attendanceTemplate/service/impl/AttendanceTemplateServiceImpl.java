package com.linle.attendanceTemplate.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JavaType;
import com.linle.attendanceAddress.model.AttendanceAddress;
import com.linle.attendanceAddress.service.AttendanceAddressService;
import com.linle.attendanceSpecialDate.model.AttendanceSpecialDate;
import com.linle.attendanceSpecialDate.service.AttendanceSpecialDateService;
import com.linle.attendanceTemplate.mapper.AttendanceTemplateMapper;
import com.linle.attendanceTemplate.model.AttendanceTemplate;
import com.linle.attendanceTemplate.service.AttendanceTemplateService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.DateUtil;
import com.linle.common.util.JsonUtil;
import com.linle.common.util.LngAndLatUtil;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.communityEmployee.service.CommunityEmployeeService;
import com.linle.entity.sys.Users;
import com.linle.entity.vo.AttendanceAddressVO;
import com.linle.entity.vo.SpecialdateVo;

@Service
@Transactional
public class AttendanceTemplateServiceImpl extends CommonServiceAdpter<AttendanceTemplate>
		implements AttendanceTemplateService {
	
	@Autowired
	private AttendanceTemplateMapper mapper;
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private AttendanceSpecialDateService dateService;
	
	@Autowired
	private AttendanceAddressService addressService;
	
	private CommunityEmployeeService employeeService;
	
	
	@Override
	public Page<AttendanceTemplate> getAllData(Page<AttendanceTemplate> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public boolean operateTemplate(AttendanceTemplate temp) {
		Subject subject = SecurityUtils.getSubject();
		Users userInfo = (Users) subject.getSession().getAttribute("cUser");
		Community community = communityService.getCommunityByuserID(userInfo.getId());
		if(temp.getId()!=null){
			//修改
			temp.setUpdateDate(new Date());
			mapper.updateByPrimaryKeySelective(temp);
			//先删除以前的特殊时间表
			//FIXME 这里直接删除以前的特殊时间  会让之前的考勤中找不到对应的特殊时间
			dateService.deleteAttendanceSpecialDateByTemplate(temp.getId());
			//插入新的特殊时间表
			insertAttendanceSpecialDate(temp);
			//先删除以前的地址信息表
			addressService.deleteByTemplateId(temp.getId());
			//插入新的地址信息表
			insertAttendanceAddress(temp);
			return true;
		}
		//第一步插入模板表
		temp.setDelFlg(0);
		temp.setCommunityId(community.getId());
		temp.setCreateDate(new Date());
		temp.setCreateUser(userInfo.getId());
		temp.setType(1);
		mapper.insertSelective(temp);
		//插入特殊时间表
		insertAttendanceSpecialDate(temp);
		//插入考勤地址表
		insertAttendanceAddress(temp);
		return true;
	}
	
	
	public boolean insertAttendanceSpecialDate(AttendanceTemplate temp){
		JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, SpecialdateVo.class);
		List<SpecialdateVo> list = JsonUtil.StringToList(temp.getSpecialdateList(), javaType);
		for (SpecialdateVo specialdateVo : list) {
			AttendanceSpecialDate date = new AttendanceSpecialDate();
			date.setTemplateId(temp.getId());
			date.setCreateUser(temp.getCreateUser());
			date.setType(specialdateVo.getType());
			date.setSpecialDate(specialdateVo.getSpecialDate());
			date.setDescription(specialdateVo.getDescription());
			date.setCreateDate(new Date());
			dateService.insertSelective(date);
		}
		return true;
	}
	
	public boolean insertAttendanceAddress(AttendanceTemplate temp){
		JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, AttendanceAddressVO.class);
		List<AttendanceAddressVO> list = JsonUtil.StringToList(temp.getAddressList(), javaType);
		for (AttendanceAddressVO attendanceAddressVO : list) {
			AttendanceAddress address = new AttendanceAddress();
			address.setTemplateId(temp.getId());
			address.setAddress(attendanceAddressVO.getAddress());
			address.setLat(attendanceAddressVO.getLat());
			address.setLng(attendanceAddressVO.getLng());
			address.setCreateDate(new Date());
			addressService.insertSelective(address);
		}
		return true;
	}

	@Override
	public String getAllTemplateUser(Long communityId) {
		return mapper.getAllTemplateUser(communityId);
	}

	@Override
	public void addDefaultTemplate(Community community) {
		AttendanceTemplate temp = new AttendanceTemplate();
		temp.setName("默认考勤规则");
		temp.setWorkDays("1,2,3,4,5");
		temp.setOnDuty("09:00");
		temp.setOffDuty("18:00");
		temp.setRemind(0);
		temp.setAddress(community.getAddress());
		temp.setCommunityId(community.getId());
		temp.setCreateUser(community.getUser().getId());
		temp.setCreateDate(new Date());
		temp.setDelFlg(0);
		temp.setShowDate("星期一,星期二,星期三,星期四,星期五");
		temp.setType(0);
		mapper.insertSelective(temp);
		//地址表插入
		AttendanceAddress address = new AttendanceAddress();
		address.setTemplateId(temp.getId());
		Map<String,Double> lnglat = LngAndLatUtil.getLngAndLat(community.getAddress());
		address.setLat(lnglat.get("lat").toString());
		address.setLng(lnglat.get("lng").toString());
		address.setCreateDate(new Date());
		address.setAddress(community.getAddress());
		addressService.insertSelective(address);
	}

	@Override
	public AttendanceTemplate selectTemplateByUserId(Users user) {
		return mapper.selectTemplateByUserId(user);
	}

	@Override
	public AttendanceTemplate selectDefaultTemplate(Users userinfo) {
		return mapper.selectDefaultTemplate(userinfo);
	}

	@Override
	public int needAttendanceByUserId(Date date,AttendanceTemplate template, Long uid) {
		Date now = new Date();
//		System.out.println("date:"+date.toLocaleString()+" 比较  now:"+now.toLocaleString()+"的结果是:"+date.compareTo(now));
		if(date.compareTo(now)==1){
			//如果传来的日期比现在大。那么直接返回不需要打卡
			return 1;
		}
		Integer toDay = DateUtil.getSomeDayofWeek(date);
		//1先判断特殊时间表里今天需不需要打卡
		AttendanceSpecialDate specialDate = dateService.toDaySpecialDateByTemplateId(template.getId());
		//如果特殊时间表里存在今天的记录 那么直接返回特殊时间表的类型
		if (specialDate!=null) {
			return specialDate.getType();
		}
		//如果不在特殊时间表中,去判断今天是否是工作日
		if(template.getWorkDays().indexOf(toDay.toString())>-1){
			return 0;
		}
		return 1;
	}

	@Override
	public List<AttendanceTemplate> selectNeedRemindTemplate() {
		return mapper.selectNeedRemindTemplate();
	}

	@Override
	public List<String> getAllTemplateUserByTemplateId(Long templateId) {
		AttendanceTemplate template = mapper.selectByPrimaryKey(templateId);
		List<String> userList = new ArrayList<>();
		if(template.getType()==0){
			//默认的规则
			userList = employeeService.getEmployeeByCommunityId(template.getCommunityId());
		}else{
			//用户自定义的规则
			userList = Arrays.asList(template.getUids().split(","));
		}
		return userList;
	}

	@Override
	public int needAttendanceByTemplate(Date date, AttendanceTemplate template) {
		Integer toDay = DateUtil.getSomeDayofWeek(date);
		//1先判断特殊时间表里今天需不需要打卡
		AttendanceSpecialDate specialDate = dateService.toDaySpecialDateByTemplateId(template.getId());
		//如果特殊时间表里存在今天的记录 那么直接返回特殊时间表的类型
		if (specialDate!=null) {
			return specialDate.getType();
		}
		//如果不在特殊时间表中,去判断今天是否是工作日
		if(template.getWorkDays().indexOf(toDay.toString())==-1){
			return 0;
		}
		return 1;
	}


}
