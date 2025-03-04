USE master
GO

DROP DATABASE IF EXISTS QUANLYDIEM
CREATE DATABASE QUANLYDIEM
GO

USE QUANLYDIEM3
GO

DROP TABLE IF EXISTS student
CREATE TABLE student
(
	stuId INT PRIMARY KEY IDENTITY,
	stuName NVARCHAR(50),
	stuGender BIT,
	stuDob DATE,
	stuEmail VARCHAR(100),
	stuPhone VARCHAR(10),
	stuAddress NVARCHAR(200),
	stuImage VARCHAR(300) NOT NULL
)
GO

DROP TABLE IF EXISTS employee
CREATE TABLE employee
(
	empId INT PRIMARY KEY IDENTITY,
	empName NVARCHAR(50),
	empGender BIT,
	empDob DATE,
	empPhone VARCHAR(10),
	empAddress NVARCHAR(200),
	empHireDate DATE DEFAULT GETDATE(),
	empImage VARCHAR(300) NOT NULL,
	roleId INT
)
GO

DROP TABLE IF EXISTS course
CREATE TABLE course
(
	courseId INT PRIMARY KEY IDENTITY,
	courseName NVARCHAR(100),
	courseDesc NVARCHAR(200),
	courseDuration INT
)
GO

DROP TABLE IF EXISTS class
CREATE TABLE class
(
	classId INT PRIMARY KEY IDENTITY,
	className NVARCHAR(100) UNIQUE,
	startDate DATE,
	endDate DATE,
	courseId INT,
	empId INT
)
GO

DROP TABLE IF EXISTS enrollment
CREATE TABLE enrollment
(
	enrollId INT PRIMARY KEY IDENTITY,
	enrollDate DATE DEFAULT GETDATE(),
	stuId INT,
	classId INT
)
GO


DROP TABLE IF EXISTS grade
CREATE TABLE grade
(
	gradeId INT PRIMARY KEY IDENTITY,
	midScore FLOAT,
	finalScore FLOAT,
	avgScore FLOAT,
	enrollId INT UNIQUE
)
GO

DROP TABLE IF EXISTS accounts
CREATE TABLE accounts
(
	accId INT IDENTITY PRIMARY KEY,
	accEmail NVARCHAR(100) NOT NULL UNIQUE,
    accPass NVARCHAR(256) NOT NULL,
    CreatedDate DATETIME DEFAULT GETDATE(),
	accStatus BIT,
	roleId INT,
	empId INT
	
)
GO

DROP TABLE IF EXISTS roles 
CREATE TABLE roles
(
	roleId INT IDENTITY PRIMARY KEY,
	roleName VARCHAR(50)
)
GO


ALTER TABLE enrollment
ADD CONSTRAINT FK_enrollment_student
FOREIGN KEY(stuId) REFERENCES student(stuId)

ALTER TABLE enrollment
ADD CONSTRAINT FK_enrollment_class
FOREIGN KEY(classId) REFERENCES class(classId)

ALTER TABLE class
ADD CONSTRAINT FK_class_course
FOREIGN KEY(courseId) REFERENCES course(courseId)

ALTER TABLE class
ADD CONSTRAINT FK_class_employee
FOREIGN KEY(empId) REFERENCES employee(empId)

ALTER TABLE grade
ADD CONSTRAINT FK_grade_enrollment
FOREIGN KEY(enrollId) REFERENCES enrollment(enrollId)

ALTER TABLE accounts
ADD CONSTRAINT FK_accounts_employee
FOREIGN KEY(empId) REFERENCES employee(empId)

ALTER TABLE accounts
ADD CONSTRAINT FK_accounts_roles
FOREIGN KEY(roleId) REFERENCES roles(roleId)

ALTER TABLE employee
ADD CONSTRAINT FK_employee_roles
FOREIGN KEY (roleId) REFERENCES roles(roleId)

--DEFAULT
ALTER TABLE student
ADD CONSTRAINT DF_student_image DEFAULT 'images/a.jpg' FOR stuImage;

