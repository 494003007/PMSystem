package com.thinkgem.jeesite.modules.fin.utils;

import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.fin.entity.AccumulationFund;
import com.thinkgem.jeesite.modules.fin.entity.AccumulationFund;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admini on 2016/10/16.
 */
public class AccumulationFundUtil {

    /**
     * AccumulationFund 模型
     */
    public static AccumulationFund accumulationFund;

    /**
     * @return 获取一个AccumulationFund 模型的实例
     */
    public static AccumulationFund getNewAccumulationFundEntity() {
        accumulationFund = new AccumulationFund();
        return accumulationFund;
    }

    /**
     * @param
     * @param row excel表中的行对象
     * @throws
     */
    public static void fillEntiry(Row row) throws Exception {
        StringBuilder fsm = new StringBuilder();                                                                   //用于存储错误信息
        for (int i = 0;i<4;i++){
            switch (i){
                case 0:
                    try {
                        accumulationFund.setEmployeeId((int) row.getCell(0).getNumericCellValue());                     //从行数据中取第一个值给EmployeeId属性赋值
                    }catch (Exception e){
                        fsm.append("员工编号id格式不正确");                                                      //往
                    }
                    break;
                case 1:
                    try {
                        accumulationFund.setPayDate(row.getCell(1).getDateCellValue());                                 //从行数据中取第二个值给PayDate属性赋值
                    }catch(Exception e){
                        fsm.append("  缴费时间格式不正确");
                    }
                    break;
                case 2:

                    try {
                        if (row.getCell(2).getNumericCellValue()>2147483647){
                            throw new Exception("(金额过大,请手动添加)");
                        }
                        accumulationFund.setCompanyPay(String.valueOf((int) row.getCell(2).getNumericCellValue()));      //从行数据中取第三个值给CompanyPay属性赋值
                    }catch (Exception e){
                        fsm.append("  公司缴费金额格式不正确");
                        if (e.getMessage().equals("(金额过大,请手动添加)")){
                            fsm.append(e.getMessage());
                        }
                    }
                    break;
                case 3:
                    try {
                        accumulationFund.setPersonalPay(String.valueOf((int) row.getCell(3).getNumericCellValue()));     //从行数据中取第四个值给PersonalPay属性赋值
                    }catch (Exception e){
                        fsm.append("  个人缴费金额格式不正确");
                    }
                    break;
                default:
                    break;
            }
        }
        if (!fsm.toString().equals(""))
        {
            throw new Exception(fsm.toString());
        }
    }

    /**
     * @param importExcel 传进一个ImportExcel对象，可对excel进行操作
     * @param fsm 用于记录错误信息
     * @return 返回一个AccumulationFund对象的List数组(将excel数据写入list中并返回)
     * @throws Exception
     */
    public static List<AccumulationFund> getDataList(ImportExcel importExcel, StringBuilder fsm) throws Exception {
        List<AccumulationFund> list = new ArrayList<AccumulationFund>();               //实例化一个AccumulationFund对象的数组
        int rowNum = importExcel.getLastDataRowNum();                            //获取数据条数
        for (int i = importExcel.getDataRowNum()-1; i < rowNum; i++) {            //遍历所有数据
            getNewAccumulationFundEntity();                                         //获得一个新的AccumulationFund对象
            try {
                fillEntiry(importExcel.getRow(i));                               //调用方法将数据写入对象中
            } catch (Exception e) {
                fsm.append("导入用户失败！失败信息：第"+(i+1)+"条数据格式有误（"+e.getMessage()+")<br/>");  //将错误信息加入fsm中
            }
            list.add(accumulationFund);                                               //将accumulationFund对象加入list中
        }
        if (!fsm.toString().equals(""))
            throw new Exception(fsm.toString());                                    //若fsm不为空则抛出错误异常

        return list;
    }

    public static AccumulationFund getAccumulationFundExample(){
        AccumulationFund accumulationFund = new AccumulationFund();
        accumulationFund.setEmployeeId(1);
        Date date = new Date();
        accumulationFund.setPayDate(date);
        accumulationFund.setCompanyPay("100");
        accumulationFund.setPersonalPay("100");
        return accumulationFund;
    }
}
