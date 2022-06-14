# file-service

File Service


# HeartBeat API

http://localhost:8080/api/heartbeat

#MS SQL setup using docker

https://github.com/ashaloka/file-service/docker-compose.yml

docker-compose up -d

#FileService Database SQL schema file

https://github.com/ashaloka/file-service/file_service_schema.sql

#Design Document

https://github.com/ashaloka/file-service/FileServiceDesignDocument.doc

#Setup and TestResults

https://github.com/ashaloka/file-service/FileServiceSetUpAndTestResults.docx

#Sample Employee file

https://github.com/ashaloka/file-service/src/main/resources/EmployeeData_1.txt

#poler configuration
filePollerQuery: "?include=.*.txt&sortBy=reverse:file:modified&maxMessagesPerPoll=60000&preMove=staging&move=.completed"

Poler interval is maxMessagesPerPoll=60000
To pick only text files: include=.*.txt
Pick last modified files: sortBy=reverse:file:modified
Moving file stage after completion only poler will read : preMove=staging&move=.completed

#Required Steps
1. DB Setup --> required to change data base URL, username and password
2. Password encryption added code ServiceConfiguration, give respective password and encrypt   
3. application-dev configuration change the below values
   fileServiceDir
   backupFileDir
   
   