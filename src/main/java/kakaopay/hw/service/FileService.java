package kakaopay.hw.service;

import kakaopay.hw.domain.FileDomain;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileService {

    String getFileList(Integer page, String fileExtention);

    List<FileDomain> getFileList();

    String setFileInfo( String comment, MultipartFile[] files);

    void getFile(Integer fileId, HttpServletResponse response);

    String modifyFileInfo(Integer fileId, String comment, MultipartFile[] files);

    String removeFIleInfo(Integer fileId);

}
