#!/bin/bash

BASE="src/main/java/com/edu/LMS"

# Create package directories
mkdir -p $BASE/{config,common/{exception,response,util}}

mkdir -p $BASE/auth/{controller,service,dto,filter}
mkdir -p $BASE/user/{controller,service,repository,entity,dto}
mkdir -p $BASE/course/{controller,service,repository,entity,dto}
mkdir -p $BASE/enrollment/{controller,service,repository,entity,dto}
mkdir -p $BASE/quiz/{controller,service,repository,entity,dto}
mkdir -p $BASE/progress/{controller,service,repository,entity,dto}
mkdir -p $BASE/payment/{controller,service,repository,entity,dto}
mkdir -p $BASE/notification/{controller,service,repository,entity,dto}
mkdir -p $BASE/leaderboard/{controller,service}

# ---------------- CONFIG ----------------

touch $BASE/config/SecurityConfig.java
touch $BASE/config/JwtConfig.java
touch $BASE/config/RedisConfig.java
touch $BASE/config/CorsConfig.java
touch $BASE/config/SwaggerConfig.java

# ---------------- COMMON ----------------

touch $BASE/common/exception/GlobalExceptionHandler.java
touch $BASE/common/exception/ResourceNotFoundException.java
touch $BASE/common/exception/UnauthorizedException.java
touch $BASE/common/exception/BusinessException.java

touch $BASE/common/response/ApiResponse.java

touch $BASE/common/util/JwtUtil.java

# ---------------- AUTH ----------------

touch $BASE/auth/controller/AuthController.java
touch $BASE/auth/service/AuthService.java

touch $BASE/auth/dto/LoginRequest.java
touch $BASE/auth/dto/RegisterRequest.java
touch $BASE/auth/dto/AuthResponse.java

touch $BASE/auth/filter/JwtAuthFilter.java

# ---------------- USER ----------------

touch $BASE/user/controller/UserController.java
touch $BASE/user/service/UserService.java
touch $BASE/user/repository/UserRepository.java
touch $BASE/user/entity/User.java
touch $BASE/user/dto/UserDto.java

# ---------------- COURSE ----------------

touch $BASE/course/controller/CourseController.java
touch $BASE/course/service/CourseService.java

touch $BASE/course/repository/CourseRepository.java
touch $BASE/course/repository/ModuleRepository.java
touch $BASE/course/repository/LessonRepository.java

touch $BASE/course/entity/Course.java
touch $BASE/course/entity/CourseModule.java
touch $BASE/course/entity/Lesson.java

# ---------------- ENROLLMENT ----------------

touch $BASE/enrollment/controller/EnrollmentController.java
touch $BASE/enrollment/service/EnrollmentService.java
touch $BASE/enrollment/repository/EnrollmentRepository.java
touch $BASE/enrollment/entity/Enrollment.java

# ---------------- QUIZ ----------------

touch $BASE/quiz/controller/QuizController.java
touch $BASE/quiz/service/QuizService.java

touch $BASE/quiz/entity/Quiz.java
touch $BASE/quiz/entity/Question.java
touch $BASE/quiz/entity/QuizAttempt.java

# ---------------- PROGRESS ----------------

touch $BASE/progress/controller/ProgressController.java
touch $BASE/progress/service/ProgressService.java
touch $BASE/progress/repository/ProgressRepository.java
touch $BASE/progress/entity/LessonProgress.java

# ---------------- PAYMENT ----------------

touch $BASE/payment/controller/PaymentController.java
touch $BASE/payment/service/PaymentService.java
touch $BASE/payment/entity/Payment.java

# ---------------- NOTIFICATION ----------------

touch $BASE/notification/controller/NotificationController.java
touch $BASE/notification/service/NotificationService.java
touch $BASE/notification/entity/Notification.java

# ---------------- LEADERBOARD ----------------

touch $BASE/leaderboard/controller/LeaderboardController.java
touch $BASE/leaderboard/service/LeaderboardService.java

echo "Project structure created successfully."
