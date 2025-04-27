package com.seocodes.qrcode.gen.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.seocodes.qrcode.gen.dto.QrCodeGenerateResponse;
import com.seocodes.qrcode.gen.ports.StoragePort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service  //pro Spring mapear corretamente e injetar as dependências corretas no Controller
public class QrCodeGeneratorService {
    //aqui está se referindo à interface (StoragePort)
    //mas o Spring injeta uma instância da classe concreta que implementa essa interface, no caso a S3StorageAdapter
    //como o Spring encontra uma implementação concreta da interface StoragePort, ele injeta automaticamente o S3StorageAdapter no lugar onde a interface for requerida
    private final StoragePort storage;

    public QrCodeGeneratorService(StoragePort storage){
        this.storage = storage;
    }

    public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
        //isso é da biblioteca zxing
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //aqui eu defino em qual texto é pra fazer o encode e em que tipo de formato q vai ser transformado (nesse caso um qrcode mesmo)
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        //pngOutputStream guarda os bytes da imagem PNG em memória
        //ou seja, recebe os dados da imagem que o MatrixToImageWriter
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngQrCodeData = pngOutputStream.toByteArray();

        //upload via metodo lá da infra/ports (ports é a interface q o infra implementou no caso):
        String url = storage.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), "image/png");
        return new QrCodeGenerateResponse(url);

    }
}


