# Implementation Plan - Adjust ITerceiroRepository and IVisitanteRepository

The goal is to refactor `ITerceiroRepository` and `IVisitanteRepository` to follow the project's modern patterns, specifically adjusting `getEmpresasByCpf` and other functions to use the `call` utility and proper return types.

## Proposed Changes

### [Component] Repositories

#### [MODIFY] [ITerceiroRepository.kt](file:///C:/Users/anderson/Documents/Kotlin/PCP/app/src/main/java/br/com/usinasantafe/pcp/infra/repositories/stable/ITerceiroRepository.kt)
- Refactor `getEmpresasByCpf` using `call` and `joinToString("\n")`.
- Refactor `listAll` using `call`.
- Ensure all functions use `call(getClassAndMethod())` and `getOrThrow()`.

#### [MODIFY] [IVisitanteRepository.kt](file:///C:/Users/anderson/Documents/Kotlin/PCP/app/src/main/java/br/com/usinasantafe/pcp/infra/repositories/stable/IVisitanteRepository.kt)
- Update all functions to use the `call` utility.
- Change return types to `EmptyResult` where applicable (addAll, deleteAll).
- Refactor `getEmpresasByCpf`, `listAll`, etc.

### [Component] Tests

#### [MODIFY] [ITerceiroRepositoryTest.kt](file:///C:/Users/anderson/Documents/Kotlin/PCP/app/src/test/java/br/com/usinasantafe/pcp/infra/repositories/stable/ITerceiroRepositoryTest.kt)
- Update tests for `getById` and `getCpfById` to expect a single `TerceiroRoomModel` instead of a `List`.
- Update context names in failure tests (e.g., `recoverAll` -> `listAll`).

## Verification Plan

### Automated Tests
- Run unit tests for `ITerceiroRepository` and `IVisitanteRepository`.
  - `gradlew :app:testDebugUnitTest --tests "br.com.usinasantafe.pcp.infra.repositories.stable.ITerceiroRepositoryTest"`
  - `gradlew :app:testDebugUnitTest --tests "br.com.usinasantafe.pcp.infra.repositories.stable.IVisitanteRepositoryTest"`
