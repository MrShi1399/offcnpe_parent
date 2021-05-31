package com.offcn.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.offcn.pojo.Caldate;
import com.offcn.pojo.Ordersetting;
import com.offcn.service.IOrdersettingService;
import com.offcn.util.MessageConstant;
import com.offcn.util.MyFileUtils;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Value("${excelFilePath}")
    private String excelFilePath;

    @Reference
    private IOrdersettingService ordersettingService;

    @RequestMapping("/uploadTempleate")
    public Result upload(@RequestParam("excelFile") MultipartFile multipartFile) {
        try {
            File excel = MyFileUtils.upload(multipartFile,excelFilePath);
            //上传成功
            if (excel != null) {
                //读取文件
                List<Ordersetting> ordersettingList = excelToList(excel);
                //把list集合中的数据加载到数据库中
                ordersettingService.saveList(ordersettingList);
                return new Result(true, MessageConstant.UPLOAD_SUCCESS);
            }
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPLOAD_FAIL);
        }
    }

    /**
     * 把excel数据读取到list集合中
     * @param excel excel文件
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    private List<Ordersetting> excelToList(File excel) throws IOException, InvalidFormatException {
        XSSFWorkbook workbook = new XSSFWorkbook(excel);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        List<Ordersetting> list = new ArrayList<>();
        for (int i = 1; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            Date date = row.getCell(0).getDateCellValue();
            int number = (int) row.getCell(1).getNumericCellValue();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Ordersetting ordersetting = new Ordersetting(0, localDate, number, 0);
            list.add(ordersetting);
        }
        return list;
    }

    @RequestMapping("/listCaldate")
    public Result listCaldate(String date){
        try {
            List<Caldate> caldateList = ordersettingService.listOrdersetting(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,caldateList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL,null);
        }
    }

    @RequestMapping("/update")
    public Result update(String date,Integer number){
        try {
            ordersettingService.update(date,number);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}