ALTER TABLE employee
ADD CONSTRAINT DF_employee_image DEFAULT 'images/a.jpg' FOR empImage;

INSERT INTO student(stuName, stuAddress, stuDob, stuEmail, stuGender, stuPhone, stuImage)
VALUES( 'StudentName', 'address', '2013-10-09', 'stu@gmail.com', 1, '0039939830', 'images/a.jpg')
GO 10

createStu 'StudentName', 1, '2013-10-09', 'stu@gmail.com', '0039939830', 'address', 'images/a.jpg'
GO 

INSERT INTO employee(empName, empAddress, empDob, empGender, empPhone, empImage)
VALUES('Name', 'address', '1999-10-24', 0, '0039939820', 'images/a.jpg')
GO 10

INSERT INTO roles(roleName)
VALUES('admin')
GO
INSERT INTO roles(roleName)
VALUES('teacher')
GO
INSERT INTO roles(roleName)
VALUES('staff')
GO


INSERT INTO course(courseName, courseDesc, courseDuration)
VALUES('Tiếng Anh Giao Tiếp', 'course desc', 10)
GO
INSERT INTO course(courseName, courseDesc, courseDuration)
VALUES('Tiếng Anh Thương Mại', 'course desc', 10)
GO

INSERT INTO class(className, courseId, startDate, endDate, empId)
VALUES('GT01', 1, '2025-03-10', '2025-03-31', 1)
GO
INSERT INTO class(className, courseId, startDate, endDate, empId)
VALUES('TM01', 2, '2025-03-10', '2025-03-31', 2)
GO

createEnrollmentAGrade 1, 1
GO
createEnrollmentAGrade 2, 1
GO
createEnrollmentAGrade 3, 1
GO
createEnrollmentAGrade 4, 1
GO
createEnrollmentAGrade 5, 1
GO
createEnrollmentAGrade 6, 2
GO
createEnrollmentAGrade 7, 2
GO
createEnrollmentAGrade 8, 2
GO
createEnrollmentAGrade 9, 2
GO
createEnrollmentAGrade 10, 2
GO

INSERT INTO enrollment(enrollDate, stuId, classId)
VALUES('2024-10-09', 1, 1)
GO
INSERT INTO enrollment(enrollDate, stuId, classId)
VALUES('2024-10-09', 2, 1)
GO
INSERT INTO enrollment(enrollDate, stuId, classId)
VALUES('2024-10-09', 3, 1)
GO
INSERT INTO enrollment(enrollDate, stuId, classId)
VALUES('2024-10-10', 4, 1)
GO
INSERT INTO enrollment(enrollDate, stuId, classId)
VALUES('2024-10-10', 5, 1)
GO
INSERT INTO enrollment(enrollDate, stuId, classId)
VALUES('2024-10-09', 6, 2)
GO
INSERT INTO enrollment(enrollDate, stuId, classId)
VALUES('2024-10-09', 7, 2)
GO
INSERT INTO enrollment(enrollDate, stuId, classId)
VALUES('2024-10-09', 8, 2)
GO
INSERT INTO enrollment(enrollDate, stuId, classId)
VALUES('2024-10-12', 9, 2)
GO
INSERT INTO enrollment(enrollDate, stuId, classId)
VALUES('2024-10-12', 10, 2)
GO


INSERT INTO accounts(accEmail, accPass, roleId, accStatus, empId)
VALUES('admin@edu.com', '123', 1, 0, 1)
GO
INSERT INTO accounts(accEmail, accPass, roleId, accStatus, empId)
VALUES('sta@edu.com', '123', 3, 0, 2)
GO
INSERT INTO accounts(accEmail, accPass, roleId, accStatus, empId)
VALUES('tea@edu.com', '123', 2, 1, 3)
GO

--stored procedure
--roles
	--R
CREATE OR ALTER PROCEDURE selectRoles
AS
BEGIN
	SELECT * FROM roles
END
GO

	--getroleid
CREATE PROCEDURE GetRoleIdByEmpId
    @empId INT
