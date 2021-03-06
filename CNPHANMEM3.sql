USE [master]
GO
/****** Object:  Database [CNPHANMEM3]    Script Date: 6/13/2021 11:55:54 PM ******/
CREATE DATABASE [CNPHANMEM3]
GO
ALTER DATABASE [CNPHANMEM3] SET QUERY_STORE = OFF
GO
USE [CNPHANMEM3]
GO
/****** Object:  UserDefinedFunction [dbo].[F_getNgayBD]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create function [dbo].[F_getNgayBD](@MaGV char(20),@NamHoc smallint,@HocKy tinyint)
returns date
as
begin
	declare @NgayBD date
	select @NgayBD=Min(LLD.NgayBD) from F_getLichDayTM(@MaGV,@NamHoc,@HocKy)as LLD
	return @NgayBD
end
GO
/****** Object:  UserDefinedFunction [dbo].[F_getNgayBDHocKy]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[F_getNgayBDHocKy](@NamHoc smallint,@HocKy tinyint)
RETURNS date
AS
begin
	declare @NgayBD date
	select @NgayBD=Min(LLD.NgayBD) from F_getLichDayTheoHocKy(@NamHoc,@HocKy)as LLD
	return @NgayBD
end
GO
/****** Object:  UserDefinedFunction [dbo].[F_getNgayKT]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE function [dbo].[F_getNgayKT](@MaGV char(20),@NamHoc smallint,@HocKy tinyint)
returns date
as
begin
	declare @NgayKT date
	select @NgayKT=Max(LLD.NgayKT) from F_getLichDayTM(@MaGV,@NamHoc,@HocKy)as LLD
	return @NgayKT
end
GO
/****** Object:  UserDefinedFunction [dbo].[F_getNgayKTHocKy]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create function [dbo].[F_getNgayKTHocKy](@NamHoc smallint,@HocKy tinyint)
returns date
as
begin
	declare @NgayKT date
	select @NgayKT=Max(LLD.NgayKT) from F_getLichDayTheoHocKy(@NamHoc,@HocKy)as LLD
	return @NgayKT
end
GO
/****** Object:  UserDefinedFunction [dbo].[F_maxHocKi]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE function [dbo].[F_maxHocKi]()
returns tinyint
as
BEGIN
	
	DECLARE @maxHocKi TINYINT, @HocKi1 BIT,@HocKi2 BIT,@HocKi3 BIT
	SET @maxHocKi =0 
	SET @HocKi1=0
	SET @HocKi2=0
	SET @HocKi3=0
    DECLARE @tabletmp TABLE (NamHoc SMALLINT,HocKy TINYINT,ID INT IDENTITY(1,1))
	INSERT INTO @tabletmp(NamHoc,HocKy) select * from F_getListNamHoc() as LNH where LNH.NamHoc=dbo.F_maxNamHoc()
	DECLARE @id INT
    DECLARE @tmp TINYINT
	WHILE(SELECT COUNT(*) FROM @tabletmp)>0
		BEGIN
			SELECT TOP 1 @id=ID,@tmp=HocKy FROM @tabletmp
			IF (@tmp=1 ) SET @HocKi1=1
			IF (@tmp=2) SET @HocKi2=1
			IF (@tmp=3) SET @HocKi3=1
			DELETE @tabletmp WHERE ID=@id
        END
	IF(@HocKi1=1) SET @maxHocKi=1
		ELSE 
			BEGIN
				IF(@HocKi3=1) SET @maxHocKi=3
				ELSE SET @maxHocKi=2
            END

	return @maxHocKi
END
GO
/****** Object:  UserDefinedFunction [dbo].[F_maxNamHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE function [dbo].[F_maxNamHoc]()
returns smallint
as
begin
	declare @maxNamHoc smallint
	select @maxNamHoc=Max(LNH.NamHoc) from F_getListNamHoc() as LNH
	return @maxNamHoc
end
GO
/****** Object:  UserDefinedFunction [dbo].[F_minHocKi]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE function [dbo].[F_minHocKi]()
RETURNS TINYINT
AS
BEGIN
	
	DECLARE @minHocKi TINYINT, @HocKi1 BIT,@HocKi2 BIT,@HocKi3 BIT
	SET @minHocKi =0 
	SET @HocKi1=0
	SET @HocKi2=0
	SET @HocKi3=0
    DECLARE @tabletmp TABLE (NamHoc SMALLINT,HocKy TINYINT,ID INT IDENTITY(1,1))
	INSERT INTO @tabletmp(NamHoc,HocKy) select * from F_getListNamHoc() as LNH where LNH.NamHoc=dbo.F_minNamHoc()
	DECLARE @id INT
    DECLARE @tmp TINYINT
	WHILE(SELECT COUNT(*) FROM @tabletmp)>0
		BEGIN
			SELECT TOP 1 @id=ID,@tmp=HocKy FROM @tabletmp
			IF (@tmp=1 ) SET @HocKi1=1
			IF (@tmp=2) SET @HocKi2=1
			IF (@tmp=3) SET @HocKi3=1
			DELETE @tabletmp WHERE ID=@id
        END
	IF(@HocKi2=1) SET @minHocKi=2
		ELSE 
			BEGIN
				IF(@HocKi3=1) SET @minHocKi=3
				ELSE SET @minHocKi=1
            END

	RETURN @minHocKi
END
GO
/****** Object:  UserDefinedFunction [dbo].[F_minNamHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- END1--

-- BEGIN 2--
CREATE FUNCTION [dbo].[F_minNamHoc]()
RETURNS smallInt 
AS 
BEGIN 
	DECLARE @minNamHoc SMALLINT
	SELECT @minNamHoc=MIN(LNH.NamHoc) FROM F_getListNamHoc() AS LNH
	RETURN @minNamHoc
END
GO
/****** Object:  UserDefinedFunction [dbo].[F_slLopMonHocGVDay]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- END2--

-- BEGIN3--
CREATE FUNCTION [dbo].[F_slLopMonHocGVDay](@MaGV varchar(20),@NamHoc smallint, @HocKy tinyint)
RETURNS int 
AS 
BEGIN 
	DECLARE @tmp INT
	SET @tmp=0
	SELECT @tmp=COUNT(*) FROM dbo.GVDayLopMonHoc AS gvd, dbo.LopMonHoc AS lmh
	WHERE gvd.MaLopMH=lmh.MaLopMH AND gvd.MaGV= @MaGV AND lmh.NamHoc = @NamHoc AND lmh.HocKy=@HocKy
	RETURN @tmp
END
GO
/****** Object:  UserDefinedFunction [dbo].[FSV_getListLopHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- begin 1--
CREATE FUNCTION [dbo].[FSV_getListLopHoc](@MaSV varchar(20))
RETURNS @tmptable1 TABLE(ID INT ,MaLopMH VARCHAR(20), MSSV VARCHAR(20), IDNhomTH TINYINT,NamHoc SMALLINT,HocKY TINYINT,Lop VARCHAR(20),TenMH NVARCHAR(50),MaGV VARCHAR(20),IDVaiTro TINYINT,ChuThich NVARCHAR(20))
AS 
BEGIN
	DECLARE @tmptable TABLE (ID INT IDENTITY(1,1),MaLopMH VARCHAR(20), MSSV VARCHAR(20), IDNhomTH TINYINT,NamHoc SMALLINT,HocKY TINYINT,Lop VARCHAR(20),TenMH NVARCHAR(50),MaGV VARCHAR(20),IDVaiTro TINYINT,ChuThich NVARCHAR(20))
	INSERT INTO @tmptable(MaLopMH,MSSV,IDNhomTH,NamHoc,HocKY,Lop,TenMH,MaGV,IDVaiTro,ChuThich) SELECT svhlmh.*,lmh.NamHoc,lmh.HocKy,lmh.Lop,mh.TenMH,gv.MaGV,gv.IDVaiTro,vt.ChuThich FROM dbo.SVHocLopMonHoc AS svhlmh, dbo.LopMonHoc AS lmh,dbo.MonHoc AS mh,dbo.GVDayLopMonHoc AS gv,dbo.LoaiNhomTH AS vt
	WHERE svhlmh.MSSV=@MaSV AND  svhlmh.MaLopMH=lmh.MaLopMH AND lmh.MaMH = mh.MaMH AND gv.MaLopMH = svhlmh.MaLopMH AND svhlmh.IDNhomTH = vt.IDNhomTH
	DECLARE @ID INT
	DECLARE @IDNhomTH INT
	DECLARE @IDVaiTro INT 

	WHILE (SELECT COUNT(*) FROM @tmptable)>0
		BEGIN
			SELECT TOP 1 @ID=ID,@IDNhomTH=IDNhomTH,@IDVaiTro=IDVaiTro FROM @tmptable
			IF (@IDNhomTH =0 AND @IDVaiTro =0) OR (@IDNhomTH >0 AND @IDVaiTro = 1)
				INSERT INTO @tmptable1(ID,MaLopMH,MSSV,IDNhomTH,NamHoc,HocKY,Lop,TenMH,MaGV,IDVaiTro,ChuThich) SELECT * FROM @tmptable WHERE ID = @ID 
			DELETE @tmptable WHERE ID=@ID
		END
	RETURN  
END
GO
/****** Object:  UserDefinedFunction [dbo].[FSV_maxHocKi]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE function [dbo].[FSV_maxHocKi](@MSSV varchar(20))
returns tinyint
as
BEGIN
	
	DECLARE @maxHocKi TINYINT, @HocKi1 BIT,@HocKi2 BIT,@HocKi3 BIT
	SET @maxHocKi =0 
	SET @HocKi1=0
	SET @HocKi2=0
	SET @HocKi3=0
    DECLARE @tabletmp TABLE (NamHoc SMALLINT,HocKy TINYINT,ID INT IDENTITY(1,1))
	INSERT INTO @tabletmp(NamHoc,HocKy) select * from FSV_getListNamHoc(@MSSV) as LNH where LNH.NamHoc=dbo.FSV_maxNamHoc(@MSSV)
	DECLARE @id INT
    DECLARE @tmp TINYINT
	WHILE(SELECT COUNT(*) FROM @tabletmp)>0
		BEGIN
			SELECT TOP 1 @id=ID,@tmp=HocKy FROM @tabletmp
			IF (@tmp=1 ) SET @HocKi1=1
			IF (@tmp=2) SET @HocKi2=1
			IF (@tmp=3) SET @HocKi3=1
			DELETE @tabletmp WHERE ID=@id
        END
	IF(@HocKi1=1) SET @maxHocKi=1
		ELSE 
			BEGIN
				IF(@HocKi3=1) SET @maxHocKi=3
				ELSE SET @maxHocKi=2
            END

	return @maxHocKi
END
GO
/****** Object:  UserDefinedFunction [dbo].[FSV_maxNamHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE function [dbo].[FSV_maxNamHoc](@MSSV varchar(20))
returns smallint
as
begin
	declare @maxNamHoc smallint
	select @maxNamHoc=Max(LNH.NamHoc) from FSV_getListNamHoc(@MSSV) as LNH
	return @maxNamHoc
end
GO
/****** Object:  Table [dbo].[LichDay]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LichDay](
	[MaLopMH] [varchar](20) NOT NULL,
	[IDNhomTH] [tinyint] NOT NULL,
	[BuoiHoc] [tinyint] NOT NULL,
	[Ca] [bit] NOT NULL,
	[NgayBD] [date] NOT NULL,
	[NgayKT] [date] NOT NULL,
 CONSTRAINT [PK_LichHoc] PRIMARY KEY CLUSTERED 
(
	[MaLopMH] ASC,
	[IDNhomTH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LopMonHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LopMonHoc](
	[MaLopMH] [varchar](20) NOT NULL,
	[HocKy] [tinyint] NOT NULL,
	[NamHoc] [smallint] NOT NULL,
	[MaMH] [varchar](7) NOT NULL,
	[Lop] [varchar](20) NOT NULL,
 CONSTRAINT [PK_MonHoc_LopMonHoc_1] PRIMARY KEY CLUSTERED 
(
	[MaLopMH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GVDayLopMonHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GVDayLopMonHoc](
	[MaLopMH] [varchar](20) NOT NULL,
	[MaGV] [varchar](20) NOT NULL,
	[IDVaiTro] [tinyint] NOT NULL,
 CONSTRAINT [PK_GVDayLopMonHoc] PRIMARY KEY CLUSTERED 
(
	[MaLopMH] ASC,
	[MaGV] ASC,
	[IDVaiTro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[F_getLichDayTM]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE function [dbo].[F_getLichDayTM](@MaGV char(20),@NamHoc smallint,@HocKy tinyint)
returns table
as
return select ld.MaLopMH,ld.NgayBD,ld.NgayKT from dbo.GVDayLopMonHoc as gvd,dbo.LopMonHoc as lmh,dbo.LichDay as ld
where gvd.MaGV=@MaGV and lmh.MaLopMH=gvd.MaLopMH and lmh.HocKy=@HocKy and lmh.NamHoc=@NamHoc
and lmh.MaLopMH=ld.MaLopMH
GO
/****** Object:  UserDefinedFunction [dbo].[F_getListNamHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

--BEGIN 1---
CREATE function [dbo].[F_getListNamHoc]()
returns table
as return
select lmh.NamHoc,lmh.HocKy from dbo.LopMonHoc as lmh

GO
/****** Object:  Table [dbo].[MonHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MonHoc](
	[MaMH] [varchar](7) NOT NULL,
	[TenMH] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK__MonHoc__4127737FBE360FD9] PRIMARY KEY CLUSTERED 
(
	[MaMH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[F_getListLopMonHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE function [dbo].[F_getListLopMonHoc](@MaGV char(20))
returns table
as return
select lmh.*,gvdlmh.IDVaiTro,mh.TenMH from dbo.GVDayLopMonHoc as gvdlmh,dbo.LopMonHoc as lmh,dbo.MonHoc as mh
where gvdlmh.MaGV=@MaGV and gvdlmh.MaLopMH=lmh.MaLopMH and mh.MaMH=lmh.MaMH
GO
/****** Object:  UserDefinedFunction [dbo].[F_getListLMHTheoHocKy]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create function [dbo].[F_getListLMHTheoHocKy](@MaGV char(20),@NamHoc smallint,@HocKy tinyint)
returns table
as return
select lmh.*,gvdlmh.IDVaiTro,mh.TenMH from dbo.GVDayLopMonHoc as gvdlmh, dbo.LopMonHoc as lmh,dbo.MonHoc as mh
where lmh.NamHoc=@NamHoc and lmh.HocKy=@HocKy and gvdlmh.MaLopMH=lmh.MaLopMH and gvdlmh.MaGV=@MaGV and mh.MaMH=lmh.MaMH
GO
/****** Object:  Table [dbo].[LoaiNhomTH]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiNhomTH](
	[IDNhomTH] [tinyint] NOT NULL,
	[ChuThich] [nvarchar](30) NOT NULL,
 CONSTRAINT [PK_LoaiNhomTH] PRIMARY KEY CLUSTERED 
(
	[IDNhomTH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[F_getLichDayChinh]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create function [dbo].[F_getLichDayChinh](@MaLopMH varchar(20))
returns table
as return 
select ld.*,lnth.ChuThich from dbo.LichDay as ld,dbo.LoaiNhomTH as lnth
where ld.MaLopMH=@MaLopMH and ld.IDNhomTH = lnth.IDNhomTH
GO
/****** Object:  Table [dbo].[SVDiemDanh]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SVDiemDanh](
	[MaBH] [varchar](20) NOT NULL,
	[MSSV] [varchar](20) NOT NULL,
	[IDDiemDanh] [int] NOT NULL,
 CONSTRAINT [PK_InfoDiemDanh] PRIMARY KEY CLUSTERED 
(
	[MaBH] ASC,
	[MSSV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[F_getListSVDD]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE function [dbo].[F_getListSVDD](@MaBH varchar(20))
returns table
as return
select * from dbo.SVDiemDanh as DiemDanh
where DiemDanh.MaBH=@MaBH
GO
/****** Object:  Table [dbo].[NhomTHBuoiHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhomTHBuoiHoc](
	[MaBH] [varchar](20) NOT NULL,
	[MaLopMH] [varchar](20) NOT NULL,
	[IDNhomTH] [tinyint] NOT NULL,
 CONSTRAINT [PK_BuoiHocExtend] PRIMARY KEY CLUSTERED 
(
	[MaBH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BuoiHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BuoiHoc](
	[MaBH] [varchar](20) NOT NULL,
	[MaLopMH] [varchar](20) NOT NULL,
	[MaGV] [varchar](20) NOT NULL,
	[IDVaiTro] [tinyint] NOT NULL,
	[Ngay] [date] NOT NULL,
	[Ca] [bit] NOT NULL,
 CONSTRAINT [PK_BuoiHoc_1] PRIMARY KEY CLUSTERED 
(
	[MaBH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[F_getListBuoiHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE function [dbo].[F_getListBuoiHoc](@MaGV char(20),@MaLopMH varchar(20))
returns table
as return
select bh.*,lnth.ChuThich from dbo.BuoiHoc as bh,dbo.NhomTHBuoiHoc as nthbh, dbo.LoaiNhomTH as lnth
where bh.MaGV=@MaGV and bh.MaLopMH=@MaLopMH and nthbh.MaBH=bh.MaBH and nthbh.IDNhomTH = lnth.IDNhomTH
GO
/****** Object:  Table [dbo].[SVHocLopMonHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SVHocLopMonHoc](
	[MaLopMH] [varchar](20) NOT NULL,
	[MSSV] [varchar](20) NOT NULL,
	[IDNhomTH] [tinyint] NOT NULL,
 CONSTRAINT [PK_SVHocLopMonHoc] PRIMARY KEY CLUSTERED 
(
	[MaLopMH] ASC,
	[MSSV] ASC,
	[IDNhomTH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[F_getListSVLopMonHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--select * from LopMonHoc
--select * from LichDay --as l, GVDayLopMonHoc as gv where gv.MaLopMH = l.MaLopMH and gv.MaGV = 'GV001'
create function [dbo].[F_getListSVLopMonHoc](@MaLMH varchar(20))
returns table
as return
select * from dbo.SVHocLopMonHoc as svhlmh
where svhlmh.MaLopMH=@MaLMH
GO
/****** Object:  UserDefinedFunction [dbo].[F_getListSVLMHTheoNhom]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE function [dbo].[F_getListSVLMHTheoNhom](@MaLMH varchar(20), @IDNhomTH smallint)
returns table
as return
select svhlmh.*,lnth.ChuThich from dbo.SVHocLopMonHoc as svhlmh,dbo.LoaiNhomTH as lnth
where svhlmh.MaLopMH=@MaLMH and svhlmh.IDNhomTH=@IDNhomTH AND lnth.IDNhomTH=svhlmh.IDNhomTH
GO
/****** Object:  UserDefinedFunction [dbo].[F_getListVaiTroGV]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create function [dbo].[F_getListVaiTroGV](@MaGV varchar(20),@MaLMH varchar(20))
returns table
as return
select gvdlmh.IDVaiTro from dbo.GVDayLopMonHoc as gvdlmh
where gvdlmh.MaGV=@MaGV and gvdlmh.MaLopMH=@MaLMH
GO
/****** Object:  Table [dbo].[GVThongBao]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GVThongBao](
	[MaTB] [varchar](20) NOT NULL,
	[MaLopMH] [varchar](20) NOT NULL,
	[MaGV] [varchar](20) NOT NULL,
	[IDVaiTro] [tinyint] NOT NULL,
	[ThongBaoTuDo] [bit] NOT NULL,
 CONSTRAINT [PK_GVThongBao] PRIMARY KEY CLUSTERED 
(
	[MaTB] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NDThongBao]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NDThongBao](
	[MaTB] [varchar](20) NOT NULL,
	[ThoiGian] [datetime] NOT NULL,
	[NoiDung] [nvarchar](300) NOT NULL,
 CONSTRAINT [PK_NDThongBao] PRIMARY KEY CLUSTERED 
(
	[MaTB] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[F_getListGVThongBao]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE function [dbo].[F_getListGVThongBao](@MaGV varchar(20))
returns table
as return
select gvtb.MaLopMH,gvtb.MaTB,nd.ThoiGian,nd.NoiDung, gvtb.ThongBaoTuDo from dbo.GVThongBao as gvtb,dbo.NDThongBao as nd
where gvtb.MaGV=@MaGV and gvtb.MaTB=nd.MaTB
GO
/****** Object:  Table [dbo].[NhomTHLopMonHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhomTHLopMonHoc](
	[MaLopMH] [varchar](20) NOT NULL,
	[IDNhomTH] [tinyint] NOT NULL,
 CONSTRAINT [PK_NhomTHLopMonHoc] PRIMARY KEY CLUSTERED 
(
	[MaLopMH] ASC,
	[IDNhomTH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[F_getIDNhom]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create function [dbo].[F_getIDNhom](@MaLopHoc varchar(20), @TenNhom nvarchar(30))
returns table
as return
Select nhom.IDNhomTH from NhomTHLopMonHoc as nhom, LoaiNhomTH as loaiNhom 
where nhom.IDNhomTH = loaiNhom.IDNhomTH 
and loaiNhom.ChuThich = @TenNhom
and nhom.MaLopMH = @MaLopHoc
GO
/****** Object:  UserDefinedFunction [dbo].[F_getLichDayTheoHocKy]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[F_getLichDayTheoHocKy](@NamHoc smallint,@HocKy tinyint)
RETURNS TABLE
AS RETURN 
SELECT ld.MaLopMH,ld.NgayBD,ld.NgayKT FROM dbo.LopMonHoc AS lmh,dbo.LichDay AS ld
WHERE lmh.HocKy=@HocKy AND lmh.NamHoc=@NamHoc AND lmh.MaLopMH=ld.MaLopMH
GO
/****** Object:  UserDefinedFunction [dbo].[FSV_getListBuoiHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--end1-
-- begin 2---
CREATE FUNCTION [dbo].[FSV_getListBuoiHoc](@MSSV varchar(20), @MaLopMH varchar(20),@ChuThich nvarchar(20),@MaGV varchar(20) )
RETURNS TABLE AS 
RETURN SELECT bh.*,lnth.*,svdd.MSSV,svdd.IDDiemDanh FROM dbo.BuoiHoc AS bh, dbo.NhomTHBuoiHoc AS nthbh, dbo.LoaiNhomTH AS lnth, dbo.SVDiemDanh AS svdd
WHERE bh.MaBH = nthbh.MaBH AND nthbh.IDNhomTH = lnth.IDNhomTH AND svdd.MaBH = bh.MaBH 
AND bh.MaLopMH = @MaLopMH AND bh.MaGV= @MaGV AND lnth.ChuThich = @ChuThich AND svdd.MSSV = @MSSV
GO
/****** Object:  UserDefinedFunction [dbo].[FSV_getListNamHoc]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--end 2--
--begin 3---
CREATE FUNCTION [dbo].[FSV_getListNamHoc](@MSSV varchar(20))
RETURNS TABLE AS 
RETURN SELECT lmh.NamHoc,lmh.HocKy FROM dbo.SVHocLopMonHoc AS svhlmh, dbo.LopMonHoc AS lmh
WHERE svhlmh.MaLopMH = lmh.MaLopMH AND svhlmh.MSSV = @MSSV;
GO
/****** Object:  Table [dbo].[GVThongBaoSV]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GVThongBaoSV](
	[MaTB] [varchar](20) NOT NULL,
	[MSSV] [varchar](20) NOT NULL,
 CONSTRAINT [PK_GVThongBaoSV] PRIMARY KEY CLUSTERED 
(
	[MaTB] ASC,
	[MSSV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GiaoVien]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GiaoVien](
	[MaGV] [varchar](20) NOT NULL,
	[HoTenGV] [nvarchar](50) NOT NULL,
	[GioiTinh] [nvarchar](5) NOT NULL,
	[NgaySinh] [date] NOT NULL,
 CONSTRAINT [PK__giangvie__C03BEEBAC79B8CC9] PRIMARY KEY CLUSTERED 
(
	[MaGV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoaiDiemDanh]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiDiemDanh](
	[IDDiemDanh] [int] NOT NULL,
	[ChuThich] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_LoaiDiemDanh] PRIMARY KEY CLUSTERED 
(
	[IDDiemDanh] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoaiTK]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiTK](
	[IDLoaiTK] [tinyint] NOT NULL,
	[ChuThich] [varchar](50) NOT NULL,
 CONSTRAINT [PK_LoaiQuyen] PRIMARY KEY CLUSTERED 
(
	[IDLoaiTK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LopNienChe]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LopNienChe](
	[MaLopNC] [varchar](20) NOT NULL,
	[TenLopNC] [nvarchar](50) NOT NULL,
	[NienKhoa] [smallint] NULL,
 CONSTRAINT [PK_LopNienChe] PRIMARY KEY CLUSTERED 
(
	[MaLopNC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SinhVien]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SinhVien](
	[MSSV] [varchar](20) NOT NULL,
	[HoTen] [nvarchar](50) NOT NULL,
	[NgaySinh] [date] NOT NULL,
	[GioiTinh] [nvarchar](5) NOT NULL,
	[MaLopNC] [varchar](20) NOT NULL,
 CONSTRAINT [PK__SinhVien__939AE7753BBA86D2] PRIMARY KEY CLUSTERED 
(
	[MSSV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[User] [varchar](20) NOT NULL,
	[Pass] [varchar](20) NOT NULL,
	[IDLoaiTK] [tinyint] NOT NULL,
 CONSTRAINT [PK_TaiKhoan_1] PRIMARY KEY CLUSTERED 
(
	[User] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[VaiTroGV]    Script Date: 6/13/2021 11:55:54 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[VaiTroGV](
	[IDVaiTro] [tinyint] NOT NULL,
	[ChuThich] [varchar](50) NOT NULL,
 CONSTRAINT [PK_LoaiGV] PRIMARY KEY CLUSTERED 
(
	[IDVaiTro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[BuoiHoc] ([MaBH], [MaLopMH], [MaGV], [IDVaiTro], [Ngay], [Ca]) VALUES (N'GV002_2021-06-15_0', N'L_INT1330_21_3_1', N'GV002', 0, CAST(N'2021-06-15' AS Date), 0)
INSERT [dbo].[BuoiHoc] ([MaBH], [MaLopMH], [MaGV], [IDVaiTro], [Ngay], [Ca]) VALUES (N'GV002_2021-06-16_1', N'L_INT1330_21_3_1', N'GV002', 0, CAST(N'2021-06-16' AS Date), 1)
INSERT [dbo].[BuoiHoc] ([MaBH], [MaLopMH], [MaGV], [IDVaiTro], [Ngay], [Ca]) VALUES (N'GV002_2021-06-17_0', N'L_INT1330_21_3_1', N'GV002', 0, CAST(N'2021-06-17' AS Date), 0)
GO
INSERT [dbo].[GVDayLopMonHoc] ([MaLopMH], [MaGV], [IDVaiTro]) VALUES (N'L_INT1328_21_2_1', N'GV004', 0)
INSERT [dbo].[GVDayLopMonHoc] ([MaLopMH], [MaGV], [IDVaiTro]) VALUES (N'L_INT1330_20_1_1', N'GV002', 0)
INSERT [dbo].[GVDayLopMonHoc] ([MaLopMH], [MaGV], [IDVaiTro]) VALUES (N'L_INT1330_21_3_1', N'GV002', 0)
INSERT [dbo].[GVDayLopMonHoc] ([MaLopMH], [MaGV], [IDVaiTro]) VALUES (N'L_INT1332_20_1_1', N'GV001', 0)
INSERT [dbo].[GVDayLopMonHoc] ([MaLopMH], [MaGV], [IDVaiTro]) VALUES (N'L_INT1332_20_1_1', N'GV001', 1)
INSERT [dbo].[GVDayLopMonHoc] ([MaLopMH], [MaGV], [IDVaiTro]) VALUES (N'L_INT1336_21_2_1', N'GV003', 0)
GO
INSERT [dbo].[GVThongBao] ([MaTB], [MaLopMH], [MaGV], [IDVaiTro], [ThongBaoTuDo]) VALUES (N'TB_1', N'L_INT1330_21_3_1', N'GV002', 0, 1)
INSERT [dbo].[GVThongBao] ([MaTB], [MaLopMH], [MaGV], [IDVaiTro], [ThongBaoTuDo]) VALUES (N'TB_2', N'L_INT1330_21_3_1', N'GV002', 0, 0)
GO
INSERT [dbo].[GVThongBaoSV] ([MaTB], [MSSV]) VALUES (N'TB_1', N'SV001')
INSERT [dbo].[GVThongBaoSV] ([MaTB], [MSSV]) VALUES (N'TB_1', N'SV004')
INSERT [dbo].[GVThongBaoSV] ([MaTB], [MSSV]) VALUES (N'TB_1', N'SV010')
INSERT [dbo].[GVThongBaoSV] ([MaTB], [MSSV]) VALUES (N'TB_2', N'SV001')
INSERT [dbo].[GVThongBaoSV] ([MaTB], [MSSV]) VALUES (N'TB_2', N'SV004')
INSERT [dbo].[GVThongBaoSV] ([MaTB], [MSSV]) VALUES (N'TB_2', N'SV006')
INSERT [dbo].[GVThongBaoSV] ([MaTB], [MSSV]) VALUES (N'TB_2', N'SV010')
GO
INSERT [dbo].[GiaoVien] ([MaGV], [HoTenGV], [GioiTinh], [NgaySinh]) VALUES (N'GV001', N'Lê A', N'Nữ', CAST(N'1989-12-20' AS Date))
INSERT [dbo].[GiaoVien] ([MaGV], [HoTenGV], [GioiTinh], [NgaySinh]) VALUES (N'GV002', N'Lê B', N'Nữ', CAST(N'1970-02-20' AS Date))
INSERT [dbo].[GiaoVien] ([MaGV], [HoTenGV], [GioiTinh], [NgaySinh]) VALUES (N'GV003', N'Lê C', N'Nam', CAST(N'1975-12-01' AS Date))
INSERT [dbo].[GiaoVien] ([MaGV], [HoTenGV], [GioiTinh], [NgaySinh]) VALUES (N'GV004', N'Lê D', N'Nam', CAST(N'1985-06-03' AS Date))
GO
INSERT [dbo].[LichDay] ([MaLopMH], [IDNhomTH], [BuoiHoc], [Ca], [NgayBD], [NgayKT]) VALUES (N'L_INT1328_21_2_1', 0, 7, 0, CAST(N'2021-03-01' AS Date), CAST(N'2021-04-11' AS Date))
INSERT [dbo].[LichDay] ([MaLopMH], [IDNhomTH], [BuoiHoc], [Ca], [NgayBD], [NgayKT]) VALUES (N'L_INT1330_20_1_1', 0, 2, 1, CAST(N'2020-09-07' AS Date), CAST(N'2020-11-08' AS Date))
INSERT [dbo].[LichDay] ([MaLopMH], [IDNhomTH], [BuoiHoc], [Ca], [NgayBD], [NgayKT]) VALUES (N'L_INT1330_21_3_1', 0, 2, 1, CAST(N'2021-06-07' AS Date), CAST(N'2021-08-01' AS Date))
INSERT [dbo].[LichDay] ([MaLopMH], [IDNhomTH], [BuoiHoc], [Ca], [NgayBD], [NgayKT]) VALUES (N'L_INT1332_20_1_1', 0, 7, 1, CAST(N'2020-10-19' AS Date), CAST(N'2020-11-15' AS Date))
INSERT [dbo].[LichDay] ([MaLopMH], [IDNhomTH], [BuoiHoc], [Ca], [NgayBD], [NgayKT]) VALUES (N'L_INT1332_20_1_1', 1, 3, 1, CAST(N'2020-11-09' AS Date), CAST(N'2020-12-20' AS Date))
INSERT [dbo].[LichDay] ([MaLopMH], [IDNhomTH], [BuoiHoc], [Ca], [NgayBD], [NgayKT]) VALUES (N'L_INT1332_20_1_1', 2, 4, 1, CAST(N'2020-11-09' AS Date), CAST(N'2020-12-20' AS Date))
INSERT [dbo].[LichDay] ([MaLopMH], [IDNhomTH], [BuoiHoc], [Ca], [NgayBD], [NgayKT]) VALUES (N'L_INT1336_21_2_1', 0, 4, 1, CAST(N'2021-03-01' AS Date), CAST(N'2021-05-09' AS Date))
GO
INSERT [dbo].[LoaiDiemDanh] ([IDDiemDanh], [ChuThich]) VALUES (0, N'Có Mặt')
INSERT [dbo].[LoaiDiemDanh] ([IDDiemDanh], [ChuThich]) VALUES (1, N'Học Muộn')
INSERT [dbo].[LoaiDiemDanh] ([IDDiemDanh], [ChuThich]) VALUES (2, N'Vắng Không Phép')
INSERT [dbo].[LoaiDiemDanh] ([IDDiemDanh], [ChuThich]) VALUES (3, N'Vắng Có Phép')
GO
INSERT [dbo].[LoaiNhomTH] ([IDNhomTH], [ChuThich]) VALUES (0, N'Lý Thuyết')
INSERT [dbo].[LoaiNhomTH] ([IDNhomTH], [ChuThich]) VALUES (1, N'Thực Hành 1')
INSERT [dbo].[LoaiNhomTH] ([IDNhomTH], [ChuThich]) VALUES (2, N'Thực Hành 2')
INSERT [dbo].[LoaiNhomTH] ([IDNhomTH], [ChuThich]) VALUES (3, N'Thực Hành 3')
INSERT [dbo].[LoaiNhomTH] ([IDNhomTH], [ChuThich]) VALUES (4, N'Thực Hành 4')
GO
INSERT [dbo].[LoaiTK] ([IDLoaiTK], [ChuThich]) VALUES (1, N'Sinh Vien')
INSERT [dbo].[LoaiTK] ([IDLoaiTK], [ChuThich]) VALUES (2, N'Giao Vien')
GO
INSERT [dbo].[LopMonHoc] ([MaLopMH], [HocKy], [NamHoc], [MaMH], [Lop]) VALUES (N'L_INT1330_20_1_1', 1, 2020, N'INT1330', N'D18CQCN02-N')
INSERT [dbo].[LopMonHoc] ([MaLopMH], [HocKy], [NamHoc], [MaMH], [Lop]) VALUES (N'L_INT1332_20_1_1', 1, 2020, N'INT1332', N'D18CQCN02-N')
INSERT [dbo].[LopMonHoc] ([MaLopMH], [HocKy], [NamHoc], [MaMH], [Lop]) VALUES (N'L_SKD1108_20_1_1', 1, 2020, N'SKD1108', N'D18CQCN02-N')
INSERT [dbo].[LopMonHoc] ([MaLopMH], [HocKy], [NamHoc], [MaMH], [Lop]) VALUES (N'L_INT1328_21_2_1', 2, 2021, N'INT1328', N'D18CQCN02-N')
INSERT [dbo].[LopMonHoc] ([MaLopMH], [HocKy], [NamHoc], [MaMH], [Lop]) VALUES (N'L_INT1336_21_2_1', 2, 2021, N'INT1336', N'D18CQCN02-N')
INSERT [dbo].[LopMonHoc] ([MaLopMH], [HocKy], [NamHoc], [MaMH], [Lop]) VALUES (N'L_INT1330_21_3_1', 3, 2021, N'INT1330', N'D18CQCN02-N')
GO
INSERT [dbo].[LopNienChe] ([MaLopNC], [TenLopNC], [NienKhoa]) VALUES (N'D18CQCN01-N', N'Công Nghệ Thông Tin 1', 2018)
INSERT [dbo].[LopNienChe] ([MaLopNC], [TenLopNC], [NienKhoa]) VALUES (N'D18CQCN02-N', N'Công Nghệ Thông Tin 2', 2018)
INSERT [dbo].[LopNienChe] ([MaLopNC], [TenLopNC], [NienKhoa]) VALUES (N'D18CQCN03-N', N'Công Nghệ Thông Tin 3', 2018)
GO
INSERT [dbo].[MonHoc] ([MaMH], [TenMH]) VALUES (N'INT1328', N'Kĩ Thuật Đồ Họa')
INSERT [dbo].[MonHoc] ([MaMH], [TenMH]) VALUES (N'INT1330', N'Kĩ Thuật Vi Xử Lí')
INSERT [dbo].[MonHoc] ([MaMH], [TenMH]) VALUES (N'INT1332', N'Lập Trình Hướng Đối Tượng')
INSERT [dbo].[MonHoc] ([MaMH], [TenMH]) VALUES (N'INT1336', N'Mạng Máy Tính')
INSERT [dbo].[MonHoc] ([MaMH], [TenMH]) VALUES (N'SKD1108', N'Phương Pháp Luận Nghiên Cứu KH')
GO
INSERT [dbo].[NDThongBao] ([MaTB], [ThoiGian], [NoiDung]) VALUES (N'TB_1', CAST(N'2021-06-13T22:42:59.250' AS DateTime), N'Thư Đầu Tiên')
INSERT [dbo].[NDThongBao] ([MaTB], [ThoiGian], [NoiDung]) VALUES (N'TB_2', CAST(N'2021-06-13T23:30:41.783' AS DateTime), N'Thư Thứ Hai')
GO
INSERT [dbo].[NhomTHBuoiHoc] ([MaBH], [MaLopMH], [IDNhomTH]) VALUES (N'GV002_2021-06-15_0', N'L_INT1330_21_3_1', 0)
INSERT [dbo].[NhomTHBuoiHoc] ([MaBH], [MaLopMH], [IDNhomTH]) VALUES (N'GV002_2021-06-16_1', N'L_INT1330_21_3_1', 0)
INSERT [dbo].[NhomTHBuoiHoc] ([MaBH], [MaLopMH], [IDNhomTH]) VALUES (N'GV002_2021-06-17_0', N'L_INT1330_21_3_1', 0)
GO
INSERT [dbo].[NhomTHLopMonHoc] ([MaLopMH], [IDNhomTH]) VALUES (N'L_INT1328_21_2_1', 0)
INSERT [dbo].[NhomTHLopMonHoc] ([MaLopMH], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', 0)
INSERT [dbo].[NhomTHLopMonHoc] ([MaLopMH], [IDNhomTH]) VALUES (N'L_INT1330_21_3_1', 0)
INSERT [dbo].[NhomTHLopMonHoc] ([MaLopMH], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', 0)
INSERT [dbo].[NhomTHLopMonHoc] ([MaLopMH], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', 1)
INSERT [dbo].[NhomTHLopMonHoc] ([MaLopMH], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', 2)
INSERT [dbo].[NhomTHLopMonHoc] ([MaLopMH], [IDNhomTH]) VALUES (N'L_INT1336_21_2_1', 0)
GO
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV001', N'Nguyễn Văn A', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN01-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV002', N'Nguyễn Văn B', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN01-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV003', N'Nguyễn Văn C', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN01-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV004', N'Nguyễn Văn D', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN01-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV005', N'Nguyễn Văn E', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN01-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV006', N'Nguyễn Văn F', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN02-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV007', N'Nguyễn Văn G', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN02-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV008', N'Nguyễn Văn H', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN02-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV009', N'Nguyễn Văn K', CAST(N'2000-01-01' AS Date), N'Nữ', N'D18CQCN02-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV010', N'Nguyễn Văn L', CAST(N'2000-01-01' AS Date), N'Nữ', N'D18CQCN02-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV011', N'Nguyễn Văn M', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN02-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV012', N'Nguyễn Văn N', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN02-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV013', N'Nguyễn Văn O', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN03-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV014', N'Nguyễn Văn P', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN03-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV015', N'Nguyễn Văn Q', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN03-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV016', N'Nguyễn Văn R', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN03-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV017', N'Nguyễn Văn S', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN03-N')
INSERT [dbo].[SinhVien] ([MSSV], [HoTen], [NgaySinh], [GioiTinh], [MaLopNC]) VALUES (N'SV018', N'Nguyễn Văn T', CAST(N'2000-01-01' AS Date), N'Nam', N'D18CQCN03-N')
GO
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-15_0', N'SV001', 2)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-15_0', N'SV004', 2)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-15_0', N'SV006', 2)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-15_0', N'SV010', 2)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-16_1', N'SV001', 0)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-16_1', N'SV004', 0)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-16_1', N'SV006', 2)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-16_1', N'SV010', 2)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-17_0', N'SV001', 0)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-17_0', N'SV004', 2)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-17_0', N'SV006', 2)
INSERT [dbo].[SVDiemDanh] ([MaBH], [MSSV], [IDDiemDanh]) VALUES (N'GV002_2021-06-17_0', N'SV010', 2)
GO
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV001', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV002', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV003', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV004', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV005', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV006', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV007', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV008', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV009', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV010', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV011', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_20_1_1', N'SV012', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_21_3_1', N'SV001', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_21_3_1', N'SV004', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_21_3_1', N'SV006', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1330_21_3_1', N'SV010', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV001', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV001', 1)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV002', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV002', 1)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV003', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV003', 1)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV004', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV004', 1)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV005', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV005', 1)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV006', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV006', 1)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV007', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV007', 2)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV008', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV008', 2)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV009', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV009', 2)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV010', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV010', 2)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV011', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV011', 2)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV012', 0)
INSERT [dbo].[SVHocLopMonHoc] ([MaLopMH], [MSSV], [IDNhomTH]) VALUES (N'L_INT1332_20_1_1', N'SV012', 2)
GO
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'GV001', N'12345', 2)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'GV002', N'12345', 2)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'GV003', N'12345', 2)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'GV004', N'12345', 2)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV001', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV002', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV003', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV004', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV005', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV006', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV007', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV008', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV009', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV010', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV011', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV012', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV013', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV014', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV015', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV016', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV017', N'12345', 1)
INSERT [dbo].[TaiKhoan] ([User], [Pass], [IDLoaiTK]) VALUES (N'SV018', N'12345', 1)
GO
INSERT [dbo].[VaiTroGV] ([IDVaiTro], [ChuThich]) VALUES (0, N'Ly Thuyet')
INSERT [dbo].[VaiTroGV] ([IDVaiTro], [ChuThich]) VALUES (1, N'Thuc Hanh')
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UniqueBH]    Script Date: 6/13/2021 11:55:54 PM ******/
ALTER TABLE [dbo].[BuoiHoc] ADD  CONSTRAINT [UniqueBH] UNIQUE NONCLUSTERED 
(
	[MaLopMH] ASC,
	[MaGV] ASC,
	[Ngay] ASC,
	[Ca] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UniqueLopMH]    Script Date: 6/13/2021 11:55:54 PM ******/
