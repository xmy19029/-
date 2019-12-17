服务器地址：http://39.105.21.114:11451

测试：http://39.105.21.114:11451/greeting

http协议版本：1.1

### 注册登录相关

1.注册账号 [get]http://127.0.0.1:11451/api/v1/user/register?name={账号名}&pwd={密码}&role={角色}

账户名和密码即注册时的账户名和密码，密码在后台会经过加密

角色有两个可选项，为1表示学生身份，为2表示教师身份

2.登录 [post]http://127.0.0.1:11451/api/v1/user/login

Content-Type: application/json

{
	"username":{用户名},
	"password":{密码}
}

登录成功后，会在响应头中添加token字段，表示认证的身份信息，访问除登录注册之外的所有api，必须在请求头中添加字段token，并附上服务器提供的token

### 题库操作相关

1.添加试题 [post]http://127.0.0.1:11451/api/v1/problems/addOne

Content-Type: application/json

{
	"textMain":{字符串，题干},
	"textA":{字符串，选项A的描述文本},
	"textB":{字符串，选项B的描述文本},
	"textC":{字符串，选项C的描述文本},
	"textD":{字符串，选项D的描述文本},
	"A":{布尔值，A是否是正确答案},
	"B":{布尔值，B是否是正确答案},
	"C":{布尔值，C是否是正确答案},
	"D":{布尔值，D是否是正确答案}
}

2.获取所有试题 [get]http://127.0.0.1:11451/api/v1/problems/getAll

返回一个数组，表示所有试题的信息，数组中的每个元素表示一个试题，并附带tid字段，表示其在题库中的编号。

3.删除一道试题 [delete]http://127.0.0.1:11451/api/v1/problems/delOne?id={试题编号}

删除题库中对应标号的一道题

4.更新一道试题 [post]http://127.0.0.1:11451/api/v1/problems/updOne?id={试题编号}

更新对应题库中编号的一道题，需要在请求体中附带json，格式与添加时一致

5.获取一道试题 [get]http://127.0.0.1:11451/api/v1/problems/getOne?id={试题编号}

获取题库中对应编号的一道试题

### 试卷库操作相关

1.添加试卷 [post]http://127.0.0.1:11451/api/v1/exams/addOne

Content-Type: application/json

{
        "problemNum": {正整数，试题数量},
        "teacherId": {整数，教师工号},
        "usable": {布尔值，是否可用},
        "problemList": {题目列表，每道题目用其在题库中的id表示，id之间用逗号分隔开}
}

2.获取所有试卷 [get]http://127.0.0.1:11451/api/v1/exams/getAll

返回一个数组，表示所有试卷的信息，数组中的每个元素表示一个试卷，并附带eid字段，表示其在试卷库中的编号。

3.获取一道试卷 [get]http://127.0.0.1:11451/api/v1/exams/getOne?eid={试卷编号}

获取试卷库中对应编号的一套试卷

4.删除一套试卷 [delete]http://127.0.0.1:11451/api/v1/exams/delOne?eid={试卷编号}

删除试卷库中对应编号的试卷

5.获取试卷中的试题信息 [get]http://127.0.0.1:11451/api/v1/exams/getDetail?eid={试卷编号}

获取对应试卷编号中的所有试题的具体信息，以数组形式返回，数组中的每一项为对应试题

6.更新一套试卷 [post]http://127.0.0.1:11451/api/v1/exams/updOne?eid={试卷编号}

更新对应试卷中对应编号的一套试卷，需要在请求体中附带json，格式与添加时一致

### 提交相关

1.提交做题结果 [post]http://127.0.0.1:11451/api/v1/submit/addOne

Content-Type: application/json

{
        "username": {用户名},
        "eid": 试卷编号,
        "chosen":{字符串，提交的答案，按照试卷对应的试题顺序，每个试题对应四个整数，1表示选，0表示不选}
}

举例：

{
        "username": "sinemora6",
        "eid": 2,
        "chosen":"1,1,0,1,1,0,1,0"
}

表示试卷第一道题选择A、B、D，第二道题选择A、C

2.获取用户提交结果 [get]http://127.0.0.1:11451/api/v1/submit/getByUsername?username={用户名}

返回一个数组，数组的每一项的格式如下：

