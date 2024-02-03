package com.main.controller.file;

import com.main.entities.file.FileWithContentEntity;
import com.main.security.AuthorizeHandler;
import com.main.services.FileService;
import com.main.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final UserService userService;
    private final AuthorizeHandler authorizeHandler;

    @GetMapping(
            path = "/file/{fileId}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    private @ResponseBody byte[] getOrderScanById(
            HttpServletRequest httpServletRequest,
            @PathVariable Long fileId) {
        final String login = authorizeHandler.getLoginBySessionId(httpServletRequest);
        if (login.isEmpty()) {
            return new byte[]{};
        }
        final Long userId = userService.getUserIdByLogin(login);
        if (userId == null) {
            return new byte[]{};
        }
        final FileWithContentEntity fileEntity = fileService.getFileById(userId, fileId);
        if (fileEntity == null) {
            return new byte[]{};
        }
        return fileService.loadFileByOid(fileEntity.getFileOid());
    }
}
