insert into ProgPerf.ProviderGroupAssociation (
ProviderId,
ProviderGroupId,
IsAssociated,
ExpirationDate,
ValidationStatus,
ValidatedDate,
CreatedBy,
UpdatedBy,
UpdatedDate,
CreatedDate
)values (:#providerId,:#providerGroupId,:#associationStatus,:#expirationDate,:#validationStatus,:#validatedDate,:#createdBy,:#updatedBy,getDate(),getDate())