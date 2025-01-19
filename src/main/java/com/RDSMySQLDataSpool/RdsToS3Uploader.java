package com.RDSMySQLDataSpool;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.nio.file.Paths;

public class RdsToS3Uploader {

    public static void main(String[] args) {
        // Database credentials - Ideally, these should also come from environment variables or a configuration file
        String url = "jdbc:mysql://java-training.c50qogumgzkb.us-east-1.rds.amazonaws.com:3306/company"; // Replace "company" with your DB name
        String username = System.getenv("DB_USERNAME");  // Use environment variable for username
        String password = System.getenv("DB_PASSWORD");  // Use environment variable for password
        String query = "SELECT * FROM employee"; // Replace with your actual table name

        // File details
        String filePath = "employee_data.csv"; // Path to save the CSV file

        // S3 details
        String bucketName = "java-training";
        String keyName = "employee_data.csv"; // File name to save in the S3 bucket

        // Automatically use the EC2 instance role's region
        Region region = Region.of(System.getenv("AWS_REGION"));  // Fetch the region from environment variables

        // Initialize S3 Client
        S3Client s3Client = S3Client.builder()
                .region(region)
                .build();  // Uses EC2 role credentials or environment variables

        try {
            // Step 1: Connect to the database and fetch data
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to the database established successfully.");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Step 2: Write data to CSV file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("id,name\n"); // CSV header

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                writer.write(id + "," + name + "\n");
            }

            writer.close();
            resultSet.close();
            statement.close();
            connection.close();
            System.out.println("Data written to CSV file successfully.");

            // Step 3: Upload the CSV file to S3
            try {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(keyName)
                        .build();
                s3Client.putObject(putObjectRequest, Paths.get(filePath));
                System.out.println("File uploaded successfully to " + bucketName + "/" + keyName);
            } catch (S3Exception e) {
                System.err.println("S3 error: " + e.awsErrorDetails().errorMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the S3 client
            s3Client.close();
        }
    }
}
