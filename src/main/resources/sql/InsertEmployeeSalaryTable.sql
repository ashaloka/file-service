MERGE INTO FileService.EmployeeSalaryDetails AS tgt
USING( SELECT :#employeeId as EmployeeId,:#salary as Salary,
:#createdBy as CreatedBy,:#updatedBy as UpdatedBy,GETDATE() as UpdatedDate,GETDATE() as CreatedDate) AS src
ON (tgt.EmployeeId=src.EmployeeId)
WHEN MATCHED
THEN UPDATE SET
tgt.Salary=src.Salary,
tgt.UpdatedBy=src.UpdatedBy, tgt.UpdatedDate=src.UpdatedDate
WHEN NOT MATCHED
THEN INSERT (EmployeeId,Salary,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate)
VALUES (src.EmployeeId,src.Salary,src.CreatedBy,src.CreatedDate,src.UpdatedBy,src.UpdatedDate);
