package com.wanda.kyc.config;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.wanda.kyc.exception.SystemRuntimeException;
import com.wanda.kyc.exception.enumeration.SystemExceptionEnum;
import com.wanda.kyc.response.ResponseEnum;
import com.wanda.kyc.response.ResponseModel;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// @off
	/**
	 * 自定義錯誤 捕捉
	 * @param systemRuntimeException
	 * @return
	 */
	@ExceptionHandler(SystemRuntimeException.class)
	public ResponseModel handleSystemRuntimeException(SystemRuntimeException systemRuntimeException) {
		log.error("發生自定義錯誤－code：[{}]，message：[{}]", systemRuntimeException.getCode(), systemRuntimeException.getMessage());
		return ResponseModel.builder()
				.code(systemRuntimeException.getCode())
				.message(systemRuntimeException.getMessage())
				.build();
	}

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseModel handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		log.error("上傳檔案：超過最大上傳大小");
		e.printStackTrace();
		return ResponseModel.builder()
				.code(SystemExceptionEnum.MAX_UPLOAD_SIZE_EXCEEDED.getCode())
				.message(SystemExceptionEnum.MAX_UPLOAD_SIZE_EXCEEDED.getMessage())
				.build();
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseModel handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		log.error("發生資料庫異常");
		e.printStackTrace();
		return ResponseModel.builder()
				.code(ResponseEnum.DATABASE_DATA_ERROR.getCode())
				.message(ResponseEnum.DATABASE_DATA_ERROR.getMessage())
				.build();
	}
	
	/**
	 * RequestParam 參數沒送的錯誤 捕捉
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ MissingServletRequestParameterException.class})
	public ResponseModel handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		log.error("發生請求參數錯誤[RequestParam]:[{}]", e.getMessage());
		return ResponseModel.builder()
				.code(ResponseEnum.VALID_ERROR.getCode())
				.message(ResponseEnum.VALID_ERROR.getMessage())
				.data(e.getMessage())
				.build();
	}

	/**
	 * NotBlank 參數沒送的錯誤 捕捉
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ ConstraintViolationException.class})
	public ResponseModel handleMissingServletRequestParameterException(ConstraintViolationException e) {
		log.error("發生請求參數錯誤[NotBlank]:[{}]", e.getMessage());
		return ResponseModel.builder()
				.code(ResponseEnum.VALID_ERROR.getCode())
				.message(ResponseEnum.VALID_ERROR.getMessage())
				.data(e.getMessage())
				.build();
	}

	/**
	 * 請求參數轉換錯誤 捕捉 (尚未進入valid)
	 * 參數中傳入的String類型的字段 在接收時 該映射字段Integer類型
	 * 當 Controller 在 @RequestBody 接受參數時 調用接口沒有傳參數時也會拋出此異常。
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ HttpMessageNotReadableException.class})
	public ResponseModel handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("發生請求參數轉換錯誤:[{}]", e.getMessage());
		return ResponseModel.builder()
				.code(ResponseEnum.VALID_ERROR.getCode())
				.message(ResponseEnum.VALID_ERROR.getMessage())
				.data(e.getMessage())
				.build();
	}
	
	/**
	 * Valid & Validated 檢核沒過 捕捉
	 * POST 用
	 * @param e
	 * @return
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseModel handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("發生請求參數錯誤[NotBlank]:[{}]", e.getMessage());

		StringBuilder sb = new StringBuilder();
		e.getBindingResult().getFieldErrors().forEach(fieldError -> {
			sb.append("[");
			sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append("]");
		});
		//捕捉Object error，@ScriptAssert的message拋錯
		e.getBindingResult().getGlobalErrors().forEach(globalError -> {
			sb.append("[").append(globalError.getDefaultMessage()).append("]");
		});
		return ResponseModel.builder()
				.code(ResponseEnum.VALID_ERROR.getCode())
				.message(ResponseEnum.VALID_ERROR.getMessage())
				.data(sb.toString())
				.build();
	}

	/**
	 * Valid & Validated 檢核沒過 捕捉
	 * GET 用
	 * @param e
	 * @return
	 */
	@ExceptionHandler({BindException.class})
	public ResponseModel handleMethodArgumentNotValidException(BindException e) {
		log.error("發生請求參數錯誤[NotBlank]:[{}]", e.getMessage());

		StringBuilder sb = new StringBuilder();
		e.getBindingResult().getFieldErrors().forEach(fieldError -> {
			sb.append("[");sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append("]");
		});
		return ResponseModel.builder()
				.code(ResponseEnum.VALID_ERROR.getCode())
				.message(ResponseEnum.VALID_ERROR.getMessage())
				.data(sb.toString())
				.build();
	}

	/**
	 * 未知錯誤捕捉
	 * @param exception
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseModel handleException(Exception exception) {
		log.error("程式發生未知錯誤，請修正！！！");
		exception.printStackTrace();
		return ResponseModel.builder()
				.code(ResponseEnum.SYSTEM_ERROR.getCode())
				.message(ResponseEnum.SYSTEM_ERROR.getMessage())
				.build();
	}
	
	// @on

}
