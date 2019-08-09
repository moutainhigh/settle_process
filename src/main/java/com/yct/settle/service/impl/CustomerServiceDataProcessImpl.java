package com.yct.settle.service.impl;

import com.yct.settle.mapper.CpuCustomerServiceMapper;
import com.yct.settle.mapper.MCardCustomerServiceMapper;
import com.yct.settle.pojo.*;
import com.yct.settle.service.AreaService;
import com.yct.settle.service.CustomerServiceDataProcess;
import com.yct.settle.service.ProcessResultService;
import com.yct.settle.utils.FileUtil;
import com.yct.settle.utils.SqlLdrUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESC:
 * AUTHOR:mlsama
 * 2019/7/4 14:17
 */
@Service
public class CustomerServiceDataProcessImpl implements CustomerServiceDataProcess {
    private final Logger log = LoggerFactory.getLogger(CustomerServiceDataProcessImpl.class);
    @Resource
    private CpuCustomerServiceMapper cpuCustomerServiceMapper;
    @Resource
    private MCardCustomerServiceMapper mCardCustomerServiceMapper;
    @Resource
    private ProcessResultService processResultService;
    @Resource
    private AreaService areaService;

    @Override
    public boolean writerTODM(File dmmj, File dmcj, String settleDate, String zipFileName) {
        try {
            //根据服务商代码确定卡使用地：USEA
            String userArea = areaService.getUseAreaByMerchant(zipFileName);
            if (zipFileName.startsWith("CK")) { //cpu卡
                ArrayList<CpuTrade> cpuTradeList = new ArrayList<>();
                List<CpuCustomerService> customerServiceList = cpuCustomerServiceMapper.findList(settleDate,zipFileName);
                for (CpuCustomerService cpuCustomerService : customerServiceList){
                    CpuTrade cpuTrade = new CpuTrade();
                    convertToCpuTrade(cpuCustomerService,cpuTrade,settleDate,zipFileName,userArea);
                    cpuTradeList.add(cpuTrade);
                }
                FileUtil.writeToFile(dmcj,cpuTradeList);
            }else { //m1卡
                ArrayList<MCardTrade> mCardTradeList = new ArrayList<>();
                List<MCardCustomerService> list = mCardCustomerServiceMapper.findList(settleDate,zipFileName);
                for (MCardCustomerService mCardCustomerService : list){
                    MCardTrade mCardTrade = new MCardTrade();
                    convertToMCardTrade(mCardCustomerService,mCardTrade,settleDate,zipFileName,userArea);
                    mCardTradeList.add(mCardTrade);
                }
                FileUtil.writeToFile(dmmj,mCardTradeList);
            }
            return true;
        }catch (Exception e){
            log.error("把客服文件{}写到dm文件发生异常:{}。",zipFileName,e);
            //修改
            processResultService.update(
                    new FileProcessResult(zipFileName, null, new Date(),
                            "6555", "把客服文件写到dm文件发生异常"));
            return false;
        }
    }

    private void convertToMCardTrade(MCardCustomerService mCardCustomerService, MCardTrade mCardTrade,
                                     String settleDate, String zipFileName,String userArea) {
        BeanUtils.copyProperties(mCardCustomerService,mCardTrade);
        mCardTrade.setDSN(mCardCustomerService.getPSN());
        mCardTrade.setICN(mCardCustomerService.getLCN());
        mCardTrade.setQDATE(settleDate);
        mCardTrade.setDT("03");//客服
        mCardTrade.setBINF("00000000000000000000");//备用信息
        mCardTrade.setQNAME(zipFileName);
        String issuea = areaService.getIssuesByCardNo(mCardCustomerService.getLCN());
        if (StringUtils.isBlank(userArea) && StringUtils.isNotBlank(issuea)){
            userArea = issuea;
        }else if (StringUtils.isBlank(issuea) && StringUtils.isNotBlank(userArea)){
            issuea = userArea;
        }
        mCardTrade.setUSEA(userArea);//使用地
        mCardTrade.setISSUEA(issuea); //发行地
    }

    private void convertToCpuTrade(CpuCustomerService cpuCustomerService, CpuTrade cpuTrade,
                                   String settleDate, String zipFileName,String userArea) {
        BeanUtils.copyProperties(cpuCustomerService,cpuTrade);
        cpuTrade.setQDATE(settleDate);
        cpuTrade.setDT("03");//客服
        cpuTrade.setDMON("0000000000000");//扩展信息
        cpuTrade.setQNAME(zipFileName);
        String issuea = areaService.getIssuesByCardNo(cpuCustomerService.getLCN());
        if (StringUtils.isBlank(userArea) && StringUtils.isNotBlank(issuea)){
            userArea = issuea;
        }else if (StringUtils.isBlank(issuea) && StringUtils.isNotBlank(userArea)){
            issuea = userArea;
        }
        cpuTrade.setUSEA(userArea);//使用地
        cpuTrade.setISSUEA(issuea); //发行地
    }
}