AS
BEGIN
    SELECT roleId FROM employee WHERE empId = @empId;
END
GO


--accounts
--- store validate account---
CREATE OR ALTER PROCEDURE ValidateAccount
    @accEmail NVARCHAR(255),
    @accPass NVARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (SELECT 1 FROM Accounts WHERE accEmail = @accEmail AND accPass = @accPass)
    BEGIN
        SELECT 1 AS IsValid, roleId FROM Accounts WHERE accEmail = @accEmail;
    END
    ELSE
    BEGIN
        SELECT 0 AS IsValid, NULL AS roleId;
    END
END
GO
--- store select account ---
CREATE OR ALTER PROC selectAcc
AS
BEGIN
	SELECT * FROM accounts;
END
GO

CREATE OR ALTER PROC selectOneAcc
@accEmail NVARCHAR(100)
AS
BEGIN
	SELECT * FROM accounts
	WHERE accEmail = @accEmail
END
GO

---- store update account ----
CREATE OR ALTER PROCEDURE updateAcc
    @accId INT,
    @accMail VARCHAR(100),
    @accPass VARCHAR(255),
    @roleId INT,
    @accStatus BIT,
    @empId INT
AS
BEGIN
    UPDATE accounts
    SET accEmail = @accMail,
        accPass = @accPass,
        roleId = @roleId,
        accStatus = @accStatus,
        empId = @empId
    WHERE accId = @accId;
END
GO

---store add account
CREATE OR ALTER PROCEDURE insertAcc
@accMail VARCHAR(100),
@accPass VARCHAR(255),
@roleId INT,
@accStatus BIT,
@empId INT
AS
BEGIN
	INSERT INTO accounts(accEmail, accPass, roleId, accStatus, empId)
	VALUES (@accMail, @accPass, @roleId, @accStatus, @empId)
END
GO

insertAcc 'tea2@edu.com', 123, 2, True, 4

-----------employee store (bk)------------
CREATE OR ALTER PROCEDURE getEmpIdsWithoutAccount
AS
BEGIN
    SELECT empId FROM employee WHERE empId NOT IN (SELECT empId FROM accounts);
END
GO

getEmpIdsWithoutAccount 

--student


	--C
CREATE OR ALTER PROC createStu
@stuName NVARCHAR(50), @stuGender BIT, @stuDob DATE, @stuEmail VARCHAR(100), @stuPhone NVARCHAR(10), @stuAddress NVARCHAR(200), @stuImage NVARCHAR(300)
AS
BEGIN
	INSERT INTO student(stuName, stuGender, stuDob, stuEmail, stuPhone, stuAddress, stuImage)
	VALUES(@stuName, @stuGender, @stuDob, @stuEmail, @stuPhone, @stuAddress, @stuImage)
END
GO

	--R
CREATE OR ALTER PROC selectStu
AS
BEGIN
	SELECT * FROM student
END
GO

CREATE OR ALTER PROC selectStuNameByID
@stuId INT
AS
BEGIN
	SELECT stuName FROM student
	WHERE stuId = @stuId
END
GO

selectStuNameByID 1


	--U
CREATE OR ALTER PROC updateStu
@stuName NVARCHAR(50), @stuGender BIT, @stuDob DATE, @stuEmail VARCHAR(100), @stuPhone NVARCHAR(10), @stuAddress NVARCHAR(200), @stuImage NVARCHAR(300), @stuId INT
AS
BEGIN
UPDATE student
SET stuName = @stuName,
	stuGender = @stuGender,
	stuDob = @stuDob,
	stuEmail =  @stuEmail,
	stuPhone = @stuPhone,
	stuAddress = @stuAddress,
	stuImage = @stuImage
	WHERE stuId = @stuId
END
GO

	--D
CREATE OR ALTER PROC deleteStu
@stuId INT
AS
BEGIN
	DELETE FROM student
	WHERE stuId = @stuId
END
GO



--EMPLOYEE
	--C
