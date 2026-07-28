# Walkthrough - Adjustments to ITerceiroRepository and IVisitanteRepository

Refactored `ITerceiroRepository` and `IVisitanteRepository` to align with the project's current architecture, improving code consistency and readability.

## Changes

### Repositories

#### [ITerceiroRepository.kt](file:///C:/Users/anderson/Documents/Kotlin/PCP/app/src/main/java/br/com/usinasantafe/pcp/infra/repositories/stable/ITerceiroRepository.kt)
- **Refactored `getEmpresasByCpf`**: Now uses the `call` helper and `joinToString("\n")` for a much cleaner implementation.
- **Modernized all functions**: Switched to the `call(getClassAndMethod())` pattern and removed redundant `try-catch` blocks and manual `Result` mapping.

#### [IVisitanteRepository.kt](file:///C:/Users/anderson/Documents/Kotlin/PCP/app/src/main/java/br/com/usinasantafe/pcp/infra/repositories/stable/IVisitanteRepository.kt)
- **Standardized with the `call` helper**: Updated all repository functions to use the shared `call` utility.
- **Improved Return Types**: Changed `addAll` and `deleteAll` to return `EmptyResult` (Result<Boolean>), matching the project's standard for write operations.

### Tests

#### [ITerceiroRepositoryTest.kt](file:///C:/Users/anderson/Documents/Kotlin/PCP/app/src/test/java/br/com/usinasantafe/pcp/infra/repositories/stable/ITerceiroRepositoryTest.kt)
- Updated tests to match the new repository structure, including correctly mocking the `TerceiroRoomDatasource.getById` function (which now returns a single model) and updating failure context names.

#### [IVisitanteRepositoryTest.kt](file:///C:/Users/anderson/Documents/Kotlin/PCP/app/src/test/java/br/com/usinasantafe/pcp/infra/repositories/stable/IVisitanteRepositoryTest.kt)
- Standardized test context names (e.g., `listAll` instead of `recoverAll`) and ensured compatibility with the refactored repository functions.

## Verification Results

### Automated Tests
- Refactored tests were updated to ensure logic remains sound after modernization.
- Unit tests cover the core flows for both repositories.
