package com.Teng.contract.service.impl;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.Teng.base.vo.PageBean;
import com.Teng.contract.dao.ContractInfoDao;
import com.Teng.contract.service.ContractInfoService;
import com.Teng.contract.vo.ContractContentVo;
import com.Teng.contract.vo.ContractInfoVo;
import com.Teng.contract.vo.ContractMaterialSalePriceVo;
import com.Teng.material.dao.MaterialInfoDao;
import com.Teng.material.vo.MaterialInfoVo;
import com.Teng.material.vo.MaterialNumChangeLogVo;
import com.Teng.member.dao.MemberInfoDao;
import com.Teng.member.vo.MemberInfoVo;
import com.Teng.util.ObjectToString;
import com.Teng.util.SeqGenerate;
import com.Teng.util.StringUtils;
import com.Teng.contract.service.impl.ContractInfoServiceImpl;
@Service("contractInfoService")
public class ContractInfoServiceImpl implements ContractInfoService {
	
	private static Log logger = LogFactory.getLog(ContractInfoServiceImpl.class);
	
	@Resource(name="contractInfoDao")
    private ContractInfoDao contractInfoDao;
	@Resource(name="materialInfoDao")
    private MaterialInfoDao materialInfoDao;
	@Resource(name="memberInfoDao")
    private MemberInfoDao memberInfoDao;

    /**
    *注释    接口(分页查询)
    * 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryContractInfo(ContractInfoVo param, int pageNo,int pageSize) throws Exception {
    	logger.info("分页查询合同信息,param:"+ObjectToString.getString(param)+";pageNo="+pageNo+";pageSize="+pageSize);
        PageBean pageBean = contractInfoDao.queryContractInfo(param,pageNo,pageSize);
    	List<ContractInfoVo> dataList = pageBean.getDataList();
    	if(dataList.size() > 0){
    		for (int i = 0; i < dataList.size(); i++) {
				String contractId = dataList.get(i).getContractId();
				ContractContentVo contractContentVo = new ContractContentVo();
				contractContentVo.setContract_id(contractId);
				List<ContractContentVo> contentList = contractInfoDao.queryContractContentList(contractContentVo);
				dataList.get(i).setContentList(contentList);
				ContractMaterialSalePriceVo contractMaterialSalePriceVo = new ContractMaterialSalePriceVo();
				contractMaterialSalePriceVo.setContract_id(contractId);
				List<ContractMaterialSalePriceVo> materPriceList = contractInfoDao.queryContractSalePriceList(contractMaterialSalePriceVo);
				dataList.get(i).setMaterPriceList(materPriceList);
			}
    		pageBean.setDataList(dataList);
    	}
    	return pageBean;
    }
    /**
    *注释    接口（不分页查询）
    * 
    */
    public List<ContractInfoVo> queryContractInfo(ContractInfoVo param) throws Exception {
    	logger.info("不分页查询合同信息,param:"+ObjectToString.getString(param));
        return contractInfoDao.queryContractInfo(param);
    }

