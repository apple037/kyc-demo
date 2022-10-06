package com.wanda.kyc.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.wanda.kyc.constant.UploadTypeEnum;
import com.wanda.kyc.dto.UtilImage;
import com.wanda.kyc.dto.upload.DeleteFileDto;
import com.wanda.kyc.dto.upload.Upload;
import com.wanda.kyc.exception.SystemRuntimeException;
import com.wanda.kyc.exception.Validator;
import com.wanda.kyc.exception.enumeration.SystemExceptionEnum;
import com.wanda.kyc.mapper.UtilImageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Slf4j
@Service
public class UploadService {

	private static final String GCP_STORAGE_URL = "https://storage.googleapis.com";

	// @Value("${env.platform}")
	// private String env;
	@Value("${env.gcp.bucket:}")
	private String gcpBucket;
	@Value("${env.gcp.token:}")
	private String gcpToken;
	@Value("${env.gcp.image-url:}")
	private String imageUrl;

	private Storage storage;
	@Autowired
	private UtilImageMapper imageMapper;

	@PostConstruct
	public void init() {
		if ("".equals(gcpBucket) || "".equals(gcpToken)) {
			log.warn("不使用Google Storage:[storage=null]");
			return;
		}

		// @off
		try {
			File tempFile = File.createTempFile("tempFile", ".tmp");
			ClassPathResource resource = new ClassPathResource(gcpToken);
			IOUtils.copy(resource.getInputStream(), new FileOutputStream(tempFile));
			
			storage = StorageOptions.newBuilder()
					.setCredentials(GoogleCredentials.fromStream(new FileInputStream(tempFile)))
					.build().getService();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// @on
	}

	/**
	 * 上傳 storage 文檔
	 * 
	 * @param upload
	 */
	public String uploadFile(Upload upload, MultipartFile multipartFile) {
		UploadTypeEnum uploadTypeEnum = UploadTypeEnum.convert(upload.getType());
		String filePath = "bucket/".concat(uploadTypeEnum.getPath()).concat("/").concat(upload.getFileName());
		String contentType = getContentType(upload.getFileName());
		BlobInfo blobInfo = BlobInfo.newBuilder(gcpBucket, filePath).setContentType(contentType).build();
		try {
			File file = convertMultiPartToFile(multipartFile);
			multipartFile.transferTo(file.toPath());
			storage.create(blobInfo, multipartFile.getBytes());

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemRuntimeException(SystemExceptionEnum.UPLOAD_ERROR);
		}

		// @off
		if ("".equals(imageUrl)) {
			return GCP_STORAGE_URL
					.concat("/").concat(gcpBucket)
					.concat("/").concat(uploadTypeEnum.getPath())
					.concat("/").concat(upload.getFileName());
		} else {
			return imageUrl
					.concat("/").concat(uploadTypeEnum.getPath())
					.concat("/").concat(upload.getFileName());
		}
		// @on

		// try {
		// File file = convertMultiPartToFile(multipartFile);
		// multipartFile.transferTo(file.toPath());
		// storage.create(BlobInfo.newBuilder(gcpBucket, "doc/" +
		// multipartFile.getOriginalFilename()).build(), multipartFile.getBytes(),
		// BlobTargetOption.predefinedAcl(PredefinedAcl.PUBLIC_READ));
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// throw new BackstageException(BackstageExceptionEnum.UPLOAD_ERROR);
		// }
		//
		// return
		// GCP_STORAGE_URL.concat("/").concat(gcpBucket).concat("/doc/").concat(multipartFile.getOriginalFilename());
	}
	public Object insertImage(String url,String name) {
		UtilImage utilImage = UtilImage.builder()
				.url(url)
				.build();
		imageMapper.insertImage( utilImage);
		return utilImage;
	}

	private String getContentType(String fileName) {
	
		Validator.isFalseThrow(fileName.contains("."), SystemExceptionEnum.PARAM_ERROR);

		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		switch (ext) {
			case "jpg":
			case "jpeg":
				return "image/jpeg";
			case "png":
				return "image/png";
			default:
				return "application/octet-stream";
		}
	}

	/**
	 * MultiPart to File
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convFile)) {
			fos.write(file.getBytes());
		}
		return convFile;
	}

	/**
	 * 刪除 storage 文檔
	 * 
	 * @param reqDto
	 */
	public void deleteFile(DeleteFileDto reqDto) {
		for (String fileName : reqDto.getFileNameList()) {
			storage.delete(gcpBucket, fileName);
		}
	}

	/**
	 * 檢查 檔案副檔名
	 * 
	 * @param fileName
	 * 
	 *            目前還沒使用
	 */
	private void checkFile(String fileName) {
		if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
			String[] allowedExt = { ".txt", ".pdf", ".rar", ".zip" };
			for (String ext : allowedExt) {
				if (fileName.endsWith(ext)) {
					return;
				}
			}
			throw new SystemRuntimeException(SystemExceptionEnum.UPLOAD_FILE_NOT_SUPPORTED);
		}
	}

