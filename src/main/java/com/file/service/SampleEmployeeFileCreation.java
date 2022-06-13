package com.file.service;

import java.io.FileWriter;
import java.io.IOException;

public class SampleEmployeeFileCreation {

    public static void main(String args[]) throws IOException {


       for(int j=7;j<30;j++){
        //int j=6;

        FileWriter fw = new FileWriter("/Users/gashala/Asha/AshaTemp/files/EmployeeData_"+j+".txt");

        String header = "EmployeeId|Name|Email|PhoneNo|DateOfBirth|Department|Salary|TotalSickLeaves|UsedSickLeaves|TotalCasualLeaves|UsedCasualLeaves|MaritalStatus|FatherName|MotherName|SpouseName|KidsDetails|DaughterName|SonName";

        fw.write(header+ System.lineSeparator());

        for(int i=1;i<=100000;i++) {

            String str = j+"10" + i + "|Test" + i + "|Test" + i + "@test.com" + "|9999999999|02-08-1985|Admin|500000|10|2|15|3|Married|Test" + i + "|Test" + i + "|Test" + i + "|2|Test" + i + "|Test" + i;


            if(i==10 || i==17 || i==30 || i==40 || i==70 || i==100 || i==210 || i==340) {

                str = j+"10" + i + "|Test" + i + "|Test" + i + "@test.com" + "|9999999999|02-09-1995|Software|600000|10|2|15|3|Single|Test" + i + "|Test" + i + "||||";

            }


            if(i==230 || i==510 || i==999 || i==800 || i==300) {

                str = j+"10" + i + "|Test" + i + "|Test" + i + "@test.com" + "|9999999999|05-09-2010|Finance|600000|10|2|15|3|Single|Test" + i + "|Test" + i + "||||";

            }

            if(i==1000 || i==1500 || i==2300 || i==4000 || i==5000) {

                str = j+"10" + i + "|Test" + i + "|Test" + i + "@test.com" + "|9999999999|10-09-2000|HR|600000|10|2|15|3|Married|Test" + i + "|Test" + i + "|Test" + i + "|2|Test" + i + "|Test" + i;

            }
            fw.write(str+ System.lineSeparator());


        }
            fw.close();

            // Display message
            System.out.println(
                    "File reading and writing both done");

        }
       }




}
