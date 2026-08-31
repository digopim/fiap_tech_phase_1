# Code analysis
## 'oficina' 
#### Branch main
#### Version 0.0.1-SNAPSHOT 

**By: Diego**

*Date: 2026-08-31*

*Analyzed the: 2026-08-30*

## Introduction
This document contains results of the code analysis of 'oficina'

Parent pom providing dependency and plugin management for applications built with Maven

## Configuration

- Quality Profiles
    - Names: Sonar way [Java]; Sonar way [XML]; 
    - Files: bb227b19-65b6-485d-a163-263bffe5b9f1.json; 3d2be9d1-3aa4-41b4-82d3-94c871320560.json; 


 - Quality Gate
    - Name: Sonar way
    - File: Sonar way.xml

## Synthesis

### Analysis Status

Reliability | Security | Security Review | Maintainability |
:---:|:---:|:---:|:---:
A | D | A | A |

### Quality gate status

| Quality Gate Status | OK |
|-|-|



### Metrics

Coverage | Duplications | Comment density | Median number of lines of code per file | Adherence to coding standard |
:---:|:---:|:---:|:---:|:---:
0.0 % | 0.0 % | 0.6 % | 23.0 | 99.9 %

### Tests

Total | Success Rate | Skipped | Errors | Failures |
:---:|:---:|:---:|:---:|:---:
16 | 100.0 % | 0 | 0 | 0

### Detailed technical debt

Reliability|Security|Maintainability|Total
---|---|---|---
-|0d 0h 5min|0d 0h 40min|0d 0h 45min


### Metrics Range

\ | Cyclomatic Complexity | Cognitive Complexity | Lines of code per file | Coverage | Comment density (%) | Duplication (%)
:---|:---:|:---:|:---:|:---:|:---:|:---:
Min | 0.0 | 0.0 | 2.0 | 0.0 | 0.0 | 0.0
Max | 253.0 | 93.0 | 2090.0 | 0.0 | 9.1 | 0.0

### Volume

Language|Number
---|---
Java|2090
XML|208
Total|2298


## Issues

### Issues count by severity and types

Type / Severity|INFO|MINOR|MAJOR|CRITICAL|BLOCKER
---|---|---|---|---|---
BUG|0|0|0|0|0
VULNERABILITY|0|0|0|1|0
CODE_SMELL|1|1|1|0|0


### Issues List

Name|Description|Type|Severity|Number
---|---|---|---|---
"java.time" classes should be used for dates and times||CODE_SMELL|INFO|1
Generic exceptions should never be thrown||CODE_SMELL|MAJOR|1
Exceptions in "throws" clauses should not be superfluous||CODE_SMELL|MINOR|1
CSRF protections should not be disabled||VULNERABILITY|CRITICAL|1


## Security Hotspots

### Security hotspots count by category and priority

Category / Priority|LOW|MEDIUM|HIGH
---|---|---|---
LDAP Injection|0|0|0
Object Injection|0|0|0
Server-Side Request Forgery (SSRF)|0|0|0
XML External Entity (XXE)|0|0|0
Insecure Configuration|0|0|0
XPath Injection|0|0|0
Authentication|0|0|0
Weak Cryptography|0|0|0
Denial of Service (DoS)|0|0|0
Log Injection|0|0|0
Cross-Site Request Forgery (CSRF)|0|0|0
Open Redirect|0|0|0
Permission|0|0|0
SQL Injection|0|0|0
Encryption of Sensitive Data|0|0|0
Traceability|0|0|0
Buffer Overflow|0|0|0
File Manipulation|0|0|0
Code Injection (RCE)|0|0|0
Cross-Site Scripting (XSS)|0|0|0
Command Injection|0|0|0
Path Traversal Injection|0|0|0
HTTP Response Splitting|0|0|0
Others|0|0|0


### Security hotspots


