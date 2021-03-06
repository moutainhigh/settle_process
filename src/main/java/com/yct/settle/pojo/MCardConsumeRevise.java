/**
 * <html>
 *  <body>
 *   <P> Copyright 2018 广东粤通宝电子商务有限公司 </p>
 *   <p> All rights reserved.</p>
 *   <p> Created on 2019年7月1日</p>
 *   <p> Created by mlsama</p>
 *  </body>
 * </html>
 */
package com.yct.settle.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MCardConsumeRevise {

    /**
     * 所属清算文件名<br>
     * 数据库字段:FNAME
     */
    private String FNAME;

    /**
     * 脱机交易流水号<br>
     * 数据库字段:PSN
     */
    private String PSN;

    /**
     * 票卡号<br>
     * 数据库字段:LCN
     */
    private String LCN;

    /**
     * 票卡物理卡号<br>
     * 数据库字段:FCN
     */
    private String FCN;

    /**
     * 上次交易设备编号<br>
     * 数据库字段:LPID
     */
    private String LPID;

    /**
     * 上次交易日期时间<br>
     * 数据库字段:LTIM
     */
    private String LTIM;

    /**
     * 本次交易设备编号<br>
     * 数据库字段:PID
     */
    private String PID;

    /**
     * 本次交易日期时间<br>
     * 数据库字段:TIM
     */
    private String TIM;

    /**
     * 交易金额(元)<br>
     * 数据库字段:TF
     */
    private BigDecimal TF;

    /**
     * 余额(元)<br>
     * 数据库字段:BAL
     */
    private BigDecimal BAL;

    /**
     * 票价(元)<br>
     * 数据库字段:FEE
     */
    private BigDecimal FEE;

    /**
     * 交易类型(02 充值,06 消费,09 复合消费)<br>
     * 数据库字段:TT
     */
    private String TT;

    /**
     * 票卡交易计数<br>
     * 数据库字段:RN
     */
    private String RN;

    /**
     * 累计门槛月份<br>
     * 数据库字段:DMON
     */
    private String DMON;

    /**
     * 公交门槛计数<br>
     * 数据库字段:BDCT
     */
    private String BDCT;

    /**
     * 地铁门槛计数<br>
     * 数据库字段:MDCT
     */
    private String MDCT;

    /**
     * 联乘门槛计数<br>
     * 数据库字段:UDCT
     */
    private String UDCT;

    /**
     * 本次交易入口设备编号(默认值为00000000)<br>
     * 数据库字段:EPID
     */
    private String EPID;

    /**
     * 本次交易入口日期时间<br>
     * 数据库字段:ETIM
     */
    private String ETIM;

    /**
     * 分帐信息(默认值为00)<br>
     * 数据库字段:AI
     */
    private String AI;

    /**
     * 校验码<br>
     * 数据库字段:VC
     */
    private String VC;

    /**
     * 交易认证码<br>
     * 数据库字段:TAC
     */
    private String TAC;

    /**
     * 审核标志<br>
     * 数据库字段:FLAG
     */
    private String FLAG;

    /**
     * 数据修正代码(默认值为00)<br>
     * 数据库字段:CODE
     */
    private String CODE;
}