​    {

​        "submitId": 1,

​        "username": "sinemora6",

​        "timeStamp": "2019-12-15 21:42:49",（表示提交时间）

​        "eid": 2,

​        "score": 1,（表示得分，一道题1分）

​        "result": "1,0",（表示每道题正确性判断结果，1表示正确，0表示错误）

​        "chosen": "1,1,0,1,1,0,1,0"

​    }

### 学生信息操作相关

1.添加学生信息 [post]http://127.0.0.1:11451/api/v1/student/addOne

Content-Type: application/json

{
        "username": {字符串，用户名},
        "sid": {整形，学号},
        "name": {字符串，姓名},
        "academy": {字符串，学院},
        "major": {字符串，专业},
        "grade": {字符串，年级}
}

2.获取学生信息 [get]http://127.0.0.1:11451/api/v1/student/getOne?username={用户名}

根据用户名获取单个学生详细信息

3.更改学生信息 [post]http://127.0.0.1:11451/api/v1/student/updOne?username={用户名}

更新对应用户名的单个学生详细信息，需要在请求体中附带json，格式与添加时一致

### 教师信息操作相关

1.添加教师信息 [post]http://127.0.0.1:11451/api/v1/teacher/addOne

Content-Type: application/json

{
        "username": {字符串，用户名},
        "tid": {整形，工号},
        "name": {字符串，姓名},
        "academy": {字符串，学院},
        "major": {字符串，专业},
        "grade": {字符串，年级}
}

2.获取教师信息 [get]http://127.0.0.1:11451/api/v1/teacher/getOne?username={用户名}

根据用户名获取单个教师详细信息

3.更改教师信息 [post]http://127.0.0.1:11451/api/v1/teacher/updOne?username={用户名}

更新对应用户名的单个教师详细信息，需要在请求体中附带json，格式与添加时一致

### 教师和学生关系相关

学生和教师之间的关系是多对多的，用数据库将学生与教师之间的连边存储在服务器中

1.添加学生和老师之间的关系 [post]http://127.0.0.1:11451/api/v1/relations/addOne?student={学生用户名}&teacher={教师用户名}

2.删除学生和老师之间的关系 [delete]http://127.0.0.1:11451/api/v1/relations/delOne?student={学生用户名}&teacher={教师用户名}

3.获得与某学生直接相连的所有教师用户名 [get]http://127.0.0.1:11451/api/v1/relations/getTeacherToStudent?student={学生用户名}

返回教师用户名组成的字符数组

4.获得与某教师直接相连的所有学生用户名[get]http://127.0.0.1:11451/api/v1/relations/getStudentToTeacher?teacher={教师用户名}

返回学生用户名组成的字符数组

### 帖子相关

1.添加帖子 [post]http://127.0.0.1:11451/api/v1/posts/addOne

Content-Type: application/json

{
	"poster":{字符串，用户名},
	"text":{字符串，内容},
	"title":{字符串，题目}
}

2.删除帖子 [delete]http://127.0.0.1:11451/api/v1/posts/delOne?id={帖子编号}

3.获取所有帖子 [get]http://127.0.0.1:11451/api/v1/posts/getAll

4.获得单个帖子 [get]http://127.0.0.1:11451/api/v1/posts/getOne?id={帖子编号}

5.获取某个用户上传的所有帖子 [get]http://127.0.0.1:11451/api/v1/posts/getByUsername?username={用户名}

6.更新帖子文本 [post]http://127.0.0.1:11451/api/v1/posts/updOne?id={帖子编号}

直接把文本放进请求体里

### 回复相关

1.添加回复 [post]http://127.0.0.1:11451/api/v1/reply/addOne

Content-Type: application/json

{
	"postId":{整数，所属的帖子编号},
	"username":{字符串，上传回复者的用户名},
	"text":{字符串，回复内容},
	"replyTo":{整数，如果没有向另一个回复进行回复，该值设为-1，否则设为被回复的回复的编号}
}

2.删除回复 [delete]http://127.0.0.1:11451/api/v1/reply/delOne?id={回复编号}

3.获取单个回复 [get]http://127.0.0.1:11451/api/v1/reply/getOne?id={回复编号}

4.按帖子编号获取所有回复 [get]http://127.0.0.1:11451/api/v1/reply/getOne?id={所属帖子编号}

5.更新单个回复文本 [get]http://127.0.0.1:11451/api/v1/reply/updOne?id={回复编号}

文本直接放进请求体里
