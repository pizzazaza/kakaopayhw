package kakaopay.hw.controller;

import com.sun.org.apache.xpath.internal.operations.Mult;
import kakaopay.common.utils.FileUtil;
import kakaopay.hw.domain.FileDomain;
import kakaopay.hw.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/files")
public class FileController {

    FileService fileService;

    @Autowired
    FileController(FileService fileService){
        this.fileService = fileService;
    }

    @GetMapping
    public String fileList(@RequestParam(value="page", required=false, defaultValue="-1") Integer page,
                           @RequestParam(value="extention", required=false, defaultValue="all") String extention){

        return fileService.getFileList(page, extention);
    }

    @PostMapping
    @ResponseBody
    public String fileUploade(@RequestParam("comment") String comment,
                            @RequestParam("file") MultipartFile[] files ){

        return fileService.setFileInfo(comment, files);
    }

    @GetMapping("/{fileId}")
    public void fileDownload(@PathVariable int fileId, HttpServletResponse response){
        fileService.getFile(fileId, response);
    }

    //    @PutMapping("/{fileId}")
    @RequestMapping(value = "/{fileId}", method ={RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public String updateFileInfo(@PathVariable("fileId") Integer fileId,
                                @RequestParam("comment") String comment,
                               @RequestParam("file") MultipartFile[] files){

        return fileService.modifyFileInfo(fileId, comment, files);
    }

    @DeleteMapping("/{fileId}")
    @ResponseBody
    public String deleteFile(@PathVariable("fileId") Integer fileId){
        String str = fileService.removeFIleInfo(fileId);

        return str;
    }

}
