# INTRODUCTION

SQL is a programming language used to manage and manipulate relational databases. It consists of various commands to create, retrieve, update, and delete data. Databases store information in tables composed of rows and columns.

# OPERATORS

### Arithmetic operators

Operator | Description |
----|-------------|
+ | Add         |
- | Substract   |
* | Multiply    |
/ | Divide      |
% | Module      |

### Comparison operators

Operator | Description           |
----|-----------------------|
= | Equals                |
<> | Not equals            |
\> | Multiply              |
< | Lesst han             |
<= | Less or equal than    |
\>= | Greater or equal than |

### Compounds operators

Operator | Description      |
----|------------------|
+= | Add equals       |
-= | Substract equals |
*= | Multiply equals  |
/= | Divide equals    |
&= | Module equals    |

### Logical operators

Operator | Description                                                                                                                 |
----|-----------------------------------------------------------------------------------------------------------------------------|
ALL | TRUE if all of the subquery values meet the condition                                                                       |
AND | TRUE if all the conditions separated by AND is TRUE                                                                         |
ANY | TRUE if any of the subquery values meet the condition                                                                       |
BETWEEN | TRUE if the operand is within the range of comparisons                                                                      |
EXISTS | TRUE if the subquery returns one or more records                                                                            |
IN | TRUE if the operand is equal to one of a list of expressions                                                                |
LIKE | TRUE if the operand matches a pattern (% to represent zero, one or more characters and _ to represent one single character) |
NOT | Displays a record if the condition(s) is NOT TRUE                                                                           |
OR | TRUE if any of the conditions separated by OR is TRUE                                                                       |
SOME | TRUE if any of the subquery values meet the condition                                                                       |


# INSTRUCTIONS

## CREATING DATABASES AND TABLES

The CREATE statement in SQL is used to create new database objects, including databases, tables, views, indexes, and more.

### Creating a database

To create a new database, you typically use a specific SQL command provided by your database management system (DBMS).
``` sql
CREATE DATABASE mydb;
```

### Creating a table

Basic syntax:
``` sql
CREATE TABLE table_name (
    column1 datatype,
    column2 datatype,
    ...
);
```

Specifying columns:
``` sql
CREATE TABLE employees (
    id INT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    hire_date DATE
);
```

Setting Constraints. You can set constraints on columns to enforce data integrity:
``` sql
CREATE TABLE employees (
    id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    hire_date DATE
);
```

Adding Primary Key
``` sql
CREATE TABLE employees (
    id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    hire_date DATE
);
```

## INSERTING DATA

### Syntax

``` sql
INSERT INTO table_name (column1, column2, column3, ...)
VALUES (value1, value2, value3, ...);
```

### Examples

Inserting individual record:

``` sql
INSERT INTO employees (id, first_name, last_name, department)
VALUES (1, 'Mary', 'Johnson', 'Finance');
```

Inserting multiple rows:

``` sql
INSERT INTO employees (id, first_name, last_name, department)
VALUES (1, 'Mary', 'Johnson', 'Finance'),
       (2, 'Robert', 'Williams', 'IT');
```

Inserting data from another table:

``` sql
INSERT INTO new_employees (first_name, last_name)
SELECT first_name, last_name
FROM old_employees
WHERE department = 'HR';
```

## QUERYING DATA

### WHERE Clause

``` sql
SELECT * FROM employees;
```

Filter can be enhanced by using SQL comparators:

``` sql
SELECT * FROM employees WHERE salary > 50000 AND age <= 30;
```

``` sql
SELECT * FROM employees WHERE department = 'HR' OR department = 'Finance';
```

### Limiting results

MySQL, PostgreSQL
``` sql
SELECT * FROM employees LIMIT 10;
```

SQL Server, Oracle
``` sql
SELECT * FROM employees FETCH FIRST 10 ROWS ONLY;
```

## UPDATING DATA

