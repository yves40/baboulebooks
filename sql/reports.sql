use babouledb;
-- Counts
select COUNT(loc_id) from locations;
select COUNT(auth_id) from authors;
select COUNT(ed_id) from editors;
select COUNT(bk_id) from books;
-- 
select COUNT(distinct auth_fname, auth_lname)  from authors;
-- Books list by title
select b.bk_title, a.auth_fname,  a.auth_lname, e.ed_name, l.loc_city 
  from books b, authors a, editors e, locations l
  where b.bk_location = l.loc_id
    and b.bk_editor = e.ed_id
    and b.bk_author = a.auth_id
  order by bk_title asc;
-- Books list by author
select a.auth_lname, a.auth_fname, b.bk_title,  e.ed_name, l.loc_city 
  from books b, authors a, editors e, locations l
  where b.bk_location = l.loc_id
    and b.bk_editor = e.ed_id
    and b.bk_author = a.auth_id
  order by a.auth_lname,  a.auth_fname asc;
-- Books count list by author
use babouledb;
select a.auth_lname, a.auth_fname, count(b.bk_id) Livres 
  from books b, authors a
  where b.bk_author = a.auth_id
  group by a.auth_lname, a.auth_fname
  order by Livres desc;

  
