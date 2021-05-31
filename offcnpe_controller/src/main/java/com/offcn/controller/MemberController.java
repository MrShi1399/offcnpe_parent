package com.offcn.controller;


import com.offcn.pojo.Member;
import com.offcn.service.IMemberService;
import com.offcn.util.MyFileUtils;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * 会员管理
 * @author zs
 * @since 2021-05-25
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Value("${excelFilePath}")
    private String excelFilePath;

    @Reference
    private IMemberService iMemberService;

    /**
     * 分页获取会员信息
     * @param queryPageBean 分页条件
     * @return 分页结果
     */
    @RequestMapping("/listPage")
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = iMemberService.listPage(queryPageBean);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }

    /**
     * 导出当前页面的数据
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(QueryPageBean queryPageBean){
        if(queryPageBean.getQueryString().equals("null")){
            queryPageBean.setQueryString("");
        }
        //查询这一页的数据
        PageResult pageResult = iMemberService.listPage(queryPageBean);
        //把数据写到excel表中
        File file=ListToExcel(pageResult.getRows(),queryPageBean.getCurrentPage());
        //下载到客户端
        return MyFileUtils.download(file.getName(),excelFilePath);
    }

    /**
     * 将数据导出到excel表中
     * @param rows 数据
     * @param currentPage 页码
     * @return
     */
    private File ListToExcel(List<Member> rows, Integer currentPage) {
        File file=new File(excelFilePath+"第"+currentPage+"页会员数据.xlsx");
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("第"+currentPage+"页会员数据");
        //设置表头
        XSSFRow row=sheet.createRow(0);
        String[] rowStrings={"id","姓名","性别","身份证号码","电话号码","注册时间","邮箱","生日"};
        for(int i=0;i<rowStrings.length;i++){
            row.createCell(i).setCellValue(rowStrings[i]);
        }
        //遍历数据
        for(int i=0;i<rows.size();i++){
            Member member=rows.get(i);
            XSSFRow row1= sheet.createRow(i+1);
            String[] data={member.getId().toString(),member.getName(),member.getSex(),member.getIdcard(),member.getPhonenumber(),member.getRegtime().toString(),member.getEmail(),member.getBirthday().toString()};
            for(int j=0;j<data.length;j++){
                row1.createCell(j).setCellValue(data[j]);
            }
        }
        try {
            workbook.write(new FileOutputStream(file));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @RequestMapping("/listYear")
    public List<Object> listYear(){
        try {
            List<Object> allYear = iMemberService.getAllYear();
            return allYear;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

