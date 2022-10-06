package com.wanda.kyc.constant;


import com.wanda.kyc.exception.SystemRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
@AllArgsConstructor
public enum UploadTypeEnum {

	// @off
	IMG("img", "img", "png,jpg"),
	DOC("doc", "doc", "pdf,zip,doc,docx")
	// @on

	;

	private String type;
	private String path;
	private String fileType;

	public static UploadTypeEnum convert(String fileType) {
		for (UploadTypeEnum type : UploadTypeEnum.values()) {
			if (type.getType().equals(fileType)) {
				return type;
			}
		}

		log.error("[上傳圖片]不支援此類型:{}", fileType);
		throw SystemRuntimeException.systemError();
	}

}
