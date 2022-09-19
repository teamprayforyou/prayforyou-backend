INSERT INTO battle_position(id, battle_place_type, `polygon`)
VALUES (1, 'SUPPLY01', (ST_GeomFromText('POLYGON ((270 264, 269 278, 282 278, 283 265, 270 264))'))),             # 텔포자리
       (2, 'SUPPLY02', (ST_GeomFromText('POLYGON ((298 264, 298 275, 307 276, 308 265, 298 264))'))),             # 매박
       (3, 'SUPPLY03', (ST_GeomFromText('POLYGON ((285 279, 286 314, 299 313, 298 279, 285 279))'))),             # 숏인
       (4, 'SUPPLY04', (ST_GeomFromText('POLYGON ((264 315, 267 331, 315 331, 315 314, 264 315))'))),             # 숏 바닥
       (5, 'SUPPLY05', (ST_GeomFromText('POLYGON ((286 333, 286 340, 307 341, 307 331, 286 333))'))),             # 숏 녹깡 위
       (6, 'SUPPLY06', (ST_GeomFromText('POLYGON ((226 315, 226 339, 270 340, 264 315, 226 315))'))),             # 중길
       (7, 'SUPPLY07', (ST_GeomFromText('POLYGON ((307 264, 309 278, 331 279, 332 265, 307 264))'))),             # 롱길
       (8, 'SUPPLY08', (ST_GeomFromText('POLYGON ((340 263, 340 270, 367 270, 366 261, 340 263))'))),             # 롱녹위, 롱녹뒤
       (9, 'SUPPLY09', (ST_GeomFromText('POLYGON ((332 263, 332 269, 338 270, 339 263, 332 263))'))),             # 롱녹깡앞
       (10, 'SUPPLY10', (ST_GeomFromText('POLYGON ((332 270, 332 282, 365 283, 366 271, 332 270))'))),             # 머리길
       (11, 'SUPPLY11', (ST_GeomFromText('POLYGON ((367 264, 367 276, 375 277, 375 261, 367 264))'))),             # 가로등
       (12, 'SUPPLY12', (ST_GeomFromText('POLYGON ((283 266, 283 279, 298 279, 296 268, 283 266))'))),             # 숏앞
       (13, 'SUPPLY13', (ST_GeomFromText('POLYGON ((348 284, 349 305, 372 306, 374 281, 348 284))'))),             # 에롱 45도
       (14, 'SUPPLY14', (ST_GeomFromText('POLYGON ((374 278, 374 285, 403 285, 403 276, 374 278))'))),             # 컨사이
       (15, 'SUPPLY15', (ST_GeomFromText('POLYGON ((404 269, 404 282, 419 284, 420 261, 404 269))'))),             # 3미리 컨뒤
       (16, 'SUPPLY16', (ST_GeomFromText('POLYGON ((411 285, 411 305, 419 305, 420 285, 411 285))'))),             # 중 컨뒤
       (17, 'SUPPLY17', (ST_GeomFromText('POLYGON ((374 285, 374 292, 382 294, 383 285, 374 285))'))),             # 중 컨앞
       (18, 'SUPPLY18',
        (ST_GeomFromText('POLYGON ((374 295, 373 305, 383 306, 383 328, 410 327, 410 309, 412 295, 374 295))'))), # 에이 설대
       (19, 'SUPPLY19', (ST_GeomFromText('POLYGON ((349 306, 349 317, 354 318, 356 306, 349 306))'))),             # 쓰리깡
       (20, 'SUPPLY20', (ST_GeomFromText('POLYGON ((356 307, 354 335, 374 334, 376 305, 356 307))'))),             # 설 컨앞
       (21, 'SUPPLY21', (ST_GeomFromText('POLYGON ((268 332, 270 339, 284 339, 286 330, 268 332))'))),             # 숏녹깡 왼쪽
       (22, 'SUPPLY22', (ST_GeomFromText('POLYGON ((308 331, 308 340, 317 340, 319 331, 308 331))'))),             # 숏녹깡 오른쪽
       (23, 'SUPPLY23', (ST_GeomFromText('POLYGON ((314 313, 317 330, 329 330, 330 313, 314 313))'))),             # 니은자
       (24, 'SUPPLY24', (ST_GeomFromText('POLYGON ((331 322, 320 331, 321 343, 353 335, 354 319, 331 322))'))),             # 리베 에이 백업 자리
       (25, 'SUPPLY25', (ST_GeomFromText('POLYGON ((327 342, 329 395, 343 392, 343 337, 327 342))'))),             # 일문
       (26, 'SUPPLY26', (ST_GeomFromText('POLYGON ((293 385, 293 393, 328 393, 328 384, 293 385))'))),             # 계끝
       (27, 'SUPPLY27', (ST_GeomFromText('POLYGON ((249 384, 251 394, 290 394, 291 384, 249 384))'))),             # 빽창
       (28, 'SUPPLY28', (ST_GeomFromText('POLYGON ((196 382, 197 393, 238 393, 239 383, 196 382))'))),             # 앞창
       (29, 'SUPPLY29', (ST_GeomFromText('POLYGON ((240 341, 239 392, 250 394, 251 342, 240 341))'))),             # 삼거리
       (30, 'SUPPLY30', (ST_GeomFromText('POLYGON ((205 352, 205 380, 237 381, 238 352, 205 352))'))),             # 사무실
       (31, 'SUPPLY31', (ST_GeomFromText('POLYGON ((217 395, 219 405, 244 406, 245 395, 217 395))'))),             # 앵글
       (32, 'SUPPLY32', (ST_GeomFromText('POLYGON ((295 406, 295 416, 319 416, 320 405, 295 406))'))),             # 빽난간
       (33, 'SUPPLY33', (ST_GeomFromText('POLYGON ((179 428, 179 458, 227 459, 227 427, 179 428))'))),             # 1기 2기 아웃코스
       (34, 'SUPPLY34', (ST_GeomFromText('POLYGON ((228 427, 229 457, 268 458, 266 428, 228 427))'))),             # 2기 3기 아웃코스
       (35, 'SUPPLY35', (ST_GeomFromText('POLYGON ((267 430, 266 457, 315 458, 314 428, 267 430))'))),             # 3기 4기 아웃코스
       (36, 'SUPPLY36', (ST_GeomFromText('POLYGON ((183 395, 182 407, 220 408, 217 395, 183 395))'))),             # 앵글 앞 인코스
       (37, 'SUPPLY37', (ST_GeomFromText('POLYGON ((181 406, 183 419, 207 420, 208 409, 181 406))'))),             # 전난
       (38, 'SUPPLY38', (ST_GeomFromText('POLYGON ((245 396, 244 408, 293 408, 292 395, 245 396))'))),             # 비 중통앞
       (39, 'SUPPLY39', (ST_GeomFromText('POLYGON ((182 419, 183 427, 228 428, 227 419, 182 419))'))),             # 비 인코스 1번
       (40, 'SUPPLY40', (ST_GeomFromText('POLYGON ((210 408, 209 419, 256 419, 257 408, 210 408))'))),             # 비 인코스 2번
       (41, 'SUPPLY41', (ST_GeomFromText('POLYGON ((227 419, 227 428, 306 427, 306 419, 227 419))'))),             # 비 인코스 3번
       (42, 'SUPPLY42', (ST_GeomFromText('POLYGON ((320 397, 318 423, 330 425, 332 397, 320 397))'))),             # 5철
       (43, 'SUPPLY43', (ST_GeomFromText('POLYGON ((154 429, 152 449, 179 450, 179 428, 154 429))'))),             # 비설대
       (44, 'SUPPLY44', (ST_GeomFromText('POLYGON ((148 430, 145 181, 176 182, 176 430, 148 430))'))),             # 비롱
       (45, 'SUPPLY45', (ST_GeomFromText('POLYGON ((177 316, 175 340, 181 340, 183 316, 177 316))'))),             # 디귿자
       (46, 'SUPPLY46', (ST_GeomFromText('POLYGON ((156 172, 155 181, 188 183, 188 172, 156 172))'))),             # 비끝
       (47, 'SUPPLY47', (ST_GeomFromText('POLYGON ((178 198, 177 207, 206 206, 206 197, 178 198))'))),             # 난간 타는 박스
       (48, 'SUPPLY48', (ST_GeomFromText('POLYGON ((209 98, 210 169, 352 171, 342 103, 209 98))'))),            # 에이 적베
       (49, 'SUPPLY49',
        (ST_GeomFromText('POLYGON ((230 240, 230 251, 217 253, 217 279, 269 280, 266 239, 230 240))'))),          # 홀
       (50, 'SUPPLY50', (ST_GeomFromText('POLYGON ((176 300, 176 317, 198 317, 197 300, 176 300))'))),             # 달방, 달방위
       (51, 'SUPPLY51', (ST_GeomFromText('POLYGON ((248 196, 250 239, 267 238, 268 197, 248 196))'))),             # 짝방
       (52, 'SUPPLY52', (ST_GeomFromText('POLYGON ((183 316, 184 380, 195 382, 196 319, 183 316))'))),             # 반계
       (53, 'SUPPLY53', (ST_GeomFromText('POLYGON ((184 380, 185 393, 196 393, 196 381, 184 380))'))),             # 와리
       (54, 'SUPPLY54',
        (ST_GeomFromText('POLYGON ((216 265, 195 266, 192 296, 183 298, 182 267, 182 254, 214 253, 216 265))'))), # 기억자
       (55, 'SUPPLY55', (ST_GeomFromText('POLYGON ((176 210, 177 297, 182 299, 184 209, 176 210))'))),             # 난간
       (56, 'SUPPLY56', (ST_GeomFromText('POLYGON ((239 173, 241 194, 307 194, 305 173, 239 173))'))),             # 홀입구
       (57, 'SUPPLY57',
        (ST_GeomFromText('POLYGON ((178 198, 207 197, 208 205, 239 207, 239 197, 239 171, 218 171, 204 180, 177 184, 178 198))'))) # 역개 털리는 자리
