-- Create Database FileService
CREATE DATABASE FileService;

-- Create Employee Table
CREATE TABLE FileService.Employee (
	Id int NOT NULL IDENTITY(1,1),
	EmployeeId bigint NOT NULL,
	Name nvarchar(100) NOT NULL,
	Email nvarchar(100) NOT NULL,
	PhoneNo bigint NULL,
    DateOfBirth datetime NULL,
	CreatedBy nvarchar(50) NULL,
	CreatedDate datetime NULL,
	UpdatedBy nvarchar(50) NULL,
	UpdatedDate datetime NULL,
	CONSTRAINT PK_Employee PRIMARY KEY (Id)
);
CREATE INDEX Idx_Employee_EmployeeId ON FileService.Employee (EmployeeId);
CREATE UNIQUE INDEX Unq_Employee ON FileService.Employee (EmployeeId);

-- Create Departments Table
CREATE TABLE FileService.Departments (
	DepartmentId int NOT NULL IDENTITY(1,1),
	DepartmentName nvarchar(100) NOT NULL,
	CreatedBy nvarchar(50) NULL,
	CreatedDate datetime NULL,
	UpdatedBy nvarchar(50) NULL,
	UpdatedDate datetime NULL,
	CONSTRAINT PK_Departments PRIMARY KEY (DepartmentId)
);
CREATE INDEX Idx_Departments_DepartmentName ON FileService.Departments (DepartmentName);
CREATE UNIQUE INDEX Unq_Departments ON FileService.Departments (DepartmentName);

-- Insert Departments Table
INSERT INTO FileService.Departments (DepartmentName, CreatedBy, CreatedDate, UpdatedBy, UpdatedDate) VALUES('Admin', 'System', GETDATE(),'System', GETDATE());
INSERT INTO FileService.Departments (DepartmentName, CreatedBy, CreatedDate, UpdatedBy, UpdatedDate) VALUES('HR', 'System', GETDATE(),'System', GETDATE());
INSERT INTO FileService.Departments (DepartmentName, CreatedBy, CreatedDate, UpdatedBy, UpdatedDate) VALUES('Software', 'System', GETDATE(),'System', GETDATE());
INSERT INTO FileService.Departments (DepartmentName, CreatedBy, CreatedDate, UpdatedBy, UpdatedDate) VALUES('Finance', 'System', GETDATE(),'System', GETDATE());

-- Create EmployeeDepartmentDetails Table
CREATE TABLE FileService.EmployeeDepartmentDetails (
	Id int NOT NULL IDENTITY(1,1),
	EmployeeId bigint NOT NULL,
	DepartmentId int NULL,
	CreatedBy nvarchar(50) NULL,
	CreatedDate datetime NULL,
	UpdatedBy nvarchar(50) NULL,
	UpdatedDate datetime NULL,
	CONSTRAINT PK_EmployeeDepartmentDetails PRIMARY KEY (Id),
    CONSTRAINT FK_EmployeeDepartmentDetails_EmployeeId FOREIGN KEY (EmployeeId) REFERENCES FileService.Employee(EmployeeId),
    CONSTRAINT FK_EmployeeDepartmentDetails_DepartmentId FOREIGN KEY (DepartmentId) REFERENCES FileService.Departments(DepartmentId) 
);
CREATE INDEX Idx_EmployeeDepartmentDetails_EmployeeId ON FileService.EmployeeDepartmentDetails (EmployeeId);
CREATE UNIQUE INDEX Unq_EmployeeDepartmentDetails ON FileService.EmployeeDepartmentDetails (EmployeeId);

-- Create EmployeeSalaryDetails Table
CREATE TABLE FileService.EmployeeSalaryDetails (
	Id int NOT NULL IDENTITY(1,1),
	EmployeeId bigint NOT NULL,
	Salary FLOAT NULL,
	CreatedBy nvarchar(50) NULL,
	CreatedDate datetime NULL,
	UpdatedBy nvarchar(50) NULL,
	UpdatedDate datetime NULL,
	CONSTRAINT PK_EmployeeSalaryDetails PRIMARY KEY (Id),
    CONSTRAINT FK_EmployeeSalaryDetails_EmployeeId FOREIGN KEY (EmployeeId) REFERENCES FileService.Employee(EmployeeId)
);
CREATE INDEX Idx_EmployeeSalaryDetails_EmployeeId ON FileService.EmployeeSalaryDetails (EmployeeId);
CREATE UNIQUE INDEX Unq_EmployeeSalaryDetails ON FileService.EmployeeSalaryDetails (EmployeeId);

-- Create EmployeeLeaves Table
CREATE TABLE FileService.EmployeeLeaves (
	Id int NOT NULL IDENTITY(1,1),
	EmployeeId bigint NOT NULL,
	TotalSickLeaves int NOT NULL,
	UsedSickLeaves int NOT NULL,
    TotalCasualLeaves int NOT NULL,
    UsedCasualLeaves int NOT NULL,
    CreatedBy nvarchar(50) NULL,
	CreatedDate datetime NULL,
	UpdatedBy nvarchar(50) NULL,
	UpdatedDate datetime NULL,
	CONSTRAINT PK_EmployeeLeaves PRIMARY KEY (Id),
    CONSTRAINT FK_EmployeeLeaves_EmployeeId FOREIGN KEY (EmployeeId) REFERENCES FileService.Employee(EmployeeId)
);
CREATE INDEX Idx_EmployeeLeaves_EmployeeId ON FileService.EmployeeLeaves (EmployeeId);
CREATE UNIQUE INDEX Unq_EmployeeLeaves ON FileService.EmployeeLeaves (EmployeeId);

-- Create EmployeeFamilyDetails Table
CREATE TABLE FileService.EmployeeFamilyDetails (
	Id int NOT NULL IDENTITY(1,1),
	EmployeeId bigint NOT NULL,
    MaritalStatus nvarchar(100) NOT NULL,
	FatherName nvarchar(100) NOT NULL,
	MotherName nvarchar(100) NOT NULL,
    SpouseName nvarchar(100) NOT NULL,
    KidsDetails int NULL,
    DaughterName nvarchar(100) NOT NULL,
    SonName nvarchar(100) NOT NULL,
	CreatedBy nvarchar(50) NULL,
	CreatedDate datetime NULL,
	UpdatedBy nvarchar(50) NULL,
	UpdatedDate datetime NULL,
	CONSTRAINT PK_EmployeeFamilyDetails PRIMARY KEY (Id),
    CONSTRAINT FK_EmployeeFamilyDetails_EmployeeId FOREIGN KEY (EmployeeId)  REFERENCES FileService.Employee(EmployeeId)

);
CREATE INDEX Idx_EmployeeFamilyDetails_EmployeeId ON FileService.EmployeeFamilyDetails (EmployeeId);
CREATE UNIQUE INDEX Unq_EmployeeFamilyDetails ON FileService.EmployeeFamilyDetails (EmployeeId);

-- Create File_Processed_Audits Table
CREATE TABLE FileService.FileProcessedAudits (
	Id int NOT NULL IDENTITY(1,1),
	FileName nvarchar(100) NULL,
	FileSize nvarchar(100) NULL,
	Status nvarchar(20) NULL,
	CreatedBy nvarchar(50) NULL,
	CreatedDate datetime NULL,
	UpdatedBy nvarchar(50) NULL,
	UpdatedDate datetime NULL,
	CONSTRAINT PK_FileProcessedAudits PRIMARY KEY (Id)
);