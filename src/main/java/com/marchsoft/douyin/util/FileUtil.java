/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.marchsoft.douyin.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.marchsoft.douyin.exception.BadRequestException;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * File?????????????????? hutool ?????????
 *
 * @author Zheng Jie
 * @date 2018-12-27
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
    /**
     * ??????????????????
     * <br>
     * windows ???????????????????????????Linux ?????????,
     * ???windows \\==\ ????????????
     * ??????????????? ???????????? ??????????????????
     * <pre>
     *       java.io.tmpdir
     *       windows : C:\Users/xxx\AppData\Local\Temp\
     *       linux: /temp
     * </pre>
     */
    public static final String SYS_TEM_DIR = System.getProperty("java.io.tmpdir") + File.separator;
    /**
     * ??????GB???????????????
     */
    private static final int GB = 1024 * 1024 * 1024;
    /**
     * ??????MB???????????????
     */
    private static final int MB = 1024 * 1024;
    /**
     * ??????KB???????????????
     */
    private static final int KB = 1024;

    /**
     * ???????????????
     */
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    /**
     * MultipartFile???File
     */
    public static File toFile(MultipartFile multipartFile) {
        // ???????????????
        String fileName = multipartFile.getOriginalFilename();
        // ??????????????????
        String prefix = "." + getExtensionName(fileName);
        File file = null;
        try {
            // ???uuid???????????????????????????????????????????????????
            file = File.createTempFile(IdUtil.simpleUUID(), prefix);
            // MultipartFile to File
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return file;
    }

    /**
     * ?????????????????????????????? .
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java???????????? ?????????????????????????????????
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * ??????????????????
     */
    public static String getSize(long size) {
        String resultSize;
        if (size / GB >= 1) {
            //????????????Byte??????????????????1GB
            resultSize = DF.format(size / (float) GB) + "GB   ";
        } else if (size / MB >= 1) {
            //????????????Byte??????????????????1MB
            resultSize = DF.format(size / (float) MB) + "MB   ";
        } else if (size / KB >= 1) {
            //????????????Byte??????????????????1KB
            resultSize = DF.format(size / (float) KB) + "KB   ";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }

    /**
     * inputStream ??? File
     */
    static File inputStreamToFile(InputStream ins, String name) throws Exception {
        File file = new File(SYS_TEM_DIR + name);
        if (file.exists()) {
            return file;
        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        int len = 8192;
        byte[] buffer = new byte[len];
        while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }

    /**
     * ??????????????????????????????????????????
     */
    public static File upload(MultipartFile file, String filePath, String UUID) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssS");
        String suffix = getExtensionName(file.getOriginalFilename());
        String nowStr = "-" + format.format(date);
        try {
            String fileName = UUID + nowStr + "." + suffix;
            String path = filePath + fileName;
            if (path.length() > 255) {
                throw new BadRequestException("the file name is too long");
            }
            // getCanonicalFile ???????????????????????????
            File dest = new File(path).getCanonicalFile();
            // ????????????????????????
            if (!dest.getParentFile().exists()) {
                if (!dest.getParentFile().mkdirs()) {
                    System.out.println("was not successful.");
                }
            }
            // ????????????
            file.transferTo(dest);
            return dest;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * ??????excel
     */
    public static void downloadExcel(List<Map<String, Object>> list, HttpServletResponse response) throws IOException {
        String tempPath = SYS_TEM_DIR + IdUtil.fastSimpleUUID() + ".xlsx";
        File file = new File(tempPath);
        BigExcelWriter writer = ExcelUtil.getBigWriter(file);
        // ???????????????????????????????????????????????????????????????
        writer.write(list, true);
        SXSSFSheet sheet = (SXSSFSheet) writer.getSheet();
        //??????????????????SXSSFSheet  ????????????trackAllColumnsForAutoSizing??????
        sheet.trackAllColumnsForAutoSizing();
        //???????????????
        writer.autoSizeColumnAll();
        // ?????????????????????????????????????????????
        setSizeColumn(sheet, writer);
        //response???HttpServletResponse??????
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //test.xls??????????????????????????????????????????????????????????????????????????????
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
        ServletOutputStream out = response.getOutputStream();
        // ???????????????????????????
        file.deleteOnExit();
        writer.flush(out, true);
        //????????????????????????Servlet???
        IoUtil.close(out);
    }

    // ???????????????(????????????)
    private static void setSizeColumn(SXSSFSheet sheet, BigExcelWriter writer) {
        for (int columnNum = 0; columnNum < writer.getColumnCount(); columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                SXSSFRow currentRow;
                //????????????????????????
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(columnNum) != null) {
                    SXSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    public static String getFileType(String type) {
        String documents = "txt doc pdf ppt pps xlsx xls docx";
        String music = "mp3 wav wma mpa ram ra aac aif m4a";
        String video = "avi mpg mpe mpeg asf wmv mov qt rm mp4 flv m4v webm ogv ogg";
        String image = "bmp dib pcp dif wmf gif jpg tif eps psd cdr iff tga pcd mpt png jpeg";
        if (image.contains(type)) {
            return "image";
        } else if (documents.contains(type)) {
            return "doc";
        } else if (music.contains(type)) {
            return "music";
        } else if (video.contains(type)) {
            return "video";
        } else {
            return "other";
        }
    }

    public static void checkSize(long maxSize, long size) {
        // 1M
        int len = 1024 * 1024;
        if (size > (maxSize * len)) {
            throw new BadRequestException("????????????????????????");
        }
    }

    /**
     * ??????????????????????????????
     */
    public static boolean check(File file1, File file2) {
        String img1Md5 = getMd5(file1);
        String img2Md5 = getMd5(file2);
        return img1Md5.equals(img2Md5);
    }

    /**
     * ??????????????????????????????
     */
    public static boolean check(String file1Md5, String file2Md5) {
        return file1Md5.equals(file2Md5);
    }

    private static byte[] getByte(File file) {
        // ??????????????????
        byte[] b = new byte[(int) file.length()];
        try {
            InputStream in = new FileInputStream(file);
            try {
                System.out.println(in.read(b));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return b;
    }

    private static String getMd5(byte[] bytes) {
        // 16????????????
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(bytes);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            // ?????? ???????????????
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * ????????????
     *
     * @param request  /
     * @param response /
     * @param file     /
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file, boolean deleteOnExit) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    if (deleteOnExit) {
                        file.deleteOnExit();
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public static String getMd5(File file) {
        return getMd5(getByte(file));
    }

    /**
     * ????????????
     *
     * @param fileName ?????????
     * @return ??????
     */
    public static Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new BadRequestException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new BadRequestException("File not found " + fileName);
        }
    }

    /**
     * @param in
     * @param file
     * @return List
     * @author Wangmingcan
     * @date 2020-09-07 22:35
     * @description ??????excel
     */
    public static List<List<Object>> parseExcel(InputStream in, MultipartFile file) throws Exception {
        List list = null;
        Workbook workbook = null;
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        String suffix = getExtensionName(file.getOriginalFilename());
        list = new ArrayList<>();
        //??????Excel?????????
        if (suffix.equals(".xls")) {
            workbook = new HSSFWorkbook(in);
        } else if (suffix.equals(".xlsx")) {
            workbook = new XSSFWorkbook(in);
        } else {
            //???????????????????????????????????????
            workbook = WorkbookFactory.create(in);
        }
        if (null == workbook) {
            throw new Exception("??????Excel??????????????????");
        }
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheet = workbook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
                list.add(li);
            }
        }
        return list;
    }

    /**
     * ??????txt???????????????????????????????????????txt??????????????????
     *
     * @param inputStream ???????????????
     * @return ????????????
     * @Author LiuJiaChao
     * @date 2020-10-29
     */
    public static String getTxtCode(InputStream inputStream) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(inputStream);
        int p = (bin.read() << 8) + bin.read();
        bin.close();
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }
}