``` sql
UPDATE employees SET department = 'Finance' WHERE id = 1;
```

## DELETING DATA

``` sql
DELETE FROM employees WHERE id = 1;
```

## SORTING DATA

``` sql
SELECT * FROM employees ORDER BY last_name ASC;
```

## JOINING TABLES

### Inner join

The 'Inner join' retrieves rows from both tables that satisfy the specified condition.
``` sql
SELECT employees.first_name, departments.name
FROM employees
INNER JOIN departments ON employees.department_id = departments.id;
```

### Left join (outer join)

The 'Left join' retrieves all rows from the left table and matching rows from the right table. If no match is found in the right table, NULL values are returned.
``` sql
SELECT customers.customer_id, orders.order_id
FROM customers
LEFT JOIN orders ON customers.customer_id = orders.customer_id;
```

### Right join (outer join)

The 'Right join' is similar to the Left join, but it retrieves all rows from the right table and matching rows from the left table. If no match is found in the left table, NULL values are returned.
``` sql
SELECT customers.customer_id, orders.order_id
FROM customers
RIGHT JOIN orders ON customers.customer_id = orders.customer_id;
```

### Full join

The 'Full join' retrieves all rows from both tables, including unmatched rows from both sides.
``` sql
SELECT employees.first_name, departments.name
FROM employees
FULL JOIN departments ON employees.department_id = departments.id;
```

### Self join

A 'Self join' is used to combine rows from a single table. It's useful when you want to compare rows within the same table.
``` sql
SELECT e1.employee_name, e2.supervisor_name
FROM employees e1
INNER JOIN employees e2 ON e1.supervisor_id = e2.employee_id;
```

### Using Aliases with Joins

Aliases can be used to simplify table references and improve query readability.
``` sql
SELECT c.customer_id, o.order_id
FROM customers AS c
INNER JOIN orders AS o ON c.customer_id = o.customer_id;
```

## GROUPING AND AGGREGATION

### GROUP BY clause
``` sql
SELECT department, COUNT(*) as employee_count
FROM employees
GROUP BY department;
```

### Aggregate Functions (SUM, AVG, COUNT, MIN, MAX)
``` sql
SELECT department, AVG(salary) as avg_salary, MAX(age) as max_age
FROM employees
GROUP BY department;
```

## SUBQUERIES

### In WHERE clause
``` sql
SELECT first_name, last_name
FROM employees
WHERE department_id IN (SELECT id FROM departments WHERE name = 'HR');

```

### In SELECT clause
``` sql
SELECT first_name, last_name,
       (SELECT name FROM departments WHERE id = employees.department_id) AS department
FROM employees;

```

## VIEWS

``` sql
CREATE VIEW hr_employees AS
SELECT * FROM employees WHERE department = 'HR';
```

## TRANSACTIONS

``` sql
BEGIN;
UPDATE employees SET department = 'IT' WHERE id = 2;
UPDATE employees SET department = 'Management' WHERE id = 3;
COMMIT;
```

## INDEXES

### Introduction
Indexes are database structures that enhance query performance by providing quick access to rows in a table. They work similarly to the index of a book, allowing the database engine to locate data more efficiently. Properly designed indexes can significantly speed up data retrieval operations.

### How indexes work
Indexes store a sorted copy of selected columns' data, along with pointers to the corresponding rows. When a query is executed, the database engine uses the index to find relevant rows quickly.

### Type of indexes
- B-Tree indexes are the most common type. They're suitable for equality and range-based searches. 
- Bitmap indexes are used for columns with a small set of distinct values. They use a bitmap to indicate whether a value exists. 
- Hash indexes work well for equality searches, but not for range queries. They use a hash function to map values to an index. 
- FTS indexes are used for searching text within columns containing large amounts of text data.

### Creating indexes
Syntax:
``` sql
CREATE INDEX index_name ON table_name (column1, column2, ...);
```

Example:
``` sql
CREATE INDEX idx_last_name ON employees(last_name);
```

