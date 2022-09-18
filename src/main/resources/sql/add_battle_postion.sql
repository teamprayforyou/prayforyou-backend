INSERT INTO battle_position(id, battle_place_type, `polygon`)
VALUES (1, 'SUPPLY01', (ST_GeomFromText('POLYGON ((185 248, 184 269, 219 271, 225 252, 185 248))'))), # 기억자
       (2, 'SUPPLY02', (ST_GeomFromText('POLYGON ((233 284, 219 283, 220 271, 230 271, 233 284))'))), # 3빡
       (3, 'SUPPLY03', (ST_GeomFromText('POLYGON ((231 241, 232 269, 265 270, 266 243, 231 241))'))), # 홀
       (4, 'SUPPLY04', (ST_GeomFromText('POLYGON ((234 270, 235 281, 265 281, 266 271, 234 270))'))) # 왼벽

