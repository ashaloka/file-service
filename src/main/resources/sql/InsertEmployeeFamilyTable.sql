MERGE INTO FileService.EmployeeFamilyDetails AS tgt
USING( SELECT :#employeeId as EmployeeId,:#maritalStatus as MaritalStatus,:#fatherName as FatherName,:#motherName as MotherName,
:#spouseName as SpouseName,:#kidsDetails as KidsDetails,:#daughterName as DaughterName,:#sonName as SonName,
:#createdBy as CreatedBy,:#updatedBy as UpdatedBy,GETDATE() as UpdatedDate,GETDATE() as CreatedDate) AS src
ON (tgt.EmployeeId=src.EmployeeId)
WHEN MATCHED
THEN UPDATE SET
tgt.[MaritalStatus]=src.[MaritalStatus],tgt.FatherName=src.FatherName, tgt.MotherName=src.MotherName, tgt.SpouseName=src.SpouseName,
tgt.[KidsDetails]=src.[KidsDetails],tgt.DaughterName=src.DaughterName, tgt.SonName=src.SonName,
tgt.UpdatedBy=src.UpdatedBy, tgt.UpdatedDate=src.UpdatedDate
WHEN NOT MATCHED
THEN INSERT (EmployeeId,MaritalStatus,FatherName,MotherName,SpouseName,KidsDetails,DaughterName,SonName,
CreatedBy,CreatedDate,UpdatedBy,UpdatedDate)
VALUES (src.EmployeeId,src.MaritalStatus,src.FatherName,src.MotherName,src.SpouseName,src.KidsDetails,src.DaughterName,src.SonName,
src.CreatedBy,src.CreatedDate,src.UpdatedBy,src.UpdatedDate);
