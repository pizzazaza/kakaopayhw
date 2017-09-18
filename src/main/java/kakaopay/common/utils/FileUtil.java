package kakaopay.common.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import kakaopay.hw.domain.FileDomain;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtil {

    public static HttpServletResponse sendFileResponse(FileDomain file, HttpServletResponse response) {

        // id를 이용하여 파일의 정보를 읽어온다.
        // 이 부분은 원래 db에서 읽어오는 것인데 db부분은 생략했다.

        String originalFilename = file.getFileName();
        originalFilename = originalFilename + '.' + file.getFileExtention();
        String contentType = file.getContentType();
        Long fileSize = file.getFileLength();
        // 해당 파일이 이미 존재해야한다.
        String saveFileName = file.getSaveFileName();

        response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFilename + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileSize);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");

        File readFile = new File(saveFileName);
        if (!readFile.exists()) { // 파일이 존재하지 않다면
            throw new RuntimeException("file not found");
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(readFile);
            FileCopyUtils.copy(fis, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                fis.close();
            } catch (Exception ex) {
                // 아무것도 하지 않음 (필요한 로그를 남기는 정도의 작업만 함.)
            }
        }
        return response;
    }

    public static FileDomain saveFileInLocal(MultipartFile[] files, String baseDir) {
        FileDomain fileDomain = new FileDomain();

        //logger.info("==============File 생성 시작==============");
        if (files != null && files.length > 0) {

            // windows 사용자라면 "c:\boost\storage\년도\월\일" 형태의 문자열을 구한다.
            String formattedDate = baseDir
                    + new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd").format(new Date());
            File f = new File(formattedDate);
            if (!f.exists()) { // 파일이 존재하지 않는다면, 여기서는 폴더가 있는지 없는지 확인
                f.mkdirs(); // 해당 디렉토리를 만든다. 하위폴더까지 한꺼번에 만든다.
            }


            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                String contentType = file.getContentType();
                String name = file.getName();

                String originalFilename = file.getOriginalFilename();
                String extention = originalFilename.substring(originalFilename.lastIndexOf(".")+1, originalFilename.length());
                Long size = file.getSize();
                String uuid = UUID.randomUUID().toString(); // 중복될 일이 거의 없다.
                String saveFileName = formattedDate + File.separator + uuid;

                fileDomain.setFileName(originalFilename);
                fileDomain.setSaveFileName(saveFileName);
                fileDomain.setFileLength(size);
                fileDomain.setContentType(contentType);
                fileDomain.setFileExtention(extention);


                try (InputStream in = file.getInputStream();
                     FileOutputStream fos = new FileOutputStream(saveFileName)) {
                    int readCount = 0;
                    byte[] buffer = new byte[512];
                    while ((readCount = in.read(buffer)) != -1) {
                        fos.write(buffer, 0, readCount);
                    }
                } catch (Exception e) {
                    //logger.error("==============File : "+originalFilename+" 생성 오류==============");
                    e.printStackTrace();
                }
            }
        }
        //logger.info("==============File 생성 성공==============");
        return fileDomain;
    }

    public static boolean deleteFile(String saveFileName){

        File f = new File(saveFileName);

        if (f.delete()) {
            //logger.info("==============File 삭제 성공==============");
            return true;
        } else {
            //logger.info("==============File 삭제 실패==============");
            return false;
        }

    }

}
