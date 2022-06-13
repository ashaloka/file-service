MERGE INTO FileService.Employee AS tgt
USING( SELECT :#employeeId as EmployeeId,:#name as Name,:#email as Email,:#phoneNo as PhoneNo,:#dateOfBirth as DateOfBirth,
:#createdBy as CreatedBy,:#updatedBy as UpdatedBy,GETDATE() as UpdatedDate,GETDATE() as CreatedDate) AS src
ON (tgt.EmployeeId=src.EmployeeId)
WHEN MATCHED
THEN UPDATE SET
tgt.[Name]=src.[Name],tgt.Email=src.Email, tgt.PhoneNo=src.PhoneNo, tgt.DateOfBirth=src.DateOfBirth,
tgt.UpdatedBy=src.UpdatedBy, tgt.UpdatedDate=src.UpdatedDate
WHEN NOT MATCHED
THEN INSERT (EmployeeId,[Name],Email,PhoneNo,DateOfBirth,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate)
VALUES (src.EmployeeId,src.Name,src.Email,src.PhoneNo,src.DateOfBirth,src.CreatedBy,src.CreatedDate,src.UpdatedBy,src.UpdatedDate);
