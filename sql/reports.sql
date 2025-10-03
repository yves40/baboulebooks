use babouledb;
select COUNT(loc_id) from locations;
select COUNT(auth_id) from authors;
select COUNT(ed_id) from editors;
select COUNT(bk_id) from books;

select COUNT(distinct auth_fname, auth_lname)  from authors;

select b.bk_title, a.auth_fname,  a.auth_lname, e.ed_name, l.loc_city 
  from books b, authors a, editors e, locations l
  where b.bk_location = l.loc_id
    and b.bk_editor = e.ed_id
    and b.bk_author = a.auth_id
  order by bk_title asc;
  
