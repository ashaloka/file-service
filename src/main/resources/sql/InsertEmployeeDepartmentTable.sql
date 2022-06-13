MERGE INTO FileService.EmployeeDepartmentDetails AS tgt
USING( SELECT :#employeeId as EmployeeId,:#departmentName as DepartmentName,
:#createdBy as CreatedBy,:#updatedBy as UpdatedBy,GETDATE() as UpdatedDate,GETDATE() as CreatedDate) AS src
ON (tgt.EmployeeId=src.EmployeeId)
WHEN MATCHED
THEN UPDATE SET
tgt.[DepartmentId]=(SELECT DepartmentId FROM FileService.Departments WHERE DepartmentName = src.DepartmentName),
tgt.UpdatedBy=src.UpdatedBy, tgt.UpdatedDate=src.UpdatedDate
WHEN NOT MATCHED
THEN INSERT (EmployeeId,DepartmentId,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate)
VALUES (src.EmployeeId,(SELECT DepartmentId FROM FileService.Departments WHERE DepartmentName = src.DepartmentName),
src.CreatedBy,src.CreatedDate,src.UpdatedBy,src.UpdatedDate);
