# Implementation Plan - Fix and Clean IEquipRepository

The goal is to fix `getIdByNro` in `IEquipRepository` to throw an exception when the ID is 0, and clean up other functions in the same class that are using inconsistent patterns or contain compilation errors.

## User Review Required

> [!IMPORTANT]
> Some functions in `IEquipRepository` currently have compilation errors (invalid `return` statements inside `call` blocks). I will fix these by properly using `getOrThrow()` and throwing exceptions where necessary.

## Proposed Changes

### [MODIFY] [IEquipRepository.kt](file:///C:/Users/anderson/Documents/Kotlin/PCP/app/src/main/java/br/com/usinasantafe/pcp/infra/repositories/stable/IEquipRepository.kt)

- **getIdByNro**: Throw an exception if the returned ID is 0.
- **getNroById**: Fix invalid `return` statements and simplify using `getOrThrow()` and throwing if nro is 0L.
- **getDescrById**: Refactor to use the `call` utility for consistency.
- **listAll**: Refactor to use the `call` utility for consistency.
- **General**: Remove commented-out code blocks.

## Verification Plan

### Automated Tests
- Run unit tests for `IEquipRepository` to ensure all functions behave correctly.
  - `gradlew :app:testDebugUnitTest --tests "br.com.usinasantafe.pcp.infra.repositories.stable.IEquipRepositoryTest"`
