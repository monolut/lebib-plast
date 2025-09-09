package lebib.team.lebib_plast.config;

import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    private String accessKey;

    private String secretKey;

    private String regionName;

    public S3Client s3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client
                .builder()
                .region(Region.of(regionName)).credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}
