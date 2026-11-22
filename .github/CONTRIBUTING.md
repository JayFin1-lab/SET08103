# Reporting and tracking bugs

## Report a bug
Search existing Issues to check whether the problem has already been reported.

Choose Issues → New issue → Bug report and complete the template.
Include reproduction steps, expected and actual results, environment
details and relevant evidence. The template applies the bug label.

## Track the issue
Add the issue to the team's Kanban board and assign a team member.
Use the board's existing statuses to track progress.

Use the existing labels where appropriate:
- question — more information is needed.
- help wanted — assistance is needed.
- duplicate — the issue has already been reported.
- invalid — investigation shows the report is invalid.
- wontfix — the team has decided against implementing a fix.

Explain the reason when closing an issue without a fix.

## Implement and review a fix
Create a feature or hotfix branch.
Add a regression test where appropriate.

Open a pull request and link the issue using Fixes #<issue-number>.
Include test results and evidence of the fix.
Have the group review the changes before merging.

## Verify the result
Repeat the original reproduction steps against the fixed version.
Record the verification result in the issue and update the Kanban board.

## Code review evidence
After merging the template into the default branch, demonstrate it
using a genuine reproducible issue. Show the issue, its labels,
Kanban progress, linked fix and verification result.
