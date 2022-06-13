MERGE INTO FileService.EmployeeLeaves AS tgt
USING( SELECT :#employeeId as EmployeeId,:#totalSickLeaves as TotalSickLeaves,:#usedSickLeaves as UsedSickLeaves,
:#totalCasualLeaves as TotalCasualLeaves,:#usedCasualLeaves as UsedCasualLeaves,
:#createdBy as CreatedBy,:#updatedBy as UpdatedBy,GETDATE() as UpdatedDate,GETDATE() as CreatedDate) AS src
ON (tgt.EmployeeId=src.EmployeeId)
WHEN MATCHED
THEN UPDATE SET
tgt.TotalSickLeaves=src.TotalSickLeaves,tgt.UsedSickLeaves=src.UsedSickLeaves, tgt.TotalCasualLeaves=src.TotalCasualLeaves,
tgt.UsedCasualLeaves=src.UsedCasualLeaves,
tgt.UpdatedBy=src.UpdatedBy, tgt.UpdatedDate=src.UpdatedDate
WHEN NOT MATCHED
THEN INSERT (EmployeeId,TotalSickLeaves,UsedSickLeaves,TotalCasualLeaves,UsedCasualLeaves,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate)
VALUES (src.EmployeeId,src.TotalSickLeaves,src.UsedSickLeaves,src.TotalCasualLeaves,src.UsedCasualLeaves,src.CreatedBy,src.CreatedDate,src.UpdatedBy,src.UpdatedDate);
