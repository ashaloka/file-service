INSERT INTO FileService.FileProcessedAudits (FileName,Status,FileSize,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate)
VALUES (:#fileName,:#status,:#fileSize,'System', GETDATE(), 'System', GETDATE());
