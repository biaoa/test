package com.linle.roomno.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.common.util.ResponseMsg;
import com.linle.entity.sys.RoomNo;
import com.linle.mobileapi.v1.model.BaseEntity;
import com.linle.mobileapi.v1.response.BuildResponse;
import com.linle.roomno.mapper.RoomNoMapper;
import com.linle.roomno.service.RoomService;

@Transactional
@Service("RoomService")
public class RoomServiceImpl extends CommonServiceAdpter<RoomNo> implements RoomService {
	
	@Autowired
	private RoomNoMapper mapper;
	
	@Override
	public Page<RoomNo> getAllData(Page<RoomNo> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<BuildResponse> getRomeForAPI(Long community_id) {
		return mapper.getRomeForAPI(community_id);
	}
		

	@Override
	public int countRomeByOverall(String overall, Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("overall",overall);
		map.put("id",id);
		return mapper.countRomeByOverall(map);
	}
	
	@Override
	public List<BaseEntity> getBuildForAPI(Long community_id) {
		return mapper.getBuildForAPI(community_id);
	}
	
	@Override
	public List<BaseEntity> getUnitForAPI(Long community_id,String building) {
		Map<String, Object> map = new HashMap<>();
		map.put("community_id",community_id);
		map.put("building",building);
		return mapper.getUnitForAPI(map);
	}
	
	@Override
	public List<BaseEntity> getRoomForAPI(Long community_id,String building,String unit) {
		Map<String, Object> map = new HashMap<>();
		map.put("community_id",community_id);
		map.put("building",building);
		map.put("unit",unit);
		return mapper.getRoomForAPI(map);
	}
	
	public ResponseMsg insertBatchRooms(String jsonStr,Long community_id) throws Exception {
		Gson gson = new Gson();
		List<RoomNo> list = gson.fromJson(jsonStr, new TypeToken<List<RoomNo>>() {}.getType()); 
		
		int fail_conunt=0;
		int success_conunt=0;
		String fail_overalls="";
		for (int i = 0; i < list.size(); i++) {
			RoomNo roomNo=list.get(i);
			roomNo.setCommunityId(community_id);
			//去数据库查，若有同幢同单元同房号的记录，则不做导入
			int countRoom=this.countRomeByOverall(roomNo.getOverall(),community_id);
			if(countRoom>0){//有相同房地址
//				return new ResponseMsg(2, "已经有相同房地址："+roomNo.getOverall(), null);
				fail_conunt++;
				if("".equals(fail_overalls)){
					fail_overalls=roomNo.getOverall();//已经有相同房地址
				}else{
					fail_overalls=fail_overalls+","+roomNo.getOverall();//已经有相同房地址
				}
				
				continue;
			}
			//插入数据库
			int num=mapper.insertSelective(roomNo);
			if(num>0){
				success_conunt++;
			}
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("fail_conunt", fail_conunt);
		map.put("success_conunt", success_conunt);
		map.put("fail_overalls", fail_overalls);
		return new ResponseMsg(0, "操作成功", map); 
	}
	
}
