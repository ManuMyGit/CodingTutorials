# INTRODUCTION
A relational database is a structured collection of data organized into tables, where each table represents a logical entity and each row in the table represents a record. Relational databases are managed using a Database Management System (DBMS), which ensures data integrity, security, and efficient data manipulation.

# KEY CONCEPTS

## TRANSACTION
A relational database transaction is a sequence of one or more SQL operations (queries or updates) that are executed as a single unit of work. Transactions ensure the consistency, integrity, and reliability of the data within a relational database by adhering to the ACID (Atomicity, Consistency, Isolation, Durability) properties.

## TABLES, ROWS AND COLUMNS
Tables are the fundamental components of a relational database. Each table consists of rows (also known as records or tuples) and columns (also known as attributes). Columns define the type of data that can be stored, such as numbers, strings, dates, etc.

## PRIMARY AND FOREIGN KEYS
A primary key (aka PK) uniquely identifies each row in a table. It ensures data integrity and provides a way to establish relationships between tables. Foreign keys (aka FK) are used to link tables together, creating associations between related data.

## RELATIONSHIPS
Relationships define how tables are connected to each other. Common relationship types include one-to-one, one-to-many, and many-to-many. Relationships are established using primary and foreign keys.

### One-to-One Relationship
- A one-to-one relationship exists between two tables when a single record in one table is associated with at most one record in another table.
- Consider a database for employees and their contact information. Each employee can have only one corresponding contact record, and each contact record is associated with a single employee.

### One-to-Many Relationship
- A one-to-many relationship exists between two tables when a single record in one table is associated with multiple records in another table.
- In a bookstore database, each author can write multiple books. The relationship between authors and books is a one-to-many relationship, as each author can be associated with many books, but each book is written by a single author.

### Many-to-many Relationship
- A many-to-many relationship exists between two tables when multiple records in one table are associated with multiple records in another table.
- Consider a music library database. Each song can be associated with multiple genres, and each genre can have multiple songs. The relationship between songs and genres is a many-to-many relationship.

### Self-reference Relationship
- A self-referencing relationship occurs when a table relates to itself. This is useful when modeling hierarchical or recursive data structures.
- In an organization, you might have an employee table where each employee reports to another employee, who is also represented in the same table. This creates a self-referencing relationship, as each employee relates to another employee within the same table.

### Implementing relationships
The way to implement relationships is by using PK and FK.
- One-to-One Relationship:
  - Example: consider a database for employees and their contact information. Each employee can have only one corresponding contact record, and each contact record is associated with a single employee.
  - We just need to add the employee PK to the contact information table as a FK.
- One-to-Many Relationship:
  - Example: in a bookstore database, each author can write multiple books. The relationship between authors and books is a one-to-many relationship, as each author can be associated with many books, but each book is written by a single author.
  - We just need to add the author PK to the book table as a FK. It is the same design as the one-to-one relationship, but in this case there will be multiple books with the same FK to the same author.
- Many-to-Many Relationship:
  - Example: consider a music library database. Each song can be associated with multiple genres, and each genre can have multiple songs. The relationship between songs and genres is a many-to-many relationship.
  - We need to create an intermediary table to implement this relationship.
    - The intermediary table can have its own ID or the ID can consist of the aggregation of the song ID and the genre ID.
    - In the intermediary table, the song ID will be a FK to the song table, and the genre ID will be an FK to the genre table.
- Self-reference Relationship:
  - Example: in an organization, you might have an employee table where each employee reports to another employee, who is also represented in the same table. This creates a self-referencing relationship, as each employee relates to another employee within the same table.
  - If we just need to know who that employee reports to, we just need to add a new column to the table with the ID of the employee's boss, which would be the FK to the boss.

## ACID
### Introduction
ACID is a set of properties that guarantee the correct and reliable processing of transactions in a relational database. These properties ensure that data remains accurate and consistent even in the presence of failures or concurrent access.

### Definition

