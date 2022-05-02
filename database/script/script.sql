
create user 'OR2'@'%' identified with mysql_native_password by 'mysql';
grant all privileges on *.* to 'OR2'@'%';
flush privileges;

create database oasip;
use oasip;

-- -----------------------------------------------------
-- Table `oasip`.`eventCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS eventCategory (
  `categoryId` INT NOT NULL auto_increment,
  `eventCategoryName` VARCHAR(100) NOT NULL,
  `eventCatagoryDescription` VARCHAR(400),
  `eventDuration` SMALLINT NOT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE(`eventCategoryName`) ) 
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `oasip`.`event`
-- -----------------------------------------------------
INSERT INTO eventCategory (eventCategoryName,eventCatagoryDescription,eventDuration) 
VALUES ('Project Management Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคำปรึกษา', 30);
INSERT INTO eventCategory (eventCategoryName,eventCatagoryDescription,eventDuration) 
VALUES ('DevOps/Infra Clinic', 'Use this event category for DevOps/Infra clinic', 20);
INSERT INTO eventCategory (eventCategoryName,eventCatagoryDescription,eventDuration) 
VALUES ('Database Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I', 15);
INSERT INTO eventCategory (eventCategoryName,eventCatagoryDescription,eventDuration) 
VALUES ('Client-side Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคำปรึกษา', 30);
INSERT INTO eventCategory (eventCategoryName,eventDuration) 
VALUES ('Server-side Clinic', 30);

CREATE TABLE IF NOT EXISTS event (
  `eventId` INT NOT NULL auto_increment,
  `bookingName` VARCHAR(255) NOT NULL,
  `bookingEmail` VARCHAR(70) NOT NULL,
  `eventStartTime` DATETIME NOT NULL,
  `eventDuration` SMALLINT NOT NULL,
  `categoryId` INT NOT NULL,
  `eventNotes` VARCHAR(500) ,
  PRIMARY KEY (`eventId`),
  UNIQUE(`bookingName`,`eventStartTime`),
  INDEX `fk_event_eventCategory_idx` (`categoryId` ASC) VISIBLE,
  CONSTRAINT `fk_event_eventCategory`
    FOREIGN KEY (`categoryId`)
    REFERENCES `oasip`.`eventCategory` (`categoryId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

select * from eventCategory;
select * from event;

INSERT INTO event(bookingName,bookingEmail,eventStartTime,eventDuration,categoryId) 
VALUES ('Somchai Jaidee(OR-7)','somchai.jai@mail.kmutt.ac.th','2022-05-23T13:30','30',2);
INSERT INTO event(bookingName,bookingEmail,eventStartTime,eventDuration,categoryId,eventNotes) 
VALUES ('Somsri Rakdee(SJ-3)','somsri.rak@mail.kmutt.ac.th','2022-04-27T09:30','30',1,'ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน' );
INSERT INTO event(bookingName,bookingEmail,eventStartTime,eventDuration,categoryId) 
VALUES ('สมเกียรติ ขยันเรียนกลุ่ม TT-4','somkiat.kay@kmutt.ac.th','2022-05-23T16:30','15',3);

commit;
