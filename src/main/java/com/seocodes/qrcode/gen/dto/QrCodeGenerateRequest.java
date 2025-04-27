package com.seocodes.qrcode.gen.dto;

//criei esse DTO pois, caso a gente queira estender essa app, além de lidar com o generation, pode ter outras funções
//não é tão necessário
public record QrCodeGenerateRequest(String text) {

}