#### Atomicity
- Atomicity ensures that a transaction is treated as a single, indivisible unit of work. It guarantees that either all the changes made within a transaction are committed, or none of them are. This property prevents partial updates to the database, which could lead to inconsistencies.
- Suppose a bank transfer involves deducting money from one account and crediting it to another. Without atomicity, if a failure occurs after deducting money from one account but before crediting it to the other, the database would be left in an inconsistent state.
- With atomicity, the entire transaction is treated as a single unit. If any part of the transaction fails, the entire transaction is rolled back, ensuring that the database remains consistent.

#### Consistency
- Consistency ensures that a transaction brings the database from one valid state to another. It enforces integrity constraints and business rules, preventing data corruption or invalid states.
- Consider a case where a booking system should not allow more bookings than the available seats. If a transaction attempts to book more seats than available, the system should reject the entire transaction to maintain database consistency.

#### Isolation
- Isolation ensures that concurrent transactions do not interfere with each other. It prevents one transaction from seeing the intermediate states of another transaction. Isolation helps maintain data integrity and prevents "dirty reads," "non-repeatable reads," and "phantom reads."

To understand the different levels of isolation, we need to first understand those phenomena that can occur in a relational database when multiple transactions are executed concurrently. These phenomena can impact data consistency and integrity:
- Dirty Reads: a dirty read occurs when one transaction reads data that has been modified by another transaction but has not yet been committed. In other words, a transaction reads data that is in an intermediate, uncommitted state. For example:
  - Transaction A updates a customer's email address.
  - Transaction B reads the customer's data before Transaction A has committed its changes.
  - If Transaction A is rolled back, the change to the email address is lost, and Transaction B has read incorrect data.
- Non-Repeatable Reads: a non-repeatable read occurs when a transaction reads the same data twice but obtains different values between the reads due to concurrent changes by other transactions. For example:
  - Transaction A reads a customer's balance.
  - Transaction B updates the customer's balance and commits the changes.
  - Transaction A reads the customer's balance again and gets a different value.
- Phantom Reads: a phantom read occurs when a transaction reads a set of rows that satisfy a certain condition, and then another transaction inserts, updates, or deletes a row that would have met the same condition. As a result, the first transaction observes a change in the data set between two reads. For example:
  - Transaction A reads all products with a price below $50.
  - Transaction B inserts a new product with a price below $50 and commits the change.
  - Transaction A reads all products with a price below $50 again and sees the newly inserted product.

There are several isolation levels:
- Read Uncommitted: Allows dirty reads, non-repeatable reads, and phantom reads.
  - Pros:
    - Highest level of concurrency, as transactions don't block each other.
    - Lowest impact on performance due to minimal locking.
  - Cons:
    - High risk of data integrity issues.
    - Dirty reads, non-repeatable reads, and phantom reads are possible.
    - Not suitable for scenarios where data consistency is critical.
- Read Committed: Allows non-repeatable reads and phantom reads.
  - Pros:
    - Avoids dirty reads by ensuring that transactions see only committed data.
    - Reduces the risk of data integrity issues compared to Read Uncommitted.
  - Cons:
    - Non-repeatable reads and phantom reads are still possible.
    - Potential for performance issues due to increased locking.
- Repeatable Read: Allows phantom reads.
  - Pros:
    - Prevents non-repeatable reads, ensuring that once a transaction reads a value, subsequent reads will return the same value.
    - Provides a higher level of data consistency compared to Read Committed.
  - Cons:
    - Phantom reads are still possible.
    - May lead to increased locking, potentially impacting concurrency and performance.
- Serializable: Provides the highest level of isolation, preventing all anomalies.
  - Pros:
    - Provides the highest level of data consistency and integrity.
    - Prevents dirty reads, non-repeatable reads, and phantom reads.
  - Cons:
    - Lowest level of concurrency, as transactions are fully isolated from each other.
    - Can lead to performance issues due to extensive locking and potential contention.

Choosing the right isolation level depends on the requirements of your application and the balance you need between data consistency and performance. 
- Read Uncommitted: Best for scenarios where high concurrency is essential, and data consistency is less critical. Not suitable for applications with stringent data integrity requirements. 
- Read Committed: Strikes a balance between concurrency and data consistency. Avoids dirty reads but allows non-repeatable reads and phantom reads. 
- Repeatable Read: Provides stronger data consistency by preventing non-repeatable reads. Phantom reads are still possible but reduced. 
- Serializable: Ensures the highest data consistency by preventing all anomalies. However, it comes at the cost of reduced concurrency and potential performance issues due to extensive locking.