CREATE OR ALTER PROC createEmp
@empName NVARCHAR(50), 
@empGender BIT, 
@empDob DATE, 
@empPhone VARCHAR(10), 
@empAddress NVARCHAR(200), 
@empImage VARCHAR(300),
@roleId INT
AS
BEGIN
    INSERT INTO employee (empName, empGender, empDob, empPhone, empAddress, empImage, roleId)
    VALUES (@empName, @empGender, @empDob, @empPhone, @empAddress, @empImage, @roleId)
END
GO
	--R
CREATE OR ALTER PROC selectEmp
AS
BEGIN
    SELECT * FROM employee
END
GO

CREATE OR ALTER PROC selectEmpByID
@empId INT
AS
BEGIN
    SELECT * FROM employee
	WHERE empId = @empId
END
GO

CREATE OR ALTER PROC selectTeacher
AS
BEGIN
    SELECT * FROM employee
	WHERE roleId = 2
END
GO

CREATE OR ALTER PROC selectEmpNameById
@empId INT
AS
BEGIN
	SELECT empName FROM employee
	WHERE empId = @empId
END
GO

	--U
CREATE OR ALTER PROC updateEmp
@empName NVARCHAR(50), 
@empGender BIT, 
@empDob DATE, 
@empPhone VARCHAR(10), 
@empAddress NVARCHAR(200), 
@empImage VARCHAR(300),
@roleId INT,
@empId INT
AS
BEGIN
    UPDATE employee
    SET empName = @empName,
        empGender = @empGender,
        empDob = @empDob,
        empPhone = @empPhone,
        empAddress = @empAddress,
        empImage = @empImage,
		roleId = @roleId
    WHERE empId = @empId
END
GO

	--D
CREATE OR ALTER PROC deleteEmp
@empId INT
AS
BEGIN
    DELETE FROM employee
    WHERE empId = @empId
END
GO

--COURSE
	--C
CREATE OR ALTER PROC createCourse
    @courseName NVARCHAR(100), 
    @courseDesc NVARCHAR(200), 
    @courseDuration INT
AS
BEGIN
    INSERT INTO course (courseName, courseDesc, courseDuration)
    VALUES (@courseName, @courseDesc, @courseDuration)
END
GO

	--R
CREATE OR ALTER PROC selectCourse
AS
BEGIN
    SELECT * FROM course
END
GO

CREATE OR ALTER PROC selectCourseById
@courseId INT
AS
BEGIN
	SELECT * FROM course
	WHERE courseId = @courseId
END
GO

CREATE OR ALTER PROC selectCourseNameById
@courseId INT
AS
BEGIN
	SELECT courseName FROM course
	WHERE courseId = @courseId
END
GO


	--U
CREATE OR ALTER PROC updateCourse
    @courseId INT,
    @courseName NVARCHAR(100), 
    @courseDesc NVARCHAR(200), 
    @courseDuration INT
AS
BEGIN
    UPDATE course
    SET courseName = @courseName,
        courseDesc = @courseDesc,
        courseDuration = @courseDuration
    WHERE courseId = @courseId
END
GO

	--D
CREATE OR ALTER PROC deleteCourse
    @courseId INT
AS
BEGIN
    DELETE FROM course
    WHERE courseId = @courseId
END
GO

--CLASS
	--C
CREATE OR ALTER PROC createClass
    @className NVARCHAR(100), 
    @startDate DATE, 
    @endDate DATE, 
    @courseId INT, 
    @empId INT
AS
BEGIN
    INSERT INTO class (className, startDate, endDate, courseId, empId)
    VALUES (@className, @startDate, @endDate, @courseId, @empId)
END
GO

	--R
CREATE OR ALTER PROC selectClass
AS
BEGIN
    SELECT * FROM class
END
GO

CREATE OR ALTER PROC selectClassByName
@className NVARCHAR(100)
AS
BEGIN
	SELECT * FROM class
	WHERE className = @className
END
GO

CREATE OR ALTER PROC selectClassByID
@classId INT
AS
BEGIN
	SELECT * FROM class
	WHERE classId = @classId
END
GO

