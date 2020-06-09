# Project Title

## Sample Spring Boot application for Prescription Entry
* Project home url: http://localhost:8080/login
* Create User using Create user form (There is a register here link in login form).
* During user creation encrypted password will be  stored in DB.
* Use your new user name and pass for login to the application.

## Implement Jasypt encryption for security purpose of database credentials.
* Purpose of this application is to encrypt database credentials (Like database name, username, password, smtp etc) and other any credentials using jasypt encryption.
* So that no one can breach the security of your application and database credentials.

## Prerequisites
* JDK-8
* Spring Boot - 2.1.0
* MySql DB
* jasypt library added on lib folder in project root.

# How to skip jasypt encryption 
* You can skip jasypt encryption by removing @EnableEncryptableProperties annotation from  Spring Boot Main Application class.
* And use plain text for password and for other credentials.

## We need to follow bellow steps for jasypt encryption:
1. Use EncryptConfig.java class to create Encrypted password.
3. Encrypt password and other credentials using jasypt library with a secret key.
4. Add encrypted password in application.properties file.
5. Add jasypt.encryptor.password=MY_SECRET this line in application.properties file.
6. Run application.

## Add @EnableEncryptableProperties in spring boot main class:
@EnableEncryptableProperties

## Password encryption process:
1. Navigate to the lib folder inside the project using command line. 
2. Execute command: java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="Password" password=MY_SECRET algorithm=PBEWithMD5AndDES

## Input parameters:  
1. Input="Password That you want to encrypt".
2. Password=SECRET KEY (Password will be encrypted based on that key).
3. Algorithm = PBEWithMD5AndDES  (Algorithom will be used to generate encypted password).

## Example:

* Command: java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="admin" password=MY_SECRET algorithm=PBEWithMD5AndDES
* ----ENVIRONMENT-----------------
* Runtime: Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 25.202-b08
* ----ARGUMENTS-------------------
* algorithm: PBEWithMD5AndDES
* input: admin
* password: MY_SECRET
* OUTPUT RESULT: NgGptlWy4okMbqDkolTKpA==
* You can also generate encypted password online: https://www.devglan.com/online-tools/jasypt-online-encryption-decryption

## Notes:  
1. You can encrypt any value and add as property in application properties file.

## Add this encrypted password and other encrypted credentials to application properties file
* Use ENC keyword with first bracket ex. property = ENC(Encrypted password).
* spring.datasource.password=ENC(NgGptlWy4okMbqDkolTKpA==)
* spring.data.mariadb.database=ENC(NgGptlWy4okMbqDkolTKpA==)

## Secret key in case of storing encrypted value in DB and retrieving during read and display.
1. You can use a encrypted key in properties file and refer this property in service class file.
2. Example: spring.app.key=ENC(qDEHTsKYAWu9CJ7WU12KBsVHGM5Tnz1s)
3. You can use hardcode key for this purpose because once you stored record in DB and you change this key later then you could not retrieve this records with different secret key.
4. You should use seperate secret key for record storing purpose.

## Add bellow line in properties file where "JASYPT_ENCRYPTOR_PASSWORD" is empty variable.
* jasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD:}
* You will pass value use command line.

## Deployment:
1.  Using command line navigate to folder where war file stored.
2.  Execute the command: java -Djasypt.encryptor.password=MY_SECRET -jar myapp.war
3.  Here 'MY_SECRET' is the your desired secret key and "myapp.war" is the war file name.

 Example encrypted properties and datasource connection
------------------------------
* Encrypted database, username and password with ENC prefix
* spring.data.mariadb.database=ENC(NgGptlWy4okMbqDkolTKpA==)
* spring.datasource.username=ENC(gH4jr76fcpA6SVfV8cB/rA==)
* spring.datasource.password=ENC(7IricUEWMldyeEUDnc7rQBcprjcqXqho)
* jasypt.encryptor.iv-generator-classname=org.jasypt.iv.NoIvGenerator
* jasypt.encryptor.algorithm=PBEWithMD5AndDES
* jasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD:}
* jasypt.encryptor.password=** (Comment this line or remove totaly).
* spring.app.key=ENC(qDEHTsKYAWu9CJ7WU12KBsVHGM5Tnz1s)
* spring.data.mariadb.port=3306
* spring.data.mariadb.host=localhost
------------------------------

## Happy deployment