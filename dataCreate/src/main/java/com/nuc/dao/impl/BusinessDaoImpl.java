package com.nuc.dao.impl;

import com.nuc.dao.BusinessDao;

import java.util.Random;

/**
 * @Author：朱瑞敏
 * @Description： TODO 业务dao层实体类
 **/
public class BusinessDaoImpl implements BusinessDao {

    String[] companyType = {"技术开发","技术转让","技术服务","计算机维修及维护服务","弱电工程设计安装","综合网络布线",
            "系统集成","网页设计与安装","电脑平面设计","美术设计制作","电脑图文设计","制作","绘图","网络技术开发",
            "技术转让","技术咨询","技术服务","电子科技","技术转让及咨询服务","安防技术","企业管理咨询","企业策划",
            "商务咨询","商务服务","酒店管理咨询","翻译服务","航空服务","票务","房地产信息（投资）咨询","文化咨询",
            "教育信息咨询","二手车鉴定评估","金融","保险","证券","投资","旅游","餐饮","娱乐","休闲","购物","造纸",
            "纸品","印刷","包装","广告","会展","商务办公","咨询业","IT","通信电子","互联网","房地产","建筑业",
            "交通","运输","物流","仓储","政府","非盈利机构","生产","加工","制造","医疗","护理","美容","保健",
            "卫生","媒体","出版","影视","文化传播","电气","电力","水力","航空","航天研究与制造","家居","室内设计",
            "装饰装潢","通信","电信","网路设备","电子技术","半导体","集成电路","基金","证券","期货","投资","检验",
            "检测","认证","礼品","工艺美术","奢饰品","媒体","出版","影视","文化传播",};

    /**
     * 随机生成的业务
     * @return
     */
    @Override
    public String createBusiness() {
        String business;
        Random random = new Random();
        int index = random.nextInt(companyType.length);
        business = companyType[index];
        return business;
    }
}
