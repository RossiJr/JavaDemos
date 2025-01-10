package org.rossijr.awss3crud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    // The @Value annotation is used to inject values from the application.properties file.
    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.profileName}")
    private String profileName;

    /**
     * This method configures the S3 client with the necessary credentials and region. </br>
     * The credentials can be provided in many ways, but only three are shown here: </br>
     * <ul>
     *     <li>Static credentials: Access Key ID and Secret Access Key.</li>
     *     <li>Profile credentials: Credentials stored in the AWS credentials file (local).</li>
     *     <li>Instance role credentials: Credentials obtained from the EC2 instance or ECS container role.</li>
     * </ul>
     *
     * <p>For more information, check in the official IAM documentation. </p>
     * @return an S3 client instance.
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(profileCredentialsProvider())
                .build();
    }

    // Please check the README.md file for more information about how to configure the credentials in this way.
    private StaticCredentialsProvider secretKeyCredentialsProvider() {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                        accessKeyId,
                        secretAccessKey
                )
        );
    }

    // Please check the README.md file for more information about how to configure the credentials in this way.
    private ProfileCredentialsProvider profileCredentialsProvider() {
        return ProfileCredentialsProvider.builder()
                .profileName(profileName)
                .build();
    }

    // Please check the README.md file for more information about how to configure the credentials in this way.
    private InstanceProfileCredentialsProvider instanceRoleCredentialsProvider() {
        return InstanceProfileCredentialsProvider.create();
    }

}
