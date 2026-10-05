-- ============================================================================
-- SHOPVIDEO — script tao lai toan bo database, gop them du lieu user tu
-- ShoppingServletDB (roles/users) vao dung schema Users cua SHOPVIDEO.
-- Chay toan bo file nay tren SQL Server se DROP va CREATE lai tu dau.
-- ============================================================================

IF DB_ID(N'SHOPVIDEO') IS NULL
    CREATE DATABASE SHOPVIDEO;
GO
USE SHOPVIDEO;
GO

IF OBJECT_ID('dbo.Favorites','U') IS NOT NULL DROP TABLE dbo.Favorites;
IF OBJECT_ID('dbo.Shares','U') IS NOT NULL DROP TABLE dbo.Shares;
IF OBJECT_ID('dbo.Videos','U') IS NOT NULL DROP TABLE dbo.Videos;
IF OBJECT_ID('dbo.Categories','U') IS NOT NULL DROP TABLE dbo.Categories;
IF OBJECT_ID('dbo.Users','U') IS NOT NULL DROP TABLE dbo.Users;
GO

-- Users: giu Username lam khoa chinh (dung voi entity User.java / UserDAO).
-- Them cot UpdatedAt (lay tu ALTER TABLE users ADD updated_at cua
-- ShoppingServletDB) de du tru, entity chua map cot nay.
CREATE TABLE Users (
    Username nvarchar(50) NOT NULL PRIMARY KEY,
    Password nvarchar(50) NULL,
    Phone nvarchar(15) NULL,
    Fullname nvarchar(50) NULL,
    Email nvarchar(150) NULL,
    Admin bit NULL DEFAULT 0,
    Active bit NULL DEFAULT 0,
    Images nvarchar(500) NULL,
    OtpCode nvarchar(10) NULL,
    OtpExpiry datetime NULL,
    UpdatedAt datetime NULL
);
GO

CREATE TABLE Categories (
    CategoryId int IDENTITY(1,1) PRIMARY KEY,
    CategoryName nvarchar(100) NOT NULL
);
GO

CREATE TABLE Videos (
    VideoId int IDENTITY(1,1) PRIMARY KEY,
    Title nvarchar(200) NULL,
    Poster nvarchar(50) NULL,
    Views int NULL DEFAULT 0,
    Description nvarchar(500) NULL,
    Active bit NULL DEFAULT 1,
    CategoryId int NULL,
    CONSTRAINT FK_Videos_Categories FOREIGN KEY(CategoryId) REFERENCES Categories(CategoryId)
);
GO

CREATE TABLE Shares (
    ShareId int IDENTITY(1,1) PRIMARY KEY,
    Emails nvarchar(50) NULL,
    SharedDate date NULL,
    Username nvarchar(50) NULL,
    VideoId int NULL,
    CONSTRAINT FK_Shares_Users FOREIGN KEY(Username) REFERENCES Users(Username),
    CONSTRAINT FK_Shares_Videos FOREIGN KEY(VideoId) REFERENCES Videos(VideoId)
);
GO

CREATE TABLE Favorites (
    FavoriteId int IDENTITY(1,1) PRIMARY KEY,
    LikedDate date NULL,
    VideoId int NULL,
    Username nvarchar(50) NULL,
    CONSTRAINT FK_Favorites_Users FOREIGN KEY(Username) REFERENCES Users(Username),
    CONSTRAINT FK_Favorites_Videos FOREIGN KEY(VideoId) REFERENCES Videos(VideoId)
);
GO

INSERT INTO Categories(CategoryName) VALUES
(N'Âm nhạc'),(N'Giải trí'),(N'Công nghệ'),(N'Học tập');
GO

-- --------------------------------------------------------------------------
-- Users: 2 tai khoan demo goc cua SHOPVIDEO + 2 tai khoan gop tu
-- ShoppingServletDB (roleid=2 MANAGER, roleid=3 USER -> Admin=0).
-- Tai khoan 'admin' cua ShoppingServletDB (admin/123456) bi trung Username
-- voi 'admin' cua SHOPVIDEO nen KHONG duoc gop; giu ban goc admin/123 vi
-- do la tai khoan demo dang duoc ghi trong login.jsp.
-- --------------------------------------------------------------------------
INSERT INTO Users(Username,Password,Phone,Fullname,Email,Admin,Active) VALUES
(N'admin',  N'123',    N'0900000000', N'Quản trị viên', N'admin@gmail.com',   1, 1),
(N'user',   N'123',    N'0911111111', N'Người dùng',    N'user@gmail.com',   0, 1),
(N'manager',N'123456', N'0900000000', N'Nguoi Quan Ly', N'manager@gmail.com',0, 1),
(N'mthu',   N'123456', N'0900000000', N'Minh Thu',      N'mthu@gmail.com',   0, 1);
GO

INSERT INTO Videos(Title,Poster,Views,Description,Active,CategoryId) VALUES
(N'Video âm nhạc 01',N'v1.jpg',120,N'Video mẫu số 01',1,1),
(N'Video âm nhạc 02',N'v2.jpg',90,N'Video mẫu số 02',1,1),
(N'Video âm nhạc 03',N'v8.jpg',75,N'Video mẫu số 08',1,1),
(N'Video âm nhạc 04',N'v9.jpg',60,N'Video mẫu số 09',1,1),
(N'Video âm nhạc 05',N'v10.jpg',55,N'Video mẫu số 10',1,1),
(N'Video giải trí 01',N'v3.jpg',150,N'Video mẫu số 03',1,2),
(N'Video giải trí 02',N'v4.jpg',70,N'Video mẫu số 04',1,2),
(N'Video giải trí 03',N'v11.jpg',65,N'Video mẫu số 11',1,2),
(N'Video giải trí 04',N'v12.jpg',40,N'Video mẫu số 12',1,2),
(N'Công nghệ 01',N'v5.jpg',210,N'Video mẫu số 05',1,3),
(N'Công nghệ 02',N'v6.jpg',180,N'Video mẫu số 06',1,3),
(N'Công nghệ 03',N'v13.jpg',95,N'Video mẫu số 13',1,3),
(N'Công nghệ 04',N'v14.jpg',88,N'Video mẫu số 14',1,3),
(N'Học tập 01',N'v7.jpg',60,N'Video mẫu số 07',1,4),
(N'Học tập 02',N'v15.jpg',45,N'Video mẫu số 15',1,4);
GO

INSERT INTO Shares(Emails,SharedDate,Username,VideoId) VALUES
(N'a@gmail.com', CAST(GETDATE() AS date), N'user', 1),
(N'b@gmail.com', CAST(GETDATE() AS date), N'user', 1),
(N'c@gmail.com', CAST(GETDATE() AS date), NULL, 2);
GO

INSERT INTO Favorites(LikedDate,VideoId,Username) VALUES
(CAST(GETDATE() AS date), 1, N'user'),
(CAST(GETDATE() AS date), 1, N'admin'),
(CAST(GETDATE() AS date), 3, N'user');
GO
IF COL_LENGTH('dbo.Videos', 'VideoFile') IS NULL
    ALTER TABLE Videos ADD VideoFile nvarchar(255) NULL;
GO

IF NOT EXISTS (SELECT 1 FROM sys.database_principals WHERE name = 'root')
BEGIN
    CREATE USER root FOR LOGIN root;
    ALTER ROLE db_owner ADD MEMBER root;
END
GO