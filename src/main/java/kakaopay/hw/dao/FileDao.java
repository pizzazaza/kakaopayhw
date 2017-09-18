package kakaopay.hw.dao;

import kakaopay.hw.domain.FileDomain;

import java.util.List;

public interface FileDao {
    List<FileDomain> selectFileList(Integer page, String fileExtention);

    List<FileDomain> selectFileListInit();

    Integer insertFileInfo(FileDomain fileDomain);

    FileDomain selectFile(Integer fileId);

    Integer updateFileInfo(FileDomain fileDomain);

    Integer deleteFIleInfo(Integer fileId);

}
