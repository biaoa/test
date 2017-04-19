package com.linle.attendanceRecordDetail.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.attendanceRecordDetail.mapper.AttendanceRecordDetailMapper;
import com.linle.attendanceRecordDetail.model.AttendanceRecordDetail;
import com.linle.attendanceRecordDetail.model.WEBAttendanceInfoMode;
import com.linle.attendanceRecordDetail.service.AttendanceRecordDetailService;
import com.linle.attendanceRecordMain.model.AttendanceRecordMain;
import com.linle.attendanceRecordMain.service.AttendanceRecordMainService;
import com.linle.attendanceTemplate.model.AttendanceTemplate;
import com.linle.attendanceTemplate.service.AttendanceTemplateService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.DateUtil;
import com.linle.mobileapi.attendance.model.AttendanceDetail;
import com.linle.mobileapi.attendance.model.AttendanceInfoMode;
import com.linle.mobileapi.attendance.request.AttendanceRequest;

@Service
@Transactional
public class AttendanceRecordDetailServiceImpl extends CommonServiceAdpter<AttendanceRecordDetail>
		implements AttendanceRecordDetailService {

	@Autowired
	private AttendanceRecordDetailMapper mapper;

	@Autowired
	private AttendanceTemplateService templateService;

	@Autowired
	private AttendanceRecordMainService mainService;

	@Override
	public AttendanceDetail selectAttendanceRecordDetail(Long mainId, int type) {
		Map<String, Object> map = new HashMap<>();
		map.put("mainId", mainId);
		map.put("type", type);
		return mapper.selectAttendanceRecordDetail(map);
	}

	@Override
	public boolean insertDetail(AttendanceRecordMain main, AttendanceRequest req) {
		AttendanceRecordDetail detail = new AttendanceRecordDetail();
		AttendanceTemplate template = templateService.selectByPrimaryKey(main.getTemplateId());
		Date date = new Date();
		detail.setMainId(main.getId());
		detail.setDutyDate(date);
		detail.setAddress(req.getAddress());
		detail.setDescription(req.getDescription());
		detail.setAttendanceAddress(req.getAttendanceAddress());
		detail.setType(req.getType());
		detail.setCreateDate(date);
		detail.setFolderId(req.getFolderId());
		detail.setStatus(chenkAttendanceStatus(template, detail, req.getAddressStatus()));
		mapper.insertSelective(detail);
		// 3判断今天是否考勤完了
		List<AttendanceRecordDetail> detailList = mapper.selectAttendanceRecordDetailByMainId(main.getId());
		if (detailList.size() == 2) {
			// 完了 判断状态 修改主记录
			if (detailList.get(0).getStatus() == 0 && detailList.get(1).getStatus() == 0) {
				// 修改主记录状态
				main.setStatus(0);
				main.setUpdateDate(new Date());
				mainService.updateMainRecordStatus(main);
			}
		}
		// 没完 不用处理
		return true;
	}

	// 判断考勤状态
	public static int chenkAttendanceStatus(AttendanceTemplate template, AttendanceRecordDetail detail,
			Integer addressStatus) {
		if (addressStatus == 0) {
			// 正常的
			if (detail.getType() == 0) {
				// 上班
				if (detail.getDutyDate().compareTo(DateUtil.attendanceDateConvert(template.getOnDuty())) > 0) {
					return 2;
				}
				return 0;
			} else {
				// 下班
				if (detail.getDutyDate().compareTo(DateUtil.attendanceDateConvert(template.getOffDuty())) < 0) {
					return 3;
				}
				return 0;
			}
		}
		return 1;
	}

	@Override
	public AttendanceDetail selectDetailByMainIdAndType(Long mainId, Integer type) {
		Map<String, Object> map = new HashMap<>();
		map.put("mainId", mainId);
		map.put("type", type);
		return mapper.selectDetailByMainIdAndType(map);
	}

	@Override
	public AttendanceInfoMode selectInfoByMainIdAndTypeforMode(Long id, Integer type) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("type", type);
		return mapper.selectInfoByMainIdAndType(map);
	}

	@Override
	public List<WEBAttendanceInfoMode> getSomeDayInfo(Long mainId) {
		return mapper.getSomeDayInfo(mainId);
	}

}
