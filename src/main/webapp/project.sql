create table dept (
	dept_no 	number 			primary key,
	dept_name 	varchar2(12) 	not null
);

insert into dept values (10, '구매');
insert into dept values (20, '판매');
insert into dept values (30, '재고');
insert into dept values (40, '회계');
insert into dept values (50, '인사');

create table emp (
	emp_no 			varchar2(10) 		primary key,
	dept_no 		references dept(dept_no) not null,
	password		varchar2(15)		not null,
	emp_name		varchar2(15)		not null,
	emp_email		varchar2(50)		not null,
	emp_addr_no		varchar2(7)			not null,
	emp_addr		varchar2(200)		not null,
	emp_addr_detail	varchar2(50)		not null,
	emp_tel			varchar2(15)		not null,
	hiredate		date				default sysdate,
	resign			char(1)				default 'n'
);

create table Customer (
    customer_no             VARCHAR2(10) 	primary key, 
    customer_name           VARCHAR2(30) 	not null,
    customer_reg_num        VARCHAR2(15) 	not null,
    customer_tel            VARCHAR2(15) 	not null,
    customer_email          VARCHAR2(50) 	not null,
    customer_addr_no        VARCHAR2(7)  	not null,
    customer_addr			VARCHAR2(200) 	not null,
    customer_addr_detail	VARCHAR2(50) 	not null,
    emp_no					REFERENCES Emp(emp_no) ON DELETE set null not null,
    customer_memo           VARCHAR2(100),
    customer_del            CHAR(1) 		default 'n'
); 

create table Sales_order(
    sales_order_no 		NUMBER 		primary key,
    customer_no						REFERENCES Customer(customer_no) ON DELETE set null not null,
    sales_order_date 	DATE 		default (sysdate),
    emp_no 							REFERENCES Emp(emp_no) ON DELETE set null not null
);

create table Product(
    product_no 		NUMBER 			primary key,
    product_name 	VARCHAR2(30) 	not null,
    price 			NUMBER 			not null,
    cost 			NUMBER 			not null,
    stock 			NUMBER 			not null, 
    product_memo 	VARCHAR2(100)
);

create table Sales_order_detail(
    sales_order_no					REFERENCES Sales_order(sales_order_no) ON DELETE set null not null,
    product_no 						REFERENCES Product(product_no) ON DELETE set null not null,
    sales_detail_pcount 	NUMBER 	not null,
    								PRIMARY KEY(sales_order_no, product_no)
);

create table Seller (
    seller_no           VARCHAR2(10) 	primary key, 
    seller_name         VARCHAR2(30) 	not null,
    seller_reg_num      VARCHAR2(15) 	not null,
    seller_tel          VARCHAR2(15) 	not null,
    seller_email        VARCHAR2(50) 	not null,
    seller_addr_no      VARCHAR2(7)  	not null,
    seller_addr			VARCHAR2(200) 	not null,
    seller_addr_detail	VARCHAR2(50) 	not null,
    emp_no				REFERENCES Emp(emp_no) ON DELETE set null not null,
    seller_memo         VARCHAR2(100),
    seller_del          CHAR(1) 		default 'n'
); 

create table Purchase_order(
    purchase_order_no 		NUMBER 		primary key,
    seller_no							REFERENCES Seller(seller_no) ON DELETE set null not null,
    purchase_order_date 	DATE 		default (sysdate),
    emp_no 								REFERENCES Emp(emp_no) ON DELETE set null not null
);

create table Purchase_order_detail(
    purchase_order_no				REFERENCES Purchase_order(purchase_order_no) ON DELETE set null not null,
    product_no 						REFERENCES Product(product_no) ON DELETE set null not null,
    purchase_detail_pcount 	NUMBER 	not null,
    								PRIMARY KEY(purchase_order_no, product_no)
);

create table Cash (
	cash_code	NUMBER 	primary key,
	cash		number,	
	purchase_order_no	references purchase_order(purchase_order_no),
	sales_order_no		references sales_order(sales_order_no)
)