### When to use indexes
- Use indexes for columns frequently used in WHERE, JOIN, and ORDER BY clauses.
- Index columns with high selectivity (few distinct values).
- Avoid over-indexing, as it can impact write operations.
- Evaluate queries and analyze execution plans to identify potential index candidates.

Indexes speed up read operations but may slow down write operations, as indexes need to be updated when data changes. Regularly monitor index usage, and periodically rebuild or reorganize indexes to maintain optimal performance.

## BUILT-IN FUNCTIONS

### String functions
- Concat: concatenates two or more strings.
``` sql
SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM employees;
```
- Length: returns the length of a string.
``` sql
SELECT first_name, LENGTH(first_name) AS name_length FROM employees;
```
- Substring: extracts a substring from a string.
``` sql
SELECT SUBSTRING(product_name, 1, 5) AS short_name FROM products;
```
- Upper and Lower: converts a string to uppercase or lowercase.
``` sql
SELECT UPPER(last_name) AS last_name_upper, LOWER(first_name) AS first_name_lower
FROM employees;
```
- Replace: replaces occurrences of a substring in a string.
``` sql
SELECT REPLACE(email, '@', '[at]') AS masked_email FROM contacts;
```

### Numeric Functions
- Sum: calculates the sum of numeric values in a column.
``` sql
SELECT SUM(salary) AS total_salary FROM employees;
```
- Avg: calculates the average of numeric values in a column.
``` sql
SELECT AVG(age) AS average_age FROM employees;
```
- Min and max: finds the minimum and maximum values in a column.
``` sql
SELECT MIN(salary) AS lowest_salary, MAX(salary) AS highest_salary FROM employees;
```
- Round: rounds a numeric value to a specified number of decimal places.
``` sql
SELECT product_price, ROUND(product_price, 2) AS rounded_price FROM products;
```
- Abs: returns the absolute value of a numeric expression.
``` sql
SELECT salary, ABS(salary) AS absolute_salary FROM employees;
```

### Date and Time Functions
- Current_date and current_time: retrieve the current date and time.
``` sql
SELECT CURRENT_DATE AS today, CURRENT_TIME AS current_time;
```
- Dateadd and datediff: Add or subtract intervals from dates.
``` sql
SELECT DATEADD(DAY, 7, order_date) AS future_date,
       DATEDIFF(YEAR, birthdate, CURRENT_DATE) AS age_years
FROM orders;
```
- Extract: extracts parts of a date, such as year, month, or day.
``` sql
SELECT EXTRACT(YEAR FROM hire_date) AS hire_year FROM employees;
```
- Date_format (MySQL) and to_char (PostgreSQL): Formats dates into custom representations.
``` sql
-- MySQL
SELECT DATE_FORMAT(order_date, '%Y-%m-%d') AS formatted_date FROM orders;
-- PostgreSQL
SELECT TO_CHAR(order_date, 'YYYY-MM-DD') AS formatted_date FROM orders;
```

### Conditional functions
- Case: provides conditional logic within queries.
``` sql
SELECT first_name, last_name,
       CASE WHEN age < 30 THEN 'Young' ELSE 'Adult' END AS age_group
FROM employees;
```
- Coalesce: returns the first non-null value in a list.
``` sql
SELECT COALESCE(preferred_name, first_name) AS display_name FROM employees;
```
- Nullif: returns null if two expressions are equal, otherwise returns the first expression.
``` sql
SELECT NULLIF(salary, 0) AS valid_salary FROM employees;
```
- Date_format (MySQL) and to_char (PostgreSQL): Formats dates into custom representations.
``` sql
-- MySQL
SELECT DATE_FORMAT(order_date, '%Y-%m-%d') AS formatted_date FROM orders;
-- PostgreSQL
SELECT TO_CHAR(order_date, 'YYYY-MM-DD') AS formatted_date FROM orders;
```