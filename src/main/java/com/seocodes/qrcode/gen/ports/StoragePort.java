package com.seocodes.qrcode.gen.ports;

//define o contrato do que cada classe deve implementar (as classes da infrastructure no caso)
public interface StoragePort {
    //parametros autoexplicativos
    String uploadFile(byte[] fileData, String fileName, String contentType);
}
