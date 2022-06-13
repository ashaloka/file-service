package com.file.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@CsvRecord(separator = "\\|", skipFirstLine = true)
public class EmployeeData {

	@DataField(pos = 1)
	private String employeeId;

	@DataField(pos = 2)
	private String name;

	@DataField(pos = 3)
	private String email;

	@DataField(pos = 4)
	private String phoneNo;

	@DataField(pos = 5)
	private String dateOfBirth;

	@DataField(pos = 6)
	private String departmentName;

	@DataField(pos = 7)
	private String salary;

	@DataField(pos = 8)
	private String totalSickLeaves;

	@DataField(pos = 9)
	private String usedSickLeaves;

	@DataField(pos = 10)
	private String totalCasualLeaves;

	@DataField(pos = 11)
	private String usedCasualLeaves;

	@DataField(pos = 12)
	private String maritalStatus;

	@DataField(pos = 13)
	private String fatherName;

	@DataField(pos = 14)
	private String motherName;

	@DataField(pos = 15)
	private String spouseName;

	@DataField(pos = 16)
	private String kidsDetails;

	@DataField(pos = 17)
	private String daughterName;

	@DataField(pos = 18)
	private String sonName;

	private String createdBy = "SYSTEM";
	private String updatedBy = "SYSTEM";

}
