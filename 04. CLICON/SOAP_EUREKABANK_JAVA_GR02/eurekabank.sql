CREATE DATABASE IF NOT EXISTS eurekabank;
USE eurekabank;

drop table if exists CLIENTE;

drop table if exists CUENTA;

drop table if exists EMPLEADO;

drop table if exists MOVIMIENTO;

drop table if exists SUCURSAL;

/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE
(
   CLIENTEID            int not null,
   NOMBRE               varchar(50),
   APELLIDO             varchar(50),
   CEDULA               varchar(10),
   EMAIL                varchar(50),
   TELEFONO             longtext,
   ESTADO               bool,
   primary key (CLIENTEID)
);

/*==============================================================*/
/* Table: CUENTA                                                */
/*==============================================================*/
create table CUENTA
(
   CUENTAID             int not null,
   CLIENTEID            int not null,
   SUCURSALID           int not null,
   EMPLEADOID           int not null,
   NUMEROCUENTA         varchar(10),
   SALDO                decimal(15,2),
   ESTADO               bool,
   BLOQUEADA            bool,
   primary key (CUENTAID)
);

/*==============================================================*/
/* Table: EMPLEADO                                              */
/*==============================================================*/
create table EMPLEADO
(
   EMPLEADOID           int not null,
   SUCURSALID           int not null,
   NOMBRE               varchar(20),
   APELLIDO             varchar(20),
   USERNAME             varchar(10),
   PASSWORD             varchar(9),
   ROL                  varchar(10),
   ESTADO               bool,
   primary key (EMPLEADOID)
);

/*==============================================================*/
/* Table: MOVIMIENTO                                            */
/*==============================================================*/
create table MOVIMIENTO
(
   MOVIMIENTOID         int not null,
   CUENTAID             int not null,
   EMPLEADOID           int not null,
   TIPO                 varchar(20),
   MONTO                decimal(15,2),
   SALDOANTERIOR        decimal(15,2),
   SALDOPOSTERIOR       decimal(15,2),
   FECHA                date,
   NUMEROOPERACION      int,
   primary key (MOVIMIENTOID)
);

/*==============================================================*/
/* Table: SUCURSAL                                              */
/*==============================================================*/
create table SUCURSAL
(
   SUCURSALID           int not null,
   NOMBRE               varchar(20),
   DIRECCION            varchar(30),
   CIUDAD               varchar(20),
   primary key (SUCURSALID)
);

alter table CUENTA add constraint FK_ADMINISTRA foreign key (SUCURSALID)
      references SUCURSAL (SUCURSALID) on delete restrict on update restrict;

alter table CUENTA add constraint FK_PUEDE_BLOQUEAR foreign key (EMPLEADOID)
      references EMPLEADO (EMPLEADOID) on delete restrict on update restrict;

alter table CUENTA add constraint FK_PUEDE_TENER foreign key (CLIENTEID)
      references CLIENTE (CLIENTEID) on delete restrict on update restrict;

alter table EMPLEADO add constraint FK_TIENE foreign key (SUCURSALID)
      references SUCURSAL (SUCURSALID) on delete restrict on update restrict;

alter table MOVIMIENTO add constraint FK_GENERA foreign key (CUENTAID)
      references CUENTA (CUENTAID) on delete restrict on update restrict;

alter table MOVIMIENTO add constraint FK_REGISTRA foreign key (EMPLEADOID)
      references EMPLEADO (EMPLEADOID) on delete restrict on update restrict;