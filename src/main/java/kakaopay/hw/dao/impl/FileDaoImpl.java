package kakaopay.hw.dao.impl;

import kakaopay.hw.dao.FileDao;
import kakaopay.hw.domain.FileDomain;
import kakaopay.hw.config.FileSqls;
import org.json.simple.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FileDaoImpl implements FileDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<FileDomain> rowMapper = BeanPropertyRowMapper.newInstance(FileDomain.class);


    public FileDaoImpl(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("files").usingGeneratedKeyColumns("file_id");

    }

    @Override
    public List<FileDomain> selectFileList(Integer page, String fileExtention) {

        Map<String, Object> params = new HashMap<>();
        List<FileDomain> fileList = null;
        try {
            if(page == -1 && fileExtention.equals("all")) {

                fileList = jdbc.query(FileSqls.SELECT_FILE_ALL_LIST, params, rowMapper);
            }else if(page == -1){
                params.put("extention",fileExtention);
                fileList = jdbc.query(FileSqls.SELECT_FILE_LIST_BY_EXTENTION, params, rowMapper);
            } else if(fileExtention.equals("all")){
                page = page*10-1;
                if(page*10-1 < 0)
                    page = 0;
                params.put("page", page);
                fileList = jdbc.query(FileSqls.SELECT_FILE_LIST_BY_PAGE, params, rowMapper);
            } else{
                params.put("extention",fileExtention);
                page = page*10-1;
                if(page*10-1 < 0)
                    page = 0;
                params.put("page", page);
                fileList = jdbc.query(FileSqls.SELECT_FILE_LIST_BY_PAGE_AND_EXTENTION, params, rowMapper);
            }
        }catch (EmptyResultDataAccessException e){
            return fileList;
        }

        return fileList;
    }

    @Override
    public List<FileDomain> selectFileListInit() {
        Map<String, Object> params = new HashMap<>();
        List<FileDomain> fileList = null;
        try {
            fileList = jdbc.query(FileSqls.SELECT_FILE_LIST_INIT, params, rowMapper);
        }catch (EmptyResultDataAccessException e){
            return fileList;
        }

        return fileList;
    }

    @Override
    public Integer insertFileInfo(FileDomain fileDomain) {
        Integer fileId = -1;
        SqlParameterSource params = new BeanPropertySqlParameterSource(fileDomain);
        try {
            fileId = insertAction.executeAndReturnKey(params).intValue();
        }catch (EmptyResultDataAccessException e){
            return fileId;
        }

        return fileId;
    }

    @Override
    public FileDomain selectFile(Integer fileId) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileId", fileId);
        FileDomain fileDomain = null;
        try{
            fileDomain = jdbc.queryForObject(FileSqls.SELECT_FILE_BY_ID, params, rowMapper);
        }catch (EmptyResultDataAccessException e){
            return fileDomain;
        }

        return fileDomain;
    }

    @Override
    public Integer updateFileInfo(FileDomain fileDomain) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileId", fileDomain.getFileId());
        params.put("saveFileName", fileDomain.getSaveFileName());
        params.put("fileName", fileDomain.getFileName());
        params.put("fileLength", fileDomain.getFileLength());
        params.put("fileExtention", fileDomain.getFileExtention());
        params.put("fileContentType", fileDomain.getContentType());
        params.put("comment", fileDomain.getComment());
        params.put("fileModifyDate", fileDomain.getModifyDate());

        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(params);
        Integer result = 0;
        try{
            result = jdbc.update(FileSqls.UPDATE_FILE_AND_NAME, params);
        }catch (EmptyResultDataAccessException e){
            return result;
        }

        return result;
    }

    @Override
    public Integer deleteFIleInfo(Integer fileId) {

        Map<String, Object> params = new HashMap<>();
        params.put("fileId", fileId);
        Integer result = 0;
        try{
            result = jdbc.update(FileSqls.DELETE_FILE_BY_ID, params);
        }catch (EmptyResultDataAccessException e){
            return result;
        }

        return result;
    }


}
