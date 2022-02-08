package kr.letech.study.utility;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.dto.AttachDTO;

public class FileUtilities {
	private final static String ROOT_PATH = Paths.get("C:", "Users", "Desktop","testFile").toString();

	public static List<AttachDTO> parseFileInfo(List<MultipartFile> files) throws Exception {
		if (CollectionUtils.isEmpty(files)) {
			return Collections.emptyList();
		}

		String savePath = Paths.get(ROOT_PATH, "files").toString();

		if (!new File(savePath).exists()) {
			try {
				new File(savePath).mkdir();
			} catch (Exception e) {
			}
		}
		
		List<AttachDTO> attachList = new ArrayList<>();

		for (MultipartFile file : files) {
			String oriFileName = file.getOriginalFilename();
			if(oriFileName == null || "".equals(oriFileName))continue;
			String uuid = UUID.randomUUID().toString();
			String filePath = Paths.get(savePath, uuid).toString();
			String fileType = oriFileName.substring(oriFileName.lastIndexOf(".")+1).toUpperCase();
			long size = file.getSize();
			
			AttachDTO attach = new AttachDTO(uuid, filePath, oriFileName, fileType, size);
			
			attachList.add(attach);
		}
		return attachList;
	}

}