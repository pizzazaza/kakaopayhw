package kakaopay.hw.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import kakaopay.common.utils.FileUtil;
import kakaopay.hw.dao.FileDao;
import kakaopay.hw.domain.FileDomain;
import kakaopay.hw.service.FileService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    private FileDao fileDao;

    @Value("${app.file.basedir}")
    private String baseDir;

    @Autowired
    FileServiceImpl(FileDao fileDao){
        this.fileDao = fileDao;
    }

    @Override
    @Transactional(readOnly = true)
    public String getFileList(Integer page, String fileExtention) {
        ObjectMapper oMapper = new ObjectMapper();
        List<FileDomain> fileList = fileDao.selectFileList(page, fileExtention);
        JSONObject jsonObj = new JSONObject();
        Map<Integer, Object> total = new HashMap<>();
        int indx = 0;
        for(FileDomain file : fileList) {
            Map<String, Object> map = oMapper.convertValue(file, Map.class);

            total.put(indx, map);
            indx++;
        }
        jsonObj.putAll(total);

        return jsonObj.toJSONString();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileDomain> getFileList() {

        return fileDao.selectFileListInit();
    }

    @Override
    @Transactional(readOnly = false)
    public String setFileInfo( String comment, MultipartFile[] files) {
        ObjectMapper oMapper = new ObjectMapper();
        FileDomain fileDomain = FileUtil.saveFileInLocal(files, baseDir);
        JSONObject jsonObj = new JSONObject();
        fileDomain.setComment(comment);
        fileDomain.setCreateDate(new Date());

        fileDao.insertFileInfo(fileDomain);
        Map<String, Object> map = oMapper.convertValue(fileDomain, Map.class);
        jsonObj.putAll(map);
        return jsonObj.toJSONString();
    }

    @Override
    @Transactional(readOnly = true)
    public void getFile(Integer fileId, HttpServletResponse response) {

        FileDomain fileDomain = fileDao.selectFile(fileId);

        FileUtil.sendFileResponse(fileDomain, response);
    }

    @Override
    @Transactional(readOnly = false)
    public String modifyFileInfo(Integer fileId, String comment, MultipartFile[] files) {
        ObjectMapper oMapper = new ObjectMapper();
        FileDomain beforefileDomain = fileDao.selectFile(fileId);
        boolean deleteFlag = FileUtil.deleteFile(beforefileDomain.getSaveFileName());

        FileDomain fileDomain = FileUtil.saveFileInLocal(files, baseDir);

        JSONObject jsonObj = new JSONObject();
        fileDomain.setComment(comment);
        fileDomain.setModifyDate(new Date());
        fileDomain.setFileId(fileId);
        fileDomain.setCreateDate(beforefileDomain.getCreateDate());
        fileDao.updateFileInfo(fileDomain);

        Map<String, Object> map = oMapper.convertValue(fileDomain, Map.class);
        jsonObj.putAll(map);
        return jsonObj.toJSONString();
    }

    @Override
    @Transactional(readOnly = false)
    public String removeFIleInfo(Integer fileId) {
        FileDomain fileDomain = fileDao.selectFile(fileId);
        boolean deleteFlag = FileUtil.deleteFile(fileDomain.getSaveFileName());

        Integer result = fileDao.deleteFIleInfo(fileId);
        JSONObject jsonObj = new JSONObject();
        if(result == 1){
            jsonObj.put("fileId", fileId);
            jsonObj.put("state", "OK");
        }else if(result == 0) {
            jsonObj.put("fileId", fileId);
            jsonObj.put("state", "FAIL");
        }


        return jsonObj.toJSONString();
    }
}