	/**
	 * 下載檔案
	 * 
	 * @param fileName
	 * @param response
	 */
	public void downloadFile(String fileName, HttpServletResponse response) {

		// byte[] reader = storage.readAllBytes(gcpBucket, "api/cert/test.jpg");
		// try {
		// OutputStream os = response.getOutputStream();
		// os.write(reader);
		//
		// 配置檔案下載
		// response.setContentType("application/octet-stream");
		// response.setHeader("content-type", "application/octet-stream");
		// // 下載檔案能正常顯示中文
		// response.setHeader("Content-Disposition", "attachment;filename=" +
		// URLEncoder.encode(fileName, "UTF-8"));
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// throw BackstageException.systemError();
		// }

		try {
			HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
			httpUrl.connect();
			File file = inputStreamToFile(httpUrl.getInputStream(), "url.png");
			httpUrl.disconnect();

			// 配置檔案下載
			// response.setContentType("application/octet-stream");
			// response.setHeader("content-type", "image/jpeg");
			// response.setHeader("content-type", "application/octet-stream");
			// 下載檔案能正常顯示中文
			// response.setHeader("Content-Disposition", "attachment;filename=" +
			// URLEncoder.encode(fileName, "UTF-8"));

			// 實現檔案下載
			byte[] buffer = new byte[1024];
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			} catch (Exception e) {
				log.error("{}檔案下載失敗", fileName);
				e.printStackTrace();
				throw new IllegalArgumentException();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String url = "https://runningpoint-test-1256078368.cos.ap-nanjing.myqcloud.com/jeff024-2020-11-20-16-10-16.jpg";

	private static final int numOfEncAndDec = 6666666;

	private static final String PATH = "/Users/hsiang/Code/work/1_torch/honeybeeRunningPoint/測試/";

	public static void main(String[] args) {
		File srcFile = new File(PATH + "srcFile.jpg"); // 初始檔案
		File encFile = new File(PATH + "encFile.tmp"); // 加密檔案
		File decFile = new File(PATH + "decFile.jpg"); // 解密檔案

		try {
			// encFile(srcFile, encFile); // 加密操作

			// decFile(encFile, decFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ------------------------------

		try {
			HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
			httpUrl.connect();
			File file = inputStreamToFile(httpUrl.getInputStream(), "url.png");
			System.out.println("====>>>>" + file.getPath());
			httpUrl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 工具类 inputStream 转 File
	 */
	public static File inputStreamToFile(InputStream ins, String name) throws Exception {
		File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
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

	private static void encFile(File srcFile, File encFile) throws Exception {
		if (!srcFile.exists()) {
			System.out.println("source file not exixt");
			return;
		}

		if (!encFile.exists()) {
			System.out.println("encrypt file created");
			encFile.createNewFile();
		}
		InputStream fis = new FileInputStream(srcFile);
		OutputStream fos = new FileOutputStream(encFile);

		int dataOfFile;
		while ((dataOfFile = fis.read()) > -1) {
			fos.write((dataOfFile ^ numOfEncAndDec) + 459878432);
		}

		fis.close();
		fos.flush();
		fos.close();
	}

	private static void decFile(File encFile, File decFile) throws Exception {
		if (!encFile.exists()) {
			System.out.println("encrypt file not exixt");
			return;
		}
		if (!decFile.exists()) {
			System.out.println("decrypt file created");
			decFile.createNewFile();
		}

		InputStream fis = new FileInputStream(encFile);
		OutputStream fos = new FileOutputStream(decFile);

		int dataOfFile;
		while ((dataOfFile = fis.read()) > -1) {
			fos.write((dataOfFile - 459878432) ^ numOfEncAndDec);
		}

		fis.close();
		fos.flush();
		fos.close();
	}

}
