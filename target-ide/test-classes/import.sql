INSERT INTO T_ROLE(idt_role,name) VALUES(1,'ROLE_ADMIN')
INSERT INTO T_ROLE(idt_role,name) VALUES(2,'ROLE_LIST_PET')
INSERT INTO T_ROLE(idt_role,name) VALUES(3,'ROLE_ADD_PET')
INSERT INTO T_ROLE(idt_role,name) VALUES(4,'ROLE_EDIT_PET')
INSERT INTO T_ROLE(idt_role,name) VALUES(5,'ROLE_DELETE_PET')

INSERT INTO T_USER(idt_user,username,password,status) VALUES(1,'admin','password',1)
INSERT INTO T_USER(idt_user,username,password,status) VALUES(2,'user','password',0)

INSERT INTO T_USER_ROLE(idt_user,idt_role)VALUES(1,1)
INSERT INTO T_USER_ROLE(idt_user,idt_role)VALUES(1,2)
INSERT INTO T_USER_ROLE(idt_user,idt_role)VALUES(1,3)
INSERT INTO T_USER_ROLE(idt_user,idt_role)VALUES(1,4)
INSERT INTO T_USER_ROLE(idt_user,idt_role)VALUES(1,5)

INSERT INTO T_USER_ROLE(idt_user,idt_role)VALUES(2,2)

