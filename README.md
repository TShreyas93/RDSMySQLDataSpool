![image](https://github.com/user-attachments/assets/829fee19-435e-467a-8397-d052be59f9a4)



## ****This is my First practise program that involves:****
1. Amazon RDS MySQL DB with one table and 5 records.
2. A Java code that connects to the above table to display the result.
3. A GitHub Action workflow that checks for vulnerability in code using TruffleHog and another workflow that builds the jar file using all artifacts.
4. Connect EC2 with RDS and display the results in an csv file format stored in S3 bucket.

## ****Pending Actions:****

1. Package and Deploy this jar file on a Amazon EC2 instance. { GHA Workflow fails due to dependency mismatches, but if the code is packaged locally and SCP ed  to EC2 instance it works as expected }



## ****New Topics Learnt:****

1. How to build Maven project.
2. How to use Amazon SDKs to connect and access different AWS services.
3. How to configure secrects in AWS Secrets Manager and access them in Java code.
4. How to configure secrets in Github secrets and access then in Actions workflow yaml file.
5. Create custom policies and roles and attach them to Amazon services to provide different access.
6. Different mvm commands and the lifecycle.
7. Java concepts of creating DB connections, prepared statements and resultsets and how to write the result in csv file.
8. Using different ways to use secrets i. by Secrets manager ii. using environment variables iii. aws credentails from profile.
9. Different ways of adding dependency in Maven pom files and debugging errors.
10. Setting up Amazon RDS and accessing it using workbench.
11. Git commands i. importing repository in Intellij ii. Adding files to git. iii. Commiting and pushing code.
12. Modifying all commits if it has passwords by using bfg-1.14.0.jar.