ALTER TABLE [dbo].[LopMonHoc] ADD  CONSTRAINT [UniqueLopMH] UNIQUE NONCLUSTERED 
(
	[HocKy] ASC,
	[NamHoc] ASC,
	[MaMH] ASC,
	[Lop] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BuoiHoc]  WITH CHECK ADD  CONSTRAINT [FK_BuoiHoc_GVDayLopMonHoc] FOREIGN KEY([MaLopMH], [MaGV], [IDVaiTro])
REFERENCES [dbo].[GVDayLopMonHoc] ([MaLopMH], [MaGV], [IDVaiTro])
GO
ALTER TABLE [dbo].[BuoiHoc] CHECK CONSTRAINT [FK_BuoiHoc_GVDayLopMonHoc]
GO
ALTER TABLE [dbo].[GVDayLopMonHoc]  WITH CHECK ADD  CONSTRAINT [FK_GVDayLopMonHoc_LoaiGV] FOREIGN KEY([IDVaiTro])
REFERENCES [dbo].[VaiTroGV] ([IDVaiTro])
GO
ALTER TABLE [dbo].[GVDayLopMonHoc] CHECK CONSTRAINT [FK_GVDayLopMonHoc_LoaiGV]
GO
ALTER TABLE [dbo].[GVDayLopMonHoc]  WITH CHECK ADD  CONSTRAINT [FK_LopMonHoc_giangvien] FOREIGN KEY([MaGV])
REFERENCES [dbo].[GiaoVien] ([MaGV])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GVDayLopMonHoc] CHECK CONSTRAINT [FK_LopMonHoc_giangvien]
GO
ALTER TABLE [dbo].[GVDayLopMonHoc]  WITH CHECK ADD  CONSTRAINT [FK_LopMonHoc_MonHoc_LopMonHoc] FOREIGN KEY([MaLopMH])
REFERENCES [dbo].[LopMonHoc] ([MaLopMH])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GVDayLopMonHoc] CHECK CONSTRAINT [FK_LopMonHoc_MonHoc_LopMonHoc]
GO
ALTER TABLE [dbo].[GVThongBao]  WITH CHECK ADD  CONSTRAINT [FK_GVThongBao_GVDayLopMonHoc] FOREIGN KEY([MaLopMH], [MaGV], [IDVaiTro])
REFERENCES [dbo].[GVDayLopMonHoc] ([MaLopMH], [MaGV], [IDVaiTro])
GO
ALTER TABLE [dbo].[GVThongBao] CHECK CONSTRAINT [FK_GVThongBao_GVDayLopMonHoc]
GO
ALTER TABLE [dbo].[GVThongBaoSV]  WITH CHECK ADD  CONSTRAINT [FK_GVThongBaoSV_GVThongBao1] FOREIGN KEY([MaTB])
REFERENCES [dbo].[GVThongBao] ([MaTB])
GO
ALTER TABLE [dbo].[GVThongBaoSV] CHECK CONSTRAINT [FK_GVThongBaoSV_GVThongBao1]
GO
ALTER TABLE [dbo].[GVThongBaoSV]  WITH CHECK ADD  CONSTRAINT [FK_GVThongBaoSV_SinhVien] FOREIGN KEY([MSSV])
REFERENCES [dbo].[SinhVien] ([MSSV])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[GVThongBaoSV] CHECK CONSTRAINT [FK_GVThongBaoSV_SinhVien]
GO
ALTER TABLE [dbo].[GiaoVien]  WITH CHECK ADD  CONSTRAINT [FK_GiaoVien_TaiKhoan] FOREIGN KEY([MaGV])
REFERENCES [dbo].[TaiKhoan] ([User])
GO
ALTER TABLE [dbo].[GiaoVien] CHECK CONSTRAINT [FK_GiaoVien_TaiKhoan]
GO
ALTER TABLE [dbo].[LichDay]  WITH CHECK ADD  CONSTRAINT [FK_LichHocTH_LopMonHoc] FOREIGN KEY([MaLopMH])
REFERENCES [dbo].[LopMonHoc] ([MaLopMH])
GO
ALTER TABLE [dbo].[LichDay] CHECK CONSTRAINT [FK_LichHocTH_LopMonHoc]
GO
ALTER TABLE [dbo].[LopMonHoc]  WITH CHECK ADD  CONSTRAINT [FK_LopMonHoc_LopNienChe] FOREIGN KEY([Lop])
REFERENCES [dbo].[LopNienChe] ([MaLopNC])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[LopMonHoc] CHECK CONSTRAINT [FK_LopMonHoc_LopNienChe]
GO
ALTER TABLE [dbo].[LopMonHoc]  WITH CHECK ADD  CONSTRAINT [FK_MonHoc_LopMonHoc_MonHoc] FOREIGN KEY([MaMH])
REFERENCES [dbo].[MonHoc] ([MaMH])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[LopMonHoc] CHECK CONSTRAINT [FK_MonHoc_LopMonHoc_MonHoc]
GO
ALTER TABLE [dbo].[NDThongBao]  WITH CHECK ADD  CONSTRAINT [FK_NDThongBao_GVThongBao] FOREIGN KEY([MaTB])
REFERENCES [dbo].[GVThongBao] ([MaTB])
GO
ALTER TABLE [dbo].[NDThongBao] CHECK CONSTRAINT [FK_NDThongBao_GVThongBao]
GO
ALTER TABLE [dbo].[NhomTHBuoiHoc]  WITH CHECK ADD  CONSTRAINT [FK_BuoiHocExtend_BuoiHoc] FOREIGN KEY([MaBH])
REFERENCES [dbo].[BuoiHoc] ([MaBH])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[NhomTHBuoiHoc] CHECK CONSTRAINT [FK_BuoiHocExtend_BuoiHoc]
GO
ALTER TABLE [dbo].[NhomTHBuoiHoc]  WITH CHECK ADD  CONSTRAINT [FK_BuoiHocExtend_NhomTHLopMonHoc] FOREIGN KEY([MaLopMH], [IDNhomTH])
REFERENCES [dbo].[NhomTHLopMonHoc] ([MaLopMH], [IDNhomTH])
GO
ALTER TABLE [dbo].[NhomTHBuoiHoc] CHECK CONSTRAINT [FK_BuoiHocExtend_NhomTHLopMonHoc]
GO
ALTER TABLE [dbo].[NhomTHLopMonHoc]  WITH CHECK ADD  CONSTRAINT [FK_NhomTHLopMonHoc_LichDay] FOREIGN KEY([MaLopMH], [IDNhomTH])
REFERENCES [dbo].[LichDay] ([MaLopMH], [IDNhomTH])
GO
ALTER TABLE [dbo].[NhomTHLopMonHoc] CHECK CONSTRAINT [FK_NhomTHLopMonHoc_LichDay]
GO
ALTER TABLE [dbo].[NhomTHLopMonHoc]  WITH CHECK ADD  CONSTRAINT [FK_NhomTHLopMonHoc_LoaiNhomTH] FOREIGN KEY([IDNhomTH])
REFERENCES [dbo].[LoaiNhomTH] ([IDNhomTH])
GO
ALTER TABLE [dbo].[NhomTHLopMonHoc] CHECK CONSTRAINT [FK_NhomTHLopMonHoc_LoaiNhomTH]
GO
ALTER TABLE [dbo].[SinhVien]  WITH CHECK ADD  CONSTRAINT [FK_SinhVien_LopNienChe] FOREIGN KEY([MaLopNC])
REFERENCES [dbo].[LopNienChe] ([MaLopNC])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[SinhVien] CHECK CONSTRAINT [FK_SinhVien_LopNienChe]
GO
ALTER TABLE [dbo].[SinhVien]  WITH CHECK ADD  CONSTRAINT [FK_SinhVien_TaiKhoan] FOREIGN KEY([MSSV])
REFERENCES [dbo].[TaiKhoan] ([User])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[SinhVien] CHECK CONSTRAINT [FK_SinhVien_TaiKhoan]
GO
ALTER TABLE [dbo].[SVDiemDanh]  WITH CHECK ADD  CONSTRAINT [FK_InfoDiemDanh_BuoiHoc] FOREIGN KEY([MaBH])
REFERENCES [dbo].[BuoiHoc] ([MaBH])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[SVDiemDanh] CHECK CONSTRAINT [FK_InfoDiemDanh_BuoiHoc]
GO
ALTER TABLE [dbo].[SVDiemDanh]  WITH CHECK ADD  CONSTRAINT [FK_SVDiemDanh_LoaiDiemDanh] FOREIGN KEY([IDDiemDanh])
REFERENCES [dbo].[LoaiDiemDanh] ([IDDiemDanh])
GO
ALTER TABLE [dbo].[SVDiemDanh] CHECK CONSTRAINT [FK_SVDiemDanh_LoaiDiemDanh]
GO
ALTER TABLE [dbo].[SVDiemDanh]  WITH CHECK ADD  CONSTRAINT [FK_SVDiemDanh_SinhVien1] FOREIGN KEY([MSSV])
REFERENCES [dbo].[SinhVien] ([MSSV])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[SVDiemDanh] CHECK CONSTRAINT [FK_SVDiemDanh_SinhVien1]
GO
ALTER TABLE [dbo].[SVHocLopMonHoc]  WITH CHECK ADD  CONSTRAINT [FK_LopTH_SinhVien] FOREIGN KEY([MSSV])
REFERENCES [dbo].[SinhVien] ([MSSV])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[SVHocLopMonHoc] CHECK CONSTRAINT [FK_LopTH_SinhVien]
GO
ALTER TABLE [dbo].[SVHocLopMonHoc]  WITH CHECK ADD  CONSTRAINT [FK_SVHocLopMonHoc_LopMonHoc] FOREIGN KEY([MaLopMH])
REFERENCES [dbo].[LopMonHoc] ([MaLopMH])
GO
ALTER TABLE [dbo].[SVHocLopMonHoc] CHECK CONSTRAINT [FK_SVHocLopMonHoc_LopMonHoc]
GO
ALTER TABLE [dbo].[SVHocLopMonHoc]  WITH CHECK ADD  CONSTRAINT [FK_SVHocLopMonHoc_NhomTHLopMonHoc] FOREIGN KEY([MaLopMH], [IDNhomTH])
REFERENCES [dbo].[NhomTHLopMonHoc] ([MaLopMH], [IDNhomTH])
GO
ALTER TABLE [dbo].[SVHocLopMonHoc] CHECK CONSTRAINT [FK_SVHocLopMonHoc_NhomTHLopMonHoc]
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD  CONSTRAINT [FK_TaiKhoan_LoaiQuyen] FOREIGN KEY([IDLoaiTK])
REFERENCES [dbo].[LoaiTK] ([IDLoaiTK])
GO
ALTER TABLE [dbo].[TaiKhoan] CHECK CONSTRAINT [FK_TaiKhoan_LoaiQuyen]
GO
USE [master]
GO
ALTER DATABASE [CNPHANMEM3] SET  READ_WRITE 
GO