CREATE OR ALTER PROC selectClassesByTeachID
@empId INT
AS
BEGIN
	SELECT * FROM class
	WHERE empId =@empId
END
GO



	--U
CREATE OR ALTER PROC updateClass
    @className NVARCHAR(100), 
    @startDate DATE, 
    @endDate DATE, 
    @courseId INT, 
    @empId INT,
	@classId INT
AS
BEGIN
    UPDATE class
    SET className = @className,
        startDate = @startDate,
        endDate = @endDate,
        courseId = @courseId,
        empId = @empId
    WHERE classId = @classId
END
GO

	--D
CREATE OR ALTER PROC deleteClass
    @classId INT
AS
BEGIN
    DELETE FROM class
    WHERE classId = @classId
END
GO

--ENROLLMENT
	--C
CREATE OR ALTER PROC createEnrollmentAGrade
    @stuId INT,
    @classId INT
AS
BEGIN
    -- Insert into enrollment table
    INSERT INTO enrollment (enrollDate, stuId, classId)
    VALUES (GETDATE(), @stuId, @classId)

    -- Get the newly created identity value (enrollId)
    DECLARE @newEnrollId INT;
    SET @newEnrollId = SCOPE_IDENTITY();

    -- Insert into grade table using the new enrollId
    INSERT INTO grade (midScore, finalScore, avgScore, enrollId)
    VALUES (0.0, 0.0, 0.0, @newEnrollId);
END
GO

	--R
CREATE OR ALTER PROC selectEnrollment
AS
BEGIN
    SELECT * FROM enrollment
END
GO

CREATE OR ALTER PROC selectEnrollByClass
@classId INT
AS
BEGIN
	SELECT enrollId, student.* FROM enrollment
	JOIN class ON enrollment.classId = class.classId
	JOIN student ON enrollment.stuId = student.stuId
	WHERE class.classId = @classId
END
GO

	--U
CREATE OR ALTER PROC updateEnrollment
    @enrollId INT,
    @stuId INT,
    @classId INT
AS
BEGIN
    UPDATE enrollment
    SET stuId = @stuId,
        classId = @classId
    WHERE enrollId = @enrollId
END
GO

	--D
CREATE OR ALTER PROC deleteEnrollment
    @enrollId INT
AS
BEGIN
    DELETE FROM enrollment
    WHERE enrollId = @enrollId
END
GO

--GRADE
	--C

	--R
CREATE OR ALTER PROC selectGrade
AS
BEGIN
    SELECT * FROM grade
END
GO

CREATE OR ALTER PROC selectGradeByID
@gradeId INT
AS
BEGIN
    SELECT * FROM grade
	WHERE gradeId = @gradeId
END
GO

	--U
CREATE OR ALTER PROC updateGrade
    @midScore FLOAT,
    @finalScore FLOAT,
	@avgScore FLOAT,
	@gradeId INT
AS
BEGIN
    UPDATE grade
    SET midScore = @midScore,
        finalScore = @finalScore,
		avgScore = @avgScore
    WHERE gradeId = @gradeId
END
GO

	--D
CREATE OR ALTER PROC deleteGrade
    @gradeId INT
AS
BEGIN
    DELETE FROM grade
    WHERE gradeId = @gradeId
END
GO

CREATE OR ALTER PROC deleteGradeByEnrollID
    @enrollId INT
AS
BEGIN
    DELETE FROM grade
    WHERE enrollId = @enrollId
END
GO

--PAGING

CREATE OR ALTER PROC paging
    @query NVARCHAR(MAX),
    @column NVARCHAR(20),
    @currentpage INT,
    @numberofrows INT
AS
BEGIN
    DECLARE @sql NVARCHAR(MAX);
    SET @sql = @query
              + ' ORDER BY ' + QUOTENAME(@column)
              + ' OFFSET (' + CAST((@currentpage - 1) * @numberofrows AS NVARCHAR(20)) + ') ROWS'
              + ' FETCH NEXT ' + CAST(@numberofrows AS NVARCHAR(20)) + ' ROWS ONLY';

    EXEC sp_executesql @sql;
