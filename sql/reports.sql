use babouledb;
select COUNT(loc_id) from locations;
select COUNT(auth_id) from authors;
select COUNT(ed_id) from editors;
select COUNT(bk_id) from books;

select COUNT(distinct auth_fname, auth_lname)  from authors;

