Project: GameZoneUnicesar10
Developer: Samuel David Rangel Martinez
Role: Systems Engineering Student & Backend Developer - Data Persistence, Directory Structure, and Git Flow
Assigned Branch: develop / feature/ai-usage-docs
File Location: docs/ai-usage/registro_ia.md

📑 Module Responsibilities Summary
- Project structure setup, separating execution data (`data/`) from project documentation (`docs/`).
- Implementation and formatting of preloaded test data (`persons.csv` with 2 customers and 3 sellers).
- Validation of persistence compatibility with `PersonRepository.java`.
- Implementation of strict Git Flow practices utilizing `feature/*` branches and atomic commits.
- Automation of file generation and repository tasks via PowerShell scripts.

📌 Detailed AI Interaction Records

Session 1: Structuring Root Data Directory vs. Documentation
- Date: 09/15/2026
- Prompt / Query Asked to AI:
  "¿Dónde debo ubicar la carpeta de datos persons.csv para que sea compatible con la capa de persistencia en Java sin romper la estructura de documentación?"
- Context & Problem: Ensuring the Java persistence layer (`PersonRepository.java`) could read the CSV file correctly without altering standard directory expectations, while keeping documentation clean inside `docs/`.
- AI Response Summary: Recommended keeping the active `data/` folder in the project root for direct runtime compatibility, while isolating markdown notes, requirements, and logs inside `docs/`.
- Developer Action & Validation: Configured the root `data/` directory and structured the `docs/` hierarchy accordingly, verifying successful file loading.

Session 2: Formatting Preloaded Data for CSV Persistence
- Date: 09/15/2026
- Prompt / Query Asked to AI:
  "¿Cómo debo estructurar los registros en persons.csv para incluir correctamente 2 clientes y 3 vendedores con sus respectivos atributos?"
- Context & Problem: The application needed initial test records (CUSTOMER and SELLER entities with specific fields like phones, emails, employee codes, and shifts) to populate the console view upon startup.
- AI Response Summary: Provided the exact comma-separated values layout matching the parsing logic expected by the persistence layer (`CLI-001`, `EMP-001`, etc.).
- Developer Action & Validation: Generated and validated the `persons.csv` file content using PowerShell commands, confirming that the console lists all 5 records accurately.

Session 3: Establishing Strict Git Flow and PowerShell Automation
- Date: 09/15/2026
- Prompt / Query Asked to AI:
  "¿Cómo ejecuto la creación de carpetas, archivos y la gestión de ramas en Git paso a paso usando PowerShell sin hacer commits directos a develop?"
- Context & Problem: Avoiding direct changes on core branches and ensuring clean version control workflow through feature branches.
- AI Response Summary: Outlined the sequence for switching to `develop`, creating `feature/ai-usage-docs`, using `New-Item` and `Set-Content` for file creation, and executing clean `git add`, `commit`, `push`, and `merge` sequences.
- Developer Action & Validation: Executed the PowerShell script successfully, maintaining atomic commits and integrating the documentation cleanly into `develop`.

🛡️ Ethics & Compliance Declaration
1. Self-Comprehension: All directory configurations, data formats, and architectural decisions were reviewed, tested, and validated manually in Apache NetBeans and the local terminal.
2. Academic Integrity: AI was used exclusively as a reference tool for structural layout alignment, CSV data formatting, and Git workflow optimization, fulfilling academic integrity standards.
'