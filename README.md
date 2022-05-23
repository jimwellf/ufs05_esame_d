# ufs05_esame_d
UFS 05 — Esame Traccia D

## Server TCP
>> telnet 127.0.0.1 1234
>> 'red' -> list of red wines
>> 'white' -> list of white wines
>> 'sorted_by_name' -> list of all wines sorted by name
>> 'sorted_by_price' -> list of all wines sorted by price

## Server HTTP
>> curl http://127.0.0.1:8000/?cmd=red -> list of red wines
>> curl http://127.0.0.1:8000/?cmd=white -> list of white wines
>> curl http://127.0.0.1:8000/?cmd=sorted_by_name -> list of wines sorted by name
>> curl http://127.0.0.1:8000/?cmd=sorted_by_price -> list of wines sorted by price
