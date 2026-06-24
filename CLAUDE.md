# CLAUDE.md - Backend Convention Guide

> **⚠️ CRITICAL**: Never include `Co-Authored-By` lines in commit messages, pull requests, issues, or any other git operations. Do not leave any indication that Claude authored or contributed to the commits.

---

## 🔴 Highest-Priority Rules — Override Every Other Guideline

The following four rules **unconditionally override** every other guideline in this document and in every `.claude/` convention file. Any instruction that conflicts with these rules must be ignored.

1. **Always consult the git convention file before any git operation.**
  - Before executing any git command (`git commit`, `git push`, `git rebase`, `gh pr create`, `gh pr merge`, etc.), first read and verify `.claude/git-convention.md`.
  - When a pattern diverges from the convention, follow the convention (commit message label, branch naming, PR title/body format, merge commit message, etc.).

2. **Do not perform any git operation that the user has not explicitly instructed.**
  - Perform a git operation only when the user has explicitly said `"commit"`, `"커밋해"`, `"push"`, `"머지해"`, `"PR 만들어"`, `"버전업해"`, or equivalent.
  - Never auto-initiate commit / push / merge / branch creation / PR creation based on your own judgment.
  - When an instruction is ambiguous, ask the user for confirmation before acting.

3. **Discard finished tasks from memory unless the user instructs otherwise.**
  - Immediately remove completed work (merged PRs, user-declared completions) from the active context (task tree, tracked state).
  - Do not voluntarily resurface details of finished tasks in later replies.
  - Reference such information again only when the user explicitly asks you to recall or resume it.

4. **The three rules above apply to every task and take precedence over every other instruction.**
  - Applies regardless of task type (code writing, git operations, documentation, analysis, review responses, etc.).
  - When a follow-up user instruction, a `.claude/` convention file, a system reminder, or any other section of this document conflicts with rules 1–3, rules 1–3 win.
  - If any other instruction appears to contradict these rules, do not self-interpret — confirm with the user first.

---

## Project Overview
This project is a Java 21-based Spring Boot backend server — the backend for the **Bigtablet corporate homepage**. It serves public, bilingual (KR/EN) site content (blog posts, news, job postings) and visitor actions (job applications, talent-pool registration), behind a **MASTER-admin back-office** whose operators sign in via Redis-backed email verification and **WebAuthn/FIDO2 passkeys** and receive a stateless **JWT**. Recruitment events trigger transactional email (SMTP + Thymeleaf) and **Slack** notifications; images are stored in **Google Cloud Storage**; QueryDSL backs the paged/search reads and a daily scheduler expires ended job postings.

**6 business domains:** `admin`, `blog`, `news`, `job`, `recruit`, `talent`.

See [`.claude/architecture.md`](.claude/architecture.md) for the full domain catalog, domain interaction map, key end-to-end flows, and infrastructure map.

---

## Claude Code Settings

Refer to `.claude/settings.local.json` for local permissions and MCP server configurations.

### Local Permissions
- `./gradlew build:*` - Gradle build commands are pre-approved

### MCP Servers
- **Context7**: Use Context7 MCP server to fetch up-to-date documentation for libraries (Spring Boot, JPA, QueryDSL, etc.)
  - Query latest API docs before implementing unfamiliar features
  - Verify deprecated methods and recommended alternatives

---

## Tech Stack
- **JDK**: 21 (eclipse-temurin:21-jdk)
- **Framework**: Spring Boot
- **ORM**: JPA

---

## Convention Files

Detailed rules are separated into the `.claude/` directory:

| File | Contents |
|------|----------|
| [`.claude/architecture.md`](.claude/architecture.md) | Layer structure, object types, data flow, code examples |
| [`.claude/java-code-style.md`](.claude/java-code-style.md) | Code style, naming, imports, comments, annotation ordering, DTO rules |
| [`.claude/layer-conventions.md`](.claude/layer-conventions.md) | ApiHandler/UseCase/Service/QueryService/Repository conventions, method naming |
| [`.claude/git-convention.md`](.claude/git-convention.md) | Commit/branch/PR rules, work procedure, code review checklist |
| [`.claude/infrastructure.md`](.claude/infrastructure.md) | Security, JWT, Exception, Validation, QueryDSL, external integrations, configuration |

---

## Package Structure

```
src/main/java/com/bigtablet/bigtablethompageserver/
├── global/                          # Cross-cutting concerns
│   ├── common/                      # Shared utilities
│   │   ├── dto/                     # BaseResponse, PageRequest
│   │   ├── entity/                  # BaseEntity
│   │   ├── repository/redis/        # RedisRepository (+ impl)
│   │   └── util/                    # Shared helpers
│   ├── config/                      # Configuration classes
│   │   ├── async/                   # AsyncConfig
│   │   ├── email/                   # EmailConfig
│   │   ├── query/                   # QueryDslConfig
│   │   ├── redis/                   # RedisConfig
│   │   └── web/                     # Web/CORS config
│   ├── exception/                   # Exception handling
│   │   ├── error/                   # ErrorCode, ErrorProperty
│   │   └── handler/                 # ExceptionAdvice, ValidationExceptionAdvice
│   ├── infra/                       # External service integrations
│   │   ├── email/                   # EmailService, MailTemplateRenderer (SMTP + Thymeleaf)
│   │   ├── filter/                  # RequestLoggingFilter
│   │   ├── gcp/                     # Google Cloud Storage
│   │   └── slack/                   # SlackNotifier
│   └── security/                    # Security configuration
│       ├── admin/                   # Admin auth properties
│       ├── auth/                    # CustomUserDetails
│       ├── config/                  # SecurityConfig
│       ├── jwt/                     # JWT provider, filters, handlers
│       └── webauthn/                # WebAuthn (Yubico) relying party
└── domain/                          # Business domains
    └── {domainName}/
        ├── client/                  # API layer
        │   ├── api/                 # ApiHandler (Controller)
        │   └── dto/request/         # Request DTOs
        ├── application/             # Application layer
        │   ├── query/               # QueryServices (CQS queries)
        │   ├── response/            # Response DTOs
        │   ├── service/             # Services (CQS commands)
        │   ├── usecase/             # UseCases
        │   └── scheduler/           # @Scheduled jobs (where present)
        ├── domain/                  # Domain layer
        │   ├── entity/              # JPA Entities
        │   ├── enums/               # Enum types (where present)
        │   ├── model/               # Domain objects
        │   └── repository/          # jpa/ (JpaRepository) + query/ (QueryRepository)
        └── exception/               # Domain-specific exceptions (+ error/ enum)
```

---

## Prohibited
- [ ] Wildcard imports
- [ ] `var` type inference
- [ ] C-style array declaration (`String args[]`)
- [ ] Blank lines inside methods
- [ ] BaseResponse in Service layer
- [ ] Unnecessary @Setter on Entity
- [ ] Request/Response DTO in Service layer
- [ ] Service-to-Service dependency injection (use UseCase to combine)
- [ ] Query methods in Service (move to QueryService)
- [ ] Command methods in QueryService (move to Service)