	/**
    *注释 增
    */
    public void insertContractInfo(ContractInfoVo contractInfo,JSONObject jsonObject) throws Exception{
    	logger.info("新增合同信息,jsonObject:"+ObjectToString.getString(jsonObject));
        try {
			contractInfoDao.insertContractInfo(contractInfo);
			//添加合同商品列表
			Integer mateCount = Integer.parseInt((String) jsonObject.get("materCount"));
			if(mateCount > 0){
				for (int i = 1; i <= mateCount; i++) {
					ContractContentVo contractContentVo = new ContractContentVo();
					String material_id = (String) jsonObject.get("contractMate_"+i);
					if(StringUtils.isEmpty(material_id)){
						continue;
					}else{
						StringBuffer content_id = new StringBuffer();
						content_id.append(contractInfo.getContractId()).append(SeqGenerate.getSeqId("ccon", "4"));
						contractContentVo.setContent_id(content_id.toString());
						contractContentVo.setContract_id(contractInfo.getContractId());
						contractContentVo.setMaterial_id(material_id);
						contractContentVo.setMaterial_count((String) jsonObject.get("mateCount_"+i));
						contractContentVo.setContent_status("0");
						//更新合同商品信息
						logger.info("新增合同信息，更新合同商品信息，contractContentVo:"+ObjectToString.getString(contractContentVo));
						contractInfoDao.insertContractContent(contractContentVo);
						//更新商品库存
						String contract_type = contractInfo.getContractType();
						if("0".equals(contract_type)){//买入
							MaterialInfoVo param = new MaterialInfoVo();
							param.setMaterialId(material_id);
							param.setMaterialNumber((String) jsonObject.get("mateCount_"+i));
							logger.info("新增合同信息，增加商品库存，param:"+ObjectToString.getString(param));
							materialInfoDao.addMaterialNumber(param);
							
							MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
					    	StringBuffer logid = new StringBuffer();
					    	Date nowDate = new Date();
				        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				        	logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
				        	materialNumChangeLogVo.setLog_id(logid.toString());
				        	materialNumChangeLogVo.setMaterial_id(material_id);
				        	materialNumChangeLogVo.setChange_type("0");
				        	materialNumChangeLogVo.setChange_number((String) jsonObject.get("mateCount_"+i));
				        	materialNumChangeLogVo.setChange_desc("新增合同，购入材料");
				        	materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
						}else if("1".equals(contract_type)){//卖出
							MaterialInfoVo param = new MaterialInfoVo();
							param.setMaterialId(material_id);
							param.setMaterialNumber((String) jsonObject.get("mateCount_"+i));
							logger.info("新增合同信息，减少商品库存，param:"+ObjectToString.getString(param));
							materialInfoDao.cutMaterialNumber(param);
							
							MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
					    	StringBuffer logid = new StringBuffer();
					    	Date nowDate = new Date();
				        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				        	logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
				        	materialNumChangeLogVo.setLog_id(logid.toString());
				        	materialNumChangeLogVo.setMaterial_id(material_id);
				        	materialNumChangeLogVo.setChange_type("1");
				        	materialNumChangeLogVo.setChange_number((String) jsonObject.get("mateCount_"+i));
				        	materialNumChangeLogVo.setChange_desc("新增合同，卖出材料");
				        	materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
						}
						//更新价格信息
						MemberInfoVo memberInfoVo = new MemberInfoVo();
						List<MemberInfoVo> memberList = memberInfoDao.queryMemberInfo(memberInfoVo);
						if(memberList != null && memberList.size() > 0){
							for(int j = 0; j < memberList.size(); j++){
								String memberId = memberList.get(j).getMemberId();
								//查询默认价格
								ContractMaterialSalePriceVo contractMaterialSalePrice = new ContractMaterialSalePriceVo();
								contractMaterialSalePrice.setContract_id(contractInfo.getContractId());
								contractMaterialSalePrice.setMaterial_id(material_id);
								contractMaterialSalePrice.setMember_id(memberId);
								logger.info("新增合同信息，更新价格信息，contractMaterialSalePrice:"+ObjectToString.getString(contractMaterialSalePrice));
								contractInfoDao.insertContractMaterialSalePrice(contractMaterialSalePrice);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.info("创建合同异常，e:"+e.getMessage());
			throw new Exception("创建合同异常,e:"+e.getMessage());
		}
    }


	/**
    *注释 改
    */
    public void updateContractInfo(ContractInfoVo contractInfo,JSONObject jsonObject) throws Exception{
    	logger.info("修改合同信息，jsonObject:"+ObjectToString.getString(jsonObject));
    	try {
    		contractInfoDao.updateContractInfo(contractInfo);
    		String contract_id = contractInfo.getContractId();
    		//content修改已有材料状态
    		ContractContentVo contractContentVo = new ContractContentVo();
    		contractContentVo.setContract_id(contract_id);
    		List<ContractContentVo> contentList = contractInfoDao.queryContractContentList(contractContentVo);
    		if(contentList != null && contentList.size() > 0){
    			logger.info("修改合同信息,查询合同材料列表，size:"+contentList.size());
    			for (int i = 0; i < contentList.size(); i++) {
    				String contentId = contentList.get(i).getContent_id();
    				contractContentVo = new ContractContentVo();
    				contractContentVo.setContent_id(contentId);
    				contractContentVo.setMaterial_count(StringUtils.isEmpty((String) jsonObject.get("mateCount_"+contentId))?"0":(String)jsonObject.get("mateCount_"+contentId));
    				contractContentVo.setContent_status((String) jsonObject.get("contentStatus_"+contentId));
    				logger.info("修改合同信息,更新合同材料信息，contractContentVo:"+ObjectToString.getString(contractContentVo));
    				contractInfoDao.updateContractContent(contractContentVo);

    				String materialId = (String) jsonObject.get("conMateId_"+contentId);
    				String mateCount = (String) jsonObject.get("mateCount_"+contentId);
    				String mateCountOld = (String) jsonObject.get("mateCountOld_"+contentId);
    				
    				//根据材料状态变化修改库存
    				if(!(jsonObject.get("contentStatus_"+contentId)).equals(jsonObject.get("contentStatusOld_"+contentId))){
    					if("0".equals(contractInfo.getContractType())){//买入
    						if("0".equals((String)jsonObject.get("contentStatus_"+contentId))){//删除改为可用
								MaterialInfoVo param = new MaterialInfoVo();
								param.setMaterialId(materialId);
								param.setMaterialNumber(mateCountOld);
								logger.info("修改合同信息，买入合同，材料删除变可用，增加商品库存，param:"+ObjectToString.getString(param));
								materialInfoDao.addMaterialNumber(param);
								
								MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
						    	StringBuffer logid = new StringBuffer();
						    	Date nowDate = new Date();
					        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					        	logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
					        	materialNumChangeLogVo.setLog_id(logid.toString());
					        	materialNumChangeLogVo.setMaterial_id(materialId);
					        	materialNumChangeLogVo.setChange_type("0");
					        	materialNumChangeLogVo.setChange_number(mateCountOld);
					        	materialNumChangeLogVo.setChange_desc("修改合同信息，买入合同，材料删除变可用,增加商品库存");
					        	materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
    						}else if("1".equals((String)jsonObject.get("contentStatus_"+contentId))){//可用改为删除
    							MaterialInfoVo param = new MaterialInfoVo();
    							param.setMaterialId(materialId);
    							param.setMaterialNumber(mateCountOld);
    							logger.info("修改合同信息，买入合同，材料可用变删除，减少商品库存，param:"+ObjectToString.getString(param));
    							materialInfoDao.cutMaterialNumber(param);
    							
    							MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
    							StringBuffer logid = new StringBuffer();
    							Date nowDate = new Date();
    							SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    							logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
    							materialNumChangeLogVo.setLog_id(logid.toString());
    							materialNumChangeLogVo.setMaterial_id(materialId);
    							materialNumChangeLogVo.setChange_type("1");
    							materialNumChangeLogVo.setChange_number(mateCountOld);
    							materialNumChangeLogVo.setChange_desc("修改合同信息，买入合同，材料可用变删除，减少商品库存");
    							materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
    						}
    					}else if("1".equals(contractInfo.getContractType())){//卖出
    						if("0".equals((String)jsonObject.get("contentStatus_"+contentId))){//删除改为可用
    							MaterialInfoVo param = new MaterialInfoVo();
    							param.setMaterialId(materialId);
    							param.setMaterialNumber(mateCountOld);
    							logger.info("修改合同信息，卖出合同，材料删除变可用，减少商品库存，param:"+ObjectToString.getString(param));
    							materialInfoDao.cutMaterialNumber(param);
    							
    							MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
    							StringBuffer logid = new StringBuffer();
    							Date nowDate = new Date();
    							SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    							logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
    							materialNumChangeLogVo.setLog_id(logid.toString());
    							materialNumChangeLogVo.setMaterial_id(materialId);
    							materialNumChangeLogVo.setChange_type("1");
    							materialNumChangeLogVo.setChange_number(mateCountOld);
    							materialNumChangeLogVo.setChange_desc("修改合同信息，卖出合同，材料删除变可用，减少商品库存");
    							materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
    						}else if("1".equals((String)jsonObject.get("contentStatus_"+contentId))){//可用改为删除
    							MaterialInfoVo param = new MaterialInfoVo();
								param.setMaterialId(materialId);
								param.setMaterialNumber(mateCountOld);
								logger.info("修改合同信息，卖出合同，材料可用变删除，增加商品库存，param:"+ObjectToString.getString(param));
								materialInfoDao.addMaterialNumber(param);
								
								MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
						    	StringBuffer logid = new StringBuffer();
						    	Date nowDate = new Date();
					        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					        	logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
					        	materialNumChangeLogVo.setLog_id(logid.toString());
					        	materialNumChangeLogVo.setMaterial_id(materialId);
					        	materialNumChangeLogVo.setChange_type("0");
					        	materialNumChangeLogVo.setChange_number(mateCountOld);
					        	materialNumChangeLogVo.setChange_desc("修改合同信息，卖出合同，材料可用变删除,增加商品库存");
					        	materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
    						}
    					}
    				}
    				//根据数量变化修改库存
    				if("0".equals((String)jsonObject.get("contentStatus_"+contentId))){//可用状态
    					if(!(mateCount).equals(mateCountOld)){//数量变化
    						Integer count = Integer.parseInt(mateCount);
    						Integer countOld = Integer.parseInt(mateCountOld);
    						if(count > countOld){//增加数量
    							if("0".equals(contractInfo.getContractType())){//买入
    								MaterialInfoVo param = new MaterialInfoVo();
    								param.setMaterialId(materialId);
    								param.setMaterialNumber(String.valueOf(count - countOld));
    								logger.info("修改合同信息，买入合同，增加数量，增加商品库存，param:"+ObjectToString.getString(param));
    								materialInfoDao.addMaterialNumber(param);
    								
    								MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
    						    	StringBuffer logid = new StringBuffer();
    						    	Date nowDate = new Date();
    					        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    					        	logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
    					        	materialNumChangeLogVo.setLog_id(logid.toString());
    					        	materialNumChangeLogVo.setMaterial_id(materialId);
    					        	materialNumChangeLogVo.setChange_type("0");
    					        	materialNumChangeLogVo.setChange_number(String.valueOf(count - countOld));
    					        	materialNumChangeLogVo.setChange_desc("修改合同信息，买入合同，增加数量,增加商品库存");
    					        	materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
    							}else if("1".equals(contractInfo.getContractType())){//卖出
    								MaterialInfoVo param = new MaterialInfoVo();
        							param.setMaterialId(materialId);
        							param.setMaterialNumber(String.valueOf(count - countOld));
        							logger.info("修改合同信息，卖出合同，增加数量，减少商品库存，param:"+ObjectToString.getString(param));
        							materialInfoDao.cutMaterialNumber(param);
        							
        							MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
        							StringBuffer logid = new StringBuffer();
        							Date nowDate = new Date();
        							SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        							logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
        							materialNumChangeLogVo.setLog_id(logid.toString());
        							materialNumChangeLogVo.setMaterial_id(materialId);
        							materialNumChangeLogVo.setChange_type("1");
        							materialNumChangeLogVo.setChange_number(String.valueOf(count - countOld));
        							materialNumChangeLogVo.setChange_desc("修改合同信息，卖出合同，增加数量，减少商品库存");
        							materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
    							}
    						}else if(count < countOld){//减少
    							if("0".equals(contractInfo.getContractType())){//买入
    								MaterialInfoVo param = new MaterialInfoVo();
        							param.setMaterialId(materialId);
        							param.setMaterialNumber(String.valueOf(countOld - count));
        							logger.info("修改合同信息，买入合同，减少数量，减少商品库存，param:"+ObjectToString.getString(param));
        							materialInfoDao.cutMaterialNumber(param);
        							
        							MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
        							StringBuffer logid = new StringBuffer();
        							Date nowDate = new Date();
        							SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        							logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
        							materialNumChangeLogVo.setLog_id(logid.toString());
        							materialNumChangeLogVo.setMaterial_id(materialId);
        							materialNumChangeLogVo.setChange_type("1");
        							materialNumChangeLogVo.setChange_number(String.valueOf(countOld - count));
        							materialNumChangeLogVo.setChange_desc("修改合同信息，买入合同，减少数量，减少商品库存");
        							materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
    							}else if("1".equals(contractInfo.getContractType())){//卖出
    								MaterialInfoVo param = new MaterialInfoVo();
    								param.setMaterialId(materialId);
    								param.setMaterialNumber(String.valueOf(countOld - count));
    								logger.info("修改合同信息，卖出合同，减少数量，增加商品库存，param:"+ObjectToString.getString(param));
    								materialInfoDao.addMaterialNumber(param);
    								
    								MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
    						    	StringBuffer logid = new StringBuffer();
    						    	Date nowDate = new Date();
    					        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    					        	logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
    					        	materialNumChangeLogVo.setLog_id(logid.toString());
    					        	materialNumChangeLogVo.setMaterial_id(materialId);
    					        	materialNumChangeLogVo.setChange_type("0");
    					        	materialNumChangeLogVo.setChange_number(String.valueOf(countOld - count));
    					        	materialNumChangeLogVo.setChange_desc("修改合同信息，卖出合同，减少数量,增加商品库存");
    					        	materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
    							}
    						}
    					}
    				}
    				
    				//price
    				ContractMaterialSalePriceVo contractMaterialSalePriceVo = new ContractMaterialSalePriceVo();
    				contractMaterialSalePriceVo.setContract_id(contract_id);
    				List<ContractMaterialSalePriceVo> materPriceList = contractInfoDao.queryContractSalePriceList(contractMaterialSalePriceVo);
    				if(materPriceList != null && materPriceList.size() > 0){
    					for (int j = 0; j < materPriceList.size(); j++) {
    						String material_id = materPriceList.get(j).getMaterial_id();
    						String member_id = materPriceList.get(j).getMember_id();
    						contractMaterialSalePriceVo = new ContractMaterialSalePriceVo();
    						contractMaterialSalePriceVo.setContract_id(contract_id);
    						contractMaterialSalePriceVo.setMaterial_id(material_id);
    						contractMaterialSalePriceVo.setMember_id(member_id);
    						StringBuffer outS = new StringBuffer();
    						outS.append("out_price_").append(contract_id).append("_").append(material_id).append("_").append(member_id);
    						contractMaterialSalePriceVo.setSale_price(StringUtils.isEmpty((String)jsonObject.get(outS.toString()))?"0.00":(String)jsonObject.get(outS.toString()));
    						StringBuffer inS = new StringBuffer();
    						inS.append("in_price_").append(contract_id).append("_").append(material_id).append("_").append(member_id);
    						contractMaterialSalePriceVo.setIn_price(StringUtils.isEmpty((String)jsonObject.get(inS.toString()))?"0.00":(String)jsonObject.get(inS.toString()));
    						logger.info("修改合同信息,更新合同材料成员价格信息，contractMaterialSalePriceVo:"+ObjectToString.getString(contractMaterialSalePriceVo));
    						contractInfoDao.updateContractMaterialSalePrice(contractMaterialSalePriceVo);
    					}
    				}
    			}
    		}else{
    			logger.info("修改合同信息,查询合同材料列表为空");
    		}
    		//修改新增材料信息
    		//添加合同商品列表
			Integer mateCount = Integer.parseInt((String)jsonObject.get("materCount"));
			if(mateCount > 0){
				for (int i = 1; i <= mateCount; i++) {
					contractContentVo = new ContractContentVo();
					String material_id = (String) jsonObject.get("contractMate_"+i);
					if(StringUtils.isEmpty(material_id)){
						continue;
					}else{
						StringBuffer content_id = new StringBuffer();
						content_id.append(contractInfo.getContractId()).append(SeqGenerate.getSeqId("ccon", "4"));
						contractContentVo.setContent_id(content_id.toString());
						contractContentVo.setContract_id(contractInfo.getContractId());
						contractContentVo.setMaterial_id(material_id);
						contractContentVo.setMaterial_count((String)jsonObject.get("mateCount_"+i));
						contractContentVo.setContent_status("0");
						//更新合同商品信息
						logger.info("修改合同信息，新增合同商品，contractContentVo:"+ObjectToString.getString(contractContentVo));
						contractInfoDao.insertContractContent(contractContentVo);
						//更新商品库存
						String contract_type = contractInfo.getContractType();
						if("0".equals(contract_type)){//买入
							MaterialInfoVo param = new MaterialInfoVo();
							param.setMaterialId(material_id);
							param.setMaterialNumber((String)jsonObject.get("mateCount_"+i));
							logger.info("修改合同信息，买入合同,新增材料列表，增加商品库存，param:"+ObjectToString.getString(param));
							materialInfoDao.addMaterialNumber(param);
							
							MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
					    	StringBuffer logid = new StringBuffer();
					    	Date nowDate = new Date();
				        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				        	logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
				        	materialNumChangeLogVo.setLog_id(logid.toString());
				        	materialNumChangeLogVo.setMaterial_id(material_id);
				        	materialNumChangeLogVo.setChange_type("0");
				        	materialNumChangeLogVo.setChange_number((String)jsonObject.get("mateCount_"+i));
				        	materialNumChangeLogVo.setChange_desc("修改合同信息，买入合同,新增材料列表，增加商品库存");
				        	materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
						}else if("1".equals(contract_type)){//卖出
							MaterialInfoVo param = new MaterialInfoVo();
							param.setMaterialId(material_id);
							param.setMaterialNumber((String)jsonObject.get("mateCount_"+i));
							logger.info("修改合同信息，卖出合同,新增材料列表，减少商品库存，param:"+ObjectToString.getString(param));
							materialInfoDao.cutMaterialNumber(param);
							
							MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
					    	StringBuffer logid = new StringBuffer();
					    	Date nowDate = new Date();
				        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				        	logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
				        	materialNumChangeLogVo.setLog_id(logid.toString());
				        	materialNumChangeLogVo.setMaterial_id(material_id);
				        	materialNumChangeLogVo.setChange_type("1");
				        	materialNumChangeLogVo.setChange_number((String)jsonObject.get("mateCount_"+i));
				        	materialNumChangeLogVo.setChange_desc("修改合同信息，卖出合同,新增材料列表，减少商品库存");
				        	materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
						}
						//更新价格信息
						MemberInfoVo memberInfoVo = new MemberInfoVo();
						List<MemberInfoVo> memberList = memberInfoDao.queryMemberInfo(memberInfoVo);
						if(memberList != null && memberList.size() > 0){
							for(int j = 0; j < memberList.size(); j++){
								String memberId = memberList.get(j).getMemberId();
								//查询默认价格
								ContractMaterialSalePriceVo contractMaterialSalePrice = new ContractMaterialSalePriceVo();
								contractMaterialSalePrice.setContract_id(contractInfo.getContractId());
								contractMaterialSalePrice.setMaterial_id(material_id);
								contractMaterialSalePrice.setMember_id(memberId);
								logger.info("修改合同信息，新增合同商品，更新价格信息，contractMaterialSalePrice:"+ObjectToString.getString(contractMaterialSalePrice));
								contractInfoDao.insertContractMaterialSalePrice(contractMaterialSalePrice);
							}
						}
					}
				}
			}
    		
		} catch (Exception e) {
			logger.info("修改合同信息异常，e:"+e.getMessage());
			throw new Exception("修改合同信息异常,e:"+e.getMessage());
		}
    }

    public void insertContractContent(ContractContentVo vo) throws Exception{
    	logger.info("新增合同材料信息,vo="+ObjectToString.getString(vo));
    	contractInfoDao.insertContractContent(vo);
    }
    
    public void insertContractMaterialSalePrice(ContractMaterialSalePriceVo vo) throws Exception{
    	logger.info("新增合同材料成员价格信息,vo="+ObjectToString.getString(vo));
    	contractInfoDao.insertContractMaterialSalePrice(vo);
    }
    
    public ContractInfoDao getContractInfo() {
        return contractInfoDao;
    }
    
    public void setContractInfoDao(ContractInfoDao dao) {
        this.contractInfoDao = dao;
    }

}