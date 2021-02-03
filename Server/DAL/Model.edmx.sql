
-- --------------------------------------------------
-- Entity Designer DDL Script for SQL Server 2005, 2008, 2012 and Azure
-- --------------------------------------------------
-- Date Created: 02/02/2021 19:43:44
-- Generated from EDMX file: D:\Mislav\ALGEBRA\S6\OICAR\git\OICAR-Project_BLN\Server\DAL\Model.edmx
-- --------------------------------------------------

USE master
DROP DATABASE [OICAR];
GO
CREATE DATABASE [OICAR];
GO

SET QUOTED_IDENTIFIER OFF;
GO
USE [OICAR];
GO
IF SCHEMA_ID(N'dbo') IS NULL EXECUTE(N'CREATE SCHEMA [dbo]');
GO

-- --------------------------------------------------
-- Dropping existing FOREIGN KEY constraints
-- --------------------------------------------------

IF OBJECT_ID(N'[dbo].[FK_EmployerListing]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Listings] DROP CONSTRAINT [FK_EmployerListing];
GO
IF OBJECT_ID(N'[dbo].[FK_EmployeeOffer]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Offers] DROP CONSTRAINT [FK_EmployeeOffer];
GO
IF OBJECT_ID(N'[dbo].[FK_ListingOffer]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Offers] DROP CONSTRAINT [FK_ListingOffer];
GO
IF OBJECT_ID(N'[dbo].[FK_UserReview]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Reviews] DROP CONSTRAINT [FK_UserReview];
GO
IF OBJECT_ID(N'[dbo].[FK_UserReview1]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Reviews] DROP CONSTRAINT [FK_UserReview1];
GO
IF OBJECT_ID(N'[dbo].[FK_LocationListing]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Locations] DROP CONSTRAINT [FK_LocationListing];
GO
IF OBJECT_ID(N'[dbo].[FK_UserTransaction]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Transactions] DROP CONSTRAINT [FK_UserTransaction];
GO
IF OBJECT_ID(N'[dbo].[FK_UserTransaction_PaidTo]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Transactions] DROP CONSTRAINT [FK_UserTransaction_PaidTo];
GO
IF OBJECT_ID(N'[dbo].[FK_WorkCategoryListing]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Listings] DROP CONSTRAINT [FK_WorkCategoryListing];
GO
IF OBJECT_ID(N'[dbo].[FK_WorkTypeListing]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Listings] DROP CONSTRAINT [FK_WorkTypeListing];
GO
IF OBJECT_ID(N'[dbo].[FK_Employer_inherits_User]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Users_Employer] DROP CONSTRAINT [FK_Employer_inherits_User];
GO
IF OBJECT_ID(N'[dbo].[FK_Employee_inherits_User]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Users_Employee] DROP CONSTRAINT [FK_Employee_inherits_User];
GO

-- --------------------------------------------------
-- Dropping existing tables
-- --------------------------------------------------

IF OBJECT_ID(N'[dbo].[WorkCategories]', 'U') IS NOT NULL
    DROP TABLE [dbo].[WorkCategories];
GO
IF OBJECT_ID(N'[dbo].[WorkTypes]', 'U') IS NOT NULL
    DROP TABLE [dbo].[WorkTypes];
GO
IF OBJECT_ID(N'[dbo].[Listings]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Listings];
GO
IF OBJECT_ID(N'[dbo].[Users]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Users];
GO
IF OBJECT_ID(N'[dbo].[Locations]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Locations];
GO
IF OBJECT_ID(N'[dbo].[Reviews]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Reviews];
GO
IF OBJECT_ID(N'[dbo].[Offers]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Offers];
GO
IF OBJECT_ID(N'[dbo].[Transactions]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Transactions];
GO
IF OBJECT_ID(N'[dbo].[Users_Employer]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Users_Employer];
GO
IF OBJECT_ID(N'[dbo].[Users_Employee]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Users_Employee];
GO

-- --------------------------------------------------
-- Creating all tables
-- --------------------------------------------------

-- Creating table 'WorkCategories'
CREATE TABLE [dbo].[WorkCategories] (
    [IdWorkCategory] int IDENTITY(1,1) NOT NULL,
    [Title] nvarchar(max)  NOT NULL
);
GO

-- Creating table 'WorkTypes'
CREATE TABLE [dbo].[WorkTypes] (
    [IdWorkType] int IDENTITY(1,1) NOT NULL,
    [Title] nvarchar(max)  NOT NULL
);
GO

-- Creating table 'Listings'
CREATE TABLE [dbo].[Listings] (
    [IdListing] int IDENTITY(1,1) NOT NULL,
    [Title] nvarchar(max)  NOT NULL,
    [Description] nvarchar(max)  NOT NULL,
    [EmployerIdUser] int  NOT NULL,
    [ToolsRequired] bit  NOT NULL,
    [WorkCategoryId] int  NOT NULL,
    [WorkTypeId] int  NOT NULL,
    [IsListed] bit  NOT NULL DEFAULT 1
);
GO

-- Creating table 'Users'
CREATE TABLE [dbo].[Users] (
    [IdUser] int IDENTITY(1,1) NOT NULL,
    [FirstName] nvarchar(max)  NOT NULL,
    [LastName] nvarchar(max)  NOT NULL,
    [Email] nvarchar(max)  NOT NULL,
    [PhoneNumber] nvarchar(max)  NOT NULL,
    [PasswordHash] nvarchar(max)  NOT NULL,
    [PasswordSalt] nvarchar(max)  NOT NULL,
    [Balance] float  NOT NULL
);
GO

-- Creating table 'Locations'
CREATE TABLE [dbo].[Locations] (
    [IdLocation] int IDENTITY(1,1) NOT NULL,
    [Title] nvarchar(max)  NOT NULL,
    [Coordinates] geography  NOT NULL,
    [Listing_IdListing] int  NOT NULL
);
GO

-- Creating table 'Reviews'
CREATE TABLE [dbo].[Reviews] (
    [IdReview] int IDENTITY(1,1) NOT NULL,
    [Grade] int  NOT NULL,
    [Comment] nvarchar(max)  NOT NULL,
    [UserIdUser] int  NOT NULL,
    [UserReviewed_IdUser] int  NOT NULL
);
GO

-- Creating table 'Offers'
CREATE TABLE [dbo].[Offers] (
    [IdOffer] int IDENTITY(1,1) NOT NULL,
    [EmployeeIdUser] int  NOT NULL,
    [ListingIdListing] int  NOT NULL,
    [Price] float  NOT NULL,
    [HasTools] bit  NOT NULL,
    [IsAccepted] bit  NOT NULL DEFAULT 0
);
GO

-- Creating table 'Transactions'
CREATE TABLE [dbo].[Transactions] (
    [IdTransaction] int IDENTITY(1,1) NOT NULL,
    [Time] datetime  NOT NULL,
    [Balance] nvarchar(max)  NOT NULL,
    [UserIdPaid] int  NOT NULL,
    [UserIdPaidTo] int  NOT NULL,
    [Note] nvarchar(max)  NOT NULL
);
GO

-- Creating table 'Users_Employer'
CREATE TABLE [dbo].[Users_Employer] (
    [IdUser] int  NOT NULL
);
GO

-- Creating table 'Users_Employee'
CREATE TABLE [dbo].[Users_Employee] (
    [IBAN] nvarchar(max)  NOT NULL,
    [IdUser] int  NOT NULL
);
GO

-- --------------------------------------------------
-- Creating all PRIMARY KEY constraints
-- --------------------------------------------------

-- Creating primary key on [IdWorkCategory] in table 'WorkCategories'
ALTER TABLE [dbo].[WorkCategories]
ADD CONSTRAINT [PK_WorkCategories]
    PRIMARY KEY CLUSTERED ([IdWorkCategory] ASC);
GO

-- Creating primary key on [IdWorkType] in table 'WorkTypes'
ALTER TABLE [dbo].[WorkTypes]
ADD CONSTRAINT [PK_WorkTypes]
    PRIMARY KEY CLUSTERED ([IdWorkType] ASC);
GO

-- Creating primary key on [IdListing] in table 'Listings'
ALTER TABLE [dbo].[Listings]
ADD CONSTRAINT [PK_Listings]
    PRIMARY KEY CLUSTERED ([IdListing] ASC);
GO

-- Creating primary key on [IdUser] in table 'Users'
ALTER TABLE [dbo].[Users]
ADD CONSTRAINT [PK_Users]
    PRIMARY KEY CLUSTERED ([IdUser] ASC);
GO

-- Creating primary key on [IdLocation] in table 'Locations'
ALTER TABLE [dbo].[Locations]
ADD CONSTRAINT [PK_Locations]
    PRIMARY KEY CLUSTERED ([IdLocation] ASC);
GO

-- Creating primary key on [IdReview] in table 'Reviews'
ALTER TABLE [dbo].[Reviews]
ADD CONSTRAINT [PK_Reviews]
    PRIMARY KEY CLUSTERED ([IdReview] ASC);
GO

-- Creating primary key on [IdOffer] in table 'Offers'
ALTER TABLE [dbo].[Offers]
ADD CONSTRAINT [PK_Offers]
    PRIMARY KEY CLUSTERED ([IdOffer] ASC);
GO

-- Creating primary key on [IdTransaction] in table 'Transactions'
ALTER TABLE [dbo].[Transactions]
ADD CONSTRAINT [PK_Transactions]
    PRIMARY KEY CLUSTERED ([IdTransaction] ASC);
GO

-- Creating primary key on [IdUser] in table 'Users_Employer'
ALTER TABLE [dbo].[Users_Employer]
ADD CONSTRAINT [PK_Users_Employer]
    PRIMARY KEY CLUSTERED ([IdUser] ASC);
GO

-- Creating primary key on [IdUser] in table 'Users_Employee'
ALTER TABLE [dbo].[Users_Employee]
ADD CONSTRAINT [PK_Users_Employee]
    PRIMARY KEY CLUSTERED ([IdUser] ASC);
GO

-- --------------------------------------------------
-- Creating all FOREIGN KEY constraints
-- --------------------------------------------------

-- Creating foreign key on [EmployerIdUser] in table 'Listings'
ALTER TABLE [dbo].[Listings]
ADD CONSTRAINT [FK_EmployerListing]
    FOREIGN KEY ([EmployerIdUser])
    REFERENCES [dbo].[Users_Employer]
        ([IdUser])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_EmployerListing'
CREATE INDEX [IX_FK_EmployerListing]
ON [dbo].[Listings]
    ([EmployerIdUser]);
GO

-- Creating foreign key on [EmployeeIdUser] in table 'Offers'
ALTER TABLE [dbo].[Offers]
ADD CONSTRAINT [FK_EmployeeOffer]
    FOREIGN KEY ([EmployeeIdUser])
    REFERENCES [dbo].[Users_Employee]
        ([IdUser])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_EmployeeOffer'
CREATE INDEX [IX_FK_EmployeeOffer]
ON [dbo].[Offers]
    ([EmployeeIdUser]);
GO

-- Creating foreign key on [ListingIdListing] in table 'Offers'
ALTER TABLE [dbo].[Offers]
ADD CONSTRAINT [FK_ListingOffer]
    FOREIGN KEY ([ListingIdListing])
    REFERENCES [dbo].[Listings]
        ([IdListing])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_ListingOffer'
CREATE INDEX [IX_FK_ListingOffer]
ON [dbo].[Offers]
    ([ListingIdListing]);
GO

-- Creating foreign key on [UserIdUser] in table 'Reviews'
ALTER TABLE [dbo].[Reviews]
ADD CONSTRAINT [FK_UserReview]
    FOREIGN KEY ([UserIdUser])
    REFERENCES [dbo].[Users]
        ([IdUser])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_UserReview'
CREATE INDEX [IX_FK_UserReview]
ON [dbo].[Reviews]
    ([UserIdUser]);
GO

-- Creating foreign key on [UserReviewed_IdUser] in table 'Reviews'
ALTER TABLE [dbo].[Reviews]
ADD CONSTRAINT [FK_UserReview1]
    FOREIGN KEY ([UserReviewed_IdUser])
    REFERENCES [dbo].[Users]
        ([IdUser])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_UserReview1'
CREATE INDEX [IX_FK_UserReview1]
ON [dbo].[Reviews]
    ([UserReviewed_IdUser]);
GO

-- Creating foreign key on [Listing_IdListing] in table 'Locations'
ALTER TABLE [dbo].[Locations]
ADD CONSTRAINT [FK_LocationListing]
    FOREIGN KEY ([Listing_IdListing])
    REFERENCES [dbo].[Listings]
        ([IdListing])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_LocationListing'
CREATE INDEX [IX_FK_LocationListing]
ON [dbo].[Locations]
    ([Listing_IdListing]);
GO

-- Creating foreign key on [UserIdPaid] in table 'Transactions'
ALTER TABLE [dbo].[Transactions]
ADD CONSTRAINT [FK_UserTransaction]
    FOREIGN KEY ([UserIdPaid])
    REFERENCES [dbo].[Users]
        ([IdUser])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_UserTransaction'
CREATE INDEX [IX_FK_UserTransaction]
ON [dbo].[Transactions]
    ([UserIdPaid]);
GO

-- Creating foreign key on [UserIdPaidTo] in table 'Transactions'
ALTER TABLE [dbo].[Transactions]
ADD CONSTRAINT [FK_UserTransaction_PaidTo]
    FOREIGN KEY ([UserIdPaidTo])
    REFERENCES [dbo].[Users]
        ([IdUser])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_UserTransaction_PaidTo'
CREATE INDEX [IX_FK_UserTransaction_PaidTo]
ON [dbo].[Transactions]
    ([UserIdPaidTo]);
GO

-- Creating foreign key on [WorkCategoryId] in table 'Listings'
ALTER TABLE [dbo].[Listings]
ADD CONSTRAINT [FK_WorkCategoryListing]
    FOREIGN KEY ([WorkCategoryId])
    REFERENCES [dbo].[WorkCategories]
        ([IdWorkCategory])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_WorkCategoryListing'
CREATE INDEX [IX_FK_WorkCategoryListing]
ON [dbo].[Listings]
    ([WorkCategoryId]);
GO

-- Creating foreign key on [WorkTypeId] in table 'Listings'
ALTER TABLE [dbo].[Listings]
ADD CONSTRAINT [FK_WorkTypeListing]
    FOREIGN KEY ([WorkTypeId])
    REFERENCES [dbo].[WorkTypes]
        ([IdWorkType])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_WorkTypeListing'
CREATE INDEX [IX_FK_WorkTypeListing]
ON [dbo].[Listings]
    ([WorkTypeId]);
GO

-- Creating foreign key on [IdUser] in table 'Users_Employer'
ALTER TABLE [dbo].[Users_Employer]
ADD CONSTRAINT [FK_Employer_inherits_User]
    FOREIGN KEY ([IdUser])
    REFERENCES [dbo].[Users]
        ([IdUser])
    ON DELETE CASCADE ON UPDATE NO ACTION;
GO

-- Creating foreign key on [IdUser] in table 'Users_Employee'
ALTER TABLE [dbo].[Users_Employee]
ADD CONSTRAINT [FK_Employee_inherits_User]
    FOREIGN KEY ([IdUser])
    REFERENCES [dbo].[Users]
        ([IdUser])
    ON DELETE CASCADE ON UPDATE NO ACTION;
GO

-- --------------------------------------------------
-- Script has ended
-- --------------------------------------------------