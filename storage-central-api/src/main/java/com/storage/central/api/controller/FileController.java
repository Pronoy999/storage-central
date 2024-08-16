package com.storage.central.api.controller;

import com.storage.central.api.service.FileService;
import com.storage.central.api.util.JwtUtils;
import com.storage.central.api.util.ResponseGenerator;
import com.storage.central.common.exceptions.InvalidTokenException;
import com.storage.central.common.model.dto.JwtData;
import com.storage.central.common.model.requests.CreateFileRequest;
import com.storage.central.common.model.responses.CreateFileResponse;
import com.storage.central.common.util.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "FileController")
@RequestMapping(path = Constants.FILE_API_PATH)
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileService fileService;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<?> createFile(@NotNull final CreateFileRequest request, @RequestHeader("token") String jwt) {
        try {
            JwtData jwtData = jwtUtils.verifyJwt(jwt);
            CreateFileResponse response = fileService.persistFile(request.getFileContent(), request.getFileName(), request.getFileExtension(), jwtData.getGuid(), request.getPath());
            return ResponseGenerator.generateSuccessfulFileCreationResponse(response.getFileName(), response.getFilePath(), response.getFileGuid());
        } catch (InvalidTokenException e) {
            log.error("Invalid JWT Token.");
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "User Token Not valid");
        } catch (Exception e) {
            log.error("Exception while creating file: ", e);
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error");
        }
    }
}
