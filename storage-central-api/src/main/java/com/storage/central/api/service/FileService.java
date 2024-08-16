package com.storage.central.api.service;

import com.storage.central.api.entity.FileMaster;
import com.storage.central.api.repository.FileMasterRepository;
import com.storage.central.common.model.responses.CreateFileResponse;
import com.storage.central.common.util.Constants;
import com.storage.central.common.util.GuidUtils;
import com.storage.central.core.provider.StorageProviderService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final FileMasterRepository fileMasterRepository;
    private final StorageProviderService storageProviderService;

    public CreateFileResponse persistFile(@NotNull final String fileContent, @NotNull final String fileName,
                                          @NotNull final String extension, @NotNull String userGuid, final String filePath) {
        try {
            FileMaster fileMaster = new FileMaster();
            fileMaster.setFileId(GuidUtils.generateGuid());
            fileMaster.setFileName(fileName);
            fileMaster.setFileExtension(extension);
            fileMaster.setUserGuid(userGuid);
            storageProviderService.persistFile(fileName, extension, generateFilePath(userGuid, filePath, fileName + "." + extension), fileContent);
            //TODO: Update SHA from Provider.
            fileMaster.setShaValue("");
            fileMasterRepository.save(fileMaster);
        } catch (Exception e) {
            log.error("Error while saving file:", e);
            return null;
        }
        return null;
    }

    /**
     * Method to generate the path where it will be stored including the base path.
     *
     * @param userGuid: the user guid who is the owner of the file.
     * @param path:     the path user wants to create it.
     * @param fileName: the file name including extension.
     * @return the absolute path generated.
     */
    private String generateFilePath(@NotNull final String userGuid, final String path, @NotNull final String fileName) {
        if (StringUtils.hasText(path)) {
            return Constants.BASE_FILE_PATH + path + Constants.PATH_SEPARATOR + userGuid + Constants.PATH_SEPARATOR + fileName;
        }
        return Constants.BASE_FILE_PATH + Constants.PATH_SEPARATOR + userGuid + Constants.PATH_SEPARATOR + fileName;
    }
}