END;
GO

CREATE OR ALTER PROC pagingWithIds
    @table NVARCHAR(20),
    @column NVARCHAR(20),
    @ids NVARCHAR(MAX),
    @currentpage INT,
    @numberofrows INT
AS
BEGIN
    DECLARE @sql NVARCHAR(MAX);
    SET @sql = 'SELECT * FROM ' + QUOTENAME(@table)
              + ' WHERE ' + QUOTENAME(@column) + ' IN (' + @ids + ')'
              + ' ORDER BY ' + QUOTENAME(@column)
              + ' OFFSET (' + CAST((@currentpage - 1) * @numberofrows AS NVARCHAR(20)) + ') ROWS'
              + ' FETCH NEXT ' + CAST(@numberofrows AS NVARCHAR(20)) + ' ROWS ONLY';

    EXEC sp_executesql @sql;
END;
GO

--student list
CREATE OR ALTER PROC pagingStudentList
@currentpage INT, @numberofrows INT, @classId INT
AS
BEGIN
	SELECT enrollment.enrollId, student.*
	FROM enrollment
	JOIN student ON student.stuId = enrollment.stuId
	JOIN class ON enrollment.classId = class.classId
	WHERE enrollment.classId = @classId
	ORDER BY student.stuName
	OFFSET ((@currentpage - 1) * @numberofrows) ROWS
	FETCH NEXT @numberofrows ROWS ONLY;
END
GO
pagingStudentList 1, 10, 1

CREATE OR ALTER PROC NotStudentList
    @classId INT
AS
BEGIN
    SELECT s.stuId, s.stuName, s.stuDob, s.stuAddress, s.stuEmail, s.stuPhone
    FROM student s
    WHERE NOT EXISTS (
        SELECT 1 FROM enrollment e
        WHERE e.stuId = s.stuId AND e.classId = @classId
    )
    ORDER BY s.stuName;
END
GO
NotStudentList 1


CREATE OR ALTER PROC pagingStudentNotInClass
@currentpage INT, @numberofrows INT, @classId INT
AS
BEGIN
	 SELECT *
    FROM student s
    WHERE NOT EXISTS (
        SELECT 1 FROM enrollment e
        WHERE e.stuId = s.stuId AND e.classId = @classId
    )
    ORDER BY s.stuId
	OFFSET ((@currentpage - 1) * @numberofrows) ROWS
	FETCH NEXT @numberofrows ROWS ONLY;
END
GO

pagingStudentNotInClass 1, 10, 1

CREATE OR ALTER PROC countStudentNotInClass
@classId INT
AS
BEGIN
	SELECT COUNT(s.stuId) total
	FROM student s
	LEFT JOIN enrollment e ON s.stuId = e.stuId
	LEFT JOIN class c ON e.classId = c.classId
	WHERE c.classId != @classId
	OR c.classId IS NULL
END
GO

CREATE OR ALTER PROC pagingEnrollListOfStu
@currentpage INT, @numberofrows INT, @stuId INT
AS
BEGIN
	SELECT * FROM enrollment
	WHERE stuId = @stuId
    ORDER BY enrollId
	OFFSET ((@currentpage - 1) * @numberofrows) ROWS
	FETCH NEXT @numberofrows ROWS ONLY;
END
GO

pagingEnrollListOfStu 1, 10 , 2

CREATE OR ALTER PROC countEnrollListOfStu
@stuId INT
AS
BEGIN
	SELECT count(stuId) total FROM enrollment
	WHERE stuId = @stuId
END
GO


--Grade
CREATE OR ALTER PROC pagingGradeStu
@currentpage INT, @numberofrows INT, @classId INT
AS
BEGIN
	SELECT student.stuId, student.stuName, grade.midScore, grade.finalScore, grade.avgScore, grade.gradeId, grade.enrollId FROM enrollment
	JOIN student ON enrollment.stuId = student.stuId
	JOIN grade ON enrollment.enrollId = grade.enrollId
	WHERE enrollment.classId = @classId
	ORDER BY student.stuName
	OFFSET ((@currentpage - 1) * @numberofrows) ROWS
	FETCH NEXT @numberofrows ROWS ONLY
