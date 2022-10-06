package com.wanda.kyc.controller;

import com.wanda.kyc.dto.upload.DeleteFileDto;
import com.wanda.kyc.dto.upload.Upload;
import com.wanda.kyc.response.ResponseModel;
import com.wanda.kyc.service.KycService;
import com.wanda.kyc.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
public class UploadController {

	@Autowired
	private UploadService uploadService;
	@Autowired
	private KycService kycService;

	// 上傳檔案 form-data
	@PostMapping("/upload/file")
	public Object uploadFile(@Valid Upload upload, @RequestParam MultipartFile file) {
		String url = uploadService.uploadFile(upload, file);
		return ResponseModel.success(uploadService.insertImage(url,upload.getFileName()));
	}

	// 刪除 GCP 值區裡的檔案
	@PostMapping("/delete/file")
	public Object deleteFile(@RequestBody DeleteFileDto reqDto) {
		uploadService.deleteFile(reqDto);
		return ResponseModel.success();
	}

	@GetMapping("/download/file/{fileName}")
	public void downloadFile(@PathVariable String fileName, HttpServletResponse response) {
		uploadService.downloadFile(fileName, response);
	}

	@GetMapping("/find/file")
	public ResponseModel findFile(String fileIds) {
		return ResponseModel.success(kycService.getImageUrl(fileIds));
	}

}
