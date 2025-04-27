package com.seocodes.qrcode.gen.infra;
import com.seocodes.qrcode.gen.ports.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component  //MAPEIA a implementação pro Spring injetar as dependências
public class S3StorageAdapter implements StoragePort {
    private final S3Client s3Client;
    private final String bucketName;
    private final String region;

    //esses values é lá do application.properties (são strings lá)
    public S3StorageAdapter(@Value("${aws.s3.region}") String region,
                            @Value("${aws.s3.bucket-name") String bucketName, S3Client s3Client){
        this.bucketName = bucketName;
        this.region = region;
        this.s3Client = S3Client.builder()
                .region(Region.of(this.region))
                .build();
    }

    //implemntando da interface StoragePort
    //lembrando: interface é o contrato que define um conjunto de métodos que uma classe deve implementar
    @Override
    public String uploadFile(byte[] fileData, String fileName, String contentType){
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()  //isso aqui é da AWS
                .bucket(bucketName)  //passo o bucket q quero inserir
                .key(fileName)       //passo a chave (é usada pra recuperar o arquivo tb)
                .contentType(contentType)   //autoexplicativo
                .build();   //pra montar o request mesmo, buildar ele

        //bota o objeto (o request que foi buildado), passando qual é o arquivo que será mandado
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));

        //url montadinha do jeito que a AWS gosta
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
    }
}
