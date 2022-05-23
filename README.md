# ufs05_esame_d
UFS 05 — Esame Traccia D

## Server TCP
> telnet 127.0.0.1 1234 <br>
> 'red' -> list of red wines <br>
> 'white' -> list of white wines <br>
> 'sorted_by_name' -> list of all wines sorted by name <br>
> 'sorted_by_price' -> list of all wines sorted by price <br>

## Server HTTP
> curl http://127.0.0.1:8000/?cmd=red -> list of red wines <br>
> curl http://127.0.0.1:8000/?cmd=white -> list of white wines <br>
> curl http://127.0.0.1:8000/?cmd=sorted_by_name -> list of wines sorted by name <br>
> curl http://127.0.0.1:8000/?cmd=sorted_by_price -> list of wines sorted by price <br>
