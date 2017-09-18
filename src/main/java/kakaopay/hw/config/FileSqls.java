package kakaopay.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileSqls {

    public static String SELECT_FILE_LIST_INIT;
    public static String SELECT_FILE_BY_ID;
    public static String SELECT_FILE_ALL_LIST;
    public static String SELECT_FILE_LIST_BY_PAGE;
    public static String SELECT_FILE_LIST_BY_EXTENTION;
    public static String SELECT_FILE_LIST_BY_PAGE_AND_EXTENTION;
    public static String DELETE_FILE_BY_ID;
    public static String UPDATE_FILE_AND_NAME;



    @Value("${spring.database.sql.select-file-list-by-page}")
    public void setSelectFileListByPage(String selectFileListByPage) {
        SELECT_FILE_LIST_BY_PAGE = selectFileListByPage;
    }

    @Value("${spring.database.sql.update-file-and-name-by-id}")
    public void setUpdateFileAndName(String updateFileAndName) {
        UPDATE_FILE_AND_NAME = updateFileAndName;
    }

    @Value("${spring.database.sql.select-file-list-by-extention}")
    public void setSelectFileListByExtention(String selectFileListByExtention) {
        SELECT_FILE_LIST_BY_EXTENTION = selectFileListByExtention;
    }

    @Value("${spring.database.sql.select-file-list-by-page-and-extention}")
    public void setSelectFileListByPageAndExtention(String selectFileListByPageAndExtention) {
        SELECT_FILE_LIST_BY_PAGE_AND_EXTENTION = selectFileListByPageAndExtention;
    }

    @Value("${spring.database.sql.select-file-list-init}")
    public void setSelectFileListInit(String selectFileListInit) {
        SELECT_FILE_LIST_INIT = selectFileListInit;
    }

    @Value("${spring.database.sql.select-file-by-id}")
    public void setSelectFileById(String selectFileById) {
        SELECT_FILE_BY_ID = selectFileById;
    }

    @Value("${spring.database.sql.select-file-all-list}")
    public void setSelectFileAllList(String selectFileAllList) {
        SELECT_FILE_ALL_LIST = selectFileAllList;
    }

    @Value("${spring.database.sql.delete-file-by-id}")
    public void setDeleteFileById(String deleteFileById) {
        DELETE_FILE_BY_ID = deleteFileById;
    }


}
