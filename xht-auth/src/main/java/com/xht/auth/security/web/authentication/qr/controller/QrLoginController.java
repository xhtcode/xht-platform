package com.xht.auth.security.web.authentication.qr.controller;

import com.xht.auth.security.web.authentication.qr.domain.response.QrCodeGenerateResponse;
import com.xht.auth.security.web.authentication.qr.service.IQrLoginService;
import com.xht.framework.common.domain.R;
import com.xht.framework.security.annotation.IgnoreAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 二维码登录接口
 *
 * @author xht
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sso/qrCode")
public class QrLoginController {

    private final IQrLoginService qrLoginService;

    /**
     * 生成二维码登录码
     * 创建一个新的二维码，包含唯一的二维码ID，用于后续的扫码登录流程
     *
     * @return 二维码生成响应对象，包含二维码ID和相关信息
     */
    @IgnoreAuth
    @GetMapping("/login/generate")
    public R<QrCodeGenerateResponse> generateQrCode(HttpServletRequest request, HttpServletResponse response) {
        return R.ok().build(qrLoginService.generateQrCode(request, response));
    }

}