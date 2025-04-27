package com.seocodes.qrcode.gen.controller;

import com.seocodes.qrcode.gen.dto.QrCodeGenerateRequest;
import com.seocodes.qrcode.gen.dto.QrCodeGenerateResponse;
import com.seocodes.qrcode.gen.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//é o endpoint base que o controlador escuta. Qualquer requisição iniciada com "/qrcode" será tratada por este controlador.
@RequestMapping("/qrcode")
    //esse metodo será acionado para qualquer requisição POST para o endpoint "/qrcode".
public class QrCodeController {
    private final QrCodeGeneratorService qrCodeGeneratorService;

    public QrCodeController(QrCodeGeneratorService qrCodeService){
        this.qrCodeGeneratorService = qrCodeService;
    }

    @PostMapping
    //@RequestBody converte os dados da requisição em um objeto QrCodeGenerateRequest
    //ResponseEntity retorna a resposta com o status e os dados
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request) {
        try{
            QrCodeGenerateResponse response = this.qrCodeGeneratorService.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);

        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }

    }
}
