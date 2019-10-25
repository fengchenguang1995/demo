package cn.f.demo.controller;


import cn.f.demo.Service.UserService;
import cn.f.demo.pojo.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/user/import",method = RequestMethod.POST)
    public String importUser(@RequestParam(name="file") MultipartFile file) throws Exception {
        //1.解析Excel
        //1.1.根据Excel文件创建工作簿
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        //1.2.获取Sheet
        Sheet sheet = wb.getSheetAt(0);//参数：索引
        //1.3.获取Sheet中的每一行，和每一个单元格
        //2.获取用户数据列表
        List<User> list = new ArrayList<>();
        System.out.println(sheet.getLastRowNum());
        for (int rowNum = 1; rowNum<= sheet.getLastRowNum() ;rowNum ++) {
            Row row = sheet.getRow(rowNum);//根据索引获取每一个行
            Object [] values = new Object[row.getLastCellNum()];
            for(int cellNum=1;cellNum< row.getLastCellNum(); cellNum ++) {
                Cell cell = row.getCell(cellNum);
                Object value = getCellValue(cell);
                values[cellNum] = value;
            }
            //在User的构造函数中，根据表格值顺序设置User对应的属性值
            User user = new User();
            user.setId((Integer) values[0]);
            user.setName((String) values[1]);
            user.setPhone((String) values[2]);
            user.setEducation((String) values[3]);
            user.setState((String) values[4]);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setBirthday(df.parse((String) values[5]));
            user.setTime(df.parse((String) values[6]));
            list.add(user);
        }
        //3.调用服务层批量保存用户
        userService.insertUser(list);

        return "OK";
    }

    public static Object getCellValue(Cell cell) {
        //1.获取到单元格的属性类型
        CellType cellType = cell.getCellType();
        //2.根据单元格数据类型获取数据
        Object value = null;
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)) {
                    //日期格式
                    value = cell.getDateCellValue();
                }else{
                    //数字
                    value = cell.getNumericCellValue();
                }
                break;
            case FORMULA: //公式
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletResponse response) throws Exception {
        //1.构造数据,某个月的用户数据
        List<User> list =userService.findAll(null);
        //2.创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //3.构造sheet
        String[] titles = {"编号", "姓名", "手机","最高学历", "国家地区","生日", "时间"};
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        AtomicInteger headersAi = new AtomicInteger();
        for (String title : titles) {
            Cell cell = row.createCell(headersAi.getAndIncrement());
            cell.setCellValue(title);
        }
        AtomicInteger datasAi = new AtomicInteger(1);
        Cell cell = null;
        for (User user : list) {
            Row dataRow = sheet.createRow(datasAi.getAndIncrement());
            //编号
            cell = dataRow.createCell(0);
            cell.setCellValue(user.getId());
            //姓名
            cell = dataRow.createCell(1);
            cell.setCellValue(user.getName());
            //手机
            cell = dataRow.createCell(2);
            cell.setCellValue(user.getPhone());
            //最高学历
            cell = dataRow.createCell(3);
            cell.setCellValue(user.getEducation());
            //国家地区
            cell = dataRow.createCell(4);
            cell.setCellValue(user.getState());
            //生日
            Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(format.format(user.getBirthday()));
            cell = dataRow.createCell(5);
            cell.setCellValue(format.format(user.getBirthday()));
            //时间
            cell = dataRow.createCell(6);
            cell.setCellValue(format.format(user.getTime()));
        }
        String fileName = "poi_test.xlsx";
        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("ISO8859-1")));
        response.setHeader("filename", fileName);
        workbook.write(response.getOutputStream());
    }

}
