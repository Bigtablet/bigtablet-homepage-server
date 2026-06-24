# Git Convention

## Do Not Commit
- Files in `.gitignore` - Never commit even if modified

---

## Commit Rules
- NEVER commit unless the user explicitly asks you to
- Only commit when the user says "commit", "커밋해", etc.

---

## Commit Message Format
```
label: message
```
**❗️Always write commit messages in English**

---

## Branch Naming Format
```
label/domain
```

---

## Commit Labels

| Label | Description |
|-------|-------------|
| feat | New feature implementation |
| fix | Non-urgent bug fix (typos, minor issues) |
| bug | Critical/urgent bug fix |
| refactor | Internal code restructuring (no external behavior change) |
| merge | Branch merge |
| deploy | Deployment-related changes |
| docs | Documentation updates |
| delete | Code or file deletion |
| note | Comments or annotations |
| style | Code formatting (no logic changes) |
| config | Configuration changes |
| etc | Other changes |
| tada | Project initialization |

### Commit Message Examples
```
feat: add user authentication
fix: correct typo in error message
bug: resolve critical login failure
refactor: extract email setup helper from UseCase
docs: update API documentation
style: format code according to guidelines
config: update database connection settings
```

### Branch Naming Examples
```
feat/auth
fix/user
bug/payment
refactor/calendar-cache
docs/readme
```

---

## Pull Request Guidelines

1. **Title Format**: Use the branch name as the PR title
   ```
   feat/auth
   fix/user
   config/deploy-version-tag
   ```

2. **Merge Commit Message**:
   - `develop → main`: `merge: {version}` (e.g., `merge: release 0.1.12`)
   - Other branches → target: `merge: {branch-name}` (e.g., `merge: fix/review-findings`)

3. **Description Template** (`develop → main` PRs must include the current version in the body):
   ```
   ## 제목
   ## 작업한 내용
   ## 전달할 추가 이슈
   ```

4. **Review Process**:
   - Request review from at least one team member
   - Address all review comments before merging
   - Ensure CI/CD passes before merge

---

## Work Procedure

Follow this procedure for all code modifications:

1. **Create Issue**
   - Assignees: `lgwk42` (always fixed)
   - Label: Match the issue type (`Feature`, `Fix`, `Bug`, etc.)
   - Issue type: Match the issue type
   - Template: Use Task template (`## 작업 개요`, `## TO DO`, `## 전달할 추가 이슈`)

2. **Create Branch**
   - Format: `label/domain` (e.g., `fix/stt-notification`, `feat/auth`)
   - Branch from develop

3. **Implement Changes**
   - Commit per class unit
   - Verify build before committing

4. **Version Bump and Commit**
   - For single-branch work, include version bump (`build.gradle`) in the commit
   - Commit message: `config: bump version to x.x.x`

5. **Push**
   - Push only when the user explicitly instructs (`"push"`, etc.)
   - Never push automatically without instruction

6. **Create PR and Link Issue**
   - Title: Use branch name (e.g., `fix/stt-notification`)
   - Body: Link issue with `Closes #N` for auto-close
   - Assignees: `lgwk42` (always fixed)
   - Label: Match the PR type

---

## Code Review Checklist
- [ ] Code follows project conventions
- [ ] No unnecessary changes included
- [ ] Tests pass (if applicable)
- [ ] Documentation updated (if needed)
- [ ] No security vulnerabilities introduced