#### Durability
- Durability ensures that once a transaction is committed, its changes are permanent and will survive even if the system crashes or fails.
- After a transaction is committed, the database system ensures that the changes are written to a durable storage medium, such as disk. This guarantees that the changes will be available even if there is a power outage or hardware failure.

### ACID in practice
1. Atomicity: Use transactions to group related database operations. Ensure that the transaction is either fully completed or fully rolled back in case of failure. 
2. Consistency: Define and enforce integrity constraints and business rules using database triggers, stored procedures, or application logic. 
3. Isolation: Choose an appropriate isolation level based on the concurrency requirements of your application. Higher isolation levels provide stronger guarantees but may impact performance. 
4. Durability: Ensure that your database management system (DBMS) provides proper durability mechanisms, and regularly back up your database to prevent data loss.

## NORMALIZATION
Normalization is the process of designing a database schema to reduce data redundancy, improve data integrity, and enhance query performance. It involves organizing data into related tables and applying specific rules to ensure that each piece of data is stored only once and is easily maintainable.

Unnormalized or poorly normalized databases can suffer from various issues, including:
- Data redundancy, leading to inconsistencies and update anomalies. 
- Poor data integrity due to duplicated information. 
- Difficulty in making changes to the database schema. 
- Inefficient query performance.

### NORMALIZATION FORMS
#### First Normal Form (1NF)
1NF requires that each column in a table contains only atomic (indivisible) values, not any multi-value attributes.

#### Second Normal Form (2NF)
2NF builds on 1NF and ensures that each non-key column is fully functionally dependent on the entire primary key. It eliminates partial dependencies.

#### Third Normal Form (3NF)
3NF ensures that there are no transitive dependencies, where a non-key column depends on another non-key column.

#### Boyce-Codd Normal Form (BCNF)
BCNF is a stricter form of 3NF and focuses on eliminating all non-trivial functional dependencies.

#### Fourth Normal Form (4NF)
4NF addresses multi-valued dependencies, where a non-key column depends on a combination of other non-key columns.

#### Fifth Normal Form (5NF) and Beyond
5NF (also known as Project-Join Normal Form or PJNF) deals with cases where certain join dependencies are present.

#### Example of normalization
###### Database not normalized
Membership table

Person full name | Person Address         | Movie rented 1 | Movie rented 2 | Salutation |
----|------------------------|----------------------|--------------------------|------------|
Janet Jones  | First Street Plot No 4 | Pirates of the Caribbean | The Lord of the Rings | Ms.        |
Robert Phil  | 3rd Street 34          | Matrix | Harry Potter                            | Mr.        |
Mark Johnson | 5th Avenue             | Batman | Winter Soldier                          | Mr.        |

###### 1NF
Membership table

Person full name | Person Address         | Movies rented                                   | Salutation |
------------------|------------------------|-------------------------------------------------|------------|
Janet Jones      | First Street Plot No 4  | Pirates of the Caribbean | Ms.        |
Janet Jones      | First Street Plot No 4 | The Lord of the Rings | Ms.        |
Robert Phil      | 3rd Street 34    | Matrix                            | Mr.        |
Robert Phil      | 3rd Street 34    | Harry Potter                            | Mr.        |
Mark Johnson     | 5th Avenue       | Batman                         | Mr.        |
Mark Johnson     | 5th Avenue       | Winter Soldier                          | Mr.        |

###### 2NF
Membership table

ID | Person full name | Person Address         | Salutation |
----|------------------|------------------------|-------------------------------------------------|
1 | Janet Jones      | First Street Plot No 4  | Ms.        |
2 | Robert Phil      | 3rd Street 34    | Mr.        |
3 | Mark Johnson     | 5th Avenue       | Mr.        |

Movies rented table