END
GO

--countStudentList use for both paging GradeStu and studentList
CREATE OR ALTER PROC countStudentList
@classId INT
AS
BEGIN
	SELECT COUNT(enrollId) total
	FROM enrollment
	JOIN student ON student.stuId = enrollment.stuId
	JOIN class ON enrollment.classId = class.classId
	WHERE enrollment.classId = @classId
END
GO



--pagingGradeStu 1, 5, 1

--Employee and Teacher
CREATE OR ALTER PROC pagingTeacher
@currentpage INT, @numberofrows INT
AS
BEGIN
	SELECT * FROM employee
	WHERE roleId = 2
	ORDER BY empId
	OFFSET ((@currentpage - 1) * @numberofrows) ROWS
	FETCH NEXT @numberofrows ROWS ONLY
END
GO

pagingTeacher 1, 10

CREATE OR ALTER PROC countTeacher
AS
BEGIN
	SELECT COUNT(empId) total FROM employee
	WHERE roleId = 2
END
GO

CREATE OR ALTER PROC pagingEmp
@currentpage INT, @numberofrows INT
AS
BEGIN
	SELECT * FROM employee
	WHERE roleId != 2
	ORDER BY empId
	OFFSET ((@currentpage - 1) * @numberofrows) ROWS
	FETCH NEXT @numberofrows ROWS ONLY
END
GO

CREATE OR ALTER PROC countEmp
AS
BEGIN
	SELECT COUNT(empId) total FROM employee
	WHERE roleId != 2
END
GO

--Account
CREATE OR ALTER PROC pagingAccount
@currentpage INT, @numberofrows INT
AS
BEGIN
	SELECT * FROM accounts
	ORDER BY accId
	OFFSET ((@currentpage - 1) * @numberofrows) ROWS
	FETCH NEXT @numberofrows ROWS ONLY
END
GO

CREATE OR ALTER PROC countAccount
AS
BEGIN
	SELECT COUNT(accId) total FROM accounts
END
GO
--Student
CREATE OR ALTER PROC pagingStudent
@currentpage INT, @numberofrows INT
AS
BEGIN
	SELECT * FROM student
	ORDER BY stuId
	OFFSET ((@currentpage - 1) * @numberofrows) ROWS
	FETCH NEXT @numberofrows ROWS ONLY
END
GO


CREATE OR ALTER PROC countStudent
AS
BEGIN
	SELECT COUNT(stuId) total FROM student
END
GO

--Course
CREATE OR ALTER PROC pagingCourse
@currentpage INT, @numberofrows INT
AS
BEGIN
	SELECT * FROM course
	ORDER BY courseId
	OFFSET ((@currentpage - 1) * @numberofrows) ROWS
	FETCH NEXT @numberofrows ROWS ONLY
END
GO

CREATE OR ALTER PROC countCourse
AS
BEGIN
	SELECT COUNT(courseId) total FROM course
END
GO

--Class
CREATE OR ALTER PROC pagingClass
@currentpage INT, @numberofrows INT
AS
BEGIN
    SELECT * FROM class
    ORDER BY classId
    OFFSET ((@currentpage - 1) * @numberofrows) ROWS
    FETCH NEXT @numberofrows ROWS ONLY
END
GO

CREATE OR ALTER PROC countClass
AS
BEGIN
    SELECT COUNT(classId) AS total FROM class
END
GO



	--count something
CREATE OR ALTER PROC countSth
    @column NVARCHAR(20),
    @table NVARCHAR(20)
AS
BEGIN
    DECLARE @sql NVARCHAR(MAX);

    -- Construct the dynamic SQL query
    SET @sql = N'SELECT COUNT(' + QUOTENAME(@column) + N') AS total FROM ' + QUOTENAME(@table);

    -- Execute the dynamic SQL
    EXEC sp_executesql @sql;
END
GO