create table product_modified (
	product_modified_date	date,
	product_no								references Product(product_no) not null,
	emp_no									references Emp(emp_no) not null,
	modified_stock			number,
	modified_memo			varchar2(100),
											primary key(product_modified_date, product_no)
);

create or replace view v_hr
as
	select e1.rn, e1.emp_no, e1.emp_name, d.dept_name, e1.emp_tel, e1.emp_email 
	from (select rownum rn, e.* from emp e) e1 
	join dept d
	on e1.dept_no = d.dept_no 
	order by emp_no, e1.dept_no
with read only;

create or replace view hr
as
	select e.emp_no, e.emp_name, d.dept_name, e.emp_tel, e.emp_email
	from emp e, dept d
	where e.dept_no = d.dept_no
	order by e.dept_no, emp_no desc
with read only;
	
select * from hr;

select * from v_hr where rn between '1' and '10';

create or replace view modified_stock
as
	select pm.product_modified_date, p.product_no, p.product_name, pm.modified_stock, pm.modified_memo, e.emp_no, e.emp_name 
	from product p, PRODUCT_MODIFIED pm, EMP e
	where p.product_no = pm.product_no and pm.emp_no = e.emp_no
	order by pm.product_modified_date desc, p.product_no desc
with read only;

create or replace view purchase
as
    select po.purchase_order_date, po.purchase_order_no, s.seller_no, s.seller_name, p.product_no, p.product_name, p.price, p.cost, p.stock, pod.purchase_DETAIL_PCOUNT, e.emp_no, e.emp_name 
    from purchase_order po, seller s, Product p, purchase_order_detail pod, Emp e 
    where e.emp_no=po.emp_no and s.seller_no=po.seller_no and po.purchase_order_no=pod.purchase_order_no and p.product_no=pod.product_no 
    order by po.purchase_order_date desc
with read only;

create or replace view sales
as
    select so.sales_order_date, so.sales_order_no, c.customer_no, c.customer_name, p.product_no, p.product_name, p.price, p.cost, p.stock, sod.sales_detail_pcount, e.emp_no, e.emp_name 
    from Sales_order so, Customer c, Product p, Sales_order_detail sod, Emp e 
    where e.emp_no=so.emp_no and c.customer_no=so.customer_no and so.sales_order_no=sod.sales_order_no and p.product_no=sod.product_no 
    order by so.sales_order_date desc
with read only;



insert into CASH values (1, 1000000, null, null);

update emp set password = '1234' where emp_no = '21-00001';

select p.product_no, p.product_name, p.cost, p.purchase_detail_pcount
from (
	select rownum rn, product_no, product_name, cost, asd
	from (
		select product_no, product_name, cost, sum(purchase_detail_pcount) "asd" 
		from purchase 
		group by product_no, product_name, cost
		order by product_no
	)
)
where rn between 1 and 10;

create or replace view p_balance
as
	select product_no, product_name, cost, sum(purchase_detail_pcount) "purchase_detail_pcount" 
	from purchase 
	group by product_no, product_name, cost
	order by product_no
with read only;

select * from P_BALANCE;

delete from product where product_no = 6;

select * from purchase;

insert into emp values ('21-00001', 50, '1234', '이종민', 'fjswhd93@gmail.com', '10358','고양시', '덕양구', '010-9052-1980', to_date('210502', 'YYMMDD'), 'n');
insert into emp values ('21-00002', 50, '1234', '김상우', 'fjswhd93@gmail.com', '10358','고양시', '덕양구', '010-9052-1980', to_date('210502', 'YYMMDD'), 'n');

select product_no, product_name, cost, price, sum(purchase_detail_pcount) 
from purchase
where purchase_order_date < sysdate
and purchase_order_date > trunc(sysdate, 'mm')
group by product_no, product_name, cost, price
order by product_no desc;

select * from emp;
select * from product;