ID | Movie ID | Membership ID |
----|----------|---------------|
1 | Pirates of the Caribbean        | 1             |
2 | The Lord of the Rings        | 1             |
3 | Matrix        | 2             |
4 | Harry Potter        | 2             |
5 | Batman        | 3             |
6 | Winter Soldier        | 3             |

###### 3NF
Membership table

ID | Person full name | Person Address         | Salutation ID |
----|------------------|------------------------|---------------|
1 | Janet Jones      | First Street Plot No 4  | 1             |
2 | Robert Phil      | 3rd Street 34    | 2             |
3 | Mark Johnson     | 5th Avenue       | 2             |

Movies rented table

ID | Movie ID | Membership ID |
----|----------|---------------|
1 | Pirates of the Caribbean        | 1             |
2 | The Lord of the Rings        | 1             |
3 | Matrix        | 2             |
4 | Harry Potter        | 2             |
5 | Batman        | 3             |
6 | Winter Soldier        | 3             |

Salutation table

ID | Salutation |
----|------------------------------------------------|
1 | Ms.        |
2 | Mr.        |

###### BCNF
Membership table

ID | Person full name | Person Address         | Salutation ID |
----|------------------|------------------------|---------------|
1 | Janet Jones      | First Street Plot No 4  | 1             |
2 | Robert Phil      | 3rd Street 34    | 2             |
3 | Mark Johnson     | 5th Avenue       | 2             |

Movies table

ID | Title                    |
----|--------------------------|
1 | Pirates of the Caribbean |
2 | The Lord of the Rings    |
3 | Matrix                   |
4 | Harry Potter             |
5 | Batman                   |
6 | Winter Soldier           |

Movies rented table

ID | Movie ID | Membership ID |
----|----------|---------------|
1 | 1        | 1             |
2 | 2        | 1             |
3 | 3        | 2             |
4 | 4        | 2             |
5 | 5        | 3             |
6 | 6        | 3             |

Salutation table

ID | Salutation |
----|------------------------------------------------|
1 | Ms.        |
2 | Mr.        |

## PROS AND CONS OF RELATIONAL DATABASES

### PROS

1. Data Integrity and Accuracy: Relational databases enforce data integrity through constraints and rules, ensuring that the data is accurate and consistent. 
2. Data Relationships: Relational databases excel at handling complex relationships between data through foreign keys and join operations. 
3. Structured Query Language (SQL): SQL provides a standardized language for querying and manipulating data, making it easy to retrieve and update information. 
4. ACID Transactions: Relational databases offer ACID properties (Atomicity, Consistency, Isolation, Durability) to ensure data integrity and consistency in transactions. 
5. Mature Technology: Relational databases have been around for decades, making them well-tested and stable solutions for various applications. 
6. Data Security: Most relational database systems provide robust security features, including access control and authentication mechanisms. 
7. Flexible Schema Design: Relational databases allow you to design a flexible schema that can evolve over time without affecting existing data. 
8. Normalization: Normalization techniques help organize data efficiently and reduce redundancy, leading to better data quality. 
9. Adherence to Standards: Relational databases adhere to established standards, making it easier to integrate with other systems and tools.

### CONS

1. Scalability: Scaling a relational database can be challenging, especially for applications with high write loads. Vertical scaling (adding more resources to a single server) has limits. 
2. Performance: Complex queries involving multiple joins can lead to performance issues, especially as the data volume grows. 
3. Schema Changes: Modifying the database schema can be cumbersome, especially for large databases, and may require downtime or data migration efforts. 
4. Fixed Schema: Relational databases require a predefined schema, which can be restrictive in scenarios where data structures are highly dynamic. 
5. Storage Overhead: The need to maintain relationships and adhere to ACID properties can result in higher storage overhead compared to some NoSQL solutions. 
6. Less Suited for Unstructured Data: Relational databases are optimized for structured data and may not be the best choice for handling unstructured or semi-structured data types. 
7. Learning Curve: While SQL is a powerful tool, learning the language and understanding complex query optimization can take time. 
8. Vendor Lock-In: Each relational database vendor has its own proprietary features and extensions, which can lead to vendor lock-in.

## SQL

Read the SQL tutorial [here](https://github.com/ManuMyGit/CodingTutorials/tree/main/database/relational/sql.md).