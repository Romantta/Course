```markdown
# 校园选课系统（单体版）

## 项目介绍

这是一个基于Spring Boot构建的校园选课系统，实现了完整的课程管理、学生管理和选课业务流程。系统采用RESTful API设计，使用内存存储作为数据持久化方案。

### 技术栈
- **后端框架**: Spring Boot 3.x
- **构建工具**: Maven
- **数据存储**: 内存存储
- **API测试**: Postman
- **开发语言**: Java

### 核心功能
- ✅ 课程管理（创建、查询、更新、删除）
- ✅ 学生管理（创建、查询、更新、删除）
- ✅ 选课管理（选课、退课、查询选课记录）
- ✅ 业务规则验证（容量限制、重复选课检查等）
- ✅ 统一异常处理
- ✅ 数据格式验证

## 项目结构

src/main/java/com/zjgsu/lyy/course/
├── CourseApplication.java          # 启动类
├── model/                          # 实体类
│   ├── ApiResponse.java           # 统一响应格式
│   ├── Course.java                # 课程实体
│   ├── Student.java               # 学生实体
│   ├── Enrollment.java            # 选课记录实体
│   ├── Instructor.java            # 教师实体
│   └── ScheduleSlot.java          # 课程时间安排
├── repository/                     # 数据访问层
│   ├── CourseRepository.java
│   ├── StudentRepository.java
│   └── EnrollmentRepository.java
├── service/                        # 业务逻辑层
│   ├── CourseService.java
│   ├── StudentService.java
│   └── EnrollmentService.java
├── controller/                     # 控制器层
│   ├── CourseController.java
│   ├── StudentController.java
│   ├── EnrollmentController.java
│   └── HomeController.java
└── exception/
    └── GlobalExceptionHandler.java # 全局异常处理
```

## API接口列表

### 首页
- **GET /** - 系统欢迎页面和接口概览

### 课程管理接口

| 方法 | 路径 | 描述 | 状态码 |
|------|------|------|--------|
| GET | `/api/courses` | 查询所有课程 | 200 |
| GET | `/api/courses/{id}` | 根据ID查询课程 | 200,404 |
| POST | `/api/courses` | 创建新课程 | 201,400 |
| PUT | `/api/courses/{id}` | 更新课程信息 | 200,400,404 |
| DELETE | `/api/courses/{id}` | 删除课程 | 200,404 |

### 学生管理接口

| 方法 | 路径 | 描述 | 状态码 |
|------|------|------|--------|
| GET | `/api/students` | 查询所有学生 | 200 |
| GET | `/api/students/{id}` | 根据ID查询学生 | 200,404 |
| POST | `/api/students` | 创建新学生 | 201,400 |
| PUT | `/api/students/{id}` | 更新学生信息 | 200,400,404 |
| DELETE | `/api/students/{id}` | 删除学生 | 200,400,404 |

### 选课管理接口

| 方法 | 路径 | 描述 | 状态码 |
|------|------|------|--------|
| GET | `/api/enrollments` | 查询所有选课记录 | 200 |
| POST | `/api/enrollments` | 学生选课 | 201,400 |
| DELETE | `/api/enrollments/{id}` | 取消选课 | 200,404 |
| GET | `/api/enrollments/course/{courseId}` | 按课程查询选课记录 | 200,404 |
| GET | `/api/enrollments/student/{studentId}` | 按学生查询选课记录 | 200,404 |

## 请求示例

### 创建课程
```http
POST http://localhost:8080/api/courses
Content-Type: application/json

{
    "code": "CS101",
    "title": "计算机科学导论",
    "instructor": {
        "id": "T001",
        "name": "张教授",
        "email": "zhang@example.zjgsu.cn"
    },
    "schedule": {
        "dayOfWeek": "MONDAY",
        "startTime": "08:00",
        "endTime": "10:00",
        "expectedAttendance": 50
    },
    "capacity": 60
}
```

### 创建学生
```http
POST http://localhost:8080/api/students
Content-Type: application/json

{
    "studentId": "S2024001",
    "name": "张三",
    "major": "计算机科学与技术",
    "grade": 2024,
    "email": "zhangsan@student.zjgsu.cn"
}
```

### 学生选课
```http
POST http://localhost:8080/api/enrollments
Content-Type: application/json

{
    "courseId": "课程UUID",
    "studentId": "S2024001"
}
```

## 统一响应格式

### 成功响应
```json
{
    "code": 200,
    "message": "Success",
    "data": { ... }
}
```

### 错误响应
```json
{
    "code": 400,
    "message": "错误描述",
    "data": null
}
```

## 运行说明

### 环境要求
- JDK 17+
- Maven 3.6+
- Spring Boot 3.x

### 启动步骤
1. 克隆项目到本地
2. 进入项目根目录
3. 使用Maven编译项目：
   ```bash
   mvn clean compile
   ```
4. 运行应用程序：
   ```bash
   mvn spring-boot:run
   ```
5. 应用程序将在 `http://localhost:8080` 启动

### 访问系统
打开浏览器访问 `http://localhost:8080/` 查看系统欢迎页面。

## 测试说明

### 测试环境
- **基础URL**: `http://localhost:8080`
- **测试工具**: Postman

### 测试场景

#### 场景1：完整的课程管理流程
1. 创建3门不同的课程
2. 查询所有课程，验证返回记录数
3. 根据ID查询特定课程
4. 更新课程信息
5. 删除课程
6. 验证删除结果

#### 场景2：选课业务流程
1. 创建容量为2的测试课程
2. 学生S001选课（成功）
3. 学生S002选课（成功）
4. 学生S003选课（容量已满）
5. 学生S001重复选课（重复错误）
6. 验证课程enrolled字段

#### 场景3：学生管理流程
1. 创建3个不同学号的学生
2. 查询所有学生
3. 根据ID查询学生
4. 更新学生信息
5. 不存在的学生选课测试
6. 删除有选课记录的学生（失败）
7. 删除无选课记录的学生（成功）

#### 场景4：错误处理
1. 查询不存在的资源
2. 创建时缺少必填字段
3. 使用重复的学号/课程代码
4. 邮箱格式验证
5. 课程容量验证

### 测试文档
详细的API测试用例和预期结果请参考项目中的 `api-test.md` 文件。

## 业务规则

1. **课程容量限制**: 选课人数不能超过课程容量
2. **重复选课检查**: 同一学生不能重复选择同一门课程
3. **数据验证**: 所有必填字段必须提供且格式正确
4. **级联保护**: 有选课记录的学生不能被删除
5. **数据一致性**: 选课/退课时自动更新课程enrolled字段

## 注意事项

- 系统使用内存存储，重启后数据将丢失
- 学生学号（studentId）必须全局唯一
- 邮箱格式必须符合标准规范
- 课程和邮箱不为空
